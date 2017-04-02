package CppImplementation;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import mainPackage.LanguageController;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
