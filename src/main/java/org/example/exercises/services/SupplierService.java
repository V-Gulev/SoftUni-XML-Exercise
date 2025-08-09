package org.example.exercises.services;

import jakarta.validation.Valid;
import org.example.exercises.dtos.SupplierDto;
import org.example.exercises.dtos.SupplierInputDto;
import org.example.exercises.dtos.SupplierReportDto;
import org.example.exercises.entities.Supplier;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface SupplierService {
    SupplierDto create(@Valid SupplierInputDto supplierInputDto);
    Supplier findById(Long id);
    List<SupplierReportDto> generateReport(boolean isImporter);
}
