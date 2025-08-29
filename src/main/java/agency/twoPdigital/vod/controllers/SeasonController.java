package agency.twoPdigital.vod.controllers;

import agency.twoPdigital.vod.excpetions.CreateSeasonException;
import agency.twoPdigital.vod.excpetions.NotFoundException;
import agency.twoPdigital.vod.excpetions.UpdateSeasonException;
import agency.twoPdigital.vod.models.ApiResponse;
import agency.twoPdigital.vod.models.SeasonModel;
import agency.twoPdigital.vod.services.SeasonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seasons")
@AllArgsConstructor
@Tag(name = "Seasons API", description = "Manage season (create, update, fetch, search)")
public class SeasonController {
    private final SeasonService seasonService;

    @Operation(summary = "Create a new season", description = "Creates a new season in the database")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "season created",
            content = @Content(schema = @Schema(implementation = SeasonModel.class)))
    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse<SeasonModel>> createNewSeason(
            @RequestBody @Valid SeasonModel seasonModel) throws CreateSeasonException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<SeasonModel>builder()
                        .body(seasonService.createNewSeason(seasonModel))
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Created")
                        .build()
        );
    }

    @Operation(summary = "Update a season", description = "Updates details of an existing season by ID")
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ApiResponse<SeasonModel>> updateSeason(
            @PathVariable(name = "id") Long id
            ,@RequestBody @Valid SeasonModel seasonModel
            ) throws NotFoundException, UpdateSeasonException {
        return ResponseEntity.ok(ApiResponse.<SeasonModel>builder()
                .body(seasonService.updateSeason(id, seasonModel))
                .statusCode(HttpStatus.OK.value())
                .message("Updated")
                .build());
    }

    @Operation(summary = "Get season details", description = "Fetch details of a season by ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<SeasonModel>> getSeasonDetails(
            @PathVariable(name = "id") Long id) throws NotFoundException {
        return ResponseEntity.ok(ApiResponse.<SeasonModel>builder()
                .body(seasonService.getSeasonDetails(id))
                .statusCode(HttpStatus.OK.value())
                .message("Success")
                .build());
    }

    @Operation(summary = "Get all shows", description = "Fetch all shows from the database")
    @GetMapping(value = "/getAll/{showId}")
    public ResponseEntity<ApiResponse<List<SeasonModel>>> getAllSeasonBYShowId(@PathVariable(name = "showId") Long showId) throws NotFoundException {
        return ResponseEntity.ok(ApiResponse.<List<SeasonModel>>builder()
                .body(seasonService.getAllSeasonByShowId(showId))
                .statusCode(HttpStatus.OK.value())
                .message("Success")
                .build());
    }
}