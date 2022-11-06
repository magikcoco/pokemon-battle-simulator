package com.magikcoco.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import static javafx.scene.input.KeyCode.ENTER;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //start function

        //create the root
        VBox root = new VBox();

        //set the size of the whole application
        double height = 400.0; //the resolution of a 3DS top and bottom screen together
        double width = 480.0;

        //the webview that sits on top
        WebView topView = new WebView(); //this is the top screen
        topView.prefHeight(height/2); //half height since there are two of these
        topView.prefWidth(width);
        WebEngine topEngine = topView.getEngine();
        topEngine.load(getClass().getResource("/mainmenu/mainmenutop.html").toString());
        root.getChildren().add(topView);

        //the webview that sits on bottom
        WebView bottomView = new WebView(); //this is the bottom screen
        bottomView.prefHeight(height/2); //half height since there are two of these
        bottomView.prefWidth(width);
        WebEngine bottomEngine = bottomView.getEngine();
        bottomEngine.load(getClass().getResource("/mainmenu/mainmenubottom.html").toString());
        root.getChildren().add(bottomView);

        //create the scene and add listener
        Scene scene = new Scene(root, height, width);
        //key bindings for events
        scene.setOnKeyPressed(e -> {
            if(e.getCode() == ENTER){
                if(bottomEngine.getTitle().equals("mainmenubottom")){
                    System.out.println("Enter Pressed");
                }
            }
        });

        //set the scene on the stage
        primaryStage.setScene(scene);

        //set close request action
        primaryStage.setOnCloseRequest((e)->{
            Platform.exit();
            System.exit(0);
        });

        //set other elements of stage and show it
        primaryStage.setTitle("Pokemon Battle Simulator");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        //main function
        //launch method for JavaFX, blocks this thread
        launch(args);
    }


}

