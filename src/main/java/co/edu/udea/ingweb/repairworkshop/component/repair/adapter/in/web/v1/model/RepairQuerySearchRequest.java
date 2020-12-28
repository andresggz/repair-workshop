package co.edu.udea.ingweb.repairworkshop.component.repair.adapter.in.web.v1.model;

import co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model.RepairQuerySearchCmd;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepairQuerySearchRequest {

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDateTime createdBeforeThan;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDateTime createdAfterThan;

    public static RepairQuerySearchCmd toModel(RepairQuerySearchRequest queryCriteria){
        return RepairQuerySearchCmd.builder()
                .createdAfterThan(queryCriteria.getCreatedAfterThan())
                .createdBeforeThan(queryCriteria.getCreatedBeforeThan())
                .build();
    }
}
