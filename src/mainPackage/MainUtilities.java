package mainPackage;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * Created by jacobmenke on 4/1/17.
 */
public class MainUtilities {
    static String output = "";

    public static void askForProperties(LinkedHashMap<String, String> properties, ArrayList<LinkedHashMap<String, String>> propertiesArray, HashMap<String, Boolean> selected) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        VBox propertiesVBox = new VBox();

        HashMap<CheckBox, String> propertiesCheckBoxes = new HashMap<>();

        if (selected.get("getter")) {
            Label getter = new Label("Getter");

            propertiesVBox.getChildren().add(getter);

            properties.forEach((name, type) -> {
                CheckBox checkBox = new CheckBox(name);
                checkBox.setSelected(true);
                propertiesCheckBoxes.put(checkBox, "getter");
                propertiesVBox.getChildren().add(checkBox);
            });
        }

        if (selected.get("setter")) {
            Label getter = new Label("Setter");
            propertiesVBox.getChildren().add(getter);

            properties.forEach((name, type) -> {
                CheckBox checkBox = new CheckBox(name);
                checkBox.setSelected(true);
                propertiesCheckBoxes.put(checkBox, "setter");
                propertiesVBox.getChildren().add(checkBox);
            });
        }

        if (selected.get("constructor")) {
            Label getter = new Label("Constructor");
            propertiesVBox.getChildren().add(getter);

            properties.forEach((name, type) -> {
                CheckBox checkBox = new CheckBox(name);
                checkBox.setSelected(true);
                propertiesCheckBoxes.put(checkBox, "constructor");
                propertiesVBox.getChildren().add(checkBox);
            });
        }
        if (selected.get("toString")) {
            Label getter = new Label("toString");
            propertiesVBox.getChildren().add(getter);

            properties.forEach((name, type) -> {
                CheckBox checkBox = new CheckBox(name);
                propertiesCheckBoxes.put(checkBox, "toString");
                checkBox.setSelected(true);
                propertiesVBox.getChildren().add(checkBox);
            });
        }

        HBox hBox = new HBox();
        Button selectAll = new Button("Select All");
        Button selectNone = new Button("Select None");

        selectAll.setOnAction(e->{
            propertiesCheckBoxes.forEach((cb,str)->{
                cb.setSelected(true);
            });
        });

        selectNone.setOnAction(e->{
            propertiesCheckBoxes.forEach((cb,str)->{
                cb.setSelected(false);
            });
        });
        hBox.getChildren().addAll(selectAll, selectNone);

        propertiesVBox.getChildren().add(hBox);

        alert.getDialogPane().setExpanded(true);
        alert.getDialogPane().setExpandableContent(propertiesVBox);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.OK) {

            propertiesVBox.getChildren().forEach(key -> {
                if (key instanceof CheckBox) {
                    CheckBox theCheckbox = (CheckBox) key;
                    if (theCheckbox.isSelected()) {
                        switch (propertiesCheckBoxes.get(theCheckbox)) {
                            case "getter":
                                propertiesArray.get(0).put(theCheckbox.getText(), properties.get(theCheckbox.getText()));
                                break;
                            case "setter":
                                propertiesArray.get(1).put(theCheckbox.getText(), properties.get(theCheckbox.getText()));
                                break;
                            case "constructor":
                                propertiesArray.get(2).put(theCheckbox.getText(), properties.get(theCheckbox.getText()));
                                break;
                            case "toString":
                                propertiesArray.get(3).put(theCheckbox.getText(), properties.get(theCheckbox.getText()));
                                break;
                            default:
                                System.out.println("bad key at properties of Vbox");
                        }
                    }
                }
            });
        } else {

            properties.clear();
        }
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
