package controller;

import java.io.File;

/**
 * 
 * Interface for fxml files loading in different ways.
 *
 */
public interface FxmlFilesLoader {
    /**
     * Get fxml file resource and load the stage.
     */
    void getScene();

    /**
     * Get fxml file resource, pass the data and load the stage.
     * @param data is the data to be passed
     * @param controllerClass is the class of the controller
     */
    void getSceneData(DBDataSaver data, Class<?>controllerClass);

    /**
     * Get fxml file of mainMenuView.
     */
    void getMainMenuScene();
    
    /**
     * Get fxml file of ManageMenu.fxml
     */
    void getManageMenuScene();

    /**
     * Get fxml file passing the file.
     * @param file is the file to be passed
     */
    void getSceneFile(File file);
}
