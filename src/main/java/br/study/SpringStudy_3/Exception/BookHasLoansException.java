package br.study.SpringStudy_3.Exception;

public class BookHasLoansException extends RuntimeException {
    public BookHasLoansException(Long id) {
        super(
                "Book with id: " + id + "couldn't be removed, because has a " +
                        "referenced loan."
        );
    }
}
