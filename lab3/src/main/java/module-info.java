module ru.nsu.koshevoi.lab3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens ru.nsu.koshevoi.lab3 to javafx.fxml;
    exports ru.nsu.koshevoi.JavaFX.view;
    exports ru.nsu.koshevoi.JavaFX.controller;
}