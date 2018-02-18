package vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controlador.ClienteController;
import controlador.UsuarioController;
import modelo.Cliente;
import modelo.Rol;
import modelo.Usuario;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import java.awt.Panel;
import javax.swing.JTable;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

public class IngresarCliente extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public IngresarCliente() {
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 418, 327);
		add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("Identificacion:");
		label.setBounds(10, 31, 82, 26);
		panel.add(label);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(102, 28, 119, 23);
		panel.add(textField);
		
		JButton button = new JButton("");
		button.setBounds(261, 31, 46, 26);
		panel.add(button);
		
		JLabel label_1 = new JLabel("Apellidos:");
		label_1.setBounds(10, 75, 82, 14);
		panel.add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(102, 66, 161, 23);
		panel.add(textField_1);
		
		JLabel label_2 = new JLabel("Nombres:");
		label_2.setBounds(10, 106, 82, 14);
		panel.add(label_2);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(102, 100, 161, 23);
		panel.add(textField_2);
		
		JLabel label_3 = new JLabel("Direccion:");
		label_3.setBounds(10, 137, 82, 20);
		panel.add(label_3);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(102, 134, 206, 46);
		panel.add(textArea);
		
		JLabel label_4 = new JLabel("Convencional:");
		label_4.setBounds(10, 207, 82, 14);
		panel.add(label_4);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(102, 201, 119, 23);
		panel.add(textField_3);
		
		JLabel label_5 = new JLabel("Celular:");
		label_5.setBounds(10, 248, 46, 14);
		panel.add(label_5);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(102, 239, 119, 23);
		panel.add(textField_4);
		
		JButton button_1 = new JButton("Guardar");
		button_1.setBounds(78, 288, 89, 23);
		panel.add(button_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 374, 712, 132);
		add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);

	}
}
