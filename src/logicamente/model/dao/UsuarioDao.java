/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicamente.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import logicamente.dto.UsuarioDto;
import logicamente.model.DaoUtil;

/**
 *
 * @author Adriano.jesus
 */
public class UsuarioDao extends DaoUtil {

    public List<UsuarioDto> listarUsuarios(){
        String sql = "SELECT id, email, nome, senha, dtNasc FROM Usuario";
        List <UsuarioDto> lstret = new LinkedList<UsuarioDto>();
        try {
            ResultSet rs = super.getStatement().executeQuery(sql);
            while (rs.next()) {
                lstret.add(new UsuarioDto(rs.getInt("id"),rs.getString("email"), rs.getString("nome"), rs.getString("senha"), rs.getDate("dtNasc") ) );
            }
            
            rs.close();
            super.destroyConnection();
        } catch (SQLException e) {
            System.out.println("Erro de Consulta" + e);
        }
        return lstret;
    }
    
    public UsuarioDto validaUsuario(UsuarioDto login) {
        String sql = "SELECT * FROM Usuario WHERE email = ? AND senha = ?";
        UsuarioDto usuario = null;
        
        try {
            PreparedStatement pst = super.getPreparedStatement(sql);
            pst.setString(1,login.getEmail() );
            pst.setString(2, login.getSenha());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                usuario = new UsuarioDto(rs.getInt("id"),rs.getString("email"), rs.getString("nome"), rs.getString("senha"), rs.getDate("dtNasc") );
            }
            rs.close();
            pst.close();
            super.destroyConnection();


        } catch (SQLException e) {
            System.out.println("Erro sql:" + e);
        }
        
        return usuario;
    }
    
    public boolean cadastrarUsuario(UsuarioDto usuario){
        String sql = "INSERT INTO Usuario (email, nome, senha, dtNasc) VALUES (?,?,?,?)";

        int ret = 0;
        try {
            PreparedStatement ps = super.getPreparedStatement(sql);
            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getNome());
            ps.setString(3, usuario.getSenha());
            ps.setDate(4, new java.sql.Date(usuario.getDtNasc().getTime() ) );
            ret = ps.executeUpdate();
            ps.close();
            super.destroyConnection();
        } catch (SQLException e) {
            System.out.println("ERRO: " + e);
        }
        
        return ret > 0;
    }
}