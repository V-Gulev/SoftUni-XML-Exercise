package org.example.exercises.services;

import org.example.exercises.dtos.PartDto;
import org.example.exercises.dtos.PartInputDto;
import org.example.exercises.dtos.PartRelationsDto;
import org.example.exercises.entities.Part;
import org.example.exercises.repositories.PartRepository;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

@Service
public class PartServiceImpl implements PartService {


    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final SupplierService supplierService;


    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, SupplierService supplierService) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.supplierService = supplierService;
    }


    @Override
    public PartDto create(PartInputDto inputDto, PartRelationsDto relationsDto) {
        Part part = modelMapper.map(inputDto, Part.class);
        part.setSupplier(supplierService.findById(relationsDto.getSupplierId()));

        partRepository.save(part);
        return modelMapper.map(part, PartDto.class);
    }

    @Override
    public Part getReferenceById(Long id) {
        return partRepository.findById(id).orElse(null);
    }
}
