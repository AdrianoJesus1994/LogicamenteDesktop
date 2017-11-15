package logicamente.controller;

import com.itextpdf.text.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import logicamente.dto.UsuarioDto;

/**
 *
 * @author Adriano.Jesus
 */
public class AplicationUtil {

    private static AplicationUtil instancia = null;

    private Stage telaAtual = null;
    private UsuarioDto usuarioLogado = null;

    private AplicationUtil() {
        this.telaAtual = new Stage();
    }

    public static AplicationUtil getInstancia() {
        if (instancia == null) {
            instancia = new AplicationUtil();
        }
        return instancia;
    }

    public void irParaTela(String nomeTela) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/logicamente/view/".concat(nomeTela)));            
            Scene scene = new Scene(root);
            this.telaAtual.setScene(scene);
            this.telaAtual.setResizable(false);
            this.telaAtual.getIcons().add(new javafx.scene.image.Image("/logicamente/view/icon.png"));
            if (!this.telaAtual.isShowing()) {
                this.telaAtual.show(); 
            }
        } catch (IOException ex) {
            System.err.println("Ocorreu um erro ao tentar navegar para tela " + nomeTela + ": " + ex.getMessage());
        }
    }

    public void setTelaAtual(Stage telaAtual) {
        this.telaAtual = telaAtual;
    }

    public Stage getTelaAtual() {
        return telaAtual;
    }

    public UsuarioDto getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(UsuarioDto usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public void setUsuarioVazio() {
        this.usuarioLogado = null;
    }
    
    public static void ExibirAviso(String titulo, String mensagem){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        
        alert.showAndWait();
    }
}