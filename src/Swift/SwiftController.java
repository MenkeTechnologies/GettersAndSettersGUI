package Swift;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import mainPackage.LanguageController;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by jacobmenke on 4/1/17.
 */
public class SwiftController extends LanguageController implements Initializable {
    public CheckBox swiftGetterCheckBox;
    public CheckBox swiftSetterCheckBox;
    public CheckBox swiftConstructorCheckBox;
    public TextArea swiftOutputTextArea;
    public TextArea swiftInputTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        swiftGetterCheckBox.setOnAction(e->{
            System.out.println("selected the getter swift");
        });

        textAreas.addAll(Arrays.asList(swiftInputTextArea, swiftOutputTextArea));
    }



}
