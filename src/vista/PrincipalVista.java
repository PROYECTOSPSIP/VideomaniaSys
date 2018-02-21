package vista;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class PrincipalVista extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JMenuItem mntmIngresarPelicula, mntmConsultarPelicula, mntmIngresarCliente, mntmGestionarUsuarios, mntmGestionarActores;;
	private IngresarPelicula ingresarPelicula;
	private ConsultarPelicula consultarPelicula;
//	private GestionCliente gestionCliente;
//	private GestionarActor gestionarActor;
//	private GestionarUsuario gestionarUsuario;
	private IngresarCliente ingresarCliente;
	private NuevoUsuario nuevoUsuario;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PrincipalVista frame = new PrincipalVista();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public PrincipalVista() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setTitle("VIDEOMANIA");// colocamos titulo a la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // hacemos que cuando se cierre la ventana termina todo proceso
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Pelicula");
		menuBar.add(mnNewMenu);
		
		mntmIngresarPelicula = new JMenuItem("Ingresar pelicula");
		mnNewMenu.add(mntmIngresarPelicula);
		
		mnNewMenu.addSeparator();
		
		mntmConsultarPelicula = new JMenuItem("Consultar Pelicula");
		mnNewMenu.add(mntmConsultarPelicula);
		
		JMenu mnCliente = new JMenu("Cliente");
		menuBar.add(mnCliente);
		
		mntmIngresarCliente = new JMenuItem("Gestionar Clientes");
		mnCliente.add(mntmIngresarCliente);
		
		JMenu mnAdministracion = new JMenu("Administracion");
		menuBar.add(mnAdministracion);
		
		mntmGestionarUsuarios = new JMenuItem("Gestionar Usuarios");
		mnAdministracion.add(mntmGestionarUsuarios);
		
		mntmGestionarActores = new JMenuItem("Gestionar Actores");
		mnAdministracion.add(mntmGestionarActores);
		
		JMenu mnCerrarSesion = new JMenu("Cerrar Sesion");
		menuBar.add(mnCerrarSesion);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		mnCerrarSesion.add(mntmSalir);
		
		mntmIngresarPelicula.addActionListener(this);
		mntmConsultarPelicula.addActionListener(this);
		mntmIngresarCliente.addActionListener(this);
		mntmGestionarUsuarios.addActionListener(this);
		mntmGestionarActores.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mntmIngresarPelicula)
			ingresarPelicula();
		
		if(e.getSource() == mntmConsultarPelicula)
			consultarPelicula();
		
		if(e.getSource() == mntmIngresarCliente)
			gestionarCliente();
		
		if(e.getSource() == mntmGestionarUsuarios)
			gestionarUsuario();
		
//		if(e.getSource() == mntmGestionarActores)
//			gestionarActor();
		
	}
	
	public void ingresarPelicula(){
		try{
			//this.remove(gestionarActor);
			this.remove(consultarPelicula);
		}
		catch(Exception e){
			
		}
		
		ingresarPelicula = new IngresarPelicula();
		getContentPane().add(ingresarPelicula, BorderLayout.CENTER);
		this.pack();
	}
	
	public void consultarPelicula(){
		try{
		 this.remove(ingresarPelicula);
		}
		catch(Exception e){
		}
		
		consultarPelicula = new ConsultarPelicula();
		getContentPane().add(consultarPelicula, BorderLayout.CENTER);
		this.pack();
	}
	
	/*public void gestionarUsuario(){
		try{
		 this.remove(ingresarPelicula);
		}
		catch(Exception e){
		}
		
		gestionarUsuario =  new GestionarUsuario();
		gestionarUsuario.setVisible(true);
		getContentPane().add(gestionarUsuario, BorderLayout.CENTER);
		pack();		
	}*/

	/*public void gestionarUsuario(){
		try{
		 this.remove(ingresarPelicula);
		}
		catch(Exception e){
		}
		
		gestionarUsuario =  new GestionarUsuario();
		gestionarUsuario.setVisible(true);
		getContentPane().add(gestionarUsuario, BorderLayout.CENTER);
		pack();		
	}

	public void gestionarUsuario(){
		try{
		 this.remove(ingresarPelicula);
		}
		catch(Exception e){
		}
		
		gestionarUsuario =  new GestionarUsuario();
		gestionarUsuario.setVisible(true);
		getContentPane().add(gestionarUsuario, BorderLayout.CENTER);
		pack();		
	}

	public void gestionarUsuario(){
		try{
		 this.remove(ingresarPelicula);
		}
		catch(Exception e){
		}
		
		gestionarUsuario =  new GestionarUsuario();
		gestionarUsuario.setVisible(true);
		getContentPane().add(gestionarUsuario, BorderLayout.CENTER);
		pack();		
	}

	public void gestionarUsuario(){
		try{
		 this.remove(ingresarPelicula);
		}
		catch(Exception e){
		}
		
		gestionarUsuario =  new GestionarUsuario();
		gestionarUsuario.setVisible(true);
		getContentPane().add(gestionarUsuario, BorderLayout.CENTER);
		pack();		
	}

	public void gestionarUsuario(){
		try{
		 this.remove(ingresarPelicula);
		}
		catch(Exception e){
		}
		
		gestionarUsuario =  new GestionarUsuario();
		gestionarUsuario.setVisible(true);
		getContentPane().add(gestionarUsuario, BorderLayout.CENTER);
		pack();		
	}

	public void gestionarUsuario(){
		try{
		 this.remove(ingresarPelicula);
		}
		catch(Exception e){
		}
		
		gestionarUsuario =  new GestionarUsuario();
		gestionarUsuario.setVisible(true);
		getContentPane().add(gestionarUsuario, BorderLayout.CENTER);
		pack();		
	}

	public void gestionarUsuario(){
		try{
		 this.remove(ingresarPelicula);
		}
		catch(Exception e){
		}
		
		gestionarUsuario =  new GestionarUsuario();
		gestionarUsuario.setVisible(true);
		getContentPane().add(gestionarUsuario, BorderLayout.CENTER);
		pack();		
	}*/

	public void gestionarUsuario(){
		try{
		 this.remove(ingresarPelicula);
		}
		catch(Exception e){
		}		
		nuevoUsuario =  new NuevoUsuario();
		nuevoUsuario.setVisible(true);
		getContentPane().add(nuevoUsuario, BorderLayout.CENTER);
		pack();		
	}
	
//	public void gestionarActor(){
//		try{
//		 this.remove(ingresarPelicula);
//		}
//		catch(Exception e){
//		}
//		
//		gestionarActor=  new GestionarActor();
//		gestionarActor.setVisible(true);
//		getContentPane().add(gestionarActor, BorderLayout.CENTER);
//		this.pack();		
//	}
//	
	public void gestionarCliente(){
		try{
		 this.remove(ingresarPelicula);
		}
		catch(Exception e){
		}
		
		ingresarCliente =  new IngresarCliente();
		ingresarCliente.setVisible(true);
		getContentPane().add(ingresarCliente, BorderLayout.CENTER);
		pack();		
	}
}
