package com.memento.web;

import com.memento.model.Neighborhood;
import com.memento.service.NeighborhoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/neighborhood")
public class NeighborhoodController {

    private final NeighborhoodService neighborhoodService;

    @Autowired
    public NeighborhoodController(final NeighborhoodService neighborhoodService) {
        this.neighborhoodService = neighborhoodService;
    }

    @GetMapping(value = "/city/name/{cityName}")
    public ResponseEntity<List<Neighborhood>> findAllByCityName(@PathVariable(value = "cityName") String cityName) {
        return ResponseEntity.ok(neighborhoodService.findAllByCityName(cityName));
    }

}
