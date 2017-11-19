/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicamente.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Header;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logicamente.dto.JogadoresDto;
import logicamente.dto.PartidaDto;
import logicamente.model.dao.PartidaDao;

/**
 * FXML Controller class
 *
 * @author Adriano
 */
public class HistoricoJogadorController implements Initializable {

    @FXML
    private TableColumn<PartidaDto, String> colunaDuracaoPartida;

    @FXML
    private TableView<PartidaDto> tabelaPartidas;

    @FXML
    private Button btnGerarPdfPartidas;
    
    @FXML
    private Button btnFechar;    

    @FXML
    private TableColumn<PartidaDto, String> colunaNivelPartida;

    @FXML
    private Label labelNomeJogador;

    @FXML
    private TableColumn<PartidaDto, String> colunaDataPartida;

    @FXML
    private TableColumn<PartidaDto, String> colunaMovimentosPartida;
    

    List<PartidaDto> listaPartidas;

    ObservableList<PartidaDto> observableListPartidas;

    JogadoresDto jogadorAtual = AplicationUtil.getInstancia().getJogadorAtual();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        carregarTabelaJogadores(jogadorAtual);
        labelNomeJogador.setText(jogadorAtual.getNome());

    }

    public void carregarTabelaJogadores(JogadoresDto jogadorAtual) {
        PartidaDao partidas = new PartidaDao();

        colunaDataPartida.setCellValueFactory(new PropertyValueFactory<>("data"));
        colunaNivelPartida.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        colunaMovimentosPartida.setCellValueFactory(new PropertyValueFactory<>("num_movimentos"));
        colunaDuracaoPartida.setCellValueFactory(new PropertyValueFactory<>("duracao"));

        listaPartidas = partidas.recuperaPartidaPorJogador(jogadorAtual.getId());
        observableListPartidas = FXCollections.observableArrayList(listaPartidas);
        tabelaPartidas.setItems(observableListPartidas);

    }

    @FXML
    void gerarPdfPartidas(ActionEvent event) throws FileNotFoundException, DocumentException, IOException {

        if (listaPartidas.isEmpty()) {
            AplicationUtil.ExibirAviso("Aviso", "Nenhuma partida encontrada para o jogador selecionado.");
            return;
        }

        Document pdf = new Document();
        PdfWriter.getInstance(pdf, new FileOutputStream("RelatorioPartidasPorJogador.pdf"));

        pdf.open();

//        Image img = Image.getInstance("logicamente/view/logo.png");
//        img.scaleToFit(400, 200);
//        
//        pdf.add(img);
        Paragraph p = new Paragraph("Relatório de Partidas por Jogador");
        p.setAlignment(1);
        pdf.add(p);

        pdf.add(new Paragraph("NOME: " + AplicationUtil.getInstancia().getJogadorAtual().getNome()));
        pdf.add(new Paragraph("SEXO: " + AplicationUtil.getInstancia().getJogadorAtual().getSexo()));
        pdf.add(new Paragraph("NASCIMENTO: " + new SimpleDateFormat("dd/MM/yyyy").format(AplicationUtil.getInstancia().getJogadorAtual().getNasc())));
        pdf.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(4);
        PdfPCell data = new PdfPCell(new Paragraph("Data da Partida"));
        PdfPCell dificuldade = new PdfPCell(new Paragraph("Dificuldade"));
        PdfPCell numMovimentos = new PdfPCell(new Paragraph("Num. Movimentos"));
        PdfPCell tempo = new PdfPCell(new Paragraph("Tempo de Jogo"));

        table.addCell(data);
        table.addCell(dificuldade);
        table.addCell(numMovimentos);
        table.addCell(tempo);

        for (PartidaDto partida : listaPartidas) {

            data = new PdfPCell(new Paragraph(new SimpleDateFormat("dd/MM/yyyy").format(partida.getData())));
            dificuldade = new PdfPCell(new Paragraph(partida.getNivel() == 1 ? "Fácil" : "Difícil"));
            numMovimentos = new PdfPCell(new Paragraph(Integer.toString(partida.getNum_movimentos())));
            tempo = new PdfPCell(new Paragraph(partida.getDuracao().toString()));

            table.addCell(data);
            table.addCell(dificuldade);
            table.addCell(numMovimentos);
            table.addCell(tempo);
        }
        
        pdf.add(table);
        pdf.close();

        AplicationUtil.ExibirAviso("Aviso", "Relatório gerado com sucesso!");

        try {
            Desktop.getDesktop().open(new File("RelatorioPartidasPorJogador.pdf"));
        } catch (IOException ex) {
            System.out.println("Erro: " + ex);
        }

    }
    
    @FXML
    void fecharDialog(ActionEvent event) {
      Stage stage = (Stage) btnFechar.getScene().getWindow();
      stage.close();
    }

}
