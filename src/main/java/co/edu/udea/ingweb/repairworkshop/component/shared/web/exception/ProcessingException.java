package co.edu.udea.ingweb.repairworkshop.component.shared.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProcessingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProcessingException(final String message) {
        super(message);
    }

    public ProcessingException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
