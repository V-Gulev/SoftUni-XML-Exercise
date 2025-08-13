package org.example.exercises.repositories;

import org.example.exercises.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c FROM Car AS c LEFT JOIN FETCH c.parts")
    List<Car> findAllWithParts();
}
