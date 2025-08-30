package agency.twoPdigital.vod.controller;


import agency.twoPdigital.vod.controllers.SeasonController;
import agency.twoPdigital.vod.excpetions.CreateSeasonException;
import agency.twoPdigital.vod.excpetions.NotFoundException;
import agency.twoPdigital.vod.excpetions.UpdateSeasonException;
import agency.twoPdigital.vod.models.ApiResponse;
import agency.twoPdigital.vod.models.SeasonModel;
import agency.twoPdigital.vod.services.SeasonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SeasonControllerTest {
    @Mock
    SeasonService seasonService;
    @InjectMocks
    SeasonController seasonController;
    private SeasonModel seasonModel;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        seasonModel = SeasonModel.builder()
                .id(1L)
                .title("testTitle")
                .showId(1L)
                .seasonNumber(1)
                .description("testDescription")
                .activated(true)
                .build();
    }

    @Test
    void testCreateNewSeason() throws CreateSeasonException {
        when(seasonService.createNewSeason(seasonModel)).thenReturn(seasonModel);

        ResponseEntity<ApiResponse<SeasonModel>> response = seasonController.createNewSeason(seasonModel);
        assertEquals(HttpStatus.CREATED.value(), response.getBody().getStatusCode());
        assertEquals(1L, response.getBody().getBody().getId());
        verify(seasonService, times(1)).createNewSeason(seasonModel);
    }

    @Test
    void testUpdateSeason() throws UpdateSeasonException, NotFoundException {
        Long testId = 2L;
        String testTitle = "editedTitle";
        SeasonModel editedSeason = seasonModel;
        editedSeason.setTitle(testTitle);
        editedSeason.setId(testId);

        when(seasonService.updateSeason(1L, editedSeason)).thenReturn(editedSeason);

        ResponseEntity<ApiResponse<SeasonModel>> response = seasonController.updateSeason(1L, editedSeason);
        assertEquals(HttpStatus.OK.value(), response.getBody().getStatusCode());
        assertEquals(testId, response.getBody().getBody().getId());
        assertEquals(testTitle, response.getBody().getBody().getTitle());
        verify(seasonService, times(1)).updateSeason(1L, editedSeason);
    }

    @Test
    void testGetSeasonDetails() throws NotFoundException {
        Long testId = 1L;

        when(seasonService.getSeasonDetails(testId)).thenReturn(seasonModel);

        ResponseEntity<ApiResponse<SeasonModel>> response = seasonController.getSeasonDetails(testId);
        assertEquals(HttpStatus.OK.value(), response.getBody().getStatusCode());
        assertEquals(testId, response.getBody().getBody().getId());
        verify(seasonService, times(1)).getSeasonDetails(testId);
    }

    @Test
    void testGetAllSeasonByShowId() throws NotFoundException {
        Long testId1 = 2L;
        Long testId2 = 3L;
        Long testId3 = 4L;
        Long showId = 100L;
        SeasonModel seasonModel1 = SeasonModel.builder().id(testId1).build();
        SeasonModel seasonModel2 = SeasonModel.builder().id(testId2).build();
        SeasonModel seasonModel3 = SeasonModel.builder().id(testId3).build();
        when(seasonService.getAllSeasonByShowId(showId)).thenReturn(List.of(seasonModel, seasonModel1, seasonModel2, seasonModel3));
        ResponseEntity<ApiResponse<List<SeasonModel>>> response = seasonController.getAllSeasonBYShowId(showId);
        assertEquals(HttpStatus.OK.value(), response.getBody().getStatusCode());
        assertEquals(seasonModel.getId(), response.getBody().getBody().get(0).getId());
        assertEquals(testId1, response.getBody().getBody().get(1).getId());
        assertEquals(testId2, response.getBody().getBody().get(2).getId());
        assertEquals(testId3, response.getBody().getBody().get(3).getId());
        verify(seasonService, times(1)).getAllSeasonByShowId(showId);


    }

}

