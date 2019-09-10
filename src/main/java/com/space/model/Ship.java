package com.space.model;

import com.space.validator.DateValidator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

/*
 * “name”:[String],
 * “planet”:[String],
 * “shipType”:[ShipType],
 * “prodDate”:[Long],
 * “isUsed”:[Boolean], --optional, default=false
 * “speed”:[Double],
 * “crewSize”:[Integer] }
 *
 * Мы не можем создать корабль, если:
 * - указаны не все параметры из Data Params (кроме isUsed);
 * - длина значения параметра “name” или “planet” превышает размер соответствующего поля в БД (50 символов);
 * - значение параметра “name” или “planet” пустая строка;
 * - скорость или размер команды находятся вне заданных пределов;
 * - “prodDate”:[Long] < 0;
 * - год производства находятся вне заданных пределов.
 * В случае всего вышеперечисленного необходимо ответить ошибкой с кодом 400.
 */

@Entity
@Table(name = "ship")
public class Ship {

//    @Column - принадлежность переменных к определенному полю БД
//    если имя столбца таблицы совпадает с именем переменной - данные в скобках можно не указывать

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Ship name can't be null.")
    @NotEmpty(message = "Ship name can't be empty.")
    @Size(max = 50)
    private String name;

    @Column(name = "planet")
    @NotNull(message = "Planet name can't be null.")
    @NotEmpty(message = "Planet name can't be empty.")
    @Size(max = 50)
    private String planet;

    @Column(name = "shipType")
    @NotNull
    @Enumerated(EnumType.STRING)
    private ShipType shipType;

    @Column(name = "prodDate")
    @NotNull
    @DateValidator
    private Date prodDate;

    @Column(name = "isUsed")
    private Boolean isUsed; //--optional, default=false (как у переменной класса это значение по умолчанию)

    @Column(name = "speed")
    @NotNull
    @DecimalMin("0.01")
    @DecimalMax("0.99")
    private Double speed;

    @Column(name = "crewSize")
    @NotNull
    @Min(1)
    @Max(9999)
    private Integer crewSize;

    @Column(name = "rating")
    private Double rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
