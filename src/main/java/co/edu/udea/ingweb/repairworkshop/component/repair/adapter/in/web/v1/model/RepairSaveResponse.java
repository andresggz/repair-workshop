package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model;


import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairState;
import co.edu.udea.ingweb.repairworkshop.component.user.domain.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepairSaveResponse {

    private Long id;

    private RepairState state;

    private String commentary;

    private Set<Long> repairmanIds;

    private Long ownerId;

    private Set<Long> repairLineIds;

    private Long totalPrice;

    private Long totalCost;

    private Long createdBy;

    private LocalDateTime createdAt;

    private Long updatedBy;

    private LocalDateTime updatedAt;

    public static RepairSaveResponse fromModel(Repair repair){
        return RepairSaveResponse.builder()
                .id(repair.getId()).state(repair.getState()).commentary(repair.getCommentary())
                .repairmanIds(repair.getRepairmen().stream().map(User::getId).collect(Collectors.toSet()))
                .ownerId(repair.getOwner().getId())
                .repairLineIds(repair.getRepairLines().stream().map(RepairLine::getId).collect(Collectors.toSet()))
                .totalPrice(repair.getTotalPrice()).totalCost(repair.getTotalCost()).createdBy(repair.getCreatedBy())
                .createdAt(repair.getCreatedAt()).updatedBy(repair.getUpdatedBy()).updatedAt(repair.getUpdatedAt())
                .build();
    }
}
