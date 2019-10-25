package com.memento.service.impl;

import com.memento.model.EstateType;
import com.memento.repository.EstateTypeRepository;
import com.memento.service.EstateTypeService;
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
public class EstateTypeServiceImpl implements EstateTypeService {

    private final EstateTypeRepository estateTypeRepository;

    @Autowired
    public EstateTypeServiceImpl(final EstateTypeRepository estateTypeRepository) {
        this.estateTypeRepository = estateTypeRepository;
    }

    @Override
    public Set<EstateType> getAll() {
        return Set.copyOf(estateTypeRepository.findAll());
    }

    @Override
    public EstateType save(EstateType estateType) {
        return estateTypeRepository.save(estateType);
    }

    @Override
    public EstateType update(EstateType estateType) {
        final EstateType oldEstateType = estateTypeRepository.findById(estateType.getId()).orElseThrow(ResourceNotFoundException::new);

        final EstateType newEstateType = EstateType.builder()
                .id(oldEstateType.getId())
                .type(estateType.getType())
                .estates(oldEstateType.getEstates())
                .build();

        return estateTypeRepository.save(newEstateType);
    }
}
