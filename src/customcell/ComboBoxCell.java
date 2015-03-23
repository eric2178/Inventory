
package customcell;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import miss_class_objects.Employee;

/**
 *
 * @author Tanisha
 */
public class ComboBoxCell extends TableCell<Employee, String>
{
    private final ComboBox combo;
    
    
    public ComboBoxCell(String firstInView,String...data)
    {
        combo = new ComboBox();
        combo.getSelectionModel().select(firstInView);
        combo.getItems().addAll(data);
    }

    public ComboBox getComboBox(){return combo;}
    
    //Display button if the row is not empty
    @Override
    protected void updateItem(String t,boolean empty)
    {
        super.updateItem(t,empty);
        // Add the button if the cell is not emp
        if (!empty)
        {
            setGraphic(combo);
//            setText((String)combo.getSelectionModel().getSelectedItem());
        }
        else
        {
           
            // Remove button if cell is empty.
            setGraphic(null);
        }
    }
}
