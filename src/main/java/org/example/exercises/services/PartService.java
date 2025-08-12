package org.example.exercises.services;

import jakarta.validation.Valid;
import org.example.exercises.dtos.PartDto;
import org.example.exercises.dtos.PartInputDto;
import org.example.exercises.dtos.PartRelationsDto;
import org.example.exercises.entities.Part;
import org.springframework.validation.annotation.Validated;

@Validated
public interface PartService {
    PartDto create(@Valid PartInputDto inputDto, @Valid PartRelationsDto relationsDto);
    Part getReferenceById(Long id);
}
