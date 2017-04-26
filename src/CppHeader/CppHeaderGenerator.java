package CppHeader;

import mainPackage.GeneratorAncestor;
import mainPackage.MainUtilities;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by jacobmenke on 4/25/17.
 */
public class CppHeaderGenerator extends GeneratorAncestor {
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

            //have found class definition

            if (inClassDef) {

                Integer startingIndex = text.indexOf("{");

                text = text.substring(startingIndex);

                StringTokenizer st1 = new StringTokenizer(text, ";");
                String firstSemicolonDelimited = st1.nextToken();

                StringTokenizer st2 = new StringTokenizer(firstSemicolonDelimited);

                while (st2.hasMoreTokens()){
                    String token = st2.nextToken();

                    if (token.equals("private:")){

                    }
                    type = st2.nextToken();

                    text = text.substring(text.indexOf(type)+type.length());

                    StringTokenizer commaTokenizer = new StringTokenizer(",");

                    propertyName = st2.nextToken();

                    if (propertyName.contains(";")){
                        propertyName.replace(";","");
                        properties.put(propertyName,type);

                    } else if (propertyName.contains(",")){



                        propertyName = propertyName.substring(propertyName.indexOf(","));
                        properties.put(propertyName,type);

                        propertyName = st2.nextToken();
                    }

                }

                }



                //find first variable;


            }






//                Integer indexVar = text.indexOf("var");
//
//                if (text.indexOf("let") > 0 && text.indexOf("let") < text.indexOf("var")){
//                    indexVar = text.indexOf("let");
//                }
//
//
//                while (indexVar > 0) {
//
//                    text = text.substring(indexVar + 3);
//
//                    StringTokenizer st1 = new StringTokenizer(text);
//
//                    propertyName = st1.nextToken();
//
//                    if (propertyName.contains(":")) {
//                        propertyName = propertyName.substring(0, propertyName.indexOf(":"));
//                    }
//
//                    text = text.substring(text.indexOf(propertyName) + propertyName.length());
//
//                    StringTokenizer st2 = new StringTokenizer(text);
//
//                    String colon = st2.nextToken();
//
//                    if (colon.charAt(0) != ':') {
//                        faultyProps.add(propertyName);
//                    }
//
//                    text = text.substring(text.indexOf(colon) + 1);
//
//                    StringTokenizer st3 = new StringTokenizer(text);
//
//                    type = st3.nextToken();
//
//                    if (type.equals("=") || type.equals("var")) {
//                        faultyProps.add(propertyName);
//                    }
//
//                    if (type.contains("=")) {
//                        type = type.substring(0, type.indexOf("="));
//                    }
//
//                    if (type.contains(";")) {
//                        type = type.substring(0, type.indexOf(";"));
//                    }
//
//                    if (text.indexOf("let") > 0 && text.indexOf("let") < text.indexOf("var")){
//                        indexVar = text.indexOf("let");
//                    } else {
//                        indexVar = text.indexOf("var");
//
//                    }
//
//
//
//
//                    if (propertyName != null && type != null) {
//                        properties.put(propertyName, type);
//                        initialValues.put(propertyName, "null");
//                    }
//                }
//            }
//        }
//
//        if (faultyProps.size() > 0) {
//            StringBuilder sb = new StringBuilder();
//
//            sb.append("Missing Type for ");
//
//            for (int i = 0; i < faultyProps.size(); i++) {
//                sb.append("property : \"" + faultyProps.get(i) + "\"");
//
//                if (i == faultyProps.size() - 1) {
//                    sb.append(".");
//                } else {
//                    sb.append(", ");
//                }
//            }
//
//            MainUtilities.generateAlert(sb.toString());
//            throw new Exception();
//        }

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
