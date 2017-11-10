package logicamente.controller;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
public class CadastroUsuarioController implements Initializable {
    @FXML
    private TextField inputCadastroNome;

    @FXML
    private PasswordField inputCadastroSenha;

    @FXML
    private Button btnCadastroCadastrar;

    @FXML
    private AnchorPane anchorpaneCadastro;

    @FXML
    private Button btnCadastroVoltar;

    @FXML
    private TextField inputCadastroEmail;
    
    @FXML
    private TextField inputConfirmSenha;
    
    @FXML
    private Label labelCadastroStatus; 

    @FXML
    private DatePicker inputCadastroNasc;
    
    //Date dataJavaUtil = null;

    @FXML
    void cadastrarUsuario(ActionEvent event) {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setNome(inputCadastroNome.getText());
        usuario.setEmail(inputCadastroEmail.getText());
        usuario.setSenha(inputCadastroSenha.getText());
        usuario.setDtNasc(Date.from(inputCadastroNasc.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        
        try {
            if(inputCadastroEmail.getText().isEmpty() || inputCadastroNome.getText().isEmpty() || inputCadastroNasc == null || inputCadastroSenha.getText().isEmpty()){
                labelCadastroStatus.setText("É nescessário preencer os campos.");
            }else if(!(inputCadastroSenha.getText().equals(inputConfirmSenha.getText()))  ){
                labelCadastroStatus.setText("As senhas digitadas não conferem.");
            }else{
                UsuarioDao registro = new UsuarioDao();
                registro.cadastrarUsuario(usuario);
                //AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/logicamente/view/VboxMain.fxml"));
                //anchorpaneCadastro.getChildren().setAll(a); 
            } 
        } catch (Exception e) {
            System.out.println("Erro ao realizar cadastro:" + e);
            e.printStackTrace();
        }
    }

    @FXML
    void voltarLogin(ActionEvent event) {
        try {
            AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/logicamente/view/Login.fxml"));
            anchorpaneCadastro.getChildren().setAll(a);
        } catch (IOException e) {
            System.out.println("Erro: " + e);         
        }         
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
