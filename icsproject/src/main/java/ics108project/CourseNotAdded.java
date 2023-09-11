package ics108project;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;


public class CourseNotAdded {

    private final static String BUTTON_FONT = "Verdana";
    private final static double BUTTON_FONT_SIZE = 12;
    
    public static void display(){

    Stage popupwindow = new Stage();

    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.setTitle("Course could not be added");

    Label label1= new Label("Course could not be added please check timings and days");
    label1.setTextFill(Color.WHITE);
    label1.setFont(Font.font(BUTTON_FONT, FontWeight.BOLD, 15));
    label1.setFont(new Font("Arial", 15));

    Button button1= new Button("OK");
    button1.setTextFill(Color.WHITE);
    button1.setFont(Font.font(BUTTON_FONT, FontWeight.BOLD, BUTTON_FONT_SIZE));
    button1.setStyle("-fx-background-radius: 50; -fx-background-color: rgb(50, 50, 50);");

    button1.setOnAction(e -> popupwindow.close());
    VBox layout= new VBox(10);

    layout.getChildren().addAll(label1, button1);
    layout.setAlignment(Pos.CENTER);
    layout.setStyle("-fx-background-color: #155566;");
    Scene scene1= new Scene(layout, 400,200,Color.TRANSPARENT);
    popupwindow.setScene(scene1);
    popupwindow.showAndWait();
    }
}