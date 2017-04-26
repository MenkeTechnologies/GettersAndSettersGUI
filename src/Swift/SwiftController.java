package Swift;

import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import mainPackage.LanguageController;
import mainPackage.MainUtilities;

import java.net.URL;
import java.util.*;
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
    static LinkedHashMap<String, String> outputMap = new LinkedHashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        swiftInputTextArea.setText("var str = \"Hello, playground\"\n" +
                "\n" +
                "\n" +
                "                           class Dog{var\n" +
                "                            \n" +
                "                            \n" +
                "                            \n" +
                "                            \n" +
                "                            \n" +
                "                            name\n" +
                "                                \n" +
                "                                :\n" +
                "                                \n" +
                "                                \n" +
                "                                \n" +
                "                            String=\"dogs are cool\" ; var age : Int = 25\n" +
                "    var weight:Int = 55\n" +
                "    var cool : Double = 2.5\n" +
                "    \n" +
                "}");

        textAreas.addAll(Arrays.asList(swiftInputTextArea, swiftOutputTextArea));

        mainSplitPane = swiftSplitPane;

        swiftGenerator = new SwiftGenerator();
    }

    public String generateSwift(HashMap<String, Boolean> selected, boolean dialog, String previousOutputText) throws Exception {

        return MainUtilities.mainRoutine(selected, dialog, previousOutputText, this, swiftGenerator);
    }

     void filterOutPropertiesAlreadyPresent(String outputText) {

        LinkedHashMap<String, LinkedHashMap<String, String>> methodTypeAndPropertiesMapFinal = new LinkedHashMap<>();

        swiftGenerator.methodTypeAndPropertiesMap.forEach((methodType, propertiesMap) -> {

            switch (methodType) {
                case "getter":
                    propertiesMap.forEach((propertyName, propertyType) -> {
                        createMapFromTextGetterSetter(outputText, propertyName, propertyType, "get", outputMap);
                    });
                    methodTypeAndPropertiesMapFinal.put("getter", new LinkedHashMap<>(outputMap));
                    outputMap.clear();

                    break;
                case "setter":
                    propertiesMap.forEach((propertyName, propertyType) -> {
                        createMapFromTextGetterSetter(outputText, propertyName, propertyType, "set", outputMap);
                    });
                    methodTypeAndPropertiesMapFinal.put("setter", new LinkedHashMap<>(outputMap));
                    outputMap.clear();
                    break;
                case "constructor":
                    propertiesMap.forEach((propertyName, propertyType) -> {
                        outputMap = createMapFromTextConstructor(outputText, propertyName, propertyType);
                    });

                    methodTypeAndPropertiesMapFinal.put("constructor", new LinkedHashMap<>(outputMap));
                    outputMap.clear();
                    break;
                case "toString":
                    //just search for toString, if present then add to existingToString
                    propertiesMap.forEach((propertyName, propertyType) -> {
                        createMapFromTextGetterSetter(outputText, propertyName, propertyType, "toString", outputMap);
                    });
                    methodTypeAndPropertiesMapFinal.put("toString", new LinkedHashMap<>(propertiesMap));
                    outputMap.clear();
                    break;
                default:
                    break;
            }
        });

        swiftGenerator.methodTypeAndPropertiesMap = methodTypeAndPropertiesMapFinal;
    }

    private LinkedHashMap<String, String> createMapFromTextConstructor(String outputText, String propertyName, String propertyType) {
        LinkedHashMap<String, String> outputMap = new LinkedHashMap<>();

        boolean match = false;

        Scanner myScanner = new Scanner(outputText);

        while (myScanner.hasNextLine()) {
            String line = myScanner.nextLine();
            Pattern pattern = Pattern.compile(".*init.*()");

            if (pattern.matcher(line).matches()) {
                match = true;
                break;
            }
        }

        if (!match) {
            outputMap.put(propertyName, propertyType);
        }

        return outputMap;
    }

    private LinkedHashMap<String, String> createMapFromTextGetterSetter(String outputText, String propertyName, String propertyType, String type, LinkedHashMap<String, String> outputMap) {

        boolean match = false;

        Scanner myScanner = new Scanner(outputText);

        String UppercaseFirstLetterName = MainUtilities.capitalizeFirstLetter(propertyName);

        while (myScanner.hasNextLine()) {
            String line = myScanner.nextLine();
            Pattern pattern = null;
            if (type.equals("toString")) {
                pattern = Pattern.compile(".*" + type + ".*");
            } else {
                pattern = Pattern.compile(".*" + type + UppercaseFirstLetterName + ".*");
            }

            if (pattern.matcher(line).matches()) {
                match = true;
                if (type.equals("get")) {
                    swiftGenerator.getterProperties.put(propertyName, propertyType);
                } else if (type.equals("set")) {
                    swiftGenerator.setterProperties.put(propertyName, propertyType);
                } else {
                    SwiftGenerator.toStringPresent = true;
                    break;
                }
                break;
            }
        }

        if (!match) {
            outputMap.put(propertyName, propertyType);
        }

        return outputMap;
    }

    private void clearMainHashMap() {

        swiftGenerator.methodTypeAndPropertiesMap.clear();
    }

    private void fillMainHashMapWithAllNamesAndTypes() {
        swiftGenerator.methodTypeAndPropertiesMap.put("getter", swiftGenerator.properties);
        swiftGenerator.methodTypeAndPropertiesMap.put("setter", swiftGenerator.properties);
        swiftGenerator.methodTypeAndPropertiesMap.put("constructor", swiftGenerator.properties);
        swiftGenerator.methodTypeAndPropertiesMap.put("toString", swiftGenerator.properties);
    }

    public void generateSwiftWithOptions(HashMap<String, Boolean> options, String outputText) throws Exception {
        generateSwift(options, true, outputText);
    }
}
