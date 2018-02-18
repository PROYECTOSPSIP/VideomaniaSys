/**
 * 
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import conexionBDD.ParametrosConexion;
import vista.PrincipalVista;

/**
 * @author elichinita58
 *
 */
public class LoginDAO {
	
	public boolean consultarUsuario(String usuario, String contrasenia )	{
		boolean result = false;
		Connection conn = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    
		try {
			ParametrosConexion parametrosConexion = new ParametrosConexion();
			conn = parametrosConexion.conectar();
			
		      String sql = "select * from usuario u where u.nombre_usuario=? and password=?";
		      PreparedStatement preparedStatement = conn.prepareStatement(sql);
		      preparedStatement.setString(1, usuario);
		      preparedStatement.setString(2, contrasenia);
		      
		      ResultSet resultSet = preparedStatement.executeQuery();
		      if (resultSet.next()) {
		    	  result = true;
		      }		      
			
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
		finally {
			try {
				// Step 5 Close connection
		        if (stmt != null) {
		          stmt.close();
		        }
		        if (rs != null) {
		          rs.close();
		        }
		        if (conn != null) {
		          conn.close();
		        }						
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}			
		}
		return result;
	}
}
