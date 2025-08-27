package org.example.exercises.dtos;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "car")
@XmlAccessorType(jakarta.xml.bind.annotation.XmlAccessType.FIELD)
public class CarExtendedExportDto {
    @XmlElement(name = "car")
    private final List<CarExtendedDto> cars;

    public CarExtendedExportDto(List<CarExtendedDto> cars) {
        this.cars = cars;
    }

    public CarExtendedExportDto() {
        this.cars = new ArrayList<>();
    }
}
