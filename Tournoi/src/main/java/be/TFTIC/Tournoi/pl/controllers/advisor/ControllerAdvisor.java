package be.TFTIC.Tournoi.pl.controllers.advisor;

import be.TFTIC.Tournoi.bll.exception.TournamentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller advice class for global exception handling in the application.
 * Uses {@code @ControllerAdvice} to capture and handle specific exceptions in a centralized manner.
 */
@Slf4j
@ControllerAdvice
public class ControllerAdvisor {

    /**
     * <p>Handles exceptions of type {@link TournamentException}.</p>
     * <p>This method captures {@link TournamentException}, logs the error message,
     * and returns an HTTP response with a status containing the error message.</p>
     *
     * @param error the {@link TournamentException} thrown when the email already exists
     * @return a {@link ResponseEntity} containing the error message with a status
     */
    @ExceptionHandler(TournamentException.class)
    public ResponseEntity<String> handleRuntimeException(TournamentException error){
        log.error(error.toString());
        return ResponseEntity.status(error.getStatus()).body(error.getMessage());
    }

    /**
     * Handles method argument validation exceptions.
     * {@link MethodArgumentNotValidException}
     *
     * @param error The {@code MethodArgumentNotValidException} to handle.
     * @return A {@code ResponseEntity} with a status code of 406 (Not Acceptable) and a body containing a list of validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException error) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", error.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList()));
        return ResponseEntity.status(406).body(errorResponse);
    }

}