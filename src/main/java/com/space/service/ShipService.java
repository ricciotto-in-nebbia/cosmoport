package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ShipService {
    //1. получать список всех существующих кораблей;
    //   6. получать отфильтрованный список кораблей в соответствии с переданными фильтрами;
    Page<Ship> gelAllShips(Specification<Ship> specification, Pageable sortedByName);

    List<Ship> gelAllShips(Specification<Ship> specification);

    //2. создавать новый корабль;
    Ship createShip(Ship ship);

    //3. редактировать характеристики существующего корабля;
    Ship editShip(Long id, Ship ship);

    //4. удалять корабль;
    void deleteShip(Long id);

    //5. получать корабль по id;
    Ship getShip(Long id);

    //    7. получать количество кораблей, которые соответствуют фильтрам.
    Integer getCount();

    boolean isID(Long id);

    boolean isExist(Long id);

    Specification<Ship> filterByPlanet(String planet);

    Specification<Ship> filterByName(String name);

    Specification<Ship> filterByShipType(ShipType shipType);

    Specification<Ship> filterByDate(Long after, Long before);

    Specification<Ship> filterByUsage(Boolean isUsed);

    Specification<Ship> filterBySpeed(Double min, Double max);

    Specification<Ship> filterByCrewSize(Integer min, Integer max);

    Specification<Ship> filterByRating(Double min, Double max);
}