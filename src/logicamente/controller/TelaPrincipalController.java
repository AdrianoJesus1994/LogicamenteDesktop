package logicamente.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Adriano.jesus
 */
public class TelaPrincipalController implements Initializable {

    @FXML
    private Button btnVisaoGeral;

    @FXML
    private Label labelNomeUsuario;

    @FXML
    private Button btnSair;

    @FXML
    private Button btnRelatorios;

    @FXML
    private Button btnAjuda;

    @FXML
    private AnchorPane anchorpanePricipal;

    @FXML
    private AnchorPane anchorpaneTelaPricipal;

    @FXML
    void btnVisaoGeral(ActionEvent event) {
        try {
            AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/logicamente/view/VisaoGeral.fxml"));
            anchorpanePricipal.getChildren().setAll(a);
        } catch (IOException ex) {
            System.out.println("Erro ao chamar tela: " + ex);
        }
    }

    @FXML
    void btnRelatorios(ActionEvent event) {
        try {
            AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/logicamente/view/Relatorios.fxml"));
            anchorpanePricipal.getChildren().setAll(a);
        } catch (IOException e) {
            System.out.println("Erro ao chamar tela: " + e);
        }
    }

    @FXML
    void btnAjuda(ActionEvent event) {
        try {
            AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/logicamente/view/Ajuda.fxml"));
            anchorpanePricipal.getChildren().setAll(a);
        } catch (IOException e) {
            System.out.println("Erro ao chamar tela: " + e);
        }
    }

    @FXML
    void btnSair(ActionEvent event) {
        AplicationUtil.getInstancia().irParaTela("Sair.fxml");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelNomeUsuario.setText(AplicationUtil.getInstancia().getUsuarioLogado().getNome());
        try {
            AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/logicamente/view/VisaoGeral.fxml"));
            anchorpanePricipal.getChildren().setAll(a);
        } catch (IOException e) {
            System.out.println("Erro ao chamar tela: " + e);
        }
    }
}