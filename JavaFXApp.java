import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.SocketException;
import java.util.*;

import javafx.concurrent.*;

import javafx.beans.value.*;


class P_move {
    int x, y;

    public P_move() {
        x = 10;
        y = 10;
    }
}

class G_task extends Task<P_move> {
    P_move p_move;

    Server server;

    public G_task(Server s) {
        this.p_move = new P_move();
        server = s;
    }

    @Override
    protected P_move call() throws Exception {
        int i = 0;

        while (true) {
            System.out.println("Task's call method");

            p_move.x = 10 + i;
            p_move.y = 10 + i;

            updateValue(null);
            updateValue(p_move);

            System.out.println("i=" + i);

            i++;

            System.out.println("x = " + p_move.x + "y = " + p_move.y);

            System.out.println("run method called");
            server.run(p_move.x, p_move.y);

            if (i == 10) {
                updateValue(null);
                break;
            }


//            try {
//                Thread.sleep(1000);
//                System.out.println("sleep method");
//            } catch (InterruptedException ex) {
//                System.out.println("catch method");
//                break;
//            }
        }


        return p_move;
    }
}

class Game_service extends Service<P_move> {

    Task t;

    Server server;
    public Game_service(Server s) {
        server = s;
    }

    protected Task createTask() {
        t = new G_task(server);

        return t;

    }

}


public class JavaFXApp extends Application implements ChangeListener<P_move> {

    Stage stage;

    Server server = new Server(5252);
    Game_service g_s = new Game_service(server);

    public JavaFXApp() throws SocketException {
    }


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

        g_s = new Game_service(server);

        g_s.valueProperty().addListener(this::changed);

        g_s.start();

        primaryStage.show();

    }

    public void changed(ObservableValue<? extends P_move> observable,
                        P_move oldValue,
                        P_move newValue) {
        if (newValue != null) {
            System.out.println("changed method called, x = " + newValue.x + "y = " + newValue.y);
//            try {
//                Server.run(newValue.x, newValue.y);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
        }


    }


    public void item_1() {
        System.out.println("item 1");
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
}
