package view.controllers;

import java.io.File;

import controller.FxmlFilesLoader;
import controller.FxmlFilesLoaderImpl;
import controller.FxmlSetter;
import controller.FxmlSetterImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * Controller for MainMenu.fxml file.
 *
 */
public class MainMenuController {

    private final String source = "/view/createnew/chooseNameDb.fxml";
    private final FxmlFilesLoader loader = new FxmlFilesLoaderImpl(this.source);
    private final FxmlSetter setter = new FxmlSetterImpl();
    private FileChooser fileChooser;
    private String fileExtension;

    /**
     * Method for gui button that creates a new database.
     * @param event 
     * @throws Exception 
     */
    @FXML
    public void createNewDatabase(final ActionEvent event) {
        loader.getScene();
        setter.getStage(event).close();
    }

    /**
     * Method for gui button that opens an existing database.
     * @param event 
     */
    @FXML
    public void openDatabase(final ActionEvent event) {
        fileChooser = new FileChooser();
        final Stage stage = setter.getStage(event);

        fileChooser.setTitle("Open database ");
        final File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
            System.out.println(fileExtension);
            if (fileExtension.equals("kdbj")) {
                loader.getSceneFile(file);
                setter.getStage(event).close();
            } else {
                setter.showDialog("Impossibile aprire il database", AlertType.ERROR);
            }
        }
    }

    @FXML
    final void closeApp(final ActionEvent event) {
        System.exit(0);
    }

    @FXML
    final void infoApp(final ActionEvent event) {
        setter.showDialog("KeePassJ was created by:\n\n"
                + "· Giovanni Di Santi\n"
                + "· Francesco Ercolani\n"
                + "· Massimiliano Conti\n"
                + "· Davide Cellot", AlertType.INFORMATION);
    }
}
