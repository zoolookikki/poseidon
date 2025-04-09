package com.nnk.springboot.service.rating;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.rating.RatingCreateRequestDto;
import com.nnk.springboot.dto.rating.RatingDto;
import com.nnk.springboot.dto.rating.RatingUpdateRequestDto;
import com.nnk.springboot.mapper.RatingMapper;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.AbstractCrudService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RatingServiceImpl
        extends AbstractCrudService<Rating, RatingDto, RatingCreateRequestDto, RatingUpdateRequestDto>
        implements IRatingService {

    public RatingServiceImpl(
            RatingRepository ratingRepository,
            RatingMapper ratingMapper) {
        super(ratingRepository, ratingMapper);
    }
    
}
