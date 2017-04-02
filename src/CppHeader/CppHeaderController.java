package CppHeader;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import mainPackage.LanguageController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jacobmenke on 4/1/17.
 */
public class CppHeaderController extends LanguageController implements Initializable{
    public CheckBox cppHeaderGetterCheckBox;
    public CheckBox cppHeaderSetterCheckBox;
    public CheckBox cppHeaderConstructorCheckBox;
    public CheckBox cppHeaderDeleterCheckBox;
    public CheckBox cppHeaderGenerateImplementationCheckBox;
    public TextArea cppHeaderInputTextArea;
    public TextArea cppHeaderOutputTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
