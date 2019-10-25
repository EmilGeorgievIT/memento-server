package com.memento.web.endpoint.api;

import com.memento.model.Neighborhood;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(value = "Neighborhood API")
public interface NeighborhoodApi {

    @ApiOperation(value = "Fetch all neighborhoods by city name", response = List.class)
    @ApiParam(value = "The city name for which you want to take all the neighborhoods", required = true)
    ResponseEntity<List<Neighborhood>> findAllByCityName(String cityName);
}
