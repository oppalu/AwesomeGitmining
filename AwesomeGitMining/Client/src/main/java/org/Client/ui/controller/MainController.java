package org.Client.ui.controller;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.net.URL;
import java.util.ResourceBundle;

import org.Client.ui.MainUI;
import org.Client.ui.utility.SkinConfig;
import org.Client.ui.utility.fxmlLoader;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable {
	private final Delta dragDelta = new Delta();
	private static MainController instance;
	private TrayIcon trayIcon;
	private String enterColor;
	private String baseColor;

	public static MainController getInstance() {
		if (instance == null) {
			instance = new MainController();
		}
		return instance;
	}

	@FXML
	private AnchorPane layout;
	@FXML
	public AnchorPane center_panel, common;
	@FXML
	private Label min;
	@FXML
	private Label exit;
	@FXML
	private Label repository;
	@FXML
	private Label user;
	@FXML
	private Label repositorySta;
	@FXML
	private Label userSta;
	@FXML
	private Label changeStyle;

	private AnchorPane infoPane;
	private boolean selectRepos;
	private boolean selectUser;
	private boolean selectReposSta;
	private boolean selectUserSta;
	private String styleStr = "-fx-background-color: ";
	private int skinNum;
	private String[] enterColors = { "#5d9b78;", "#ff99c7;", "#cad2dd;" };
	private String[] baseColors = { "#71af8c;", "#f8aec4;", "#b4b7bb;" };
	private String[] exitPics = { "exit_normal.png", "exit_normal_pink.png", "exit_normal_dark.png" };
	private String[] minPics = { "min_normal.png", "min_normal_pink.png", "min_normal_dark.png" };

	/**
	 * when start the app, init the homePagePanel
	 */
	public void setSkinNum(int skinNum) {
		this.skinNum = skinNum;
		this.enterColor = enterColors[skinNum];
		this.baseColor = baseColors[skinNum];
		changeLabelStyle();
	}

	public void changeLabelStyle() {
		//退出和最小化按钮
		labelInit(exit, exitPics[skinNum]);
		labelInit(min, minPics[skinNum]);
		//导航栏
		repository.setStyle(styleStr + baseColor);
		user.setStyle(styleStr + baseColor);
		repositorySta.setStyle(styleStr + baseColor);
		userSta.setStyle(styleStr + baseColor);
	}

	public void initPanel() {
		setPanel(SkinConfig.getInstance().getFxmlResoursePath("main"));
	}
	
	/**
	 * handle when server doesn't connect
	 */
	public void setError() {
		AnchorPane panel = fxmlLoader.loadPanel("Ui_Error.fxml");
		AnchorPane.setTopAnchor(panel, 270.0);
		AnchorPane.setLeftAnchor(panel, 600.0);
		center_panel.getChildren().add(panel);
	}
	
	/**
	 * the common method to change the current panel
	 *
	 * @param panel
	 */
	public void setPanel(String name) {
		AnchorPane panel = fxmlLoader.loadPanel(name);

		center_panel.getChildren().clear();
		center_panel.getChildren().add(panel);
		// if shows statistics
		if (name.equals("Ui_ReposStaPane.fxml") || name.equals("Ui_UserSta.fxml")) {
			return;
		}
		AnchorPane field = fxmlLoader.loadPanel("Ui_TextField.fxml");
		panel.getChildren().add(field);
		field.setLayoutX(10);
		field.setLayoutY(-10);
		// otherwise the searchButton cannot use
		common.toFront();
	}

	public void setGroup(String name) {
		Group group = fxmlLoader.loadGroup(name);
		center_panel.getChildren().clear();
		center_panel.getChildren().add(group);
		// otherwise the searchButton cannot use
		common.toFront();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		setSkinNum(SkinConfig.getInstance().getSkinNum());
		selectRepos = true;
		enterRepos();
		addDraggableNode(common);
		infoPane = fxmlLoader.loadPanel("Ui_InfoPane.fxml");
		infoPane.setLayoutX(changeStyle.getLayoutX());
		infoPane.setLayoutY(changeStyle.getLayoutY() + changeStyle.getPrefHeight());
		//换肤按钮
		labelInit(changeStyle, "skin1.png");
	}

	public void labelInit(Label label, String path) {
		Image image = new Image(MainUI.class.getResourceAsStream("style/" + path));
		label.setGraphic(new ImageView(image));
	}

	@FXML
	public void enterChange() {
		labelInit(changeStyle, "skin3.png");
	}

	@FXML
	public void exitChange() {
		labelInit(changeStyle, "skin1.png");
	}

	@FXML
	public void releaseChange() {
		labelInit(changeStyle, "skin2.png");
		if (layout.getChildren().contains(infoPane)) {
			layout.getChildren().remove(infoPane);
		} else {
			layout.getChildren().add(infoPane);
		}
	}

	public void removeInfoPane() {
		layout.getChildren().remove(infoPane);
	}

	@FXML
	public void enterExit() {
		if (skinNum == 0) {
			labelInit(exit, "exit_move.png");
		} else if (skinNum == 1) {
			labelInit(exit, "exit_move_pink.png");
		} else if (skinNum == 2) {
			labelInit(exit, "exit_move_dark.png");
		}
	}

	@FXML
	public void exitExit() {
		if (skinNum == 0) {
			labelInit(exit, "exit_normal.png");
		} else if (skinNum == 1) {
			labelInit(exit, "exit_normal_pink.png");
		} else if (skinNum == 2) {
			labelInit(exit, "exit_normal_dark.png");
		}

	}

	@FXML
	public void releaseExit() {
		labelInit(exit, "exit_active.png");
		SystemTray.getSystemTray().remove(trayIcon);
		Platform.exit();
		return;

	}

	@FXML
	public void enterMin() {
		if (skinNum == 0) {
			labelInit(min, "min_move.png");
		} else if (skinNum == 1) {
			labelInit(min, "min_move_pink.png");
		} else if (skinNum == 2) {
			labelInit(min, "min_move_dark.png");
		}

	}

	@FXML
	public void exitMin() {
		if (skinNum == 0) {
			labelInit(min, "min_normal.png");
		} else if (skinNum == 1) {
			labelInit(min, "min_normal_pink.png");
		} else if (skinNum == 2) {
			labelInit(min, "min_normal_dark.png");
		}
	}

	@FXML
	public void releaseMin() {
		labelInit(min, "min_active.png");
		MainUI.getUI().getStage().setIconified(true);

	}

	@FXML
	public void enterRepos() {
		repository.setStyle(styleStr + enterColor);
	}

	@FXML
	public void enterUser() {
		user.setStyle(styleStr + enterColor);
	}

	@FXML
	public void enterReposSta() {
		repositorySta.setStyle(styleStr + enterColor);
	}

	@FXML
	public void enterUserSta() {
		userSta.setStyle(styleStr + enterColor);
	}

	@FXML
	public void releaseRepos() {
		selectUser = false;
		selectReposSta = false;
		selectUserSta = false;
		selectRepos = true;
		enterRepos();
		user.setStyle(styleStr + baseColor);
		repositorySta.setStyle(styleStr + baseColor);
		userSta.setStyle(styleStr + baseColor);
		setPanel(SkinConfig.getInstance().getFxmlResoursePath("main"));
	}

	@FXML
	public void releaseUser() {
		selectUser = true;
		selectRepos = false;
		selectReposSta = false;
		selectUserSta = false;
		enterUser();
		repository.setStyle(styleStr + baseColor);
		repositorySta.setStyle(styleStr + baseColor);
		userSta.setStyle(styleStr + baseColor);
		setPanel("Ui_UserPagePanel.fxml");
	}

	@FXML
	public void releaseReposSta() {
		selectReposSta = true;
		selectRepos = false;
		selectUser = false;
		selectUserSta = false;
		enterReposSta();
		user.setStyle(styleStr + baseColor);
		repository.setStyle(styleStr + baseColor);
		userSta.setStyle(styleStr + baseColor);
		setPanel("Ui_ReposStaPane.fxml");
	}

	@FXML
	public void releaseUserSta() {
		selectUserSta = true;
		selectRepos = false;
		selectUser = false;
		selectReposSta = false;
		enterUserSta();
		user.setStyle(styleStr + baseColor);
		repositorySta.setStyle(styleStr + baseColor);
		repository.setStyle(styleStr + baseColor);
		setPanel("Ui_UserSta.fxml");
	}

	@FXML
	public void exitRepos() {
		if (!selectRepos) {
			repository.setStyle(styleStr + baseColor);
		}
	}

	@FXML
	public void exitUser() {
		if (!selectUser) {
			user.setStyle(styleStr + baseColor);
		}
	}

	@FXML
	public void exitReposSta() {
		if (!selectReposSta) {
			repositorySta.setStyle(styleStr + baseColor);
		}
	}

	@FXML
	public void exitUserSta() {
		if (!selectUserSta) {
			userSta.setStyle(styleStr + baseColor);
		}
	}

	public boolean isSelectRepos() {
		return selectRepos;
	}

	public boolean isSelectUser() {
		return selectUser;
	}

	class Delta {
		double x, y;
	}

	private void addDraggableNode(final Node node) {

		node.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				if (me.getButton() != MouseButton.MIDDLE) {
					dragDelta.x = me.getSceneX();
					dragDelta.y = me.getSceneY();
				}
			}
		});

		node.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				if (me.getButton() != MouseButton.MIDDLE) {
					node.getScene().getWindow().setX(me.getScreenX() - dragDelta.x);
					node.getScene().getWindow().setY(me.getScreenY() - dragDelta.y);
				}
			}
		});
	}
}
