/**
 * 
 */
package conexionBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author elichinita58
 *
 */
public class ParametrosConexion {
	
	//Connection conn = null;
    //Statement stmt = null;
    //ResultSet rs = null;
	
	String jdbcUrl = "jdbc:postgresql://localhost:5432/videomania";
    String username = "postgres";
    String password = "26011989";   
    
    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     * @throws java.sql.SQLException
     */
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }
    

}
