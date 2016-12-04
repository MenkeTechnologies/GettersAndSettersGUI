package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Controller implements Initializable {
    public RadioButton pythonRadioButton;
    public RadioButton cppRadioButton;
    public CheckBox getterCheckBox;
    public CheckBox setterCheckBox;
    public CheckBox constructorCheckBox;
    public TextArea inputTextArea;
    public TextArea outputTextArea;
    public CheckBox deleterCheckBox;
    public CheckBox addToClipboardCheckBox;
    ArrayList<String> properties;
    ArrayList<String> values;
    String className = "";
    String fourSpaces = "    ";
    String outputText = "";
    Scene mainScene;
    private boolean validInput = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initBindings();

        inputTextArea.setText("class Dog:\n" +
                "    __name = \"\"\n" +
                "    __age = 0\n" +
                "    __weight = 200\n" +
                "    __playFull = True");

        generateCode(new ActionEvent());
    }

    void initBindings() {
        mainScene = inputTextArea.getScene();

//
//        inputTextArea.prefWidthProperty().bind(mainScene.widthProperty().divide(2));
    }

    public void generateCode(ActionEvent actionEvent) {

        if (pythonRadioButton.isSelected()) {
            generatePython();
        }

        if (addToClipboardCheckBox.isSelected()) {
            StringSelection stringSelection = new StringSelection(outputText);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);
        }
    }

    public void generateAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("No properties found");
        alert.showAndWait();
    }

    public void genererateDuplicateAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Duplicate properties found");
        alert.showAndWait();
    }

    void generatePython() {
        properties = new ArrayList<>();
        values = new ArrayList<>();
        properties.clear();

        String inputCode = inputTextArea.getText() + "\n" + "\n";

        Scanner scanner = new Scanner(inputCode);

        while (scanner.hasNextLine()) {
            String scannerNextLine = scanner.nextLine();

            if (Pattern.compile("class .*").matcher(scannerNextLine).find()) {

                String substring = scannerNextLine.substring(scannerNextLine.indexOf("class"));
                StringTokenizer stringTokenizer = new StringTokenizer(substring);
                stringTokenizer.nextToken();
                className = stringTokenizer.nextToken();
                className = className.replace(":", "");
            }

            if (Pattern.compile("__.*").matcher(scannerNextLine).find()) {

                String property = scannerNextLine.trim().substring(2);
                if (!values.contains(property)) {
                    values.add(property);
                } else
                    genererateDuplicateAlert();

                property = property.replaceAll("[ =].*", "");
                if (!properties.contains(property)) {
                    properties.add(property);
                }
            }
        }

        if (properties.size() > 0) {

            StringBuilder sb = new StringBuilder();

            sb.append("class ").append(className).append(":").append("\n");
            for (int i = 0; i < values.size(); i++) {
                createLine(sb, "__", values.get(i));
            }
            sb.append("\n");

            outputText = sb.toString();

            if (constructorCheckBox.isSelected()) {
                writePythonConstructor();
            }
            writePythonGettersAndSetters();
            outputTextArea.setText(outputText);
        } else {
            generateAlert();
        }
    }

    public String createLine(StringBuilder sb, String... names) {

        sb.append(fourSpaces);

        for (String name : names) {
            sb.append(name);
        }

        sb.append("\n");

        return sb.toString();
    }

    public void writePythonConstructor() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(fourSpaces).append("def __init__(self,");

        for (int i = 0; i < properties.size(); i++) {
            stringBuilder.append(properties.get(i));

            if (i == properties.size() - 1) {

                stringBuilder.append("):");
            } else {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("\n");

        for (int i = 0; i < properties.size(); i++) {
            stringBuilder.append(fourSpaces).append(fourSpaces).append("self.__").append(properties.get(i)).append(" = ").append(properties.get(i)).append("\n");
        }

        stringBuilder.append("\n");

        String classdef = stringBuilder.toString();

        outputText += classdef;
    }

    public void writePythonGettersAndSetters() {

        StringBuilder stringBuilder = new StringBuilder();

        properties.forEach(key -> {
            if (getterCheckBox.isSelected()) {
                createLine(stringBuilder, "@property");
                createLine(stringBuilder, "def ", key, "(self):");
                createLine(stringBuilder, fourSpaces, "return self.__", key);
                stringBuilder.append("\n");
            }

            if (setterCheckBox.isSelected()) {
                createLine(stringBuilder, "@", key, ".setter");
                createLine(stringBuilder, "def ", key, "(self, ", key, "):");
                createLine(stringBuilder, fourSpaces, "self.__", key, " = ", key);

                stringBuilder.append("\n");
            }

            if (deleterCheckBox.isSelected()) {

                createLine(stringBuilder, "@", key, ".deleter");
                createLine(stringBuilder, "def ", key, "(self):");
                createLine(stringBuilder, fourSpaces, "del self.__", key);

                stringBuilder.append("\n");
            }
        });

        String getters = stringBuilder.toString();

        outputText += getters;
    }

    public void clearAll(ActionEvent actionEvent) {
        outputText = "";
        inputTextArea.setText("");
        outputTextArea.setText("");
    }
}
