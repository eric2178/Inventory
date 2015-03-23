/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.BinModel;
import models.StoreroomModel;
import models.EmployeeModel;
import models.ItemModel;


/**
 *
 * @author Eric Moore
 */
public class Controller
{
    // Storeroom code data.
    private StoreroomModel storeroomModel; 
    
    // Employee code data.
    private EmployeeModel employeeModel;
    
    private ItemModel itemModel;
    
    private BinModel binModel;
    /**
     * Constructor creates a StoreroomController controller
     * Instantiating a StoreroomModel.
     */
    public Controller()
    {
        storeroomModel = new StoreroomModel();
        employeeModel = new EmployeeModel();
        itemModel = new ItemModel();
        binModel = new BinModel();
    }

//    /**
//     * The start method starts the view(GUI) portion.
//     */
//    public void start()
//    {
////        viewFrame = new ViewFrame(this);
//    }

    /**
     * The getStoreroomModel gets the StoreroomModel where
     * code logic is located.
     * @return
     */
    public StoreroomModel getStoreroomModel()
    {
        return storeroomModel;
    }

    public EmployeeModel getEmployeeModel()
    {
        return employeeModel;
    }
    
    public ItemModel getItemModel()
    {
        return itemModel;
    }

    public BinModel getBinModel()
    {
        return binModel;
    }
}
