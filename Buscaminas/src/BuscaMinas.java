import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
	

public class BuscaMinas extends JFrame{
	PanelCampoMinas PCM;
	JLabel lblMinas;
	JLabel lblTiempo;
	JMenuBar menuBar;
	JMenu menuJuego;
	JMenuItem itemNuevo;
	JMenuItem itemEstadisticas;
	JMenuItem itemOpciones;
	JMenuItem itemAparencia;
	JMenuItem itemSalir;
	
	public BuscaMinas(){
		super("Buscaminas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		menuBar=new JMenuBar();
		menuJuego=new JMenu("Juego");
		itemNuevo=new JMenuItem("Nuevo Juego");
		itemEstadisticas=new JMenuItem("Estadisticas");
		itemOpciones= new JMenuItem("Opciones");
		itemAparencia=new JMenuItem("Aparencia");
		itemSalir=new JMenuItem("Salir");
		menuJuego.add(itemNuevo);
		menuJuego.addSeparator();
		menuJuego.add(itemEstadisticas);
		menuJuego.add(itemOpciones);
		menuJuego.add(itemAparencia);
		menuJuego.addSeparator();
		menuJuego.add(itemSalir);
		menuBar.add(menuJuego);
		
		setJMenuBar(menuBar);
		PCM = new PanelCampoMinas(16,30,99);
		add(PCM,BorderLayout.NORTH);
		lblMinas=new JLabel("0");
		add(lblMinas,BorderLayout.EAST);
		lblTiempo=new JLabel("0");
		add(lblTiempo,BorderLayout.WEST);
		pack();
		
	}
	public static void main(String[] args) {
		new BuscaMinas().setVisible(true);
		

	}

}
