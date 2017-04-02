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

    public void passControllers(LanguageController... args) {
        this.pythonController = (PythonController) args[0];
        this.swiftController = (SwiftController)args[1];
        this.cppHeaderController = (CppHeaderController)args[2];
        this.cppImplementationController = (CppImplementationController)args[3];
        this.cppStandaloneController = (CppStandaloneController)args[4];

    }
}
