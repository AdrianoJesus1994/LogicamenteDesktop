package logicamente.dto;

import java.util.Date;

public class JogadoresDto {

    private int id;
    private Date nasc;
    private String nome;
    private String sexo;

    public JogadoresDto() {
    }

    public JogadoresDto(int id, Date nasc, String nome, String sexo) {
        this.id = id;
        this.nasc = nasc;
        this.nome = nome;
        this.sexo = sexo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getNasc() {
        return nasc;
    }

    public void setNasc(Date nasc) {
        this.nasc = nasc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
