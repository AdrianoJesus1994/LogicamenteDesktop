
package logicamente.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;
import logicamente.dto.JogadoresDto;
import logicamente.model.DaoUtil;

/**
 *
 * @author Adriano
 */
public class JogadoresDao extends DaoUtil{
    
   public List<JogadoresDto> recuperaJogador(){
        String sql = "SELECT id, dt_anoNascimento, nome, sexo FROM Jogador ORDER BY nome";
        List <JogadoresDto> lstret = new LinkedList<JogadoresDto>();
        try {
            ResultSet rs = super.getStatement().executeQuery(sql);
            while (rs.next()) {
                lstret.add(new JogadoresDto(rs.getInt("id"), rs.getDate("dt_anoNascimento"), rs.getString("nome"), rs.getString("sexo")) );
            }
            
            rs.close();
            super.destroyConnection();
        } catch (SQLException e) {
            System.out.println("Erro de Consulta" + e);
        }
        return lstret;
    }
   
   public List<JogadoresDto> recuperaJogadorNome(JogadoresDto nome){
        String sql = "SELECT id, nome FROM Jogador WHERE nome LIKE ?";
        List<JogadoresDto> lista = new LinkedList<JogadoresDto>();
        try {
            PreparedStatement pst = super.getPreparedStatement(sql);
            pst.setString(1, "%" + nome.getNome() + "%" );
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                lista.add( new JogadoresDto(rs.getInt("id"), rs.getDate("dt_anoNascimento"), rs.getString("nome"), rs.getString("sexo")));
            }
            
            rs.close();
            pst.close();
            super.destroyConnection();
        } catch (SQLException e) {
            System.out.println("Erro de Consulta" + e);
        }
        return lista;
    }
   
   // Retorna o total de jogadores de um determinado sexo
   public int RecuperarGenero(String genero){
       int total = 0;
       String sql = "SELECT COUNT(*) FROM Jogador WHERE sexo LIKE '" + genero + "'";
       
       try {
           ResultSet result = super.getStatement().executeQuery(sql);
           result.next();
           
           total = result.getInt(1);
       }
       catch (SQLException ex) {
           System.out.println("Erro ao consultar Total de Gêneros: " + ex);
       }
       finally{
           super.destroyConnection();
       }
       
       return total;
   }
   
   // Retorna o total de jogadores de todos os sexos
   public int[] RecuperarGeneroTotal(){
       int[] total = new int[3];
       
       String sql = "SELECT\n" +
               "  sum(case when sexo LIKE 'Masculino' then 1 end) as Masculino,\n" +
               "  sum(case when sexo LIKE 'Feminino' then 1 end) as Feminino,\n" +
               "  sum(case when sexo LIKE 'Outros' then 1 end) as Outros\n" +
               "FROM Jogador";
       
       try {
           ResultSet result = super.getStatement().executeQuery(sql);
           result.next();
           
           for(int i = 0; i < total.length; i++)
               total[i] = result.getInt(i+1);
       }
       catch (SQLException ex) {
           System.out.println("Erro ao consultar Total de Gêneros: " + ex);
       }
       finally{
           super.destroyConnection();
       }
       
       return total;
   }
   
   // Retorna o total de jogadores de todos os sexos
   public int[] RecuperarFaixaEtariaTotal(){
       int[] total = new int[6];
       
       String sql = "SELECT\n" +
               "  sum(case when dt_anoNascimento < '2017-1-1' and dt_anoNascimento > '2007-1-1' then 1 end) as '0-10',\n" +
               "  sum(case when dt_anoNascimento < '2006-1-1' and dt_anoNascimento > '1997-1-1' then 1 end) as '11-20',\n" +
               "  sum(case when dt_anoNascimento < '1996-1-1' and dt_anoNascimento > '1987-1-1' then 1 end) as '21-30',\n" +
               "  sum(case when dt_anoNascimento < '1986-1-1' and dt_anoNascimento > '1977-1-1' then 1 end) as '31-40',\n" +
               "  sum(case when dt_anoNascimento < '1976-1-1' and dt_anoNascimento > '1967-1-1' then 1 end) as '41-50',\n" +
               "  sum(case when dt_anoNascimento < '1966-1-1' then 1 end) as '51+'\n" +
               "FROM Jogador;";
       
       try {
           ResultSet result = super.getStatement().executeQuery(sql);
           result.next();
           
           for(int i = 0; i < total.length; i++)
               total[i] = result.getInt(i+1);
       }
       catch (SQLException ex) {
           System.out.println("Erro ao consultar Total de Gêneros: " + ex);
       }
       finally{
           super.destroyConnection();
       }
       
       return total;
   }
   
   // Retorna o total de jogadores de todos os sexos
   public int[] RecuperarResultadoIdeal(){
       int[] total = new int[3];
       
       String sql = "select\n" +
               "  count(*) as Total,\n" +
               "  sum(case when tipo_dificuldade = 1 and num_movimentos = 7 then 1 end) as Facil,\n" +
               "  sum(case when tipo_dificuldade = 2 and num_movimentos = 15 then 1 end) as Dificil\n" +
               "from Partida;";
       
       try {
           ResultSet result = super.getStatement().executeQuery(sql);
           result.next();
           
           for(int i = 0; i < total.length; i++)
               total[i] = result.getInt(i+1);
       }
       catch (SQLException ex) {
           System.out.println("Erro ao consultar Resultado Ideal: " + ex);
       }
       finally{
           super.destroyConnection();
       }
       
       return total;
   }
   
   // Retorna uma lista resumida de todos os jogadores, com seus respectivos totais de partidas
   public List<String> RecuperarJogadorPartida(){
       List<String> resultList = new LinkedList<>();
       
       String sql = "select\n" +
               "  Jogador.id as IdJogador,\n" +
               "  Jogador.dt_anoNascimento as DataNascimento,\n" +
               "  Jogador.sexo as Sexo,\n" +
               "  count(Partida.id) as TotalPartidas\n" +
               "    from Partida\n" +
               "    join Jogador on Jogador.id = Partida.id_jogador\n" +
               "  group by Jogador.id\n" +
               "order by Jogador.id";
       
       try {
           ResultSet result = super.getStatement().executeQuery(sql);
           while(result.next()){
               resultList.add(result.getInt("IdJogador") +"|"+result.getDate("DataNascimento").toString() +"|"+result.getString("Sexo") +"|"+result.getInt("TotalPartidas") +"|");
           }
       }
       catch (SQLException ex) {
           System.out.println("Erro ao consultar Total Jogadores: " + ex);
       }
       finally{
           super.destroyConnection();
       }
       
       return resultList;
   }
}
