package Python;

import javafx.fxml.Initializable;
import mainPackage.MainUtilities;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import mainPackage.LanguageController;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * Created by jacobmenke on 4/1/17.
 */
public class PythonController extends LanguageController implements Initializable {
    public CheckBox pythonGetterCheckBox;
    public CheckBox pythonSetterCheckBox;
    public CheckBox pythonConstructorCheckBox1;
    public CheckBox pythonDeleterCheckBox;
    public CheckBox pythonAddToClipboardCheckBox;
    public RadioButton pythonPropertiesRadio;
    public RadioButton pythonCustomRadio;
    public TextArea pythonInputTextArea;
    public TextArea pythonOutputTextArea;
    ArrayList<String> properties;
    ArrayList<String> values;
    String className = "";
    String fourSpaces = "    ";
    String outputText = "";
    Scene mainScene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pythonInputTextArea.setText("class Dog:\n" +
                "    __name = \"\"\n" +
                "    __age = 0\n" +
                "    __weight = 200\n" +
                "    __playFull = True");


        System.out.println("Python Controller");
    }

    public void generatePython(boolean toClipboard) {
        properties = new ArrayList<>();
        values = new ArrayList<>();
        properties.clear();

        String inputCode = pythonInputTextArea.getText() + "\n" + "\n";

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
                    MainUtilities.genererateDuplicateAlert();

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
                PythonUtilites.createLine(sb, "__", values.get(i));
            }
            sb.append("\n");

            outputText = sb.toString();

            if (pythonConstructorCheckBox1.isSelected()) {
                outputText += PythonUtilites.writePythonConstructor(properties);
            }
            outputText += PythonUtilites.writePythonGettersAndSetters(properties, pythonGetterCheckBox.isSelected(), pythonSetterCheckBox.isSelected(), pythonDeleterCheckBox.isSelected());
            pythonOutputTextArea.setText(outputText);
            if (toClipboard) {
                MainUtilities.copyToClipboard(outputText);
            }

        } else {
            MainUtilities.generateAlert();
        }
    }



    public void initBindings() {

        mainScene = pythonInputTextArea.getScene();
        pythonInputTextArea.minWidthProperty().bind(mainScene.widthProperty().divide(2));
        pythonOutputTextArea.minWidthProperty().bind(mainScene.widthProperty().divide(2));
        pythonInputTextArea.minHeightProperty().bind(mainScene.heightProperty());


    }


}
