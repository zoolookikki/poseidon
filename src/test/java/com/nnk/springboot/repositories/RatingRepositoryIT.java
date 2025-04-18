package com.nnk.springboot.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.nnk.springboot.domain.Rating;

public class RatingRepositoryIT extends AbstractRepositoryIT<Rating, Integer> {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    protected Rating createEntity() {
        return new Rating(null, "Moodys Rating", "Sand PRating", "Fitch Rating", 10);
    }
    
    @Override
    protected List<Rating> getMoreEntities() {
        return List.of(
            new Rating(null, "Rating 1", "Rating 2", "Rating 3", 123)
        );
    }

    @Override
    protected JpaRepository<Rating, Integer> getRepository() {
        return ratingRepository;
    }

    @Override
    protected Integer getId(Rating rating) {
        return rating.getId();
    }

    @Override
    protected void verifyCreate(Rating rating) {
        assertThat(rating.getMoodysRating()).isEqualTo("Moodys Rating");
        assertThat(rating.getSandPRating()).isEqualTo("Sand PRating");
        assertThat(rating.getFitchRating()).isEqualTo("Fitch Rating");
        assertThat(rating.getOrderNumber()).isEqualTo(10);
    }

    @Override
    protected void updateEntity(Rating rating) {
        rating.setMoodysRating("Moodys Rating updated");
        rating.setSandPRating("Sand PRating updated");
        rating.setFitchRating("Fitch Rating updated");
        rating.setOrderNumber(1);
    }

    @Override
    protected void verifyUpdate(Rating rating) {
        assertThat(rating.getMoodysRating()).isEqualTo("Moodys Rating updated");
        assertThat(rating.getSandPRating()).isEqualTo("Sand PRating updated");
        assertThat(rating.getFitchRating()).isEqualTo("Fitch Rating updated");
        assertThat(rating.getOrderNumber()).isEqualTo(1);
    }
}
