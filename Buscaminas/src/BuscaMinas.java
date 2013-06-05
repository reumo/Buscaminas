import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
	

public class BuscaMinas extends JFrame implements ActionListener,Runnable{
	private PanelCampoMinas PCM;
	private JLabel lblMinas;
	private JLabel lblTiempo;
	private JMenuBar menuBar;
	private JMenu menuJuego;
	private JMenuItem itemNuevo;
	private JMenuItem itemEstadisticas;
	private JMenuItem itemOpciones;
	private JMenuItem itemAparencia;
	private JMenuItem itemSalir;
	
	//elementos pop up menu
	private JRadioButton principiante;
	private JRadioButton intermedio;
	private JRadioButton experto;
	private JMenuItem exitMenu;
	private JMenuItem jugar;
	
	
	
	private Thread t;
	private boolean iniciado=true; 
	private long tiempoInicio=System.currentTimeMillis();
	private String tiempoJuego="0";
	private JPopupMenu menuOpciones;
	private static int PRINCIPIANTE=0;
	private static int INTERMEDIO=1;
	private static int EXPERTO=2;
	private int configuracion=EXPERTO;
	
	
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
		itemEstadisticas.addActionListener(this);
		itemOpciones= new JMenuItem("Opciones");
		itemOpciones.addActionListener(this);
		itemAparencia=new JMenuItem("Aparencia");
		itemAparencia.addActionListener(this);
		itemSalir=new JMenuItem("Salir");
		itemSalir.addActionListener(this);
		menuJuego.add(itemNuevo);
		menuJuego.addSeparator();
		menuJuego.add(itemEstadisticas);
		menuJuego.add(itemOpciones);
		menuJuego.add(itemAparencia);
		menuJuego.addSeparator();
		menuJuego.add(itemSalir);
		menuBar.add(menuJuego);
		setJMenuBar(menuBar);
		PCM = new PanelCampoMinas(30,16,10);
		add(PCM,BorderLayout.NORTH);
		lblMinas=new JLabel(Integer.toString(PCM.getMinasFaltantes()));
		lblMinas.setForeground(Color.WHITE);
		lblMinas.setIcon(new ImageIcon(getClass().getResource("/img/mina.png")));
		add(lblMinas,BorderLayout.EAST);
	
		
		lblTiempo=new JLabel(tiempoJuego);
		lblTiempo.setIcon(new ImageIcon(getClass().getResource("/img/reloj.png")));
		lblTiempo.setForeground(Color.WHITE);
		add(lblTiempo,BorderLayout.WEST);
		getContentPane().setBackground(Color.BLACK);
		
		
		menuOpciones = new JPopupMenu("Opciones");
		JLabel etiqueta = new JLabel("MENÚ DE OPCIONES");
		ButtonGroup grupo=new ButtonGroup();
		principiante=new JRadioButton("Principiante");
		principiante.addActionListener(this);
		intermedio=new JRadioButton("Intermedio");
		intermedio.addActionListener(this);
		experto=new JRadioButton("Experto");
		experto.addActionListener(this);
		jugar=new JMenuItem("Jugar");
		jugar.addActionListener(this);
		exitMenu=new JMenuItem("Salir");
		exitMenu.addActionListener(this);
		
		
		// menu opciones
		grupo.add(principiante);
		grupo.add(intermedio);
		grupo.add(experto);
		if(configuracion==EXPERTO) experto.setSelected(true);
		else if (configuracion==INTERMEDIO) intermedio.setSelected(true);
		else principiante.setSelected(true);
		
		menuOpciones.add(etiqueta);
		menuOpciones.addSeparator();
		menuOpciones.add(principiante);
		menuOpciones.add(intermedio);
		menuOpciones.add(experto);
		menuOpciones.addSeparator();
		menuOpciones.add(jugar);
		menuOpciones.addSeparator();
		menuOpciones.add(exitMenu);
	
		//add(menuOpciones);
		menuOpciones.setVisible(false);
		
		iniciar();
		pack();
		
	}
	public static void main(String[] args) {
		new BuscaMinas().setVisible(true);
		

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==itemNuevo){
			lblTiempo.setText("0");
			tiempoInicio=System.currentTimeMillis();
			PCM.reset();
			
		}
		else if(e.getSource()==itemEstadisticas){
			
			//System.out.println("estad");
		}
		else if(e.getSource()==itemOpciones){
			add(menuOpciones);
			menuOpciones.setVisible(true);
		}
		/*else if(e.getSource()==principiante){
			configuracion=PRINCIPIANTE;
		}*/
		else if(e.getSource()==itemAparencia){
	
			//System.out.println("estad");
		}
		else if(e.getSource()==itemSalir){
			
			System.exit(0);
		}
		else if(e.getSource()==principiante){
			configuracion=PRINCIPIANTE;
			//PCM.configuracion(10, 10, 10);
			
			
		}
		else if(e.getSource()==jugar){
			//PCM.reset();
			//PCM=new PanelCampoMinas(10, 10, 10);
			
			//menuOpciones.setVisible(false);
		}
		else if(e.getSource()==exitMenu){
			menuOpciones.setVisible(false);
		}
	}
	@Override
	public void run() {
		while(iniciado){
			
			if(PCM.juegoIniciado())
				tiempoJuego=Long.toString((System.currentTimeMillis()-tiempoInicio)/1000);
			lblTiempo.setText(tiempoJuego);
			lblMinas.setText(Integer.toString(PCM.getMinasFaltantes()));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				
			}
		}
	}
	public void iniciar(){
		t=new Thread(this);
		iniciado=true;
		t.start();
	}
	

}
