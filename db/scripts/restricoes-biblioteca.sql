
BEGIN;

ALTER TABLE livro
    ALTER COLUMN estoque SET NOT NULL,
    ALTER COLUMN titulo SET NOT NULL;

ALTER TABLE emprestimo
    ALTER COLUMN membro_id SET NOT NULL,
    ALTER COLUMN livro_id SET NOT NULL,
    ALTER COLUMN status SET NOT NULL;

ALTER TABLE autor
    ALTER COLUMN nome SET NOT NULL;

ALTER TABLE livro
    ADD CONSTRAINT estoque_check
        CHECK (estoque >= 0);

CREATE UNIQUE INDEX uq_emprestimo_livro_membro_ativo
    ON emprestimo (livro_id, membro_id)
    WHERE status IN ('EMPRESTADO', 'ATRASADO');

COMMIT;
