package io.github.thaisazuoss.libraryapi.exceptions;

import lombok.Getter;

public class CampoIvalidoException extends RuntimeException {

    @Getter
    private String campo;

    public CampoIvalidoException(String campo, String mensagem) {

        super(mensagem);
        this.campo = campo;
    }
}
