package vista;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import modelo.Actor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 * @author Eduardo Rosero
 *
 */

public class ConsultarPelicula extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	
	private JComboBox<String> cmbAutor; 
	private EntityManagerFactory conexion;
	private DefaultListModel<Object> modeloList = new DefaultListModel<>();
	private JList<Object> list;


	/**
	 * Create the panel.
	 */
	public ConsultarPelicula() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblConsultaDePeliculas = new JLabel("CONSULTA DE PELICULAS");
		lblConsultaDePeliculas.setBounds(515, 6, 288, 24);
		lblConsultaDePeliculas.setFont(new Font("Dialog", Font.BOLD, 20));
		add(lblConsultaDePeliculas);
		
		JPanel panel = new JPanel();
		panel.setBounds(75, 42, 1100, 104);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblCodigo = new JLabel("Código:");
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigo.setBounds(12, 12, 70, 20);
		panel.add(lblCodigo);
		
		JLabel lblAutor = new JLabel("Actor:");
		lblAutor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAutor.setBounds(12, 60, 70, 20);
		panel.add(lblAutor);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(100, 13, 150, 20);
		panel.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		/*EntityManager em = conexion.createEntityManager();
		TypedQuery<Actor> query = em.createNamedQuery("Actor.findAll", Actor.class);
		List<Actor> actorList = query.getResultList();*/
		
		cmbAutor = new JComboBox();
		cmbAutor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbAutor.getSelectedItem().equals("Seleccione.")){
					modeloList.addElement(cmbAutor.getSelectedItem().toString());
					list.setModel(modeloList);
				}
			}
		});
		
		/*if(actorList.size() > 0){
			for(Actor actor : actorList){
				cmbAutor.addItem(actor.getNombresActor());
			}
		}*/
		cmbAutor.setBounds(102, 60, 300, 20);
		panel.add(cmbAutor);
		
		JLabel lblPais = new JLabel("País:");
		lblPais.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPais.setBounds(421, 63, 70, 15);
		panel.add(lblPais);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(421, 15, 70, 20);
		panel.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(510, 16, 250, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JComboBox cmbPais = new JComboBox();
		cmbPais.setBounds(509, 60, 150, 20);
		panel.add(cmbPais);
		
		JLabel lblGenero = new JLabel("Genero:");
		lblGenero.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGenero.setBounds(778, 15, 70, 20);
		panel.add(lblGenero);
		
		JLabel lblIdioma = new JLabel("Idioma:");
		lblIdioma.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIdioma.setBounds(778, 63, 70, 20);
		panel.add(lblIdioma);
		
		JComboBox cmbGenero = new JComboBox();
		cmbGenero.setBounds(866, 16, 150, 20);
		panel.add(cmbGenero);
		
		JComboBox cmbIdioma = new JComboBox();
		cmbIdioma.setBounds(866, 63, 150, 20);
		panel.add(cmbIdioma);

	}	

}
