package com.memento.web.endpoint.api;

import com.memento.model.City;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.Set;

@Api(value = "City API")
public interface CityApi {

    @ApiOperation(value = "Fetch all cities", response = Set.class)
    ResponseEntity<Set<City>> getAll();

    @ApiOperation(value = "Save city", response = City.class)
    ResponseEntity<City> save(City city);

    @ApiOperation(value = "Update city", response = City.class)
    ResponseEntity<City> update(City city);
}
