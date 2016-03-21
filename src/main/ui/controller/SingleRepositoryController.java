package main.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.business.impl.repository.RepositoryServiceImpl;
import main.business.service.RepositoryService;
import main.ui.MainUI;
import main.vo.RepositoryVO;

/**
 * @author tj
 * @date 2016年3月3日
 */
public class SingleRepositoryController implements Initializable {
	@FXML
	private Label repositoryName;
	@FXML
	private Label lastUpdated;
	@FXML
	private Label language;
	@FXML
	private Label contributors;
	@FXML
	private Label contriNum;
	@FXML
	private Label forks;
	@FXML
	private Label forkNum;
	@FXML
	private Label stars;
	@FXML
	private Label starNum;
	@FXML
	private Label description;

	private RepositoryService repositoryImpl;
	private RepositoryVO vo;
	private RepositoryVO fullVO;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		repositoryImpl = RepositoryServiceImpl.getInstance();
		repositoryName.setOnMouseClicked((e) -> {
			MainController.getInstance().setGroup("Ui_ProjectPanel.fxml");
			fullVO = repositoryImpl.searchRepositoryInfo(vo.getFull_name());
			ProjectController.getInstance().setVO(fullVO);
		});
		
		Image image = new Image(MainUI.class.getResourceAsStream("style/contributor.png"));
		contriNum.setGraphic(new ImageView(image));
		image = new Image(MainUI.class.getResourceAsStream("style/fork.png"));
		forkNum.setGraphic(new ImageView(image)); 
		image = new Image(MainUI.class.getResourceAsStream("style/star.png"));
		starNum.setGraphic(new ImageView(image)); 
	}

	/**
	 * set text on the labels
	 *
	 * @param vo
	 */
	public void setVO(RepositoryVO vo) {
		this.vo = vo;
		if (vo != null) {
			repositoryName.setText(vo.getFull_name());
			language.setText(vo.getLanguage());
			contributors.setText("  "+vo.getContributors_login().size());
			forks.setText(" "+vo.getForks_count());
			stars.setText(" "+vo.getSubscribers_count());
			lastUpdated.setText(vo.getUpdated_at()+"");
			//set description
			String str = vo.getDescription();
			int size = 130;
			int line = str.length()/size;
			String result = "";
			int i = 0;
			for(i = 0;i<line;i++){
				result+=str.substring(i*size,i*size+size)+"\n";
			}
			description.setText(result+str.substring(i*size));
		}
	}

}
