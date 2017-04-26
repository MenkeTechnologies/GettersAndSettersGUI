package CppImplementation;

import mainPackage.GeneratorAncestor;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by jacobmenke on 4/25/17.
 */
public class CppImplementationGenerator extends GeneratorAncestor {
    @Override
    public String generateClassDeclaration(String className) {
        return null;
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


            if (inClassDef){
                Integer startingIndex = text.indexOf("{");

                text = text.substring(startingIndex);




            }
        }

        return className;


    }

    @Override
    public void filterOutPropertiesAlreadyPresent(String outputText) {

    }

    @Override
    public String generateGetters() {
        return null;
    }

    @Override
    public String generateSetters() {
        return null;
    }

    @Override
    public String generateConstructor() {
        return null;
    }

    @Override
    public String generateToString() {
        return null;
    }

    @Override
    public String generateClassEnding() {
        return null;
    }
}
