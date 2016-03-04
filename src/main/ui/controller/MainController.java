package main.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import main.ui.MainUI;

public class MainController implements Initializable{
	
	private static MainController instance;

    public static MainController getInstance() {
        return instance;
    }

    @FXML
    private AnchorPane center_panel,common;
    @FXML
    private TextField search;
    @FXML
    private Button btn_search,btn_menu;
    
    @FXML
    public void handleMenu() {
    	initPanel();
    }
    @FXML
    public void handleSearch() {
    	setPanel(MainUI.searchPanel);
    	
    	//TODO
    }
    
    /**
     * when start the app, init the homePagePanel
     */
    public void initPanel() {
    	setPanel(MainUI.homePanel);

    	//TODO
    	//add project items here(the method write into homeController)
    }
    
    /**
     * the common method to change the current panel
     * @param panel
     */
    public void setPanel(final AnchorPane panel){
    	if(panel == null) {
    		return;
    	}
    	center_panel.getChildren().clear();
    	center_panel.getChildren().add(panel);
    	//otherwise the searchButton cannot use
    	common.toFront();
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
	}

}