/**
 * 
 */
package controlador;

import modelo.LoginDAO;

/**
 * @author elichinita58
 *
 */
public class LoginControlador {
	
	public boolean consultarUsuario(String usuario, String contrasenia )	{
		LoginDAO loginDAO = new LoginDAO();
		return loginDAO.consultarUsuario(usuario, contrasenia);
	}

}
