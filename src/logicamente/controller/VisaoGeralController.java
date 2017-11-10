package logicamente.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import logicamente.model.dao.JogadoresDao;

/**
 * FXML Controller class
 *
 * @author Adriano.jesus
 */
public class VisaoGeralController implements Initializable {    
    @FXML
    private PieChart pieChartGenero;
    @FXML
    private PieChart pieChartFaixaEtaria;
    @FXML
    private PieChart pieChartResultadoIdeal;    
    
    ObservableList<String> sexo = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AtualizarGraficoGenero();
        AtualizarGraficoFaixaEtaria();
        AtualizarGraficoResultadoIdeal();
    }
    
    // Atualiza gráfico-pizza com informações de Gênero dos jogadores.
    private void AtualizarGraficoGenero(){
        JogadoresDao jogadoresDao = new JogadoresDao();
        
        int[] resultado = jogadoresDao.RecuperarGeneroTotal();
        
        ObservableList<PieChart.Data> pieChartData = 
                FXCollections.observableArrayList(
                    new PieChart.Data("Masculino ("+resultado[0]+")", resultado[0]),
                    new PieChart.Data("Feminino ("+resultado[1]+")", resultado[1]),
                    new PieChart.Data("Outros ("+resultado[2]+")", resultado[2]));
         
        pieChartGenero.setData(pieChartData);
    }
    
    // Atualiza gráfico-pizza com informações de Faixa Etária dos jogadores.
    private void AtualizarGraficoFaixaEtaria(){
        JogadoresDao jogadoresDao = new JogadoresDao();
        
        int[] resultado = jogadoresDao.RecuperarFaixaEtariaTotal();
        
        ObservableList<PieChart.Data> pieChartData = 
                FXCollections.observableArrayList(
                    new PieChart.Data("0~10", resultado[0]),
                    new PieChart.Data("11~20", resultado[1]),
                    new PieChart.Data("21~30", resultado[2]),
                    new PieChart.Data("31~40", resultado[3]),
                    new PieChart.Data("41~50", resultado[4]),
                    new PieChart.Data("51+", resultado[5]));
         
        pieChartFaixaEtaria.setData(pieChartData);
    }
    
    // Atualiza gráfico-pizza com informações sobre jogadores que alcançaram resultado ideal em cada dificuldade.
    private void AtualizarGraficoResultadoIdeal(){
        JogadoresDao jogadoresDao = new JogadoresDao();
        
        int[] resultado = jogadoresDao.RecuperarResultadoIdeal();
        int totalOutros = resultado[0] - resultado[1] - resultado[2];
        
        ObservableList<PieChart.Data> pieChartData = 
                FXCollections.observableArrayList(
                    new PieChart.Data("Fácil em 7 Movimentos (" +resultado[1]+")", resultado[1]),
                    new PieChart.Data("Difícil em 15 movimentos (" +resultado[2]+")", resultado[2]),
                    new PieChart.Data("Não alcançaram resultado ideal (" +totalOutros+")", totalOutros));
        
        pieChartResultadoIdeal.setData(pieChartData);
    }
}