package com.nnk.springboot.service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.rating.RatingCreateRequestDto;
import com.nnk.springboot.dto.rating.RatingDto;
import com.nnk.springboot.dto.rating.RatingUpdateRequestDto;
import com.nnk.springboot.mapper.RatingMapper;
import com.nnk.springboot.mapper.IMapper;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.rating.RatingServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RatingServiceImplTest extends AbstractCrudServiceTest<Rating, RatingDto, RatingCreateRequestDto, RatingUpdateRequestDto> {

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RatingMapper ratingMapper;

    private Rating rating1, rating2;
    private RatingDto ratingDto1, ratingDto2;
    private RatingCreateRequestDto ratingCreateDto1;
    private RatingUpdateRequestDto ratingUpdateDto1;
    private Rating ratingUpdated1;
    private RatingDto ratingDtoUpdated1;

    @BeforeEach
    void setUp() {
        rating1 = new Rating(1,"Moodys Rating 1", "Sand PRating 1", "Fitch Rating 1", 10);
        rating2 = new Rating(2, "Moodys Rating 2", "Sand PRating 2", "Fitch Rating 2", 20);
        ratingDto1 = new RatingDto(1,"Moodys Rating 1", "Sand PRating 1", "Fitch Rating 1", 10);
        ratingDto2 = new RatingDto(2, "Moodys Rating 2", "Sand PRating 2", "Fitch Rating 2", 20);
        ratingCreateDto1 = new RatingCreateRequestDto();
        ratingCreateDto1.setMoodysRating("Moodys Rating 1");
        ratingCreateDto1.setSandPRating("Sand PRating 1");
        ratingCreateDto1.setFitchRating("Fitch Rating 1");
        ratingCreateDto1.setOrderNumber(10);
        ratingUpdateDto1 = new RatingUpdateRequestDto();
        ratingUpdateDto1.setMoodysRating("Moodys Rating 1 updated");
        ratingUpdateDto1.setSandPRating("Sand PRating 1 updated");
        ratingUpdateDto1.setFitchRating("Fitch Rating 1 updated");
        ratingUpdateDto1.setOrderNumber(11);
        ratingUpdated1 = new Rating(1,"Moodys Rating 1 updated", "Sand PRating 1 updated", "Fitch Rating 1 updated", 11); 
        ratingDtoUpdated1 = new RatingDto(1, "Moodys Rating 1 updated", "Sand PRating 1 updated", "Fitch Rating 1 updated", 11); 
    }

    @Override
    protected Rating getEntity() {
        return rating1;
    }
    
    @Override
    protected List<Rating> getEntities() {
        return List.of(rating1, rating2);
    }

    @Override
    protected List<RatingDto> getDtos() {
        return List.of(ratingDto1, ratingDto2);
    }

    @Override
    protected AbstractCrudService<Rating, RatingDto, RatingCreateRequestDto, RatingUpdateRequestDto> getService() {
        return ratingService;
    }

    @Override
    protected JpaRepository<Rating, Integer> getRepository() {
        return ratingRepository;
    }

    @Override
    protected IMapper<Rating, RatingDto, RatingCreateRequestDto, RatingUpdateRequestDto> getMapper() {
        return ratingMapper;
    }

    @Override
    protected RatingCreateRequestDto getCreateDto() {
        return ratingCreateDto1;
    }
    
    @Override
    protected RatingDto getExpectedCreatedDto() {
        return ratingDto1;
    }
    
    @Override
    protected RatingUpdateRequestDto getUpdateDto() {
        return ratingUpdateDto1;
    }
    @Override
    protected Rating getUpdatedEntity() {
        return ratingUpdated1;
    }

    @Override
    protected RatingDto getExpectedUpdatedDto() {
        return ratingDtoUpdated1;
    }
}
