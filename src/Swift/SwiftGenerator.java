package Swift;

import mainPackage.Constants;
import mainPackage.GeneratorAncestor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

import static mainPackage.Constants.FOUR_SPACES;
import static mainPackage.MainUtilities.capitalizeFirstLetter;

/**
 * Created by jacobmenke on 4/1/17.
 */
public class SwiftGenerator extends GeneratorAncestor {
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

    @Override
    public String parseProperties(String text) {
        properties.clear();

        String className = null;

        boolean inClassDef = false;

        try (Scanner scanner = new Scanner(text)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String propertyName = null;
                String type = null;
                String initialValue = null;
                StringTokenizer stringTokenizer = new StringTokenizer(line);

                if (stringTokenizer.hasMoreTokens()) {
                    String token = stringTokenizer.nextToken();
                    System.out.println("the token is " + token);
                    if (token.equals("class")) {
                        className = stringTokenizer.nextToken().replace("{", "");
                        System.out.println("the class is " + className);
                        inClassDef = true;
                    }
                    if (inClassDef) {
                        if (token.equals("var")) {
                            propertyName = line.substring(line.indexOf(stringTokenizer.nextToken()));
                            if (line.contains(":")) {
                                propertyName = propertyName.substring(0, propertyName.indexOf(":")).trim();
                                if (line.contains("=")) {
                                    type = line.substring(line.indexOf(":") + 1, line.indexOf("=")).trim();
                                    initialValue = line.substring(line.indexOf("=") + 1).trim();
                                    while (checkForMultLineInitial(initialValue)) {
                                        StringBuilder sb = new StringBuilder(initialValue);
                                        sb.append("\n").append(FOUR_SPACES).append(scanner.nextLine().trim());
                                        initialValue = sb.toString();
                                    }
                                } else {
                                    type = line.substring(line.indexOf(":") + 1).trim();
                                    initialValue = null;
                                }
                            } else {
                                System.out.println("THROW EXCEPTION BECAUSE NEED TO KNOW TYPE");
                            }
                        }
                    }
                }
                if (propertyName != null && type != null) {
                    properties.put(propertyName, type);
                    initialValues.put(propertyName, initialValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return className;
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

        if (!constructorProperties.isEmpty()){
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

        return sb.toString();
    }

    @Override
    public String generateClassEnding() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("}");

        return sb.toString();
    }
}
