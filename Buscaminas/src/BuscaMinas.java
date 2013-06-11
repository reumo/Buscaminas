import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.plaf.OptionPaneUI;
	

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
	private int tiempoJuego=0;
	
	private static int PRINCIPIANTE=0;
	private static int INTERMEDIO=1;
	private static int EXPERTO=2;
	private int configuracion=EXPERTO;
	
	private int record[]={-1,-1,-1};
	private String nombreRecord[]={"------","------","------"};
	
	
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
	
		
		lblTiempo=new JLabel(Long.toString(tiempoJuego));
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
			
			verRecords();
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
			
			if(PCM.getEstadoPartida()==CampoMinas.JUGANDO){
				tiempoJuego=(int) (System.currentTimeMillis()-tiempoInicio)/1000;
				lblTiempo.setText(Integer.toString(tiempoJuego));
				lblMinas.setText(Integer.toString(PCM.getMinasFaltantes()));
			}
			if(PCM.getEstadoPartida()==CampoMinas.VICTORIA)
				insertarRecord();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			
			}
		}
	}
	private void insertarRecord(){
		
		if(record[configuracion]>tiempoJuego || record[configuracion]==-1){
			record[configuracion]=tiempoJuego;
			nombreRecord[configuracion]=JOptionPane.showInputDialog("nuevo record:");
		}
		if(nombreRecord[configuracion]==null)nombreRecord[configuracion]="Anonimo";
		
		
	}
	
	private void verRecords(){
		String rec=null;
		try {
			BufferedReader bf = new BufferedReader(new FileReader("bin/txt/record.txt"));
			
			int i=0;
			while (i<configuracion) {
				rec=bf.readLine();
				  i++;
				}
			bf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		StringBuffer r=new StringBuffer();
		if(Integer.parseInt(rec)==-1)
			r.append("Principiante: "+"------------------\n");
		else r.append("Principiante: "+nombreRecord[0]+" "+rec+"s\n");
		if(Integer.parseInt(rec)==-1)
			r.append("Intermedio:   "+"------------------\n");
		else r.append("Intermedio:   "+nombreRecord[1]+" "+rec+"s\n");
		if(Integer.parseInt(rec)==-1)
			r.append("Experto:      "+"------------------\n");
		else r.append("Experto:      "+nombreRecord[2]+" "+record+"s");
			JOptionPane.showMessageDialog(null, r);
	
	}
	public void iniciar(){
		t=new Thread(this);
		iniciado=true;
		t.start();
	}
	

}
