package CppImplementation;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import mainPackage.LanguageController;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by jacobmenke on 4/1/17.
 */
public class CppImplementationController extends LanguageController implements Initializable{
    public CheckBox cppImplementationGetterCheckBox;
    public CheckBox cppImplementationSetterCheckBox;
    public CheckBox cppImplementationConstructorCheckBox;
    public CheckBox cppImplementationDeleterCheckBox;
    public TextArea cppImplementationInputTextArea;
    public TextArea cppImplementationOutputTextArea;
    public SplitPane cppImplementationSplitPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textAreas.addAll(Arrays.asList(cppImplementationInputTextArea, cppImplementationOutputTextArea));

        mainSplitPane =cppImplementationSplitPane;

    }
}
