/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicamente.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import logicamente.dto.UsuarioDto;
import logicamente.model.dao.UsuarioDao;

/**
 * FXML Controller class
 *
 * @author Adriano.jesus
 */
public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField inputLogin;

    @FXML
    private PasswordField inputSenha;

    @FXML
    private Button btnCadatrar;

    @FXML
    private Label labelStatus;

    @FXML
    private AnchorPane anchorpaneLogin;

    @FXML
    void realizarLogin(ActionEvent event) throws SQLException {

        if (inputLogin.getText().isEmpty() || inputSenha.getText().isEmpty()) {
            labelStatus.setText("Preencha os campos acima.");
        } else {
            UsuarioDto login = new UsuarioDto();
            login.setEmail(inputLogin.getText());
            login.setSenha(inputSenha.getText());

            UsuarioDao valida = new UsuarioDao();
            UsuarioDto result = valida.validaUsuario(login);

            if (result != null) {
                AplicationUtil.getInstancia().setUsuarioLogado(result);
                AplicationUtil.getInstancia().irParaTela("TelaPrincipal.fxml");
//                             AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/logicamente/view/VboxMain.fxml"));
//                             anchorpaneLogin.getChildren().setAll(a);
            } else {
                inputLogin.setText("");
                inputSenha.setText("");
                labelStatus.setText("Ocorreram erros ao validar as informações digitadas, por favor verifique-as  e tente novamente");
            }
        }
    }

    @FXML
    void realizarCadastro(ActionEvent event) {
        AplicationUtil.getInstancia().irParaTela("CadastroUsuario.fxml");
//
//        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/logicamente/view/CadastroUsuario.fxml"));
//        anchorpaneLogin.getChildren().setAll(a);
//
//        Parent root = FXMLLoader.load(getClass().getResource("/logicamente/view/CadastroUsuario.fxml"));
//        Stage stage = new Stage();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}