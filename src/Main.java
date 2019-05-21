import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class Main extends JFrame implements KeyListener{

	private JTextField linea;
	private JTextArea resultado;
	private Connection conexion;
	
	public Main() {
		super("Cliente MySQL");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Border borde = BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10), 
				BorderFactory.createCompoundBorder(
						BorderFactory.createEtchedBorder(),
						BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		getContentPane().setLayout(new BorderLayout());
		resultado = new JTextArea();
		resultado.setFocusable(false);
		resultado.setEditable(false);
		resultado.setBorder(borde);
		getContentPane().add(resultado, BorderLayout.CENTER);
		linea = new JTextField();
		linea.setBorder(borde);
		linea.addKeyListener(this);
		getContentPane().add(linea, BorderLayout.SOUTH);
		getContentPane().setPreferredSize(new Dimension(900, 700));
		pack();
		setLocationRelativeTo(null);
		
		Properties propiedades = new Properties();
		propiedades.put("user", "root");
		propiedades.put("password", "practicas");
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://192.168.10.67:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", propiedades);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					System.exit(-1);
				}
				new Main().setVisible(true);				
			}
		});

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			try {
				Statement sentencia = conexion.createStatement();
				if (sentencia.execute(linea.getText())) {
					ResultSet datos = sentencia.getResultSet();
					resultado.append("Número de columnas: " + datos.getMetaData().getColumnCount());
					while (datos.next()) {
						
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
