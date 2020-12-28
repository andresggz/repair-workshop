package co.edu.udea.ingweb.repairworkshop.component.repair.application.port.in.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepairQuerySearchCmd {

    private LocalDateTime createdBeforeThan;

    private LocalDateTime createdAfterThan;
}
