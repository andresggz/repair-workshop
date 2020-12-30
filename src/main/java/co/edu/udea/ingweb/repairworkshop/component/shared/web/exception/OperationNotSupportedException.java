package co.edu.udea.ingweb.repairworkshop.component.shared.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class OperationNotSupportedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OperationNotSupportedException(final String message) {
        super(message);
    }
}
