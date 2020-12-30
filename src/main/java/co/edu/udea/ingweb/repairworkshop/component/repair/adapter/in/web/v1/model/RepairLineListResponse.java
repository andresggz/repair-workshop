package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepairLineListResponse {

    private Long id;

    private String description;

    private Long totalSpareCost;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    public static RepairLineListResponse fromModel(RepairLine repairLine){
        return RepairLineListResponse.builder()
                .id(repairLine.getId())
                .description(repairLine.getDescription())
                .totalSpareCost(repairLine.getTotalSpareCost())
                .startedAt(repairLine.getStartedAt())
                .finishedAt(repairLine.getFinishedAt())
                .build();
    }
}
