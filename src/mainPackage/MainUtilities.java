package mainPackage;

import Swift.SwiftGenerator;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.*;

/**
 * Created by jacobmenke on 4/1/17.
 */
public class MainUtilities {
    static String output = "";

    static private void fillMainHashMapWithAllNamesAndTypes(GeneratorAncestor generatorAncestor) {
        generatorAncestor.methodTypeAndPropertiesMap.put("getter", generatorAncestor.properties);
        generatorAncestor.methodTypeAndPropertiesMap.put("setter", generatorAncestor.properties);
        generatorAncestor.methodTypeAndPropertiesMap.put("constructor", generatorAncestor.properties);
        generatorAncestor.methodTypeAndPropertiesMap.put("toString", generatorAncestor.properties);
    }


    public static String mainRoutine(HashMap<String, Boolean> selected, boolean dialog, String previousOutputText, LanguageController languageController, GeneratorAncestor languageGenerator) throws Exception{

            String outputText = "";

            MainUtilities.clearAllMaps(languageGenerator);

            try {
                //props stored as name, type in swiftgenerator.properties map
                languageController.className = languageGenerator.parseProperties(languageController.textAreas.get(0).getText());
            } catch (Exception e) {
                throw new Exception();
            }
            if (!languageController.className.equals(null)) {

                if (dialog) {
                    if (!previousOutputText.equals("")) {
                        fillMainHashMapWithAllNamesAndTypes(languageGenerator);
                        //props already present go into swiftgenerator.setterproperites
                        //props not present go into main hash map
                        languageGenerator.filterOutPropertiesAlreadyPresent(previousOutputText);

                    } else {
                        languageGenerator.toStringProperties.clear();
                        fillMainHashMapWithAllNamesAndTypes(languageGenerator);

                    }

                    MainUtilities.askForProperties(languageGenerator.methodTypeAndPropertiesMap, selected, languageGenerator);
                } else {
                    //put in to main hash map all names and type
                    fillMainHashMapWithAllNamesAndTypes(languageGenerator);
                    languageGenerator.setterProperties.putAll(languageGenerator.methodTypeAndPropertiesMap.get("setter"));
                    languageGenerator.getterProperties.putAll(languageGenerator.methodTypeAndPropertiesMap.get("getter"));
                    languageGenerator.constructorProperties.putAll(languageGenerator.methodTypeAndPropertiesMap.get("constructor"));
                    languageGenerator.toStringProperties.putAll(languageGenerator.methodTypeAndPropertiesMap.get("toString"));
                }

                outputText += languageGenerator.generateClassDeclaration(languageController.className);

                if (selected.get("constructor")) {

                    outputText += languageGenerator.generateConstructor();
                }

                if (selected.get("getter")) {

                    //generate getters based on getters hashmap
                    outputText += languageGenerator.generateGetters();
                }

                if (selected.get("setter")) {
                    outputText += languageGenerator.generateSetters();
                }

                if (selected.get("toString")) {
                    if (!languageGenerator.toStringProperties.isEmpty()) {
                        outputText += languageGenerator.generateToString();
                    }
                }

                outputText += languageGenerator.generateClassEnding();
            }

            if (!languageGenerator.properties.isEmpty()) {
                languageController.textAreas.get(1).setText(outputText);
            }

            if (selected.get("clipboard")) {
                MainUtilities.copyToClipboard(outputText);
            }

            return outputText;
        }






    public static void clearAllMaps(GeneratorAncestor generatorAncestor) {
        generatorAncestor.properties.clear();
        generatorAncestor.methodTypeAndPropertiesMap.clear();
        generatorAncestor.getterProperties.clear();
        generatorAncestor.setterProperties.clear();
        generatorAncestor.constructorProperties.clear();
        generatorAncestor.toStringProperties.clear();
    }

    public static void askForProperties(LinkedHashMap<String, LinkedHashMap<String, String>> methodTypeAndPropertiesMap, HashMap<String, Boolean> selected, GeneratorAncestor generatorAncestor) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        VBox propertiesVBox = new VBox();

