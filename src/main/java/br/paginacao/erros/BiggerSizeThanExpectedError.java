package br.paginacao.erros;

public class BiggerSizeThanExpectedError extends Error {

    public BiggerSizeThanExpectedError() {
    }

    public BiggerSizeThanExpectedError(String msg) {
        System.out.println(msg);
    }

}
