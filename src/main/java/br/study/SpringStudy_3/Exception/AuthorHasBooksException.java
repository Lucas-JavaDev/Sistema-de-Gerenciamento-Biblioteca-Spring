package br.study.SpringStudy_3.Exception;

public class AuthorHasBooksException extends RuntimeException {
    public AuthorHasBooksException(Long authorId) {
        super("Não é possível excluir o autor com id " + authorId + " porque ele possui livros vinculados");
    }
}
