package mainPackage;

import com.sun.jndi.cosnaming.CNCtx;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jacobmenke on 4/3/17.
 */
public class SettingsController implements Initializable {
    public TreeView treeView;
    public RadioButton tabsRadio;
    public RadioButton fourSpacesRadio;
    public Button saveButton;
    public TextField customIndentationTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initing the setting");

        ToggleGroup indentationToggleGroup = new ToggleGroup();
        indentationToggleGroup.getToggles().addAll(tabsRadio, fourSpacesRadio);

    }

    public void saveIndentation(ActionEvent actionEvent) {
        if (tabsRadio.isSelected()){
            Constants.FOUR_SPACES = "\t";
        }
        else if (fourSpacesRadio.isSelected()){
            Constants.FOUR_SPACES = "    ";
        } else {
            String custom = customIndentationTextField.getText();

            if (!custom.equals("")){
                Constants.FOUR_SPACES = custom;
            }
        }


    }
}
