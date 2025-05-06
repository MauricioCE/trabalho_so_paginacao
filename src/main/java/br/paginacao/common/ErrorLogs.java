package br.paginacao.common;

public class ErrorLogs {
    public static void pagesQueueSizeDifferFromActionsQueueSize(String algorithmName) {
        System.out.println(
                String.format(
                        "Erro na simulação da %s. A lista de páginas deve ter o mesmo tamanho da lista de acções",
                        algorithmName));
    }

}
