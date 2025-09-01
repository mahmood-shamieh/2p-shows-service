package agency.twoPdigital.vod.services;

import agency.twoPdigital.vod.config.ServiceConfigurations;
import agency.twoPdigital.vod.entities.SeasonEntity;
import agency.twoPdigital.vod.excpetions.CreateSeasonException;
import agency.twoPdigital.vod.excpetions.NotFoundException;
import agency.twoPdigital.vod.excpetions.UpdateSeasonException;
import agency.twoPdigital.vod.mappers.SeasonMapper;
import agency.twoPdigital.vod.models.ApiResponse;
import agency.twoPdigital.vod.models.EpisodeModel;
import agency.twoPdigital.vod.models.SeasonModel;
import agency.twoPdigital.vod.repos.SeasonRepo;
import agency.twoPdigital.vod.utils.QualifierKeyWord;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service

public class SeasonService {
    private final SeasonRepo seasonRepo;
    private final SeasonMapper seasonMapper;
    private final ServiceConfigurations serviceConfigurations;
    private final WebClient episodeWebClient;

    @Autowired
    public SeasonService(
            SeasonRepo seasonRepo
            , SeasonMapper seasonMapper
            , ServiceConfigurations serviceConfigurations
            , @Qualifier(QualifierKeyWord.EPISODE_WEB_CLIENT) WebClient episodeWebClient) {
        this.seasonRepo = seasonRepo;
        this.seasonMapper = seasonMapper;
        this.serviceConfigurations = serviceConfigurations;
        this.episodeWebClient = episodeWebClient;
    }

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
        List<SeasonEntity> seasonEntities = seasonRepo.findByShowIdAndActivatedTrue(showId);
        List<SeasonModel> seasonModels = seasonEntities.stream().map(seasonMapper::toModel).toList();

        return seasonModels.stream()
                .map(seasonModel -> {
                    List<EpisodeModel> episodes = fetchEpisodesForSeason(seasonModel.getId());
                    seasonModel.setEpisodeList(episodes);
                    return seasonModel;
                })
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

    private List<EpisodeModel> fetchEpisodesForSeason(Long seasonId) {
        try {
            Mono<List<EpisodeModel>> responseMono = episodeWebClient.get()
                    .uri(serviceConfigurations.getEpisodeService().getGetAllEpisodeBySeasonId(), seasonId)
                    .retrieve()
                    .bodyToMono(ApiResponse.class)
                    .map(apiResponse -> {
                        Object body = apiResponse.getBody();
                        if (body instanceof List<?> list) {
                            return (List<EpisodeModel>) list;
                        }
                        return Collections.<EpisodeModel>emptyList();
                    });
            return responseMono.block();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
