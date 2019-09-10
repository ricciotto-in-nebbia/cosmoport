package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/rest")
public class ShipController {

    private ShipService service;

    @Autowired
    public void setService(ShipService service) {
        this.service = service;
    }

    //1. получать список всех существующих кораблей;
    //6. получать отфильтрованный список кораблей в соответствии с переданными фильтрами;
    @GetMapping("/ships")
    @ResponseStatus(HttpStatus.OK)
    public List<Ship> getAllShips(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "planet", required = false) String planet,
                                  @RequestParam(value = "shipType", required = false) ShipType shipType,
                                  @RequestParam(value = "after", required = false) Long after,
                                  @RequestParam(value = "before", required = false) Long before,
                                  @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                  @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                  @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                  @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                  @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                  @RequestParam(value = "minRating", required = false) Double minRating,
                                  @RequestParam(value = "maxRating", required = false) Double maxRating,
                                  @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order,
                                  @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {


        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));

        return service.gelAllShips(
                Specification.where(service.filterByName(name)
                        .and(service.filterByPlanet(planet)))
                        .and(service.filterByShipType(shipType))
                        .and(service.filterByDate(after, before))
                        .and(service.filterByUsage(isUsed))
                        .and(service.filterBySpeed(minSpeed, maxSpeed))
                        .and(service.filterByCrewSize(minCrewSize, maxCrewSize))
                        .and(service.filterByRating(minRating, maxRating)), pageable)
                .getContent();
    }

    //2. создавать новый корабль;
    @PostMapping("/ships")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Ship addShip(@RequestBody @Valid Ship ship) {
        return service.createShip(ship);
    }

    //3. редактировать характеристики существующего корабля;
    @PostMapping("/ships/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Ship updateShip(@PathVariable(value = "id") Long id, @RequestBody Ship ship) {
        Ship oldShip;

       if (!service.isID(id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The ID " + id + " is not valid.");
        }

        if (!service.isExist(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The ID " + id + " does not exist.");
        }

        try{
            oldShip = service.editShip(id, ship);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return oldShip;
    }


    //4. удалять корабль;
    @DeleteMapping("/ships/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteShip(@PathVariable(value = "id") Long id) {
        if (!service.isID(id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            service.deleteShip(id);
        }
    }

    //5. получать корабль по id;
    @GetMapping("/ships/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Ship getShip(@PathVariable(value = "id") Long id) {
        if (!service.isID(id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return service.getShip(id);
    }

    //* 7. получать количество кораблей, которые соответствуют фильтрам.
    @GetMapping("/ships/count")
    @ResponseStatus(HttpStatus.OK)
    public Integer getCount(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "planet", required = false) String planet,
                            @RequestParam(value = "shipType", required = false) ShipType shipType,
                            @RequestParam(value = "after", required = false) Long after,
                            @RequestParam(value = "before", required = false) Long before,
                            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                            @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                            @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                            @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                            @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                            @RequestParam(value = "minRating", required = false) Double minRating,
                            @RequestParam(value = "maxRating", required = false) Double maxRating) {

        return service.gelAllShips(
                Specification.where(service.filterByName(name)
                        .and(service.filterByPlanet(planet)))
                        .and(service.filterByShipType(shipType))
                        .and(service.filterByDate(after, before))
                        .and(service.filterByUsage(isUsed))
                        .and(service.filterBySpeed(minSpeed, maxSpeed))
                        .and(service.filterByCrewSize(minCrewSize, maxCrewSize))
                        .and(service.filterByRating(minRating, maxRating)),Pageable.unpaged()).getNumberOfElements();
    }
}
