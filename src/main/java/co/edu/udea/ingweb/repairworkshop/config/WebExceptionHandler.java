package co.edu.udea.ingweb.repairworkshop.config;

import co.edu.udea.ingweb.repairworkshop.component.shared.model.ErrorDetails;
import co.edu.udea.ingweb.repairworkshop.component.shared.web.exception.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.stream.Collectors;

import static java.lang.String.format;

@ControllerAdvice
public class WebExceptionHandler /*extends ResponseEntityExceptionHandler*/ {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String CONSTRAINT_VIOLATION = "Constraint violation";
    private static final String VALIDATION_FAILED = "Validation Failed";
    private static final String NOT_ACCEPTABLE = "Not acceptable";
    private static final String NOT_AUTHORIZED = "Not authorized";
    private static final String RESOURCE_NOT_FOUND = "Resource not found";
    private static final String BAD_REQUEST = "Bad request";
    private static final String FAILED_DEPENDENCY = "Failed dependency";
    private static final String MESSAGE_INVALID_NUMBER_FIELD = "The value '%s' is not a number";
    private static final String MESSAGE_INVALID_PROPERTY = "This value [%s] is invalid for field '%s'";
    private static final String PRECONDITION_FAILED = "Precondition failed";
    private static final String ACCESS_DENIED = "Access is denied. Not authorized for this resource";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> handleConstraintViolation(ConstraintViolationException ex,
                                                                  NativeWebRequest request) {
        return new ResponseEntity<>(
                new ErrorDetails(LocalDate.now(), CONSTRAINT_VIOLATION, ex.getMessage(), request.getDescription(false)),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                     NativeWebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDate.now(), VALIDATION_FAILED,
                getErrors(ex.getBindingResult()),
                request.getDescription(false)), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorDetails> handleBindingResult(BindException ex, NativeWebRequest request) {
        return new ResponseEntity<>(
                new ErrorDetails(LocalDate.now(), BAD_REQUEST, getErrors(ex.getBindingResult()),
                        request.getDescription(false)),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> handleMessageNotReadableException(HttpMessageNotReadableException ex,
                                                                          NativeWebRequest req) {
        String message = ex.getMessage();
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException exInvFor = (InvalidFormatException) ex.getCause();
            if (!Number.class.isAssignableFrom(exInvFor.getTargetType())) {
                message = String.format(MESSAGE_INVALID_NUMBER_FIELD, exInvFor.getValue().toString());
            }
        }
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), BAD_REQUEST, message, req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDetails> handleNoHandlerFound(NoHandlerFoundException exception, NativeWebRequest req) {
        return new ResponseEntity<>(
                new ErrorDetails(LocalDate.now(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), req.getDescription(false)),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(final NativeWebRequest req,
                                                                        final ResourceNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), RESOURCE_NOT_FOUND, ex.getMessage(),
                req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handleBadRequestException(final NativeWebRequest req,
                                                                  final BadRequestException ex) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), BAD_REQUEST, ex.getMessage(),
                req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ErrorDetails> handleNotAuthorizedException(final NativeWebRequest req,
                                                                     final NotAuthorizedException ex) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), NOT_AUTHORIZED, ex.getMessage(),
                req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDetails> handleBusinessException(final NativeWebRequest req,
                                                                final BusinessException ex) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), NOT_ACCEPTABLE, ex.getMessage(),
                req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handleIllegalArgumentException(final NativeWebRequest req,
                                                                       final IllegalArgumentException ex) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), NOT_ACCEPTABLE, ex.getMessage(),
                req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(final NativeWebRequest req, final Exception ex) {
        logger.error("Exception handled", ex);
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorDetails> handleHttpClientErrorException(final NativeWebRequest req,
                                                                       final HttpClientErrorException ex) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), FAILED_DEPENDENCY, ex.getMessage(),
                req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(OperationNotSupportedException.class)
    public ResponseEntity<ErrorDetails> handleOperationNotSupportedException(final NativeWebRequest req,
                                                                             final OperationNotSupportedException ex) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), PRECONDITION_FAILED, ex.getMessage(),
                req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> handleSqlIntegrityConstraintViolationException(final NativeWebRequest req,
                                                                                       final SQLIntegrityConstraintViolationException ex) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), PRECONDITION_FAILED, ex.getMessage(),
                req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetails> handleSqlIntegrityConstraintViolationException(final NativeWebRequest req,
                                                                                       final DataIntegrityViolationException ex) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), PRECONDITION_FAILED, ex.getMessage(),
                req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(final NativeWebRequest req,
                                                                    final AccessDeniedException ex){
        ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(), ACCESS_DENIED, ex.getMessage(),
                req.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    private String getErrors(BindingResult bindingResult) {
        String errors;
        if (bindingResult.hasFieldErrors()) {
            errors = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> format(MESSAGE_INVALID_PROPERTY, fieldError.getRejectedValue(),
                            fieldError.getField()))
                    .collect(Collectors.joining(". "));
        } else {
            errors = bindingResult.toString();
        }
        return errors;
    }
}
