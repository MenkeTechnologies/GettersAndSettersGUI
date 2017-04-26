package CppStandalone;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import mainPackage.LanguageController;
import mainPackage.MainUtilities;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by jacobmenke on 4/1/17.
 */
public class CppStandaloneController extends LanguageController implements Initializable{
    public CheckBox cppStandaloneGetterCheckBox;
    public CheckBox cppStandaloneSetterCheckBox;
    public CheckBox cppStandaloneConstructorCheckBox;
    public CheckBox cppStandaloneDeleterCheckBox;
    public TextArea cppStandaloneInputTextArea;
    public TextArea cppStandaloneOutputTextArea;
    public SplitPane cppStandaloneSplitPane;
    public CppStandaloneGenerator cppStandaloneGenerator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textAreas.addAll(Arrays.asList(cppStandaloneInputTextArea, cppStandaloneOutputTextArea));

        mainSplitPane = cppStandaloneSplitPane;

        cppStandaloneGenerator = new CppStandaloneGenerator();

        MainUtilities.clearAllMaps(cppStandaloneGenerator);

    }

    public void generateCppStandalone(HashMap<String, Boolean> options, boolean b, String s) {
    }
}
