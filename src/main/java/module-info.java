module com.gui.xmldirectory {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.xml;

    opens com.gui.xmldirectory to javafx.fxml;
    exports com.gui.xmldirectory;
}