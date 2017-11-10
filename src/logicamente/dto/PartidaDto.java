package logicamente.dto;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Adriano
 */
public class PartidaDto {

    private int id;
    private Date data;
    private int nivel;
    private int num_movimentos;
    private Time duracao;
    private int id_jogador;

    public PartidaDto() {
    }

    public PartidaDto(int id, Date data, int nivel, int num_movimentos, Time duracao, int id_jogador) {
        this.id = id;
        this.data = data;
        this.nivel = nivel;
        this.num_movimentos = num_movimentos;
        this.duracao = duracao;
        this.id_jogador = id_jogador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getNum_movimentos() {
        return num_movimentos;
    }

    public void setNum_movimentos(int num_movimentos) {
        this.num_movimentos = num_movimentos;
    }

    public Time getDuracao() {
        return duracao;
    }

    public void setDuracao(Time duracao) {
        this.duracao = duracao;
    }

    public int getId_jogador() {
        return id_jogador;
    }

    public void setId_jogador(int id_jogador) {
        this.id_jogador = id_jogador;
    }
}