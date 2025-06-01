package com.kilicarslansoyler.tracking_service.adapter.in.web;

import com.kilicarslansoyler.tracking_service.adapter.in.web.dto.CourierLocationRequestDTO;
import com.kilicarslansoyler.tracking_service.adapter.in.web.dto.CourierLocationResponseDTO;
import com.kilicarslansoyler.tracking_service.adapter.in.web.mapper.CourierLocationWebMapper;
import com.kilicarslansoyler.tracking_service.application.port.in.CourierLocationUseCase;
import com.kilicarslansoyler.tracking_service.domain.model.CourierLocation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@Tag(name = "Courier Location Tracking", description = "APIs for tracking courier locations and calculating distances")
public class CourierLocationController {

    private final CourierLocationUseCase useCase;
    private final CourierLocationWebMapper mapper;

    @PostMapping
    @Operation(summary = "Save courier location", description = "Receives a new courier GPS location and saves it.")
    public ResponseEntity<CourierLocationResponseDTO> saveLocation(@RequestBody CourierLocationRequestDTO requestDTO) {
        CourierLocation saved = useCase.saveLocation(mapper.toDomain(requestDTO));
        return ResponseEntity.ok(mapper.toResponseDTO(saved));
    }

    @GetMapping("/{courierId}/distance")
    @Operation(
            summary = "Get total travel distance",
            description = "Calculates total distance a courier has traveled.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Total distance calculated successfully"),
                    @ApiResponse(responseCode = "404", description = "Courier location data not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<Double> getTotalDistance(@PathVariable Long courierId) {
        return ResponseEntity.ok(useCase.getTotalTravelDistance(courierId));
    }
}
