package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import controlador.PeliculaController;
import modelo.Actor;
import modelo.Genero;
import modelo.Idioma;
import modelo.Pais;
import modelo.Pelicula;
import modelo.TipoFormato;

/**
 * 
 * @author Eduado Rosero 
 *
 */

public class IngresarPelicula extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private DefaultListModel<Object> modeloList = new DefaultListModel<>();
	
	private JTextField txtTitulo;
	private JPanel pnlDatos;
	
	private JComboBox<String> cmbGenero;
	private JComboBox<String> cmbPais;
	private JComboBox<String> cmbIdioma;
	private JComboBox<String> cmbFormato;
	private JComboBox<String> cmbAutor;
	private JComboBox<String> cmbAnioCreacion;
	private JList<Object> list;
	private JFormattedTextField fmtTxtPrecio, fmtTxtDuracion;
	private JTextArea txtAreaDescripcion;
	private JScrollPane scrollPane;
	
	private EntityManagerFactory conexion;
	private PeliculaController peliculaController;
	private JTable tblPeliculasList;
	
	/**
	 * Create the panel.
	 */
	
	public IngresarPelicula() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(Color.WHITE);
		setLayout(null);
				
		this.setSize(1270, 700);		
		conexion = Persistence.createEntityManagerFactory("VideomaniaSys");
		peliculaController = new PeliculaController(conexion);
		
		JLabel lblIngresoPelicula = new JLabel("REGISTRO DE PELICULAS");
		lblIngresoPelicula.setHorizontalAlignment(SwingConstants.CENTER);
		lblIngresoPelicula.setFont(new Font("Dialog", Font.BOLD, 20));
		lblIngresoPelicula.setBounds(515, 15, 300, 31);
		add(lblIngresoPelicula);
		
		pnlDatos = new JPanel();
		pnlDatos.setBounds(60, 58, 1149, 357);
		add(pnlDatos);
		pnlDatos.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Titulo de la Pelicula:");
		lblTitulo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTitulo.setBounds(12, 25, 150, 20);
		pnlDatos.add(lblTitulo);
		
		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAutor.setBounds(12, 70, 150, 20);
		pnlDatos.add(lblAutor);
		
		JLabel lblAnoCreacion = new JLabel("Año Creación:");
		lblAnoCreacion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAnoCreacion.setBounds(12, 120, 150, 20);
		pnlDatos.add(lblAnoCreacion);
		
		JLabel lblDuracion = new JLabel("Duraci\u00F3n:");
		lblDuracion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDuracion.setBounds(12, 170, 150, 20);
		pnlDatos.add(lblDuracion);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrecio.setBounds(12, 220, 150, 20);
		pnlDatos.add(lblPrecio);
		
		txtTitulo = new JTextField();
		txtTitulo.setBounds(180, 26, 300, 20);
		pnlDatos.add(txtTitulo);
		txtTitulo.setColumns(10);
		
		JLabel lblPelicula = new JLabel("Genero Pelicula:");
		lblPelicula.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPelicula.setBounds(500, 28, 120, 20);
		pnlDatos.add(lblPelicula);
		
		JLabel lblPais = new JLabel("País:");
		lblPais.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPais.setBounds(500, 70, 120, 20);
		pnlDatos.add(lblPais);
		
		JLabel lblIdioma = new JLabel("Idioma:");
		lblIdioma.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIdioma.setBounds(500, 120, 120, 20);
		pnlDatos.add(lblIdioma);
		
		JLabel lblFormato = new JLabel("Formato:");
		lblFormato.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFormato.setBounds(500, 170, 120, 20);
		pnlDatos.add(lblFormato);
		
		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescripcion.setBounds(12, 270, 150, 20);
		pnlDatos.add(lblDescripcion);
		
		txtAreaDescripcion = new JTextArea();
		txtAreaDescripcion.setLineWrap(true);
		txtAreaDescripcion.setWrapStyleWord(true);
		txtAreaDescripcion.setRows(4);
		txtAreaDescripcion.setBounds(180, 273, 700, 70);
