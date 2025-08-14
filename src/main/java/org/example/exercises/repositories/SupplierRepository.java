package org.example.exercises.repositories;

import org.example.exercises.dtos.SupplierReportDto;
import org.example.exercises.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    @Query("SELECT s.id, s.name, size(s.parts) FROM Supplier AS s WHERE s.isImporter = :isImporter")
    List<SupplierReportDto> generateReport(boolean isImporter);
}
