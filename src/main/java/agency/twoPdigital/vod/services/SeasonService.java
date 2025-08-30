package agency.twoPdigital.vod.services;

import agency.twoPdigital.vod.entities.SeasonEntity;
import agency.twoPdigital.vod.excpetions.CreateSeasonException;
import agency.twoPdigital.vod.excpetions.NotFoundException;
import agency.twoPdigital.vod.excpetions.UpdateSeasonException;
import agency.twoPdigital.vod.mappers.SeasonMapper;
import agency.twoPdigital.vod.models.SeasonModel;
import agency.twoPdigital.vod.repos.SeasonRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SeasonService {
    private final SeasonRepo seasonRepo;
    private final SeasonMapper seasonMapper;

    public SeasonModel createNewSeason(SeasonModel seasonModel) throws CreateSeasonException {
        SeasonEntity entity = seasonMapper.toEntity(seasonModel);
        SeasonEntity addedEntity;
        try {
            addedEntity = seasonRepo.save(entity);
        } catch (Exception e) {
            throw new CreateSeasonException();
        }
        return seasonMapper.toModel(addedEntity);
    }

    public SeasonModel getSeasonDetails(Long id) throws NotFoundException {
        SeasonEntity SeasonEntity = seasonRepo.findById(id).orElseThrow(NotFoundException::new);
        return seasonMapper.toModel(SeasonEntity);
    }

    public List<SeasonModel> getAllSeasonByShowId(Long showId) {
        return seasonRepo.findByShowId(showId).stream()
                .map(seasonMapper::toModel)
                .toList();

    }
    public List<SeasonModel> getAllSeasonByShowIdForView(Long showId) {
        return seasonRepo.findByShowIdAndActivatedTrue(showId).stream()
                .map(seasonMapper::toModel)
                .toList();

    }

    public SeasonModel updateSeason(Long id, SeasonModel seasonModel) throws NotFoundException, UpdateSeasonException {
        SeasonEntity existingEntity = seasonRepo.findById(id)
                .orElseThrow(NotFoundException::new);
        LocalDateTime createdAt = existingEntity.getCreatedAt();
        seasonModel.setId(existingEntity.getId());
        SeasonEntity updatedEntity;
        try {
            updatedEntity = seasonRepo.save(seasonMapper.toEntity(seasonModel));
            updatedEntity.setCreatedAt(createdAt);
        } catch (Exception e) {
            throw new UpdateSeasonException(e);
        }
        return seasonMapper.toModel(updatedEntity);
    }
}
