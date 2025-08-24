package org.example.exercises.dtos;

import java.util.List;
import java.util.Set;

public class CarRelationsDto {
    private final Set<Long> partIds;

    public CarRelationsDto(Set<Long> partIds) {
        this.partIds = partIds;
    }

    public Set<Long> getPartIds() {
        return partIds;
    }



}
