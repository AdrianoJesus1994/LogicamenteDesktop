/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicamente;

import com.itextpdf.text.Image;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logicamente.controller.AplicationUtil;

/**
 *
 * @author Adriano
 */
public class Logicamente extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       
        AplicationUtil.getInstancia().setTelaAtual(stage);
        AplicationUtil.getInstancia().irParaTela("Login.fxml");
//        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
//        
//        Scene scene = new Scene(root);
//        
//        stage.setScene(scene);
//        stage.setResizable(true);
//        stage.setTitle("Logicamente Desktop");
//        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
