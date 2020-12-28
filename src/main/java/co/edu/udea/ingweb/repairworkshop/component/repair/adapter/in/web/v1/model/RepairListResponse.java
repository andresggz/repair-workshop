package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.Repair;
import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairState;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepairListResponse {

    private Long id;

    private RepairState state;

    private String commentary;

    private Long ownerId;

    private Long totalPrice;

    private Long totalCost;

    public static RepairListResponse fromModel(Repair repair){
        return RepairListResponse.builder()
                .id(repair.getId()).state(repair.getState())
                .commentary(repair.getCommentary()).ownerId(repair.getOwner().getId())
                .totalPrice(repair.getTotalPrice()).totalCost(repair.getTotalCost())
                .build();
    }
}
