package CppStandalone;

import mainPackage.GeneratorAncestor;

/**
 * Created by jacobmenke on 4/25/17.
 */
public class CppStandaloneGenerator extends GeneratorAncestor {
    @Override
    public String generateClassDeclaration(String className) {
        return null;
    }

    @Override
    public String parseProperties(String text) throws Exception {
        return null;
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
