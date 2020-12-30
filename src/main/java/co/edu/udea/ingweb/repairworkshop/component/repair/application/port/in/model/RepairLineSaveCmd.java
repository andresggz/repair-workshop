package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model;

import co.edu.udea.ingweb.repairworkshop.component.repair.domain.RepairLine;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepairLineSaveCmd {

    @NotBlank
    @Size(min = 3, max = 50)
    private String description;

    private Long userIdAuthenticated;

    private Long repairId;

    public static RepairLine toModel(RepairLineSaveCmd repairLine){
        return RepairLine.builder().
                description(repairLine.getDescription())
                .build();
    }
}
