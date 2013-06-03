import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
	

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
	private Thread t;
	private boolean iniciado=true; 
	private long tiempoInicio=System.currentTimeMillis();
	//private BufferedImage img= null;
	
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
		PCM = new PanelCampoMinas(30,16,10);
		add(PCM,BorderLayout.NORTH);
		lblMinas=new JLabel(Integer.toString(PCM.getMinasFaltantes()));
		lblMinas.setForeground(Color.WHITE);
		add(lblMinas,BorderLayout.EAST);
		lblTiempo=new JLabel("0");
		lblTiempo.setForeground(Color.WHITE);
		add(lblTiempo,BorderLayout.WEST);
		getContentPane().setBackground(Color.BLACK);
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
	}
	@Override
	public void run() {
		while(iniciado){
		lblTiempo.setText(Long.toString((System.currentTimeMillis()-tiempoInicio)/1000));
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
	
	/*private void setImgMina(){
		try {
			   img = ImageIO.read( getClass().getResource("/img/mina.png"));
			} catch (IOException e) {}
	}*/

}
