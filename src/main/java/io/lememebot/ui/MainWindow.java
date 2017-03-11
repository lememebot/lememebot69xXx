package io.lememebot.ui;

import io.lememebot.core.BotServer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Project: lememebot69xXx
 * Package: io.lememebot.ui
 * Created by Guy on 11-Mar-17.
 * <p>
 * Description:
 * Program main window for activating the bot
 */
public class MainWindow extends Application{

    private BotServer botServer;

    public MainWindow()
    {
        botServer = new BotServer();
    }

    public MainWindow build()
    {
        launch();
        return this;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("lememebot");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        Label stoppedLabel = new Label("Bot Stopped");
        stoppedLabel.getStyleClass().add("h1");
        stoppedLabel.setVisible(false);

        Button actionButton = new Button("Start Bot");
        actionButton.setOnAction((ActionEvent e) -> {
            if(botServer.isOnline()) {
                botServer.shutdown();
                actionButton.setVisible(false);
                stoppedLabel.setVisible(true);
            }
            else
            {
                if (botServer.start()) {
                    actionButton.setText("Stop Bot");
                }
            }
        });

        grid.add(stoppedLabel, 0, 0, 2, 2);
        grid.add(actionButton, 0, 1, 2, 2);

        Scene scene = new Scene(grid, 800, 600);
        scene.getStylesheets().add("MainWindow.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
