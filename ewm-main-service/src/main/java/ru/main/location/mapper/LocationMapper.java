package ru.main.location.mapper;

import lombok.experimental.UtilityClass;
import ru.main.location.dto.LocationDto;
import ru.main.location.model.Location;

@UtilityClass
public class LocationMapper {
    public static Location toEntity(LocationDto locationDto) {
        return Location.builder()
                .lon(locationDto.getLon())
                .lat(locationDto.getLat())
                .build();
    }

    public static LocationDto toDto(Location location) {
        return LocationDto.builder()
                .lon(location.getLon())
                .lat(location.getLat())
                .build();
    }
}
