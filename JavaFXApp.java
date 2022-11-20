import javafx.application.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketException;
import java.util.*;

import javafx.concurrent.*;

import javafx.beans.*;
import javafx.beans.value.*;


class P_move implements Serializable
{
 int x, y;

 public P_move()
 {
  x = 10;
  y = 10;
 }
}

class GameState implements Serializable
{
 P_move p1;
 P_move p2;
}

class G_task extends Task<P_move>
{
 P_move p_move;

 Client client;

 public G_task(Client c)
 {
  this.p_move = new P_move();
  client = c;
 }

 @Override
 protected P_move call() throws Exception
 {
  int i = 0;

  while(true)
  {
   System.out.println("Task's call method");

   p_move.x = 10 + i;
   p_move.y = 10 + i;

   updateValue(null);
   updateValue(p_move);

   System.out.println("i=" + i);

   i++;

   System.out.println("x = " +  p_move.x + "; y = " +  p_move.y);

   System.out.println("run method called");
   Client.run(p_move.x, p_move.y);


   if(i == 10)
   {
    updateValue(null);
    break;
   }


//   try { Thread.sleep(1000);  System.out.println("sleep method");    }
//   catch (InterruptedException ex)
//   {
//    System.out.println("catch method");
//    break;
//   }
  }


  return p_move;
 }
}

class Game_service extends Service<P_move>
{

 Task t;

 Client client;
 public Game_service(Client c)
 {
   client = c;
 }

 protected Task createTask()
 {
  t = new G_task(client);

  return t;

 }

}





public class JavaFXApp extends Application implements ChangeListener<P_move>
{
 Stage stage;

 //canvas potrzeby do obslugi przyciskow z klawiatury (1/2)
 GraphicsContext gc;
 Canvas canvas;

 private static final int FRAME_WIDTH  = 960;
 private static final int FRAME_HEIGHT = 600;


 Client client = new Client(5252);
 Game_service g_s = new Game_service(client);

 //Game_service g_s;

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

  Scene scene = new Scene(vBox, FRAME_WIDTH, FRAME_HEIGHT);

  primaryStage.setScene(scene);

  //g_s = new Game_service();
  //System.out.println("GS1:"+g_s.getState().toString());

  //canvas potrzeby do obslugi przyciskow z klawiatury (2/2)
  canvas = new Canvas(FRAME_WIDTH, FRAME_HEIGHT);
  canvas.setOnKeyPressed(this::key);
//  canvas.setOnMousePressed(this::mouse);
  canvas.setFocusTraversable(true);
  gc = canvas.getGraphicsContext2D();
  vBox.getChildren().add(canvas);

  primaryStage.setOnCloseRequest(e -> {
   e.consume();
   exit_dialog();
  });

  g_s = new Game_service(client);

  g_s.valueProperty().addListener(this::changed);

  g_s.start();

  primaryStage.show();

 }

 public void changed(ObservableValue<? extends P_move> observable,
                     P_move oldValue,
                     P_move newValue)
 {
  if(newValue != null)
   System.out.println("changed method called, x = " + newValue.x + "; y = " + newValue.y);
//  try {
//   Client.run(newValue.x, newValue.y);
//  } catch (IOException e) {
//   e.printStackTrace();
//  }

 }



 public void item_1()
 {
  System.out.println("item 1");
 }

 public void exit_dialog()
 {
  System.out.println("exit dialog");


  Alert alert = new Alert(AlertType.CONFIRMATION,
      "Do you really want to exit the program?.",
      ButtonType.YES, ButtonType.NO);

  alert.setResizable(true);
  alert.onShownProperty().addListener(e -> {
   Platform.runLater(() -> alert.setResizable(false));
  });

  Optional<ButtonType> result = alert.showAndWait();
  if (result.get() == ButtonType.YES)
  {
   Platform.exit();
  }
  else
  {
  }

 }

 private void key(KeyEvent k)
 {
//  Game_service g_s = new Game_service(client);
//  g_s.valueProperty().addListener(this::changed);
//  g_s.start();

  //System.out.println(g_s.getState().toString());

  System.out.println("Key= " + k.getCode());

  // TODO: zmienia pozycje swojego kursora (switch)
  String move = k.getCode().toString();
  switch (move) {
   case "UP":
    //y += 5;
    break;
   case "DOWN":
    //y -= 5;
    break;
   case "LEFT":
    //x += 5;
    break;
   case "RIGHT":
    //x -= 5;
    break;
  }

  // wysyla do serwera - client.run()?
  // draw()?

  //System.out.println(g_s.getState().toString());
 }

}