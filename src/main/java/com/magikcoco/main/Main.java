package com.magikcoco.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //start function
        VBox root = new VBox();

        double height = 400.0;
        double width = 480.0;

        WebView topView = new WebView();
        topView.prefHeight(height/2);
        topView.prefWidth(width);
        WebEngine topEngine = topView.getEngine();
        topEngine.load(getClass().getResource("/mainmenu/mainmenutop.html").toString());
        root.getChildren().add(topView);

        WebView bottomView = new WebView();
        bottomView.prefHeight(height/2);
        bottomView.prefWidth(width);
        WebEngine bottomEngine = bottomView.getEngine();
        bottomEngine.load(getClass().getResource("/mainmenu/mainmenubottom.html").toString());
        root.getChildren().add(bottomView);

        primaryStage.setOnCloseRequest((e)->{
            Platform.exit();
            System.exit(0);
        });
        primaryStage.setTitle("Pokemon Battle Simulator");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, height, width));
        primaryStage.show();
    }

    public static void main(String[] args) {
        //main function
        launch(args);
    }


}