//		JScrollPane scroll = new JScrollPane(txtAreaDescripcion, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		panel.add(scroll);
		pnlDatos.add(txtAreaDescripcion);
		
		JButton btnAgregarGenero = new JButton("");
		btnAgregarGenero.setIcon(new ImageIcon(IngresarPelicula.class.getResource("/resorces/img/anadir.png")));
		btnAgregarGenero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GeneroPelicula generoPelicula = new GeneroPelicula();
				generoPelicula.setVisible(true);
			}
		});
		
		btnAgregarGenero.setBounds(845, 28, 35, 20);
		pnlDatos.add(btnAgregarGenero);

//		cmbGenero = new JComboBox<String>();
//		cmbGenero.setBounds(634, 28, 200, 20);
//		cmbGenero.addItem("Seleccione.");
//		
//		List<Genero> generoItems = generoList();
//		
//		if(generoItems.size() > 0){
//			for(Genero genero : generoItems){
//				cmbGenero.addItem(genero.getGenero());
//			}
//		}
//		
//		pnlDatos.add(cmbGenero);
		
		cmbPais = new JComboBox<String>();
		cmbPais.setBounds(634, 70, 246, 20);
		cmbPais.addItem("Seleccione.");
		
		List<Pais> paisItems = paisList();
		
		if(paisItems.size() > 0){
			for(Pais pais : paisItems){
				cmbPais.addItem(pais.getNombrePais());
			}
		}
		
		pnlDatos.add(cmbPais);
		
		cmbIdioma = new JComboBox<String>();
		cmbIdioma.setBounds(634, 120, 246, 20);
		
		List<Idioma> idiomaItems = idiomaList();
		
		if(idiomaItems.size() > 0){
			for(Idioma idioma : idiomaItems){
				cmbIdioma.addItem(idioma.getDescripcionIdioma());
			}
		}
		
		pnlDatos.add(cmbIdioma);
		
		cmbFormato = new JComboBox<String>();
		cmbFormato.setBounds(634, 170, 246, 20);
		
		List<TipoFormato> formatoItems = formatoList();
		
		if(formatoItems.size() > 0){
			for(TipoFormato formato : formatoItems){
				cmbFormato.addItem(formato.getDescripcionFormato());
			}
		}
		
		pnlDatos.add(cmbFormato);
		
		cmbAnioCreacion = new JComboBox<String>();
		cmbAnioCreacion.setBounds(180, 120, 75, 20);
		
		List<String> anioItems = anioCreacionList();
		
		if(anioItems.size() > 0){
			for(String anio : anioItems){
				cmbAnioCreacion.addItem(anio);
			}
		}
		
		pnlDatos.add(cmbAnioCreacion);
		
		JButton btnNuevoActor = new JButton("");
		btnNuevoActor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActorVista actorVista = new ActorVista();
				actorVista.setVisible(true);
			}
		});
		btnNuevoActor.setIcon(new ImageIcon(IngresarPelicula.class.getResource("/resorces/img/nuevo-usuario.png")));
		btnNuevoActor.setBounds(452, 70, 28, 20);
		
		generoList();
		actorList();
		
		pnlDatos.add(btnNuevoActor);
		
		
		try {
			MaskFormatter mascaraPrecio = new MaskFormatter("#.##");
			mascaraPrecio.setPlaceholderCharacter('_');
			
			fmtTxtPrecio = new JFormattedTextField(mascaraPrecio);
			fmtTxtPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
			fmtTxtPrecio.setBounds(180, 221, 40, 20);
			pnlDatos.add(fmtTxtPrecio);
			
			
			MaskFormatter mascaraDuracion = new MaskFormatter("#h.##min");
			mascaraDuracion.setPlaceholderCharacter('_');
			
			fmtTxtDuracion = new JFormattedTextField(mascaraDuracion);
			fmtTxtDuracion.setBounds(180, 171, 75, 20);
			pnlDatos.add(fmtTxtDuracion);
			
			JPanel pnlCaratula = new JPanel();
			pnlCaratula.setBounds(911, 28, 228, 262);
			pnlDatos.add(pnlCaratula);
			
			JButton btnCargarCaratula = new JButton("Cargar Caratula");
			btnCargarCaratula.setBounds(976, 301, 120, 23);
			pnlDatos.add(btnCargarCaratula);
		} 
		catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		JPanel panel = new JPanel();
		panel.setBounds(984, 428, 225, 242);
		add(panel);
		panel.setLayout(null);		
		
		list = new JList<Object>();
		list.setBounds(10, 82, 190, 95);
		panel.add(list);
		
		JButton btnGuardar = new JButton("GUARDAR");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(guardarRegistro()){
					JOptionPane.showMessageDialog(null, "Registro Guardado.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
					txtTitulo.setText("");
					peliculasLista();
				}
				else{
					
				}
			}
		});
		btnGuardar.setIcon(new ImageIcon(IngresarPelicula.class.getResource("/resorces/img/guardar.png")));
		btnGuardar.setHorizontalTextPosition( SwingConstants.CENTER );
		btnGuardar.setVerticalTextPosition( SwingConstants.BOTTOM );
		btnGuardar.setBounds(44, 11, 100, 60);
		panel.add(btnGuardar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(60, 428, 900, 242);
		add(scrollPane);
		
		peliculasLista();
	}
	
	public void generoList(){
		EntityManager em = conexion.createEntityManager();
		TypedQuery<Genero> query = em.createNamedQuery("Genero.findAll", Genero.class);
		List<Genero> generoList = query.getResultList();
		
		cmbGenero = new JComboBox<String>();
		cmbGenero.setBounds(634, 28, 200, 20);
		cmbGenero.addItem("Seleccione.");
		
		if(generoList.size() > 0){
			for(Genero genero : generoList){
				cmbGenero.addItem(genero.getGenero());
			}
		}
		
		pnlDatos.add(cmbGenero);
	}
	
	public List<Pais> paisList(){
		EntityManager em = conexion.createEntityManager();
		TypedQuery<Pais> query = em.createNamedQuery("Pais.findAll", Pais.class);
		List<Pais> paisList = query.getResultList();
		
		return paisList;
	}
	
	public List<Idioma> idiomaList(){
		EntityManager em = conexion.createEntityManager();
		TypedQuery<Idioma> query = em.createNamedQuery("Idioma.findAll", Idioma.class);
		List<Idioma> idiomaList = query.getResultList();
		
		return idiomaList;
	}
	
	public List<TipoFormato> formatoList(){
		EntityManager em = conexion.createEntityManager();
		TypedQuery<TipoFormato> query = em.createNamedQuery("TipoFormato.findAll", TipoFormato.class);
		List<TipoFormato> formatoList = query.getResultList();
		
		return formatoList;
	}
	
	public List<String> anioCreacionList(){
		List<String> anioCreacionList = new ArrayList<String>();
		DateFormat formatoAnio = new SimpleDateFormat("yyyy");
		
		Date fechaActual = new Date();
		int anio = Integer.parseInt(formatoAnio.format(fechaActual));
		
		for(int i = anio ; i >= 1980 ; i--){
			anioCreacionList.add(""+i);
		}
		
		return anioCreacionList;
	}
	
	public void actorList(){
		EntityManager em = conexion.createEntityManager();
		TypedQuery<Actor> query = em.createNamedQuery("Actor.findAll", Actor.class);
		List<Actor> actorList = query.getResultList();
		
		cmbAutor = new JComboBox<String>();
		cmbAutor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cmbAutor.getSelectedItem().equals("Seleccione.")){
					modeloList.addElement(cmbAutor.getSelectedItem().toString());
					list.setModel(modeloList);
				}
			}
		});
		cmbAutor.setBounds(180, 70, 260, 20);
		cmbAutor.addItem("Seleccione.");
		
		if(actorList.size() > 0){
			for(Actor actor : actorList){
				cmbAutor.addItem(actor.getNombresActor());
			}
		}
		
		pnlDatos.add(cmbAutor);
	}
	
	public boolean guardarRegistro(){
		if(list.getModel().getSize() > 0){
			try{
				EntityManager em = conexion.createEntityManager();
				Pelicula pelicula = new Pelicula();
	//			Genero genero = new Genero();
				
				pelicula.setTitulo(txtTitulo.getText());
				pelicula.setAnio(Short.valueOf(cmbAnioCreacion.getSelectedItem().toString()));
				pelicula.setDuracion(fmtTxtDuracion.getText());
				pelicula.setPrecio(BigDecimal.valueOf(Double.parseDouble(fmtTxtPrecio.getText().toString())));
				pelicula.setSinopsis(txtAreaDescripcion.getText());
				pelicula.setCantidadCopias((short)10);
				
				TypedQuery<Genero> queryG = em.createNamedQuery("Genero.findByGenero", Genero.class).setParameter("genero", cmbGenero.getSelectedItem());
				List<Genero> generoList = queryG.getResultList();
				
				if(generoList.size() > 0){
					for(Genero g : generoList){
						pelicula.setIdGenero(g);
					}
				}
				
				TypedQuery<Idioma> queryI = em.createNamedQuery("Idioma.findByDescripcionIdioma", Idioma.class).setParameter("descripcionIdioma", cmbIdioma.getSelectedItem());
				List<Idioma> idiomaList = queryI.getResultList();
				
				if(idiomaList.size() > 0){
					for(Idioma i : idiomaList){
						pelicula.setIdIdioma(i);
					}
				}
				
				TypedQuery<Pais> queryP = em.createNamedQuery("Pais.findByNombrePais", Pais.class).setParameter("nombrePais", cmbPais.getSelectedItem());
				List<Pais> paisList = queryP.getResultList();
				
				if(paisList.size() > 0){
					for(Pais p : paisList){
						pelicula.setIdPais(p);
					}
				}
				
				TypedQuery<TipoFormato> queryF = em.createNamedQuery("TipoFormato.findByDescripcionFormato", TipoFormato.class).setParameter("descripcionFormato", cmbFormato.getSelectedItem());
				List<TipoFormato> formatoList = queryF.getResultList();
				
				if(formatoList.size() > 0){
					for(TipoFormato tf : formatoList){
						pelicula.setIdTipoformato(tf);
					}
				}
				
				peliculaController.create(pelicula);
				
				//SE DEBE UTILIZAR PERSITENCIA 
				/*for(int i = 0 ; i < list.getModel().getSize() ; i ++){
					TypedQuery<Actor> queryA = em.createNamedQuery("Actor.findByNombresActor", Actor.class).setParameter("nombresActor", list.getModel().getElementAt(i));
					List<Actor> actorList = queryA.getResultList();
					
					if(actorList.size() > 0){
						for(Actor a : actorList){
							peliculaController.ingresarPeliculaActor(a.getIdActor(), pelicula.getIdPelicula());
						}
					}
				}
				
				peliculaController.ingresarPeliculaActor(1, 1);*/
				
				return true;
			}
			catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	public void peliculasLista(){		
		tblPeliculasList = new JTable();
		tblPeliculasList.setEnabled(false);

		EntityManager em = conexion.createEntityManager();
		TypedQuery<Pelicula> query = em.createNamedQuery("Pelicula.findAll", Pelicula.class);
		List<Pelicula> peliculaList = query.getResultList();
		
		if(peliculaList.size() > 0){
			String tabla[][] = new String[peliculaList.size()][9];
			
			for(int i = 0 ; i < peliculaList.size() ; i++){
				tabla[i][0] = peliculaList.get(i).getTitulo();
				tabla[i][1] = String.valueOf(peliculaList.get(i).getAnio());
				tabla[i][2] = peliculaList.get(i).getDuracion();
				tabla[i][3] = String.valueOf(peliculaList.get(i).getPrecio());
				tabla[i][4] = peliculaList.get(i).getSinopsis();
				tabla[i][5] = peliculaList.get(i).getIdGenero().getGenero();
				tabla[i][6] = peliculaList.get(i).getIdIdioma().getDescripcionIdioma();
				tabla[i][7] = peliculaList.get(i).getIdPais().getNombrePais();
				tabla[i][8] = peliculaList.get(i).getIdTipoformato().getDescripcionFormato();
			}
			
			tblPeliculasList.setModel(new DefaultTableModel(tabla, new String[] {"TITULO","AÃ‘O","DURACIÃ“N","PRECIO","SINOPSIS","GENERO","IDIOMA","PAIS","FORMATO"}));
		}
		
		scrollPane.setViewportView(tblPeliculasList);
	}
}
