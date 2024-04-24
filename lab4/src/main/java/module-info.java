module ru.nsu.koshevoi.lab4 {
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

    opens ru.nsu.koshevoi.lab4 to javafx.fxml;
    exports ru.nsu.koshevoi.lab4.model;
    exports ru.nsu.koshevoi.lab4.controller;
    opens ru.nsu.koshevoi.lab4.controller to javafx.fxml;
    exports ru.nsu.koshevoi.lab4.view;
    opens ru.nsu.koshevoi.lab4.view to javafx.fxml;
    exports ru.nsu.koshevoi.lab4.model.workers;
    exports ru.nsu.koshevoi.lab4.model.dealers;
    exports ru.nsu.koshevoi.lab4.model.storages.and.warehouses;
    exports ru.nsu.koshevoi.lab4.model.cars.and.parts;
    opens ru.nsu.koshevoi.lab4.model to javafx.fxml;
}