package mainPackage;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by jacobmenke on 4/1/17.
 */
abstract public class GeneratorAncestor {

    public LinkedHashMap<String, String> properties = new LinkedHashMap<>();

    public LinkedHashMap<String, String> getterProperties = new LinkedHashMap<>();
    public LinkedHashMap<String, String> setterProperties = new LinkedHashMap<>();
    public LinkedHashMap<String, String> constructorProperties = new LinkedHashMap<>();
    public LinkedHashMap<String, String> toStringProperties = new LinkedHashMap<>();
    public LinkedHashMap<String,LinkedHashMap<String, String>> methodTypeAndPropertiesMap = new LinkedHashMap<>();

    public GeneratorAncestor() {

    }

    protected HashMap<String, String> initialValues = new HashMap<>();
    protected HashMap<String, Boolean> multiLines = new HashMap<>();



    public abstract String generateClassDeclaration(String className);

    public abstract String parseProperties(String text) throws Exception;

    public abstract void filterOutPropertiesAlreadyPresent(String outputText);

    public abstract String generateGetters();

    public abstract String generateSetters();

    public abstract String generateConstructor();

    public abstract String generateToString();

    public abstract String generateClassEnding();




}
