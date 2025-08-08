package org.example.exercises.services;

import jakarta.validation.Valid;
import org.example.exercises.dtos.CarExtendedDto;
import org.example.exercises.dtos.CarInputDto;
import org.example.exercises.dtos.CarRelationsDto;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface CarService {
    void create(@Valid CarInputDto carInputDto, @Valid CarRelationsDto relationsDto);

    List<CarExtendedDto> getExtended();
}
