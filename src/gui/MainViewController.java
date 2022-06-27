package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView2("/gui/DepartmentList.fxml");
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
	// synchronized -> para o processamento n�o ser interrompido durante o multi threading
	private synchronized void loadView(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName)); // Pega o conte�do da tela informada -> (absoluteName)
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent(); // Pega o conte�do do VBox da tela principal
			
			Node mainMenu = mainVBox.getChildren().get(0); // Armazena os dados da mainVBox em um Node
			mainVBox.getChildren().clear(); // Limpa os elementos da tela
			mainVBox.getChildren().add(mainMenu); // Adiciona um novo VBox � tela principal
			mainVBox.getChildren().addAll(newVBox.getChildren()); // Insere os elementos da tela informada no novo VBox
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	private synchronized void loadView2(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName)); // Pega o conte�do da tela informada -> (absoluteName)
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent(); // Pega o conte�do do VBox da tela principal
			
			Node mainMenu = mainVBox.getChildren().get(0); // Armazena os dados da mainVBox em um Node
			mainVBox.getChildren().clear(); // Limpa os elementos da tela
			mainVBox.getChildren().add(mainMenu); // Adiciona um novo VBox � tela principal
			mainVBox.getChildren().addAll(newVBox.getChildren()); // Insere os elementos da tela informada no novo VBox
			
			DepartmentListController controller = loader.getController();
			controller.setDepartmentService(new DepartmentService()); // Insere a depend�ncia do DepartmentService no DepartmentListController
			controller.updateTableView(); // Atualiza os dados da tabela
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
		
	}
}
