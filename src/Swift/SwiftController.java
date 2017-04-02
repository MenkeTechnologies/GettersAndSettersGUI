package Swift;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import mainPackage.LanguageController;
import mainPackage.MainUtilities;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by jacobmenke on 4/1/17.
 */
public class SwiftController extends LanguageController implements Initializable {
    public TextArea swiftOutputTextArea;
    public TextArea swiftInputTextArea;
    public SwiftGenerator swiftGenerator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        textAreas.addAll(Arrays.asList(swiftInputTextArea, swiftOutputTextArea));

        swiftGenerator = new SwiftGenerator();
    }

    public String generateSwift(HashMap<String, Boolean> selected) {

        String outputText = "";

        String className = swiftGenerator.parseProperties(swiftInputTextArea.getText());

        if (!className.equals(null)){

            outputText+= swiftGenerator.generateClassDeclaration(className);

            if (selected.get("getter")){

                outputText += swiftGenerator.generateGetters();
            }

            if (selected.get("setter")){
                outputText += swiftGenerator.generateSetters();
            }

            if (selected.get("constructor")){
                outputText += swiftGenerator.generateConstructor();
            }

            if (selected.get("toString")){
                outputText += swiftGenerator.generateToString();
            }

            outputText += swiftGenerator.generateClassEnding();
        }

        swiftOutputTextArea.setText(outputText);

        if (selected.get("clipboard")){
            MainUtilities.copyToClipboard(outputText);
        }

        return outputText;

    }
}
