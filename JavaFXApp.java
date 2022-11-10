import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.*;

// 10.10.10.50
// komunikacja, grafika, lapanie ruchu kursora
// TODO: Three buttons: connect, start, end (like woods and rocks in Map_1)

public class JavaFXApp extends Application {

    Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX App");

        stage = primaryStage;

        Menu menu1 = new Menu("File");


        MenuItem menuItem1 = new MenuItem("Item 1");


        MenuItem menuItem2 = new MenuItem("Exit");

        menuItem2.setOnAction(e -> {
            System.out.println("Exit Selected");

            exit_dialog();

        });

        menu1.getItems().add(menuItem1);
        menu1.getItems().add(menuItem2);


        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().add(menu1);

        VBox vBox = new VBox(menuBar);

        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            exit_dialog();
        });
        // TODO: Draw connect button
        // TODO: Draw start button
        // TODO: Draw end button

        primaryStage.show();
    }

    public void exit_dialog() {
        System.out.println("exit dialog");

        Alert alert = new Alert(AlertType.CONFIRMATION,
                "Do you really want to exit the program?.",
                ButtonType.YES, ButtonType.NO);

        alert.setResizable(true);
        alert.onShownProperty().addListener(e -> {
            Platform.runLater(() -> alert.setResizable(false));
        });

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            Platform.exit();
        } else {
        }
    }

    public void connect_button(){
        // TODO: Implement connect_button
    }
    public void start_button(){
        // TODO: Implement start button
    }
    public void end_button(){
        // TODO: Implement end button
    }

    public void key() {
        // TODO: Implement key handler
        // Here use server methods??
        // Jak draw_1 w C
    }

    public void draw() {
        // TODO: Implement drawing loop
    }
}