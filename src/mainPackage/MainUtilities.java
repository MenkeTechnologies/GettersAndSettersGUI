package mainPackage;

import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by jacobmenke on 4/1/17.
 */
public class MainUtilities {
    static String output = "";

    public static void askForProperties(LinkedHashMap<String, String> properties, HashMap<String, Boolean> selected) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        VBox propertiesVBox = new VBox();

        if (selected.get("getter")) {
            Label getter = new Label("Getter");

            propertiesVBox.getChildren().add(getter);

            properties.forEach((name, type) -> {
                CheckBox checkBox = new CheckBox(name);
                checkBox.setSelected(true);
                propertiesVBox.getChildren().add(checkBox);
            });

        }

        if (selected.get("setter")) {
            Label getter = new Label("Setter");
            propertiesVBox.getChildren().add(getter);

            properties.forEach((name, type) -> {
                CheckBox checkBox = new CheckBox(name);
                checkBox.setSelected(true);
                propertiesVBox.getChildren().add(checkBox);
            });
        }

        if (selected.get("constructor")) {
            Label getter = new Label("Constructor");
            propertiesVBox.getChildren().add(getter);

            properties.forEach((name, type) -> {
                CheckBox checkBox = new CheckBox(name);
                checkBox.setSelected(true);
                propertiesVBox.getChildren().add(checkBox);
            });

        }
        if (selected.get("toString")) {
            Label getter = new Label("toString");
            propertiesVBox.getChildren().add(getter);

            properties.forEach((name, type) -> {
                CheckBox checkBox = new CheckBox(name);
                checkBox.setSelected(true);
                propertiesVBox.getChildren().add(checkBox);
            });

        }


        alert.getDialogPane().setExpanded(true);
        alert.getDialogPane().setExpandableContent(propertiesVBox);
        alert.showAndWait();
    }

    public static void copyToClipboard(String outputText) {
        StringSelection stringSelection = new StringSelection(outputText);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
    }

    public static void generateAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("No properties found");
        alert.showAndWait();
    }

    public static void genererateDuplicateAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Duplicate properties found");
        alert.showAndWait();
    }

    public static String capitalizeFirstLetter(String name) {

        if (name.charAt(0) >= 97) {
            String uppercaseFirst = name.substring(0, 1).toUpperCase();

            return uppercaseFirst + name.substring(1);
        } else {
            return name;
        }
    }
}
