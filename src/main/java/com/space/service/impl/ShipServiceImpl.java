package com.space.service.impl;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import com.space.service.ShipService;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ShipServiceImpl implements ShipService {

    private ShipRepository shipRepository;

    @Autowired
    public void setShipRepository(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    private Double ratingCalculator(Ship ship) {
        double rating;
        Double shipSpeed = ship.getSpeed();
        double shipCoeff = ship.getUsed() ? 0.5 : 1;

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(ship.getProdDate());
        int shipYear = calendar.get(Calendar.YEAR);

        rating = (80 * shipSpeed * shipCoeff) / (3019 - shipYear + 1);
        rating = DoubleRounder.round(rating, 2);

        return rating;
    }
    @Override
    public Page<Ship> gelAllShips(Specification<Ship> specification, Pageable sortedByName) {
        return shipRepository.findAll(specification, sortedByName);
    }

    @Override
    public List<Ship> gelAllShips(Specification<Ship> specification) {
        return shipRepository.findAll(specification);
    }

    @Override
    public Ship createShip(Ship ship) {
        if (ship.getUsed() == null) {
            ship.setUsed(false);
        }
        Double rating = ratingCalculator(ship);
        ship.setRating(rating);

        return shipRepository.saveAndFlush(ship);
    }

    @Override
    public Ship editShip(Long id, Ship ship) {

        Ship oldShip = shipRepository.findById(id).get();

        if (ship.getName() != null) {
            oldShip.setName(ship.getName());
        }
        if (ship.getPlanet() != null) {
            oldShip.setPlanet(ship.getPlanet());
        }
        if (ship.getShipType() != null) {
            oldShip.setShipType(ship.getShipType());
        }
        if (ship.getProdDate() != null) {
            oldShip.setProdDate(ship.getProdDate());
        }
        if (ship.getUsed() != null) {
            oldShip.setUsed(ship.getUsed());
        }
        if (ship.getSpeed() != null) {
            oldShip.setSpeed(ship.getSpeed());
        }
        if (ship.getCrewSize() != null) {
            oldShip.setCrewSize(ship.getCrewSize());
        }

        Double rating = ratingCalculator(oldShip);
        oldShip.setRating(rating);

        return shipRepository.saveAndFlush(oldShip);
    }

    @Override
    public void deleteShip(Long id) {
        if (!shipRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        shipRepository.deleteById(id);
    }

    @Override
    public Ship getShip(Long id) {
        if (!shipRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return shipRepository.findById(id).get();
    }

    @Override
    public Integer getCount() {
        return null;
    }

    @Override
    public boolean isID(Long ID) {
        return ID > 0;
    }

    @Override
    public boolean isExist(Long id) {
        return shipRepository.existsById(id);
    }


    @Override
    public Specification<Ship> filterByName(String name) {

        return (Specification<Ship>) (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(root.get("name"),"%" + name + "%");
    }

    @Override
    public Specification<Ship> filterByPlanet(String planet) {

        return (Specification<Ship>) (root, query, criteriaBuilder) ->
                planet == null ? null : criteriaBuilder.like(root.get("planet"),"%" + planet + "%");
    }

    @Override
    public Specification<Ship> filterByShipType(ShipType shipType) {

        return (Specification<Ship>) (root, query, criteriaBuilder) ->
                shipType == null ? null : criteriaBuilder.equal(root.get("shipType"), shipType);
    }

    @Override
    public Specification<Ship> filterByDate(Long after, Long before) {

        return (Specification<Ship>) (root, query, criteriaBuilder) ->
        {
            if(after==null&&before==null){
                return null;
            }
            if(after==null){
                Date beforeDate = new Date(before);
                return criteriaBuilder.lessThanOrEqualTo(root.get("prodDate"),beforeDate);
            }
            if(before==null){
                Date afterDate = new Date(after);
                return criteriaBuilder.greaterThanOrEqualTo(root.get("prodDate"),afterDate);
            }
            Date beforeDate = new Date(before);
            Date afterDate = new Date(after);
            return criteriaBuilder.between(root.get("prodDate"),afterDate,beforeDate);
        };
    }

    @Override
    public Specification<Ship> filterByUsage(Boolean isUsed) {

        return (Specification<Ship>) (root, query, criteriaBuilder) ->{
            if(isUsed==null){
                return null;
            }
            if(isUsed){
                return criteriaBuilder.isTrue(root.get("isUsed"));
            }
            return criteriaBuilder.isFalse(root.get("isUsed"));
        };
    }

    @Override
    public Specification<Ship> filterBySpeed(Double min, Double max) {

        return (Specification<Ship>) (root, query, criteriaBuilder) ->{
            if (min == null && max == null){
                return null;
            }
            if (min == null){
                return criteriaBuilder.lessThanOrEqualTo(root.get("speed"), max);
            }
            if (max == null){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("speed"), min);
            }
            return criteriaBuilder.between(root.get("speed"), min, max);
        };
    }

    @Override
    public Specification<Ship> filterByCrewSize(Integer min, Integer max) {

        return (Specification<Ship>) (root, query, criteriaBuilder) ->{
            if (min == null && max == null){
                return null;
            }
            if (min == null){
                return criteriaBuilder.lessThanOrEqualTo(root.get("crewSize"), max);
            }
            if (max == null){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("crewSize"), min);
            }
            return criteriaBuilder.between(root.get("crewSize"), min, max);
        };
    }

    @Override
    public Specification<Ship> filterByRating(Double min, Double max) {
        return (Specification<Ship>) (root, query, criteriaBuilder) ->{
            if (min == null && max == null){
                return null;
            }
            if (min == null){
                return criteriaBuilder.lessThanOrEqualTo(root.get("rating"), max);
            }
            if (max == null){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), min);
            }
            return criteriaBuilder.between(root.get("rating"), min, max);
        };
    }
}
