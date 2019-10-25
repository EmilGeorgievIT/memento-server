package com.memento.web.endpoint;

import com.memento.model.City;
import com.memento.service.CityService;
import com.memento.web.endpoint.api.CityApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/city", produces = { MediaType.APPLICATION_JSON_VALUE })
public class CityApiController implements CityApi {

    private final CityService cityService;

    @Autowired
    public CityApiController(final CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    @GetMapping(value = "/all")
    public ResponseEntity<Set<City>> getAll() {
        return ResponseEntity.ok(cityService.getAll());
    }

    @Override
    @PostMapping(value = "/save")
    public ResponseEntity<City> save(@Valid @RequestBody final City city) {
        return ResponseEntity.ok(cityService.save(city));
    }

    @Override
    @PutMapping(value = "/update")
    public ResponseEntity<City> update(@Valid @RequestBody final City city) {
        return ResponseEntity.ok(cityService.update(city));
    }
}
