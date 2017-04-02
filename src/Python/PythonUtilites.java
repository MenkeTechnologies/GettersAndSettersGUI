package Python;

import mainPackage.Constants;

import java.util.ArrayList;

/**
 * Created by jacobmenke on 4/1/17.
 */
public class PythonUtilites {


    static public String writePythonGettersAndSetters(ArrayList<String> properties, boolean getter, boolean setter, boolean deleter) {

        String fourSpaces = Constants.FOUR_SPACES;

        StringBuilder stringBuilder = new StringBuilder();

        properties.forEach(key -> {
            if (getter) {
                createLine(stringBuilder, "@property");
                createLine(stringBuilder, "def ", key, "(self):");
                createLine(stringBuilder, fourSpaces, "return self.__", key);
                stringBuilder.append("\n");
            }

            if (setter) {
                createLine(stringBuilder, "@", key, ".setter");
                createLine(stringBuilder, "def ", key, "(self, ", key, "):");
                createLine(stringBuilder, fourSpaces, "self.__", key, " = ", key);

                stringBuilder.append("\n");
            }

            if (deleter) {

                createLine(stringBuilder, "@", key, ".deleter");
                createLine(stringBuilder, "def ", key, "(self):");
                createLine(stringBuilder, fourSpaces, "del self.__", key);

                stringBuilder.append("\n");
            }
        });


        return stringBuilder.toString();
    }

    public static String createLine(StringBuilder sb, String... names) {

        sb.append(Constants.FOUR_SPACES);

        for (String name : names) {
            sb.append(name);
        }

        sb.append("\n");

        return sb.toString();
    }



    public static String  writePythonConstructor(ArrayList<String> properties) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(Constants.FOUR_SPACES).append("def __init__(self,");

        for (int i = 0; i < properties.size(); i++) {
            stringBuilder.append(properties.get(i));

            if (i == properties.size() - 1) {

                stringBuilder.append("):");
            } else {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("\n");

        for (int i = 0; i < properties.size(); i++) {
            stringBuilder.append(Constants.FOUR_SPACES).append(Constants.FOUR_SPACES).append("self.__").append(properties.get(i)).append(" = ").append(properties.get(i)).append("\n");
        }

        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

}