        HashMap<CheckBox, String> propertiesCheckBoxes = new HashMap<>();

        if (selected.get("getter")) {
            Label getter = new Label("Getter");

            propertiesVBox.getChildren().add(getter);

            methodTypeAndPropertiesMap.get("getter").forEach((name, type) -> {
                CheckBox checkBox = new CheckBox(name);
                checkBox.setSelected(true);
                propertiesCheckBoxes.put(checkBox, "getter");
                propertiesVBox.getChildren().add(checkBox);
            });
        }

        if (selected.get("setter")) {
            Label getter = new Label("Setter");
            propertiesVBox.getChildren().add(getter);

            methodTypeAndPropertiesMap.get("setter").forEach((name, type) -> {
                CheckBox checkBox = new CheckBox(name);
                checkBox.setSelected(true);
                propertiesCheckBoxes.put(checkBox, "setter");
                propertiesVBox.getChildren().add(checkBox);
            });
        }

        if (selected.get("constructor")) {
            Label getter = new Label("Constructor");
            propertiesVBox.getChildren().add(getter);

            methodTypeAndPropertiesMap.get("constructor").forEach((name, type) -> {
                CheckBox checkBox = new CheckBox(name);
                checkBox.setSelected(true);
                propertiesCheckBoxes.put(checkBox, "constructor");
                propertiesVBox.getChildren().add(checkBox);
            });
        }
        if (selected.get("toString")) {
            Label getter = new Label("toString");
            propertiesVBox.getChildren().add(getter);

            methodTypeAndPropertiesMap.get("toString").forEach((name, type) -> {
                CheckBox checkBox = new CheckBox(name);
                propertiesCheckBoxes.put(checkBox, "toString");
                checkBox.setSelected(true);
                propertiesVBox.getChildren().add(checkBox);
            });
        }

        HBox hBox = new HBox();
        Button selectAll = new Button("Select All");
        Button selectNone = new Button("Select None");

        selectAll.setOnAction(e -> {
            propertiesCheckBoxes.forEach((cb, str) -> {
                cb.setSelected(true);
            });
        });

        selectNone.setOnAction(e -> {
            propertiesCheckBoxes.forEach((cb, str) -> {
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
                                if (theCheckbox.isSelected()) {
                                    Map<String, String> getterMap = methodTypeAndPropertiesMap.get("getter");

                                    generatorAncestor.getterProperties.put(theCheckbox.getText(), getterMap.get(theCheckbox.getText()));
                                }
                                break;
                            case "setter":
                                if (theCheckbox.isSelected()) {
                                    Map<String, String> setterMap = methodTypeAndPropertiesMap.get("setter");

                                    generatorAncestor.setterProperties.put(theCheckbox.getText(), setterMap.get(theCheckbox.getText()));
                                }
                                break;
                            case "constructor":
                                if (theCheckbox.isSelected()) {
                                    Map<String, String> constructorMap = methodTypeAndPropertiesMap.get("constructor");

                                    generatorAncestor.constructorProperties.put(theCheckbox.getText(), constructorMap.get(theCheckbox.getText()));
                                }
                                break;
                            case "toString":

                                if (theCheckbox.isSelected()) {
                                    Map<String, String> toStringMap = methodTypeAndPropertiesMap.get("toString");

                                    generatorAncestor.toStringProperties.put(theCheckbox.getText(), toStringMap.get(theCheckbox.getText()));
                                }
                                break;
                            default:
                                System.out.println("bad key at properties of Vbox");
                        }
                    }
                }
            });

            if (generatorAncestor.toStringProperties.isEmpty()){
                generatorAncestor.toStringProperties = SwiftGenerator.previousToStringMap;
            }

        } else {

            throw new Exception("Done");
        }
    }

    public static void copyToClipboard(String outputText) {
        StringSelection stringSelection = new StringSelection(outputText);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
    }

    public static void generateAlert(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(error);
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
