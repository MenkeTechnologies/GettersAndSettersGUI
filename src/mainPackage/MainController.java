package mainPackage;

import Python.PythonController;
import Swift.SwiftController;
import CppHeader.CppHeaderController;
import CppImplementation.CppImplementationController;
import CppStandalone.CppStandaloneController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public TabPane mainTabPane;
    public Button generateButton;
    public MenuBar menubar;
    public MenuItem quitMenuItem;
    public ToggleButton toStringToggleButton;
    public ToggleButton setterToggleButton;
    public ToggleButton getterToggleButton;
    public ToggleButton constructorToggleButton;
    public ToggleButton deleterToggleButton;
    public ToggleButton addToClipboardToggleButton;
    PythonController pythonController;
    SwiftController swiftController;
    CppHeaderController cppHeaderController;
    CppImplementationController cppImplementationController;
    CppStandaloneController cppStandaloneController;
    ArrayList<LanguageController> languageControllers = new ArrayList<>();
    HashMap<String, Boolean> options = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        menubar.setUseSystemMenuBar(true);

        quitMenuItem.setOnAction(e -> {
            Platform.exit();
        });

        getterToggleButton.setSelected(true);
        setterToggleButton.setSelected(true);
        constructorToggleButton.setSelected(true);
        toStringToggleButton.setSelected(true);

        //**********************************************************************
        // MARK:Construct Python and CppHeader classes and pass in outlets,
        // if tab pane is selected and generate button then call main method
        // in class file
        //**********************************************************************

        System.out.println("Main MainController");
    }

    public void clearAll(ActionEvent actionEvent) {

        deleteText("both");
    }

    void deleteText(String option) {

        switch (mainTabPane.getSelectionModel().getSelectedIndex()) {
            case 0:
                removeText(pythonController, option);
                break;
            case 1:
                removeText(swiftController, option);
                break;
            case 2:
                removeText(cppHeaderController, option);
                break;
            case 3:
                removeText(cppImplementationController, option);
                break;
            case 4:
                removeText(cppStandaloneController, option);
                break;
            default:
                break;
        }
    }

    private void removeText(LanguageController lc, String option) {
        LanguageController languageController = lc;

        switch (option) {
            case "left":
                languageController.textAreas.get(0).setText("");
                break;
            case "right":
                languageController.textAreas.get(1).setText("");
                break;
            case "both":
                languageController.textAreas.get(0).setText("");
                languageController.textAreas.get(1).setText("");
            default:
                break;
        }
    }

    public void openSettingsScene(ActionEvent actionEvent) {
    }

    void getOptions() {
        options.put("setter", setterToggleButton.isSelected());
        options.put("getter", getterToggleButton.isSelected());
        options.put("constructor", constructorToggleButton.isSelected());
        options.put("deleter", deleterToggleButton.isSelected());
        options.put("toString", toStringToggleButton.isSelected());
        options.put("clipboard", addToClipboardToggleButton.isSelected());
    }

    public void generateCode(ActionEvent actionEvent) {
        getOptions();

        switch (mainTabPane.getSelectionModel().getSelectedIndex()) {
            case 0:
                pythonController.generatePython(options);
                break;
            case 1:
                swiftController.generateSwift(options, false, null);
                break;
            default:
                System.out.println("error in tab selection...");
        }
    }

    public void passControllers(ArrayList<LanguageController> args) {
        languageControllers = args;
        this.pythonController = (PythonController) args.get(0);
        this.swiftController = (SwiftController) args.get(1);
        this.cppHeaderController = (CppHeaderController) args.get(2);
        this.cppImplementationController = (CppImplementationController) args.get(3);
        this.cppStandaloneController = (CppStandaloneController) args.get(4);
    }

    public void initBindings(Scene scene, ArrayList<LanguageController> languageControllers) {
        for (int i = 0; i < languageControllers.size(); i++) {
            languageControllers.get(i).textAreas.get(0).minHeightProperty().bind(scene.heightProperty().multiply(0.8));
            languageControllers.get(i).textAreas.get(1).minHeightProperty().bind(scene.heightProperty().multiply(0.8));
            languageControllers.get(i).textAreas.get(0).minWidthProperty().bind(scene.widthProperty().divide(2));
            languageControllers.get(i).textAreas.get(1).minWidthProperty().bind(scene.widthProperty().divide(2));
        }

        deleterToggleButton.disableProperty().bind(mainTabPane.getSelectionModel().selectedIndexProperty().isNotEqualTo(0));
    }

    public void generateCodeWithOptions(ActionEvent actionEvent) {
        getOptions();

        switch (mainTabPane.getSelectionModel().getSelectedIndex()) {
            case 0:
                pythonController.generatePythonWithOptions(options);
                break;
            case 1:
                swiftController.generateSwiftWithOptions(options, languageControllers.get(1).textAreas.get(1).getText());
                break;
            default:
                System.out.println("error in tab selection...");
        }
    }

    public void clearRight(ActionEvent actionEvent) {
        deleteText("right");
    }

    public void clearLeft(ActionEvent actionEvent) {
        deleteText("left");
    }
}
