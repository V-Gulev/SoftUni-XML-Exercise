package org.example.exercises;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.example.exercises.dtos.*;
import org.example.exercises.entities.Part;
import org.example.exercises.services.CarService;
import org.example.exercises.services.PartService;
import org.example.exercises.services.SupplierService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class Runner implements CommandLineRunner {


    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;

    public Runner(SupplierService supplierService, PartService partService, CarService carService) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
    }

    @Override
    public void run(String... args) throws Exception {
        JAXBContext jaxbImportContext = JAXBContext.newInstance(SuppliersImportDto.class, PartImportDto.class, CarsImportDto.class);
        Unmarshaller unmarshaller = jaxbImportContext.createUnmarshaller();

        JAXBContext jaxbExportContext = JAXBContext.newInstance(SupplierReportExportDto.class, CarExtendedExportDto.class);
        Marshaller marshaller = jaxbExportContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        List<SupplierDto> suppliers = seedSuppliers(unmarshaller);
        List<PartDto> parts = seedParts(suppliers, unmarshaller);
        seedCars(unmarshaller, parts);

        System.out.println("Seeding finished");

        List<SupplierReportDto> report = supplierService.generateReport(false);
        SupplierReportExportDto exportDto = new SupplierReportExportDto(report);



        marshaller.marshal(exportDto, System.out);

        List<CarExtendedDto> cars = carService.getExtended();
        CarExtendedExportDto carExtendedExportDto = new CarExtendedExportDto(cars);
        marshaller.marshal(carExtendedExportDto, System.out);
    }

    private List<SupplierDto> seedSuppliers(Unmarshaller unmarshaller) throws JAXBException, IOException {
        SuppliersImportDto importDto;

        try (InputStream inputStream = getInputStream("XMLs/suppliers.xml")) {
            importDto = (SuppliersImportDto) unmarshaller.unmarshal(inputStream);
        }

        List<SupplierDto> result = new ArrayList<>();
        for (SupplierInputDto supplierInputDto : importDto.getSuppliers()) {
            SupplierDto supplierDto = supplierService.create(supplierInputDto);
            result.add(supplierDto);
        }

        return result;
    }

    private List<PartDto> seedParts(List<SupplierDto> suppliers, Unmarshaller unmarshaller) throws JAXBException, IOException {

        PartImportDto partImportDto;
        try (InputStream inputStream = getInputStream("XMLs/parts.xml")){
            partImportDto = (PartImportDto) unmarshaller.unmarshal(inputStream);
        }

        List<PartDto> result = new ArrayList<>();
        for (PartInputDto partInputDto : partImportDto.getParts()) {
            int randomSupplierPosition = ThreadLocalRandom.current().nextInt(suppliers.size());
            SupplierDto supplierDto = suppliers.get(randomSupplierPosition);

            PartRelationsDto relationsDto = new PartRelationsDto(supplierDto.getId());
            PartDto partDto = partService.create(partInputDto, relationsDto);
            result.add(partDto);
        }

        return result;
    }

    private void seedCars(Unmarshaller unmarshaller, List<PartDto> parts) throws JAXBException, IOException {
        CarsImportDto carsImportDto;
        try (InputStream inputStream = getInputStream("XMLs/cars.xml")){
            carsImportDto = (CarsImportDto) unmarshaller.unmarshal(inputStream);
        }

        for (CarInputDto carInputDto : carsImportDto.getCars()) {
            Set<Long> partIds = new HashSet<>();
            int randomPartsCount = ThreadLocalRandom.current().nextInt(1, 3);

            for (int i = 0; i < randomPartsCount; i++) {
                int randomPartPosition = ThreadLocalRandom.current().nextInt(parts.size());
                PartDto partDto = parts.get(randomPartPosition);
                partIds.add(partDto.getId());
            }

            CarRelationsDto relationsDto = new CarRelationsDto(partIds);
            carService.create(carInputDto, relationsDto);
        }
    }

    private static InputStream getInputStream(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return resource.getInputStream();
    }
}
