package co.edu.udea.ingweb.repairworkshop.component.shared.web;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@JsonSerialize
@AllArgsConstructor
public class ErrorDto implements Serializable {

    private static final long serialVersionUID = 6058781533824057875L;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
