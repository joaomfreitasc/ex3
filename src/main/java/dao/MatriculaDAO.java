package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Matricula;

public class MatriculaDAO extends DAO {
    
    public MatriculaDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }
    
    
    public boolean insert(Matricula matricula) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            String sql = "INSERT INTO matricula (nome, idade, curso, matricula) "
                       + "VALUES ('"+matricula.getNome()+ "', " + matricula.getIdade() + ", '"  
                       + matricula.getCurso() + "', " + matricula.getMatricula() + ")";
            System.out.println(sql);
            st.executeUpdate(sql);
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }

    
    public Matricula get(String nome) {
        Matricula matricula = null;
    
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM matricula WHERE nome= " + nome;
            System.out.println(sql);
            ResultSet rs = st.executeQuery(sql);    
            if(rs.next()){            
                 matricula = new Matricula(rs.getString("nome"), rs.getInt("idade"), rs.getString("curso"), rs.getInt("matricula"));
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return matricula;
    }
    
    
    public List<Matricula> get() {
        return getByOrder("");
    }
    
    public List<Matricula> getOrderByMatricula() {
        return getByOrder("matricula");
    }

    public List<Matricula> getOrderByNome() {
        return getByOrder("nome");
    }

    public List<Matricula> getOrderByIdade() {
        return getByOrder("idade");
    }

    
    public List<Matricula> getByOrder(String orderBy) {   
    
        List<Matricula> matriculas = new ArrayList<Matricula>();
        
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM matricula" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            System.out.println(sql);
            ResultSet rs = st.executeQuery(sql);               
            while(rs.next()) {                 
                Matricula u = new Matricula(rs.getString("nome"), rs.getInt("idade"), rs.getString("curso"), rs.getInt("matricula"));
                matriculas.add(u);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return matriculas;
    }
    
	    public List<Matricula> getmatriculaMasculino() {
		List<Matricula> Matriculas = new ArrayList<Matricula>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM matricula WHERE matricula.matricula LIKE 'M'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Matricula u = new Matricula(rs.getString("nome"), rs.getInt("idade"), rs.getString("curso"), rs.getInt("matricula"));
	            Matriculas.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return Matriculas;
	}


    public boolean update(Matricula matricula) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            String sql = "UPDATE matricula SET idade = " + matricula.getIdade() + ", curso = '"  
                       + matricula.getCurso() + "', matricula = " + matricula.getMatricula() 
                       + " WHERE nome = '" + matricula.getNome() + "'";
            System.out.println(sql);
            st.executeUpdate(sql);
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }
    
    public boolean delete(int matricula) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            String sql = "DELETE FROM matricula WHERE matricula = " + matricula;
            System.out.println(sql);
            st.executeUpdate(sql);
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }
    
    public Matricula get( int codigo ) 
    {
    	Matricula  usuario = null;

        try 
        {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM matricula WHERE matricula=" + codigo;
            System.out.println(sql);
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                 usuario = new Matricula (rs.getString("nome"), rs.getInt("idade"), rs.getString("curso"), rs.getInt("matricula "));
            }
            st.close();
        } catch (Exception e) 
        {
            System.err.println(e.getMessage());
        } // end try catch 
        return ( usuario );
    } // end get ( )
    
}