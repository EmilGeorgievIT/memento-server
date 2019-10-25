package com.memento.web.endpoint.api;

import com.memento.model.Floor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;

import java.util.Set;

@Api(value = "Floor API")
public interface FloorApi {

    @ApiOperation(value = "Fetch all floors", response = Set.class)
    ResponseEntity<Set<Floor>> getAll();

    @ApiOperation(value = "Find the floor by number", response = Floor.class)
    @ApiParam(value = "The number of the floor", required = true)
    ResponseEntity<Floor> findByNumber(Integer number);

    @ApiOperation(value = "Save floor", response = Floor.class)
    ResponseEntity<Floor> save(Floor floor);
}
