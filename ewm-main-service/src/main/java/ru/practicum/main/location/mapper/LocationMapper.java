package ru.practicum.main.location.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.location.dto.LocationDto;
import ru.practicum.main.location.model.Location;

@UtilityClass
public class LocationMapper {
    public static Location toLocation(LocationDto locationDto) {
        return Location.builder()
                .lon(locationDto.getLon())
                .lat(locationDto.getLat())
                .build();
    }

    public static LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .lon(location.getLon())
                .lat(location.getLat())
                .build();
    }
}
