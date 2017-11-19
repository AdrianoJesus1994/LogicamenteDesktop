
package logicamente.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class DaoUtil {
    private Connection con = null;
    
    public Connection starConnection(){
        String user = "naass849_logica";
        String pws = "logicamentegame";
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://ns628.hostgator.com.br:3306/naass849_logicamente?zeroDateTimeBehavior=convertToNull";
        
        try {
            if (this.con == null) {
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, pws);
            }
            
        }catch (ClassNotFoundException cnf){
            System.out.println("Erro de drive");
            System.out.println("ERRO:"+ cnf);
        }catch(SQLException se){
            System.out.println("Erro de SQL");
            System.out.println("Erro: " + se.getMessage());
            System.out.println("Cod:" + se.getErrorCode());
            System.out.println("SQL State: " + se.getSQLState());
        } catch (Exception e) {
            System.out.println("Eerro geral:" + e);
        }
        
        return con;
    }
    
    public void destroyConnection(){
        try {
            //Fechar conection
            if (this.con != null) {
                con.close();
                this.con = null;
            }
            
            
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexaõ" + e);
        }
        
    }
    
   public Statement getStatement() throws SQLException{
       return this.starConnection().createStatement();
   }
   
   public PreparedStatement getPreparedStatement(String sql) throws SQLException{
       return this.starConnection().prepareStatement(sql);
   }
  
}
