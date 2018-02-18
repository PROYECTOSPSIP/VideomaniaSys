package vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import controlador.GeneroController;
import modelo.Genero;

/**
 * 
 * @author Eduardo Rosero
 *
 */

public class GeneroPelicula extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtGenero;
	private JScrollPane scrollPane;

	private EntityManagerFactory conexion;
	private GeneroController generoControler;
	private JTable tblGenero;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GeneroPelicula frame = new GeneroPelicula();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GeneroPelicula() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 575, 267);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		conexion = Persistence.createEntityManagerFactory("VideomaniaSys");
		generoControler = new GeneroController(conexion);
		
		JLabel lblGeneroDePeliculas = new JLabel("GENERO DE PELICULAS");
		lblGeneroDePeliculas.setFont(new Font("Dialog", Font.BOLD, 20));
		lblGeneroDePeliculas.setBounds(175, 37, 269, 24);
		contentPane.add(lblGeneroDePeliculas);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(30, 74, 300, 44);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblGenero = new JLabel("Genero:");
		lblGenero.setBounds(12, 12, 70, 15);
		panel.add(lblGenero);
		
		txtGenero = new JTextField();
		txtGenero.setBounds(80, 10, 200, 20);
		panel.add(txtGenero);
		txtGenero.setColumns(10);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(GeneroPelicula.class.getResource("/resorces/img/guardar.png")));
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Genero genero = new Genero();
				
				genero.setIdGenero((short)(genero.hashCode() + 1));
				genero.setGenero(txtGenero.getText());
				
				generoControler.create(genero);
				txtGenero.setText("");
				generoList();
			}
		});
		btnGuardar.setBounds(110, 148, 120, 44);
		contentPane.add(btnGuardar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(351, 74, 200, 150);
		contentPane.add(scrollPane);
		
		generoList();
		
		this.setLocationRelativeTo(null);
	}
	
	public void generoList(){
		DefaultTableModel modelo = new DefaultTableModel();
		
		tblGenero = new JTable();
		tblGenero.setEnabled(false);
		tblGenero.setModel(modelo);
		
		modelo.addColumn("Genero");
		
		EntityManager em = conexion.createEntityManager();
		TypedQuery<Genero> query = em.createNamedQuery("Genero.findAll", Genero.class);
		List<Genero> generoList = query.getResultList();
		
		try{
			if(generoList.size() > 0){				
				for(Genero genero : generoList){
					Object[] rows = new Object[generoList.size()];
					
					rows[0] = genero.getGenero();
					
					modelo.addRow(rows);
				}
			}
		}
		catch (Exception e) {

		}
		
		scrollPane.setViewportView(tblGenero);
	}
}
