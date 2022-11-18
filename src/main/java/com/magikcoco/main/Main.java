package com.magikcoco.main;

import com.sun.webkit.dom.HTMLDocumentImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private static Pane root; //this is the root of the scene

    public static void main(String[] args) {
        //main function
        //launch method for JavaFX, blocks this thread
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //create the root
        root = new VBox();

        //resolution for whole program
        double height = 720.0;
        double width = 480.0;

        //create the scene
        Scene scene = new Scene(root, width, height);

        //set up the gui components
        setUpGUIComponents(setUpWebView(height/2, width), setUpWebView(height/2, width));

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

    private WebEngine setUpWebView(double height, double width){
        WebView webView = new WebView();
        webView.prefHeight(height); //dimensions
        webView.prefWidth(width);
        root.getChildren().add(webView); //add to the parent
        return webView.getEngine(); //return the WebEngine, needed for loading things
    }

    private void setUpGUIComponents(WebEngine topEngine, WebEngine bottomEngine){
        //set up the WebEngines
        setUpWebEngine(topEngine);
        setUpWebEngine(bottomEngine);
        //load the main menu
        topEngine.load(getClass().getResource("/mainmenu/mainmenutop.html").toString());
        bottomEngine.load(getClass().getResource("/mainmenu/mainmenubottom.html").toString());
    }

    private void setUpWebEngine(WebEngine webEngine){
        //add a listener on the document property to detect what element we are pressing
        webEngine.documentProperty().addListener((observable, oldDocument, newDocument)->{
            if(newDocument != null) { //newDocument is null when switching initially
                //JavaFX webview uses this, so we can cast for functionality
                HTMLDocumentImpl htmldoc = (HTMLDocumentImpl) newDocument;
                //detecting where the mouse is clicking, for buttons and things
                htmldoc.setOnmouseup(e -> {
                    MouseEvent mouseEvent = (MouseEvent) e;
                    //what to do when click happens
                    if(webEngine.getTitle().equals("mainmenutop") || webEngine.getTitle().equals("mainmenubottom")){
                        //on the main menu, so move to game menu
                        WebView top = (WebView) root.getChildren().get(0);
                        top.getEngine().load(getClass().getResource("/gamemenu/gamemenutop.html").toString());
                        WebView bottom = (WebView) root.getChildren().get(1);
                        bottom.getEngine().load(getClass().getResource("/gamemenu/gamemenubottom.html").toString());
                    } else if (webEngine.getTitle().equals("gamemenutop") || webEngine.getTitle().equals("gamemenubottom")){
                        //buttons from gamemenutop.html
                        gameMenuButtonHandler(htmldoc, mouseEvent);
                    }
                });
            }
        });
    }

    private void gameMenuButtonHandler(HTMLDocumentImpl htmldoc, MouseEvent mouseEvent){
        //these are the buttons in gamemenutop.html
        try {
            //switch case is the id name of the element. id="example" will be "example"
            switch (htmldoc.elementFromPoint(mouseEvent.getClientX(), mouseEvent.getClientY()).getAttribute("id")) {
                case "game-button-img", "game-button-text", "game-button-highlight-img"
                        //game button case, gamemenutop.html
                        -> gameMenuGameButton();
                case "teambuilder-button-img", "teambuilder-button-text", "teambuilder-button-highlight-img"
                        //teambuilder button case, gamemenutop.html
                        -> gameMenuTBButton();
                case "settings-button-img", "settings-button-text", "settings-button-highlight-img"
                        //settings button case, gamemenutop.html
                        -> gameMenuSettingsButton();
                case "exit-button-img", "exit-button-text", "exit-button-highlight-img"
                        //exit button case, gamemenutop.html
                        -> gameMenuExitButton();
            }
        } catch(NullPointerException nullpointer) {
            //this happens if there is no id attribute in the html element we click
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.setLevel(Level.WARNING);
            logger.warning("Clicked item has no id attribute");
        }
    }

    private void gameMenuGameButton(){
        //game button in gamemenutop.html
        WebView bottom = (WebView) root.getChildren().get(1);
        bottom.getEngine().load(getClass().getResource("/gamemenu/startgamemenubottom.html").toString());
    }

    private void gameMenuTBButton(){
        //teambuilder button in gamemenutop.html
        WebView bottom = (WebView) root.getChildren().get(1);
        bottom.getEngine().load(getClass().getResource("/gamemenu/teambuildermenubottom.html").toString());
    }

    private void gameMenuSettingsButton(){
        //settings button in gamemenutop.html
        WebView bottom = (WebView) root.getChildren().get(1);
        bottom.getEngine().load(getClass().getResource("/gamemenu/settingsmenubottom.html").toString());
    }

    private void gameMenuExitButton(){
        //exit button in gamemenutop.html
        Platform.exit();
        System.exit(0);
    }
}
