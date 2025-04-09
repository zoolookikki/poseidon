package com.nnk.springboot.service.rating;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.rating.RatingCreateRequestDto;
import com.nnk.springboot.dto.rating.RatingDto;
import com.nnk.springboot.dto.rating.RatingUpdateRequestDto;
import com.nnk.springboot.service.ICrudService;

public interface IRatingService extends ICrudService<Rating, RatingDto, RatingCreateRequestDto, RatingUpdateRequestDto> {}
 