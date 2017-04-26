package Swift;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import mainPackage.Constants;
import mainPackage.GeneratorAncestor;
import mainPackage.MainUtilities;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static mainPackage.Constants.FOUR_SPACES;
import static mainPackage.MainUtilities.capitalizeFirstLetter;

/**
 * Created by jacobmenke on 4/1/17.
 */
public class SwiftGenerator extends GeneratorAncestor {

    public static boolean toStringPresent = false;

    public  static LinkedHashMap<String, String> previousToStringMap = new LinkedHashMap<>();


    @Override
    public String generateClassDeclaration(String className) {

        StringBuilder sb = new StringBuilder();

        sb.append("class ").append(className).append("{").append("\n");

        properties.forEach((name, type) -> {
            sb.append(FOUR_SPACES).append("var ").append(name).append(" : ").append(type);

            if (initialValues.get(name) != null) {
                sb.append(" = ").append(initialValues.get(name));
            }

            System.out.println("the INIT Value for " + name + " is " + initialValues.get(name));
            sb.append("\n");
        });

        sb.append("\n");

        return sb.toString();
    }

    public static void main(String[] args) {

        String text = "\n" +
                "import UIKit\n" +
                "\n" +
                "var str = \"Hello, playground\"\n" +
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
                "                            String = \"dogs are cool\" ;\n" +
                "                            \n" +
                "                            var age : Int = 25\n" +
                "    var weight : Int = 55\n" +
                "    var cool : Double = 2.5\n;var sex : CLLocation = 25" +
                "    \n" +
                "}";

        try {
            new SwiftGenerator().parseProperties(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String parseProperties(String text) throws Exception {

        String className = null;
        boolean inClassDef = false;

        String totalText = text;
        String propertyName = null;
        String type = null;
        String initialValue = null;
        String line = null;

        ArrayList<String> faultyProps = new ArrayList<>();

        try (Scanner scanner = new Scanner(text)) {
            scanner:
            while (scanner.hasNextLine()) {

                line = scanner.nextLine();

                if (!inClassDef) {

                    StringTokenizer stringTokenizer = new StringTokenizer(line);
                    classLoop:
                    while (stringTokenizer.hasMoreTokens()) {
                        String token = stringTokenizer.nextToken();
                        if (token.equals("class")) {

                            className = stringTokenizer.nextToken();

                            if (className.contains("{")) {
                                className = className.substring(0, className.indexOf("{"));
                            }
                            text = text.substring(text.indexOf(className) + className.length());

                            inClassDef = true;
                            break scanner;
                        }
                    }
                }
            }

            //have found class definition

            if (inClassDef) {

                Integer indexVar = text.indexOf("var");

                if (text.indexOf("let") > 0 && text.indexOf("let") < text.indexOf("var")){
                    indexVar = text.indexOf("let");
                }


                while (indexVar > 0) {

                    text = text.substring(indexVar + 3);

                    StringTokenizer st1 = new StringTokenizer(text);

                    propertyName = st1.nextToken();

                    if (propertyName.contains(":")) {
                        propertyName = propertyName.substring(0, propertyName.indexOf(":"));
                    }

                    text = text.substring(text.indexOf(propertyName) + propertyName.length());

                    StringTokenizer st2 = new StringTokenizer(text);

                    String colon = st2.nextToken();

                    if (colon.charAt(0) != ':') {
                        faultyProps.add(propertyName);
                    }

                    text = text.substring(text.indexOf(colon) + 1);

                    StringTokenizer st3 = new StringTokenizer(text);

                    type = st3.nextToken();

                    if (type.equals("=") || type.equals("var")) {
                        faultyProps.add(propertyName);
                    }

                    if (type.contains("=")) {
                        type = type.substring(0, type.indexOf("="));
                    }

                    if (type.contains(";")) {
                        type = type.substring(0, type.indexOf(";"));
                    }

                    if (text.indexOf("let") > 0 && text.indexOf("let") < text.indexOf("var")){
                        indexVar = text.indexOf("let");
                    } else {
                        indexVar = text.indexOf("var");

                    }




                    if (propertyName != null && type != null) {
                        properties.put(propertyName, type);
                        initialValues.put(propertyName, "null");
                    }
                }
            }
        }

        if (faultyProps.size() > 0) {
            StringBuilder sb = new StringBuilder();

            sb.append("Missing Type for ");

            for (int i = 0; i < faultyProps.size(); i++) {
                sb.append("property : \"" + faultyProps.get(i) + "\"");

                if (i == faultyProps.size() - 1) {
                    sb.append(".");
                } else {
                    sb.append(", ");
                }
            }

            MainUtilities.generateAlert(sb.toString());
            throw new Exception();
        }

        return className;
    }

    @Override
    public void filterOutPropertiesAlreadyPresent(String outputText) {

    }

    boolean checkForMultLineInitial(String line) {
        if (line.charAt(line.length() - 1) == '+') {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String generateGetters() {

        StringBuilder sb = new StringBuilder();

        getterProperties.forEach((name, type) -> {

            sb.append(FOUR_SPACES).append("func get").append(capitalizeFirstLetter(name)).append("() -> ").append(type).append(" {\n")
                    .append(FOUR_SPACES).append(FOUR_SPACES).append("return self.").append(name)
                    .append("\n").append(FOUR_SPACES).append("}").append("\n");
        });

        return sb.toString();
    }

    @Override
    public String generateSetters() {

        StringBuilder sb = new StringBuilder();
        setterProperties.forEach((name, type) -> {

            sb.append(FOUR_SPACES).append("func set").append(capitalizeFirstLetter(name)).append("(").append(name).append(" : ").append(type).append("){\n")
                    .append(FOUR_SPACES).append(FOUR_SPACES).append("self.").append(name)
                    .append(" = ").append(name).append("\n").append(FOUR_SPACES).append("}").append("\n");
        });

        return sb.toString();
    }

    @Override
    public String generateConstructor() {
        StringBuilder sb = new StringBuilder();

        sb.append(FOUR_SPACES).append("init(");

        constructorProperties.forEach((name, type) -> {
            sb.append(name).append(": ").append(type).append(", ");
        });

        if (!constructorProperties.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }

        sb.append("){\n");

        constructorProperties.forEach((name, type) -> {
            sb.append(FOUR_SPACES).append(FOUR_SPACES).append("self.").append(name).append(" = ").append(name).append("\n");
        });
        sb.append(FOUR_SPACES).append("}").append("\n");

        return sb.toString();
    }

    @Override
    public String generateToString() {

        StringBuilder sb = new StringBuilder();
        sb.append(FOUR_SPACES).append("func toString() -> String{").append("\n");

        sb.append(FOUR_SPACES).append(FOUR_SPACES).append("return \"");

        toStringProperties.forEach((name, type) -> {
            sb.append(name).append(" = \\(self.").append(name).append("), ");
        });

        sb.setLength(sb.length() - 2);

        sb.append("\"").append("\n");
        sb.append(FOUR_SPACES).append("}");

        previousToStringMap = new LinkedHashMap<>(toStringProperties);


        return sb.toString();
    }

    @Override
    public String generateClassEnding() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("}");

        return sb.toString();
    }
}
