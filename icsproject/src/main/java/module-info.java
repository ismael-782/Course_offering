module ics108project {
    requires javafx.controls;
    requires javafx.fxml;

    opens ics108project to javafx.fxml;
    exports ics108project;
}
