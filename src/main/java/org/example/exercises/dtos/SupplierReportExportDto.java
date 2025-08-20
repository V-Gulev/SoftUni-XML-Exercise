package org.example.exercises.dtos;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(jakarta.xml.bind.annotation.XmlAccessType.FIELD)
public class SupplierReportExportDto {

    @XmlElement(name = "supplier")
    private final List<SupplierReportDto> suppliers;

    public SupplierReportExportDto() {
        this.suppliers = new ArrayList<>();
    }

    public SupplierReportExportDto(List<SupplierReportDto> suppliers) {
        this.suppliers = suppliers;
    }

    public List<SupplierReportDto> getSuppliers() {
        return suppliers;
    }
}
