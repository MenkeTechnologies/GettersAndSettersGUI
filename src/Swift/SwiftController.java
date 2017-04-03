package Swift;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import mainPackage.LanguageController;
import mainPackage.MainUtilities;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by jacobmenke on 4/1/17.
 */
public class SwiftController extends LanguageController implements Initializable {
    public TextArea swiftOutputTextArea;
    public TextArea swiftInputTextArea;
    public SwiftGenerator swiftGenerator;
    public SplitPane swiftSplitPane;
    int i = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        swiftInputTextArea.setText("class Dog{\n" +
                "    var name : String = \"dogs\"\n" +
                "    var age : Int\n" +
                "    var weight : Int = 55\n" +
                "    var cool : Double\n" +
                "    ");

        textAreas.addAll(Arrays.asList(swiftInputTextArea, swiftOutputTextArea));

        mainSplitPane = swiftSplitPane;

        swiftGenerator = new SwiftGenerator();
    }

    public String generateSwift(HashMap<String, Boolean> selected, boolean dialog, String previousOutputText) {

        String outputText = "";

        String className = swiftGenerator.parseProperties(swiftInputTextArea.getText());

        if (!className.equals(null)) {

            if (dialog) {
                clearAllProperties();
                MainUtilities.askForProperties(swiftGenerator.properties, swiftGenerator.propertiesArray, selected);
                addPropertiesAlreadySelected(previousOutputText);
            } else {
                initAllProperties();
            }

            outputText += swiftGenerator.generateClassDeclaration(className);

            if (selected.get("constructor")) {

                outputText += swiftGenerator.generateConstructor();
            }

            if (selected.get("getter")) {

                outputText += swiftGenerator.generateGetters();
            }

            if (selected.get("setter")) {
                outputText += swiftGenerator.generateSetters();
            }

            if (selected.get("toString")) {
                if (!swiftGenerator.toStringProperties.isEmpty()) {
                    outputText += swiftGenerator.generateToString();
                }
            }

            outputText += swiftGenerator.generateClassEnding();
        }

        if (!swiftGenerator.properties.isEmpty()) {
            swiftOutputTextArea.setText(outputText);
        }

        if (selected.get("clipboard")) {
            MainUtilities.copyToClipboard(outputText);
        }

        return outputText;
    }

    private void addPropertiesAlreadySelected(String outputText) {
//
//        Scanner myScanner = new Scanner(outputText);
//
//        while (myScanner.hasNextLine()){
//            String line = myScanner.nextLine();
//            if (Pattern.compile(".*(set|get)"))
//
//
//        }
//        String type;
//        for (int i = 0; i < swiftGenerator.propertiesArray.size(); i++) {
//
//            if (i == 0){
//                type = "getter";
//            } else if (i == 1){
//                type = "setter";
//            } else  if (i == 2){
//                type = "constructor";
//            }
//
//
//            swiftGenerator.propertiesArray.get(i).forEach((name, type) -> {
//
//
//            });
//
//        }
    }

    private void clearAllProperties() {
        for (i = 0; i < swiftGenerator.propertiesArray.size(); i++) {
            swiftGenerator.propertiesArray.get(i).clear();
        }
    }

    private void initAllProperties() {
        for (i = 0; i < swiftGenerator.propertiesArray.size(); i++) {
            swiftGenerator.propertiesArray.get(i).clear();
            swiftGenerator.properties.forEach((name, type) -> {
                swiftGenerator.propertiesArray.get(i).put(name, type);
            });
        }
    }

    public void generateSwiftWithOptions(HashMap<String, Boolean> options, String outputText) {
        generateSwift(options, true, outputText);
    }
}
