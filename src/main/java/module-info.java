module com.example.studiomanagergui {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires org.testng;


    opens com.example.studiomanagergui to javafx.fxml;
    exports com.example.studiomanagergui;
}