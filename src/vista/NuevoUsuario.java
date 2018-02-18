package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.UsuarioController;
import modelo.Rol;
import modelo.Usuario;

public class NuevoUsuario extends JPanel {
	private JTextField txtApellidos;
	private JTextField txtNombre;
	private JTextField txtNombreUsuario;
	private JTextField txtClave;

	/**
	 * Create the panel.
	 */
	public NuevoUsuario() {
		setLayout(null);
		
		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setBounds(43, 37, 71, 14);
		add(lblApellidos);
		
		txtApellidos = new JTextField();
		txtApellidos.setBounds(148, 39, 151, 20);
		add(txtApellidos);
		txtApellidos.setColumns(10);
		
		JLabel lblNombres = new JLabel("Nombres");
		lblNombres.setBounds(43, 76, 71, 14);
		add(lblNombres);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario usuario = new Usuario();
				usuario.setApellidos(txtApellidos.getText());
				usuario.setNombres(txtNombre.getText());
				usuario.setNombreUsuario(txtNombreUsuario.getText());
				usuario.setPassword(txtClave.getText());
				usuario.setIdRol(new Rol());
				
				EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VideomaniaSys");
				UsuarioController usuarioJpaController = new UsuarioController(entityManagerFactory);
				
				try {					
					usuarioJpaController.create(usuario);					
				} catch (Exception e1) {
					Logger.getLogger(NuevoUsuario.class.getName()).log(Level.SEVERE, null, e1);
					e1.printStackTrace();
				}
				//lblInfo.setText("Usuario Creado Correctamente");
			}
		});
		btnGuardar.setBounds(99, 218, 89, 23);
		add(btnGuardar);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(148, 70, 151, 20);
		add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNombreUsuario = new JLabel("Nombre Usuario");
		lblNombreUsuario.setBounds(43, 121, 104, 14);
		add(lblNombreUsuario);
		
		txtNombreUsuario = new JTextField();
		txtNombreUsuario.setBounds(148, 118, 151, 20);
		add(txtNombreUsuario);
		txtNombreUsuario.setColumns(10);
		
		JLabel lblClave = new JLabel("Clave");
		lblClave.setBounds(43, 164, 82, 14);
		add(lblClave);
		
		txtClave = new JTextField();
		txtClave.setBounds(148, 161, 151, 20);
		add(txtClave);
		txtClave.setColumns(10);
		
		JLabel lblRol = new JLabel("Rol");
		lblRol.setBounds(43, 199, 46, 14);
		add(lblRol);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(148, 187, 151, 31);
		add(comboBox);

	}
}
