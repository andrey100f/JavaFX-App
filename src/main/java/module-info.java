module com.example.autoserviceapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.autoserviceapp to javafx.fxml;
    exports com.example.autoserviceapp;
    exports com.example.autoserviceapp.controllers;
    opens com.example.autoserviceapp.controllers to javafx.fxml;
    exports com.example.autoserviceapp.domain;
}