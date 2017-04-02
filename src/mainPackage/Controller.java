package mainPackage;

import Python.PythonController;
import Swift.SwiftController;
import CppHeader.CppHeaderController;
import CppImplementation.CppImplementationController;
import CppStandalone.CppStandaloneController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TabPane mainTabPane;
    public Button generateButton;
    public CheckBox addToClipboardCheckBox;
    PythonController pythonController;
    SwiftController swiftController;
    CppHeaderController cppHeaderController;
    CppImplementationController cppImplementationController;
    CppStandaloneController cppStandaloneController;
    ArrayList<LanguageController> languageControllers = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //**********************************************************************
        // MARK:Construct Python and CppHeader classes and pass in outlets,
        // if tab pane is selected and generate button then call main method
        // in class file
        //**********************************************************************

        System.out.println("Main Controller");
    }

    public void clearAll(ActionEvent actionEvent) {

        switch (mainTabPane.getSelectionModel().getSelectedIndex()) {
            case 0:
                removeText(pythonController);
                break;
            case 1:
                removeText(swiftController);
                break;
            case 2:
                removeText(cppHeaderController);
                break;
            case 3:
                removeText(cppImplementationController);
                break;
            case 4:
                removeText(cppStandaloneController);
                break;
            default:
                break;
        }
    }

    private void removeText(LanguageController lc) {
        LanguageController languageController = lc;
        languageController.textAreas.get(0).setText("");
        languageController.textAreas.get(1).setText("");
    }

    public void openSettingsScene(ActionEvent actionEvent) {
    }

    public void generateCode(ActionEvent actionEvent) {
        switch (mainTabPane.getSelectionModel().getSelectedIndex()) {
            case 0:
                pythonController.generatePython(addToClipboardCheckBox.isSelected());
                break;
            case 1:

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
            languageControllers.get(i).textAreas.get(0).minHeightProperty().bind(scene.heightProperty());
            languageControllers.get(i).textAreas.get(1).minHeightProperty().bind(scene.heightProperty());
            languageControllers.get(i).textAreas.get(0).minWidthProperty().bind(scene.widthProperty().divide(2));
            languageControllers.get(i).textAreas.get(1).minWidthProperty().bind(scene.widthProperty().divide(2));
        }
    }
}
