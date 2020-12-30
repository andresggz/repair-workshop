package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairLineSaveCmd;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepairLineSaveRequest {

    @NotBlank
    @Size(min = 3, max = 50)
    private String description;

    public static RepairLineSaveCmd toModel(RepairLineSaveRequest repairLine){
        return RepairLineSaveCmd.builder()
                .description(repairLine.getDescription())
                .build();
    }
}
