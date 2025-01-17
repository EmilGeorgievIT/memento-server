package com.memento.service;

import com.memento.model.City;

import java.util.Set;

public interface CityService {

    Set<City> getAll();

    City save(City city);

    City update(City city);
}
