package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
import model.services.SellerService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		loadView("/gui/SellerList.fxml", (SellerListController controller) -> {
			controller.setSellerService(new SellerService()); // Insere a depend�ncia do DepartmentService no DepartmentListController
			controller.updateTableView(); // Atualiza os dados da tabela
		});
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService()); // Insere a depend�ncia do DepartmentService no DepartmentListController
			controller.updateTableView(); // Atualiza os dados da tabela
		});
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
	// synchronized -> para o processamento n�o ser interrompido durante o multi threading
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName)); // Pega o conte�do da tela informada -> (absoluteName)
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent(); // Pega o conte�do do VBox da tela principal
			
			Node mainMenu = mainVBox.getChildren().get(0); // Armazena os dados da mainVBox em um Node
			mainVBox.getChildren().clear(); // Limpa os elementos da tela
			mainVBox.getChildren().add(mainMenu); // Adiciona um novo VBox � tela principal
			mainVBox.getChildren().addAll(newVBox.getChildren()); // Insere os elementos da tela informada no novo VBox
			
			// Inicializa a fun��o passada como par�metro
			T controller = loader.getController();
			initializingAction.accept(controller);
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
		
	}
}
