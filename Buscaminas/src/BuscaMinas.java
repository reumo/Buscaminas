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
import javax.swing.border.Border;
	

public class BuscaMinas extends JFrame implements ActionListener,Runnable{
	private PanelCampoMinas PCM;
	private JLabel lblMinas;
	private JLabel lblTiempo;
	private JMenuBar menuBar;
	private JMenu menuJuego;
	private JMenuItem itemNuevo;
	private JMenuItem itemEstadisticas;
	private JMenu menuOpciones;
	private JMenuItem itemAparencia;
	private JMenuItem itemSalir;
	
	//elementos  menu opciones
	//private JPopupMenu menuOpciones;
	private JMenuItem itemPrincipiante;
	private JMenuItem itemIntermedio;
	private JMenuItem itemExperto;

	
	
	
	private Thread t;
	private boolean iniciado=true; 
	private long tiempoInicio=System.currentTimeMillis();
	private String tiempoJuego="0";
	
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
		menuOpciones= new JMenu("Opciones");
		menuOpciones.addActionListener(this);
		itemAparencia=new JMenuItem("Aparencia");
		itemAparencia.addActionListener(this);
		itemSalir=new JMenuItem("Salir");
		itemSalir.addActionListener(this);
		menuJuego.add(itemNuevo);
		menuJuego.addSeparator();
		menuJuego.add(itemEstadisticas);
		menuJuego.add(menuOpciones);
		menuJuego.add(itemAparencia);
		menuJuego.addSeparator();
		menuJuego.add(itemSalir);
		menuBar.add(menuJuego);
		setJMenuBar(menuBar);
		PCM = new PanelCampoMinas(40,30,50);
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
		
		
		//menuOpciones = new JPopupMenu("Opciones");
		
		ButtonGroup grupo=new ButtonGroup();
		itemPrincipiante=new JMenuItem("Principiante");
		itemPrincipiante.addActionListener(this);
		itemIntermedio=new JMenuItem("Intermedio");
		itemIntermedio.addActionListener(this);
		itemExperto=new JMenuItem("-·- Experto -·-");
		itemExperto.addActionListener(this);
	

		
		
		// menu opciones
		grupo.add(itemPrincipiante);
		grupo.add(itemIntermedio);
		grupo.add(itemExperto);
		if(configuracion==EXPERTO) itemExperto.setSelected(true);
		else if (configuracion==INTERMEDIO) itemIntermedio.setSelected(true);
		else itemPrincipiante.setSelected(true);
		
		
		menuOpciones.add(itemPrincipiante);
		menuOpciones.add(itemIntermedio);
		menuOpciones.add(itemExperto);
		

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
		else if(e.getSource()==itemAparencia){
	
			
		}
		else if(e.getSource()==itemSalir){
			
			System.exit(0);
		}
		else if(e.getSource()==itemPrincipiante){
			configuracion=PRINCIPIANTE;
			itemPrincipiante.setText("-·-Principiante-·-");
			itemIntermedio.setText("Intermedio");
			itemExperto.setText("Experto");
			PCM.cambiarNivel(10, 10, 10);
			lblTiempo.setText("0");
			tiempoInicio=System.currentTimeMillis();
			super.pack();
		}
		else if(e.getSource()==itemIntermedio){
			configuracion=INTERMEDIO;
			itemPrincipiante.setText("Principiante");
			itemIntermedio.setText("-·-Intermedio-·-");
			itemExperto.setText("Experto");
			PCM.cambiarNivel(20, 20, 20);
			lblTiempo.setText("0");
			tiempoInicio=System.currentTimeMillis();
			super.pack();
		}
		else if(e.getSource()==itemExperto){
			configuracion=EXPERTO;
			itemPrincipiante.setText("Principiante");
			itemIntermedio.setText("Intermedio");
			itemExperto.setText("-·-Experto-·-");
			PCM.cambiarNivel(40, 30, 50);
			lblTiempo.setText("0");
			tiempoInicio=System.currentTimeMillis();
			super.pack();
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
