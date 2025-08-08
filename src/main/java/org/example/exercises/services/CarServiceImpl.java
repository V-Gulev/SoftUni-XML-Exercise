package org.example.exercises.services;

import org.example.exercises.dtos.CarExtendedDto;
import org.example.exercises.dtos.CarInputDto;
import org.example.exercises.dtos.CarRelationsDto;
import org.example.exercises.entities.Car;
import org.example.exercises.entities.Part;
import org.example.exercises.repositories.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final PartService partService;

    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, PartService partService) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.partService = partService;
    }


    @Override
    public void create(CarInputDto carInputDto, CarRelationsDto relationsDto) {
        Car car = modelMapper.map(carInputDto, Car.class);

        Set<Part> parts = new HashSet<>();

        for (Long partId : relationsDto.getPartIds()) {
            Part part = partService.getReferenceById(partId);
            parts.add(part);
        }

        car.setParts(parts);

        carRepository.save(car);
    }

    @Override
    public List<CarExtendedDto> getExtended() {
        List<Car> cars = carRepository.findAllWithParts();
        List<CarExtendedDto> result = new ArrayList<>();

        for (Car car : cars) {
            CarExtendedDto carExtendedDto = modelMapper.map(car, CarExtendedDto.class);
            result.add(carExtendedDto);
        }

        return result;
    }
}
