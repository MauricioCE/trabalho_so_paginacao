module br.paginacao {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; // Adicione isso se estiver usando elementos gráficos

    opens br.paginacao.controller to javafx.fxml;

    exports br.paginacao; // Mantenha esta linha se a sua classe App estiver diretamente em br.paginacao
    // Se a sua classe App estiver em outro pacote (ex: br.paginacao.app), ajuste a
    // linha exports
}