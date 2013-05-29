import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
	

public class BuscaMinas extends JFrame implements ActionListener{
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
	
	public BuscaMinas() {
		super("Buscaminas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout());
		menuBar=new JMenuBar();
		menuJuego=new JMenu("Juego");
		itemNuevo=new JMenuItem("Nuevo Juego");
		itemNuevo.addActionListener(this);
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
		PCM = new PanelCampoMinas(30,16,99);
		add(PCM,BorderLayout.NORTH);
		lblMinas=new JLabel("0");
		lblMinas.setForeground(Color.WHITE);
		add(lblMinas,BorderLayout.EAST);
		lblTiempo=new JLabel("0");
		lblTiempo.setForeground(Color.WHITE);
		add(lblTiempo,BorderLayout.WEST);
		getContentPane().setBackground(Color.BLACK);
		
		pack();
		
	}
	public static void main(String[] args) {
		new BuscaMinas().setVisible(true);
		

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==itemNuevo){
			PCM = new PanelCampoMinas(30,16,99);
			super.add(PCM,BorderLayout.NORTH);
		}
	}

}
