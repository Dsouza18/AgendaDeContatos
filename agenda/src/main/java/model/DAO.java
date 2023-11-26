package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
	
    private static DAO instance;
  
    MySqlConnection mySqlConnection = new MySqlConnection();

    //Construtor privado para impedir a criação de instâncias adicionais
    private DAO() {
    	mySqlConnection = new MySqlConnection();
    }
//Padrão Singleton para garantir que seja criada apenas uma instancia DAO em toda a aplicação
    public static synchronized DAO getInstance() {
        if (instance == null) {
            instance = new DAO();
        }
        return instance;
    }


    // CRUD CREATE
    public void inserirContato(JavaBeans contato) {
        String create = "INSERT INTO contato (nome,fone,email) values (?,?,?)";
        try {
            Connection con = mySqlConnection.connect();
            PreparedStatement pst = con.prepareStatement(create);
            pst.setString(1, contato.getNome());
            pst.setString(2, contato.getFone());
            pst.setString(3, contato.getEmail());
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
       	 e.printStackTrace();
        }
    }

    // CRUD READ
    public ArrayList<JavaBeans> listarContatos() {
        ArrayList<JavaBeans> contatos = new ArrayList<>();
        String read = "SELECT * FROM contato order by nome";
        try {
            Connection con = mySqlConnection.connect();
            PreparedStatement pst = con.prepareStatement(read);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String idcon = rs.getString(1);
                String nome = rs.getString(2);
                String fone = rs.getString(3);
                String email = rs.getString(4);
                contatos.add(new JavaBeans(idcon, nome, fone, email));
            }
            con.close();
            return contatos;
        } catch (Exception e) {
        	 e.printStackTrace();
            return null;
        }
    }

    // CRUD UPDATE
    public void selecionarContato(JavaBeans contato) {
        String read2 = "SELECT * FROM contato where idcon = ?";
        try {
            Connection con = mySqlConnection.connect();
            PreparedStatement pst = con.prepareStatement(read2);
            pst.setString(1, contato.getIdcon());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                contato.setIdcon(rs.getString(1));
                contato.setNome(rs.getString(2));
                contato.setFone(rs.getString(3));
                contato.setEmail(rs.getString(4));
            }
            con.close();
        } catch (Exception e) {
        	 e.printStackTrace();
        }
    }

    public void alterarContato(JavaBeans contato) {
        String update = "UPDATE contato set nome=?,fone=?,email=? where idcon=?";
        try {
            Connection con = mySqlConnection.connect();
            PreparedStatement pst = con.prepareStatement(update);
            pst.setString(1, contato.getNome());
            pst.setString(2, contato.getFone());
            pst.setString(3, contato.getEmail());
            pst.setString(4, contato.getIdcon());
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
        	 e.printStackTrace();
        }
    }

    // CRUD DELETE
    public void deletarContato(JavaBeans contato) {
        String delete = "DELETE FROM contato where idcon=?";
        try {
            Connection con = mySqlConnection.connect();
            PreparedStatement pst = con.prepareStatement(delete);
            pst.setString(1, contato.getIdcon());
            pst.executeUpdate();
            con.close();
        } catch (Exception e) {
        	 e.printStackTrace();
        }
    }
}
