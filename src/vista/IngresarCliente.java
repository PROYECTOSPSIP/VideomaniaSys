package vista;

import java.awt.Color;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import controlador.ClienteController;
import controlador.UsuarioController;
import controlador.exceptions.NonexistentEntityException;
import modelo.Cliente;
import modelo.Genero;
import modelo.Operadora;
import modelo.Pais;
import modelo.Rol;
import modelo.Usuario;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.ObjectProperty;

import com.sun.security.ntlm.Client;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

public class IngresarCliente extends JPanel {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtIdentificacion;
	private JTextField txtApellidos;
	private JTextField txtNombres;
	private JTextField txtConvencional;
	private JTextField txtCelular;
	private JTextArea txtDireccion;
	private JTextField txtEmail;
	private JComboBox cmbOperadora;
	private JLabel lblInfo;
	private JTextField txtIdCliente;
	private Cliente cliente =  null;

	/**
	 * Create the panel.
	 */
	public IngresarCliente() {
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 715, 456);
		add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("Identificacion:");
		label.setBounds(10, 79, 82, 26);
		panel.add(label);
		
		txtIdentificacion = new JTextField();
		//codigo para limitar a que solo introdusca Números
		txtIdentificacion.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
					lblInfo.setText("Solo se permiten numeros");
					} else {
						lblInfo.setText("");
						if ((txtIdentificacion.getText().length() >13)){
							e.consume();
							//lblInfo.setText("Longitud de identificacion no vailda");
						}/*else {
							lblInfo.setText("");
						}*/
					}  
				}
			});
		txtIdentificacion.setColumns(10);
		txtIdentificacion.setBounds(102, 76, 161, 23);
		
		
				
				
		panel.add(txtIdentificacion);
		
		
		JButton btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VideomaniaSys");
				ClienteController clienteController = new ClienteController(entityManagerFactory);
				
				try {	
					if (txtIdentificacion.getText().length()>0) {
						cliente =  buscarClientePorCedula();
						txtIdCliente.setText(String.valueOf(cliente.getIdCliente()));
						txtApellidos.setText(cliente.getApellidosCliente());
						txtNombres.setText(cliente.getNombresCliente());
						txtDireccion.setText(cliente.getDireccion());
						txtEmail.setText(cliente.getEmail());
						txtConvencional.setText(cliente.getTelefonoConvencional());
						cmbOperadora.setSelectedIndex(cliente.getIdOperadora().getIdOperadora());
						txtCelular.setText(cliente.getTelefeonoCelular());
					} else {
						lblInfo.setText("Debe ingresar identificacion para buscar");
					}					
				} catch (Exception e1) {
					Logger.getLogger(NuevoUsuario.class.getName()).log(Level.SEVERE, null, e1);
					e1.printStackTrace();
					lblInfo.setText("Ocurrio un error al Consultar cliente ");
				}
				
			}
		});
		btnBuscar.setIcon(new ImageIcon(IngresarPelicula.class.getResource("/resorces/img/search.png")));
		btnBuscar.setBounds(284, 72, 57, 33);
		panel.add(btnBuscar);
		
		JLabel label_1 = new JLabel("Apellidos:");
		label_1.setBounds(10, 119, 82, 14);
		panel.add(label_1);
		
		txtApellidos = new JTextField();
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(102, 110, 321, 23);
		panel.add(txtApellidos);
		
		JLabel label_2 = new JLabel("Nombres:");
		label_2.setBounds(10, 150, 82, 14);
		panel.add(label_2);
		
		txtNombres = new JTextField();
		txtNombres.setColumns(10);
		txtNombres.setBounds(102, 144, 321, 23);
		panel.add(txtNombres);
		
		JLabel label_3 = new JLabel("Direccion:");
		label_3.setBounds(10, 185, 82, 20);
		panel.add(label_3);
		
		txtDireccion = new JTextArea();
		txtDireccion.setBounds(102, 182, 321, 46);
		panel.add(txtDireccion);
		
		JLabel label_4 = new JLabel("Convencional:");
		label_4.setBounds(10, 282, 82, 14);
		panel.add(label_4);
		
		txtConvencional = new JTextField();
		txtConvencional.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		txtConvencional.setColumns(10);
		txtConvencional.setBounds(102, 276, 161, 23);
		panel.add(txtConvencional);
		
		JLabel label_5 = new JLabel("Celular:");
		label_5.setBounds(10, 356, 46, 14);
		panel.add(label_5);
		
		txtCelular = new JTextField();
		txtCelular.setColumns(10);
		txtCelular.setBounds(102, 347, 161, 23);
		panel.add(txtCelular);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VideomaniaSys");
				ClienteController clienteController = new ClienteController(entityManagerFactory);
				try {
					if (cliente != null) {
						cliente.setIdentificacion(txtIdentificacion.getText());
						cliente.setApellidosCliente(txtApellidos.getText());
						cliente.setNombresCliente(txtNombres.getText());
						cliente.setDireccion(txtDireccion.getText());
						cliente.setTelefonoConvencional(txtConvencional.getText());
						cliente.setTelefeonoCelular(txtCelular.getText());
						cliente.setEmail(txtEmail.getText());
						cliente.setIdOperadora(new Operadora(cmbOperadora.getSelectedIndex()));
						clienteController.edit(cliente);
						lblInfo.setText("Cliente Actualizado Correctamente");
					} else {
						cliente = new Cliente();
						cliente.setIdentificacion(txtIdentificacion.getText());
						cliente.setApellidosCliente(txtApellidos.getText());
						cliente.setNombresCliente(txtNombres.getText());
						cliente.setDireccion(txtDireccion.getText());
						cliente.setTelefonoConvencional(txtConvencional.getText());
						cliente.setTelefeonoCelular(txtCelular.getText());
						cliente.setEmail(txtEmail.getText());
						cliente.setIdOperadora(new Operadora(cmbOperadora.getSelectedIndex()));
						
						lblInfo.setText("Cliente Registrado Correctamente");						
					}
					
				} catch (Exception e1) {
					Logger.getLogger(NuevoUsuario.class.getName()).log(Level.SEVERE, null, e1);
					e1.printStackTrace();
					lblInfo.setText("Ocurrio un error");
				}		
				
			}
		});
		btnGuardar.setIcon(new ImageIcon(IngresarPelicula.class.getResource("/resorces/img/guardar.png")));
		btnGuardar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnGuardar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnGuardar.setBounds(507, 86, 100, 60);
		panel.add(btnGuardar);
		
		JLabel lblOperadora = new JLabel("Operadora");
		lblOperadora.setBounds(10, 319, 66, 14);
		panel.add(lblOperadora);
		
		cmbOperadora = new JComboBox();
		cmbOperadora.setBounds(102, 310, 161, 20);
		cmbOperadora.addItem("Seleccione.");
		
		if(operadoraList().size() > 0){
			for(Operadora operadora : operadoraList()){
				cmbOperadora.addItem(operadora.getOperadora());
			}
		}
		
		panel.add(cmbOperadora);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 241, 46, 14);
		panel.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(102, 245, 321, 20);
		panel.add(txtEmail);
		txtEmail.setColumns(10);
		
		lblInfo = new JLabel("");
		lblInfo.setForeground(new Color(255, 0, 0));
		lblInfo.setBounds(63, 364, 519, 68);
		panel.add(lblInfo);
		
		JLabel lblCodigoCliente = new JLabel("Codigo Cliente");
		lblCodigoCliente.setBounds(10, 36, 82, 14);
		panel.add(lblCodigoCliente);
		
		txtIdCliente = new JTextField();
		txtIdCliente.setEnabled(false);
		txtIdCliente.setEditable(false);
		txtIdCliente.setBounds(103, 33, 118, 20);
		panel.add(txtIdCliente);
		txtIdCliente.setColumns(10);
		
		operadoraList();

	}
	
	public List<Operadora> operadoraList(){
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VideomaniaSys");
		EntityManager em = entityManagerFactory.createEntityManager();
		TypedQuery<Operadora> query = em.createNamedQuery("Operadora.findAll", Operadora.class);
		List<Operadora> operadoraList = query.getResultList();
		
		return operadoraList;
	}
	
	
	public Cliente buscarClientePorCedula(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VideomaniaSys");
		EntityManager em = entityManagerFactory.createEntityManager();
		TypedQuery<Cliente> query = em.createNamedQuery("Cliente.findByIdentificacion", Cliente.class);
		query.setParameter("identificacion", txtIdentificacion.getText());
		List<Cliente> clienteList = query.getResultList();
		
		if (!clienteList.isEmpty()) {
            return clienteList.get(0);
        } else {
            return null;
        }
		
	}
	
	
}
