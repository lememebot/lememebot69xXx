package io.lememebot.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXToggleButton;
import io.lememebot.core.BotServer;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Project: lememebot69xXx
 * Package: io.lememebot.ui
 * Created by Guy on 11-Mar-17.
 * <p>
 * Description:
 * Program main window for activating the bot
 */
public class MainWindow extends Application{
    private final static Logger log = LogManager.getLogger();
    @FXML private JFXButton btn_bot_control;
    @FXML private JFXButton btn_bot_status;
    @FXML private JFXSpinner spn_bot_progress;
    @FXML private JFXToggleButton tgl_log_dbg;
    @FXML private JFXTextArea txt_log;

    private volatile BotServer botServer;

    public MainWindow()
    {
    }

    public MainWindow build()
    {
        launch();
        return this;
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("/UI/MainWindow.fxml"));
        }
        catch (IOException ex)
        {
            log.error("Could not load Main Window fxml");
            ex.printStackTrace();
        }

        Scene scene = new Scene(root, 640, 560);
        scene.getStylesheets().add("/UI/MainWindow.css");
        primaryStage.setTitle("lememebot");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /////////////
    // Stage Events
    /////////////

    public void handleBotControlAction(MouseEvent mouseEvent) {
        if (null == botServer) {
            botServer = new BotServer();
        }

        if (botServer.isOnline()) {
            botServer.stop();
            btn_bot_control.setText("START");
            btn_bot_status.setText("lememebot [OFFLINE]");
            btn_bot_status.getStyleClass().remove("status_online");
            btn_bot_status.getStyleClass().add("status_offline");
            btn_bot_control.getStyleClass().remove("btn_stop");
            btn_bot_control.getStyleClass().add("btn_start");
        } else {
            spn_bot_progress.setVisible(true);
            Task<Boolean> taskBotStartup = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    return botServer.start();
                }
            };

            taskBotStartup.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    btn_bot_control.setText("STOP");
                    btn_bot_status.setText("lememebot [ONLINE]");
                    btn_bot_status.getStyleClass().add("status_online");
                    btn_bot_status.getStyleClass().remove("status_offline");
                    btn_bot_control.getStyleClass().add("btn_stop");
                    btn_bot_control.getStyleClass().remove("btn_start");
                    spn_bot_progress.setVisible(false);
                }
            });
            Thread threadBotStart = new Thread(taskBotStartup);
            threadBotStart.setDaemon(true);
            threadBotStart.start();
        }
    }
}
