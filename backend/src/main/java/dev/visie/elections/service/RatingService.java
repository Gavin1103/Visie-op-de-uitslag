package dev.visie.elections.service;

import dev.visie.elections.dto.rating.AmountOfRatingsDTO;
import dev.visie.elections.dto.rating.RatingDTO;
import dev.visie.elections.model.User;
import dev.visie.elections.model.base.Rating;
import dev.visie.elections.repository.RatingRepository;
import dev.visie.elections.service.models.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public abstract class RatingService<RatingType extends Rating, RatingTypeRepository extends RatingRepository<RatingType>> {

    private final RatingTypeRepository ratingTypeRepository;
    private final UserService userService;

    protected RatingService(RatingTypeRepository ratingTypeRepository, UserService userService) {
        this.ratingTypeRepository = ratingTypeRepository;
        this.userService = userService;
    }

    public RatingType createOrUpdateRating(RatingDTO ratingDTO, String userEmail) {

        User user = userService.getUserByEmail(userEmail);
        Object relatedEntity = getRelatedEntity(ratingDTO.getId());
        RatingType existingRating = getExistingRating(relatedEntity, user);

        if (existingRating != null) {
            return updateRating(existingRating, ratingDTO);
        }

        return createRating(ratingDTO, user, relatedEntity);
    }

    private RatingType updateRating(RatingType existingRating, RatingDTO ratingDTO) {

        if (existingRating.getRating().equals(ratingDTO.getRating())) {
            ratingTypeRepository.delete(existingRating);
            return null;
        }

        existingRating.setRating(ratingDTO.getRating());
        return ratingTypeRepository.save(existingRating);
    }

    private RatingType createRating(RatingDTO ratingDTO, User user, Object relatedEntity) {

        RatingType newRating = createNewRatingInstance();
        setRelatedEntity(newRating, relatedEntity);
        newRating.setUser(user);
        newRating.setRating(ratingDTO.getRating());

        return ratingTypeRepository.save(newRating);
    }

    protected abstract Object getRelatedEntity(Long id);

    protected abstract RatingType getExistingRating(Object relatedEntity, User user);

    protected abstract RatingType createNewRatingInstance();

    protected abstract void setRelatedEntity(RatingType rating, Object relatedEntity);

    public abstract ResponseEntity<AmountOfRatingsDTO> getAmountOfRatings(Long ratingTypeId);
}
