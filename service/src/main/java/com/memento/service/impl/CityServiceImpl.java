package com.memento.service.impl;

import com.memento.model.City;
import com.memento.repository.CityRepository;
import com.memento.service.CityService;
import com.memento.shared.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Primary
@Slf4j
@Transactional
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(final CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Set<City> getAll() {
        return Set.copyOf(cityRepository.findAll());
    }

    @Override
    public City save(City city) {
        return cityRepository.save(city);
    }

    @Override
    public City update(City city) {
        final City oldCity = cityRepository.findById(city.getId()).orElseThrow(ResourceNotFoundException::new);
        oldCity.getNeighborhoods().clear();

        final City newCity = City.builder()
                .id(oldCity.getId())
                .name(city.getName())
                .neighborhoods(city.getNeighborhoods())
                .build();

        return cityRepository.save(newCity);
    }
}
