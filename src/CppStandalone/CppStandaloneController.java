package CppStandalone;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import mainPackage.LanguageController;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
