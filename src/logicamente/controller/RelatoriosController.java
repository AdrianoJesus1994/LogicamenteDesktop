package logicamente.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import logicamente.dto.PartidaDto;
import logicamente.model.dao.PartidaDao;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logicamente.dto.JogadoresDto;
import logicamente.model.dao.JogadoresDao;

public class RelatoriosController implements Initializable {
    @FXML
    private DatePicker dpDataReferencia;
    
     @FXML
    private TableColumn<JogadoresDto, String> colunaNascimento;

    @FXML
    private TableView<JogadoresDto> tabelaJogadores;

    @FXML
    private TableColumn<JogadoresDto, String> colunaNome;
    
    @FXML
    private Label labelSexoJogador;

    @FXML
    private Label labelNomeJogador;    
    
    @FXML
    private Label labelNascJogador;
    
    @FXML
    private Button btnHistoricoJogador;
    

    
    @FXML
    private Label labelIdadeJogador;
    
    List<JogadoresDto> listaJogadores;
    
    ObservableList<JogadoresDto> observableListJogadores;
    
    



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        carregarTabelaJogadores();
        
        tabelaJogadores.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                selecionaJogador(newValue);
            } catch (ParseException ex) {
                Logger.getLogger(RelatoriosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    
    public void carregarTabelaJogadores(){
        JogadoresDao jogador = new JogadoresDao();
                
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNascimento.setCellValueFactory(new PropertyValueFactory<>("nasc"));
        listaJogadores = jogador.recuperaJogador();
        observableListJogadores = FXCollections.observableArrayList(listaJogadores);
        tabelaJogadores.setItems(observableListJogadores);
        
    }
    
    
    public void selecionaJogador(JogadoresDto jogador) throws ParseException{
        
        //Setando o jogador seleciando atual
        AplicationUtil.getInstancia().setJogadorAtual(jogador);
        
        //formatar data
        java.util.Date d = new java.util.Date();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        String dataNascimento = f.format(jogador.getNasc());
        d = f.parse(dataNascimento);
        int idade = calculaIdade(d);
        
        
        labelNomeJogador.setText(jogador.getNome());
        labelNascJogador.setText(f.format(jogador.getNasc()));
        labelSexoJogador.setText(jogador.getSexo());
        labelIdadeJogador.setText(String.valueOf(idade));
        
        
        
    }
    
    public static int calculaIdade(java.util.Date dataNasc) {

        Calendar dataNascimento = Calendar.getInstance();  
        dataNascimento.setTime(dataNasc); 
        Calendar hoje = Calendar.getInstance();  

        int idade = hoje.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR); 

        if (hoje.get(Calendar.MONTH) < dataNascimento.get(Calendar.MONTH)) {
          idade--;  
        } 
        else 
        { 
            if (hoje.get(Calendar.MONTH) == dataNascimento.get(Calendar.MONTH) && hoje.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH)) {
                idade--; 
            }
        }

        return idade;
    }
    
    @FXML
    void gerarHistorico(ActionEvent event) {
        if (AplicationUtil.getInstancia().getJogadorAtual() == null) {
            AplicationUtil.ExibirAviso("Aviso", "Nenhum Jogador Selecionado");
        }else{
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/logicamente/view/HistoricoJogador.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.showAndWait();
                
                
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }   
        

    }
    
    @FXML
    void GerarPdf(ActionEvent event) throws DocumentException, FileNotFoundException {
        // Data referência não selecionada
        if (dpDataReferencia.getValue() == null) {
            AplicationUtil.ExibirAviso("Aviso", "Escolha uma data referência limite para gerar o relatório");
            return;
        }

        PartidaDao partidaDao = new PartidaDao();
        List<PartidaDto> partidas = partidaDao.RecuperarPartidaData(dpDataReferencia.getValue().toString());

        // Aviso caso não existam resultados para a data selecionada
        if (partidas.isEmpty()) {
            AplicationUtil.ExibirAviso("Aviso", "Nenhuma partida encontrada para a data selecionada");
            return;
        }

        Document pdf = new Document();
        PdfWriter.getInstance(pdf, new FileOutputStream("RelatorioPartidas.pdf"));

        pdf.open();

        pdf.add(new Paragraph("Data da Partida | ID do Jogador | Dificuldade | Num. de Movimentos | Tempo de Jogo"));
        
        for (PartidaDto partida : partidas) {
            String data = new SimpleDateFormat("dd/MM/yyyy").format(partida.getData());            
            String jogador = Integer.toString(partida.getId_jogador());
            String dificuldade = partida.getNivel() == 1 ? "Fácil" : "Difícil";            
            String movimentos = Integer.toString(partida.getNum_movimentos());
            String tempo = partida.getDuracao().toString();

            pdf.add(new Paragraph(data + " | " + jogador + " | " + dificuldade + " | " + movimentos + " | " + tempo));
        }

        pdf.close();
        
        AplicationUtil.ExibirAviso("Aviso", "Relatório gerado com sucesso!");
    }
    
    // Gera um relatório resumido de todos os jogadores, com seus respectivos totais de partidas
    @FXML
    void GerarRelatorioJogadores(ActionEvent event) throws DocumentException, FileNotFoundException {
        JogadoresDao jogadorDao = new JogadoresDao();
        List<String> resultList = jogadorDao.RecuperarJogadorPartida();

        Document pdf = new Document();
        PdfWriter.getInstance(pdf, new FileOutputStream("RelatorioJogadores.pdf"));

        pdf.open();
        pdf.add(new Paragraph("ID do Jogador | Data de Nascimento | Sexo | Total de Partidas"));
        
        for (int i = 0; i < resultList.size(); i++) {
            pdf.add(new Paragraph(resultList.get(i)));
        }

        pdf.close();
        
        AplicationUtil.ExibirAviso("Aviso", "Relatório gerado com sucesso!");
    }
    
    
}