package com.kilicarslansoyler.tracking_service.adapter.in.web;

import com.kilicarslansoyler.tracking_service.adapter.in.web.dto.CourierLocationRequestDTO;
import com.kilicarslansoyler.tracking_service.adapter.in.web.dto.CourierLocationResponseDTO;
import com.kilicarslansoyler.tracking_service.adapter.in.web.mapper.CourierLocationWebMapper;
import com.kilicarslansoyler.tracking_service.application.port.in.CourierLocationUseCase;
import com.kilicarslansoyler.tracking_service.domain.model.CourierLocation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @Operation(summary = "Get total travel distance", description = "Calculates total distance a courier has traveled.")
    public ResponseEntity<Double> getTotalDistance(@PathVariable Long courierId) {
        return ResponseEntity.ok(useCase.getTotalTravelDistance(courierId));
    }
}
