package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import co.edu.udea.ingweb.repairworkshop.component.spare.domain.SpareItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepairLineSaveResponse {

    private Long id;

    private String description;

    private Long workforcePrice;

    private Long totalSparePrice;

    private Long totalSpareCost;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private Long createdBy;

    private LocalDateTime createdAt;

    private Long updatedBy;

    private LocalDateTime updatedAt;

    public static RepairLineSaveResponse from(RepairLine repairLine){
        return RepairLineSaveResponse.builder()
                .id(repairLine.getId())
                .description(repairLine.getDescription())
                .workforcePrice(repairLine.getWorkforcePrice())
                .totalSparePrice(repairLine.getTotalSparePrice())
                .totalSpareCost(repairLine.getTotalSpareCost())
                .startedAt(repairLine.getStartedAt())
                .finishedAt(repairLine.getFinishedAt())
                .createdBy(repairLine.getCreatedBy())
                .createdAt(repairLine.getCreatedAt())
                .updatedBy(repairLine.getUpdatedBy())
                .updatedAt(repairLine.getUpdatedAt())
                .build();
    }
}
