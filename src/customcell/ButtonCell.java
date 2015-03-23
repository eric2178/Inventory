/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customcell;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import miss_class_objects.Employee;

/**
 *
 * @author mooree0811
 */
public class ButtonCell
    extends TableCell<Employee, String>
{

    private final Button cellButton;

    public ButtonCell(String buttonName, String toolTip)
    {
        cellButton = new Button(buttonName);
        cellButton.setTooltip(new Tooltip(toolTip));

    }

    public Button getCellButton()
    {
        return cellButton;
    }

    @Override
    public void commitEdit(String newValue)
    {
        super.commitEdit(newValue); //To change body of generated methods, choose Tools | Templates.
    }
 
    //Display button if the row is not empty
    @Override
    protected void updateItem(String t,
        boolean empty)
    {
        super.updateItem(t, empty);
        // Add the button if the cell is not empty

        if (!empty)
        {
            setGraphic(cellButton);
        }
        else
        {
            // Remove button if cell is empty.
            setGraphic(null);
        }
    }
}
