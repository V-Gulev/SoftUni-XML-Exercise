package org.example.exercises.services;

import org.example.exercises.dtos.SupplierDto;
import org.example.exercises.dtos.SupplierInputDto;
import org.example.exercises.dtos.SupplierReportDto;
import org.example.exercises.entities.Supplier;
import org.example.exercises.repositories.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {


    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public SupplierDto create(SupplierInputDto supplierInputDto) {
        Supplier supplier = modelMapper.map(supplierInputDto, Supplier.class);
        supplierRepository.save(supplier);
        return modelMapper.map(supplier, SupplierDto.class);
    }

    @Override
    public Supplier findById(Long id) {
        return supplierRepository.findById(id).orElse(null);
    }

    @Override
    public List<SupplierReportDto> generateReport(boolean isImporter) {
        return supplierRepository.generateReport(isImporter);
    }
}
