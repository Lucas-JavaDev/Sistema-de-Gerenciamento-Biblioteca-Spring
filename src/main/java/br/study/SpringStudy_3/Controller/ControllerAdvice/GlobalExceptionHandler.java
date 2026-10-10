package br.study.SpringStudy_3.Controller.ControllerAdvice;


import br.study.SpringStudy_3.Exception.*;
import br.study.SpringStudy_3.Exception.ErrorMessages.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceNotFoundErrorMessage> handleResourceNotFoundException(ResourceNotFoundException e) {
        ResourceNotFoundErrorMessage errorMessage =
                new ResourceNotFoundErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(BookAlreadyBorrowed.class)
    public ResponseEntity<BookErrorMessage> handleBookAlreadyBorrowed(BookAlreadyBorrowed e){
        BookErrorMessage bookErrorMessage = new BookErrorMessage(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(bookErrorMessage);
    }

    @ExceptionHandler(BookStockIsEmptyException.class)
    public ResponseEntity<BookStockEmptyErrorMessage> hanldeBookStockIsEmptyException(BookStockIsEmptyException e) {
        BookStockEmptyErrorMessage errorMessage = new BookStockEmptyErrorMessage(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);

    }

    @ExceptionHandler(MaxLoanLimitReachedException.class)
    public ResponseEntity<MaxLoanLimitErrorMessage> hanldeMaxLoanLimitReachedException(MaxLoanLimitReachedException e) {
        MaxLoanLimitErrorMessage errorMessage = new MaxLoanLimitErrorMessage(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

}
