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
import java.text.SimpleDateFormat;
import logicamente.model.dao.JogadoresDao;

public class RelatoriosController implements Initializable {
    @FXML
    private DatePicker dpDataReferencia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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