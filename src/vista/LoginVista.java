package vista;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controlador.UsuarioController;
import modelo.Usuario;

public class LoginVista {

	public JFrame frame;
	private JTextField textUsuario;
	private JPasswordField txtContrasenia;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginVista window = new LoginVista();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginVista() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(LoginVista.class.getResource("/resorces/img/equipo.png")));
		
		frame.setBounds(100, 100, 303, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuario.setBounds(12, 255, 100, 20);
		frame.getContentPane().add(lblUsuario);
		
		JLabel lblContrasenia = new JLabel("Contraseña:");
		lblContrasenia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblContrasenia.setBounds(12, 291, 100, 20);
		frame.getContentPane().add(lblContrasenia);
		
		textUsuario = new JTextField();
		textUsuario.setBounds(130, 256, 140, 20);
		frame.getContentPane().add(textUsuario);
		textUsuario.setColumns(10);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreUsuario = textUsuario.getText();
				String contrasenia = txtContrasenia.getText();
				
				//LoginControlador loginControlador = new LoginControlador();
				EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VideomaniaSys");
				UsuarioController usuarioController = new UsuarioController(entityManagerFactory);
				
				Usuario usuario = new Usuario();
				
				try {
					usuario = usuarioController.findUsuarioLogin(nombreUsuario);
					if (usuario != null) {
						if (usuario.getNombreUsuario().equals(nombreUsuario) && usuario.getPassword().equals(contrasenia)) {
							JOptionPane.showMessageDialog(frame, "Bienvenido al sistema");		
							PrincipalVista principal = new PrincipalVista();
							principal.setVisible(true);
							frame.dispose();
						}else {
							JOptionPane.showMessageDialog(frame, "Datos invalidos");	
						}
					}				
					
				} catch (Exception e1) {
					Logger.getLogger(LoginVista.class.getName()).log(Level.SEVERE, null, e1);
					e1.printStackTrace();
				}		
					
			}
		});
		btnIngresar.setBounds(101, 331, 117, 23);
		frame.getContentPane().add(btnIngresar);
		
		txtContrasenia = new JPasswordField();
		txtContrasenia.setBounds(130, 292, 140, 20);
		frame.getContentPane().add(txtContrasenia);
		
		label = new JLabel("");
		label.setIconTextGap(8);
		label.setIcon(new ImageIcon(LoginVista.class.getResource("/resorces/img/equipo.png")));
		label.setBounds(22, 27, 248, 186);
		frame.getContentPane().add(label);
	}
}