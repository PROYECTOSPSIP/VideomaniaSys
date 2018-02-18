package vista;

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
import javax.swing.table.DefaultTableModel;

import controlador.ActorController;
import modelo.Actor;

/**
 * 
 * @author Eduardo Rosero
 *
 */

public class ActorVista extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtActor;
	private JTable tblActor;
	
	private JScrollPane scrollPane;
	
	private EntityManagerFactory conexion;
	private ActorController actorControler;

	/**
	 * Create the panel.
	 */
	public ActorVista() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		conexion = Persistence.createEntityManagerFactory("VideomaniaSys");
		actorControler = new ActorController(conexion);
		
		JLabel lblActor = new JLabel("ACTOR");
		lblActor.setFont(new Font("Dialog", Font.BOLD, 20));
		lblActor.setBounds(263, 24, 90, 20);
		getContentPane().add(lblActor);
		
		JPanel pnlActor = new JPanel();
		pnlActor.setBounds(12, 56, 400, 50);
		getContentPane().add(pnlActor);
		pnlActor.setLayout(null);
		
		JLabel lblNombres = new JLabel("Nombres:");
		lblNombres.setBounds(12, 12, 70, 20);
		pnlActor.add(lblNombres);
		
		txtActor = new JTextField();
		txtActor.setBounds(85, 13, 303, 20);
		pnlActor.add(txtActor);
		txtActor.setColumns(10);
		
		JButton btnGuardar = new JButton("");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Actor actor = new Actor();
				
				actor.setIdActor((actor.hashCode() + 1));
				actor.setNombresActor(txtActor.getText());
				
				actorControler.create(actor);
				txtActor.setText("");
				actorList();
			}
		});
		btnGuardar.setIcon(new ImageIcon(Actor.class.getResource("/resorces/img/guardar.png")));
		btnGuardar.setBounds(425, 62, 60, 38);
		getContentPane().add(btnGuardar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 118, 473, 150);
		getContentPane().add(scrollPane);

		actorList();
		
		this.setLocationRelativeTo(null);
	}
	
	public void actorList(){
		DefaultTableModel modelo = new DefaultTableModel();
		
		tblActor = new JTable();
		tblActor.setEnabled(false);
		tblActor.setModel(modelo);
		
		modelo.addColumn("ACTORES");
		
		EntityManager em = conexion.createEntityManager();
		TypedQuery<Actor> query = em.createNamedQuery("Actor.findAll", Actor.class);
		List<Actor> actorList = query.getResultList();
		
		try{
			if(actorList.size() > 0){				
				for(Actor actor : actorList){
					Object[] rows = new Object[actorList.size()];
					
					rows[0] = actor.getNombresActor();
					
					modelo.addRow(rows);
				}
			}
		}
		catch (Exception e) {

		}
		
		scrollPane.setViewportView(tblActor);
	}
}
