package co.edu.udea.ingweb.repairworkshop.component.shared.model;

import lombok.Generated;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Generated
public class ErrorDetails {

    private final LocalDate timestamp;
    private final String message;
    private final String details;
    private final String type;

    public ErrorDetails(LocalDate timestamp, String message, String details, String type) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.type = type;
    }
    
}
