package com.magikcoco.main;

import com.sun.webkit.dom.HTMLDocumentImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import static javafx.scene.input.KeyCode.ENTER;
import org.w3c.dom.events.MouseEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //start function

        //create the root
        VBox root = new VBox();

        //set the size of the whole application
        double height = 720.0; //the resolution of two screen stacked on top of each other
        double width = 480.0;

        //the webview that sits on top
        WebView topView = new WebView(); //this is the top screen
        topView.prefHeight(height/2); //half height since there are two of these
        topView.prefWidth(width);
        WebEngine topEngine = topView.getEngine();
        topEngine.documentProperty().addListener((observable, oldDocument, newDocument)->{
            if(newDocument != null) {
                System.out.println(newDocument);
                HTMLDocumentImpl htmldoc = (HTMLDocumentImpl) newDocument;
                htmldoc.setOnmouseup(e -> {
                    MouseEvent mouseEvent = (MouseEvent) e;
                    try {
                        switch (htmldoc.elementFromPoint(mouseEvent.getClientX(), mouseEvent.getClientY()).getAttribute("id")) {
                            case "game-button-img", "game-button-text", "game-button-highlight-img"
                                    -> System.out.println("Game button pressed");
                            case "teambuilder-button-img", "teambuilder-button-text", "teambuilder-button-highlight-img"
                                    -> System.out.println("Teambuilder button pressed");
                            case "settings-button-img", "settings-button-text", "settings-button-highlight-img"
                                    -> System.out.println("Settings button pressed");
                            case "exit-button-img", "exit-button-text", "exit-button-highlight-img"
                                    -> System.out.println("Exit button pressed");
                        }
                    }catch(NullPointerException nullpointer){
                        Logger logger = Logger.getLogger(this.getClass().getName());
                        logger.setLevel(Level.WARNING);
                        logger.warning("Clicked item has no id attribute");
                    }
                });
            }
        });
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
        Scene scene = new Scene(root, width, height);
        //key bindings for events
        scene.setOnKeyPressed(e -> {
            if(e.getCode() == ENTER){
                if(bottomEngine.getTitle().equals("mainmenubottom")){
                    //when on main menu, switch to the game menu
                    topEngine.load(getClass().getResource("/gamemenu/gamemenutop.html").toString());
                    bottomEngine.load(getClass().getResource("/gamemenu/gamemenubottom.html").toString());
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

