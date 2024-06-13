package org.ts.techsieciowelista2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.ts.techsieciowelista2.exceptions.InvalidLoanStartDateException;
import org.ts.techsieciowelista2.exceptions.UserAlreadyBorrowBookException;

/**
 * Exception handler
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * @param e exception
     * @return message about exception
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }
    /**
     * Handle InvalidLoanStartDateException
     * @param e exception
     * @return message about exception
     */
    @ExceptionHandler(InvalidLoanStartDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String handleInvalidLoanStartDateException(InvalidLoanStartDateException e) {
        return e.getMessage();
    }


    @ExceptionHandler(UserAlreadyBorrowBookException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String handleUserAlreadyBorrowBook(UserAlreadyBorrowBookException e) {
        return e.getMessage();
    }
}
