import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class PanelCampoMinas extends JPanel implements MouseListener{
	private CampoMinas cm;
	private int filas;
	private int columnas;
	private int dimCelda=20;
	private int fp;
	private int cp;
	private boolean btn1=false;
	private boolean btn3=false;
	private BufferedImage img= null;
	//private BufferedImage bandera=null;
	
	public PanelCampoMinas(int filas,int columnas,int numMinas) {
		this.filas=filas;
		this.columnas=columnas;
		addMouseListener(this);
		cm=new CampoMinas(filas, columnas, numMinas);
		setPreferredSize(new java.awt.Dimension(filas*dimCelda, columnas*dimCelda));
		
		
	}
	public void cambiarNivel(int filas,int columnas,int numMinas){
		this.filas=filas;
		this.columnas=columnas;
		//addMouseListener.(this);
		cm=new CampoMinas(filas,columnas,numMinas);
		setPreferredSize(new java.awt.Dimension(filas*dimCelda, columnas*dimCelda));
		repaint();
		
	}
	public void paintComponent(Graphics g){
		
		if(cm.getEstadoPartida()==CampoMinas.DERROTA){
			
			for(int i=0;i<filas;i++)
				for(int j=0;j< columnas;j++){
					if(cm.getEstadoCelda(i, j)==Casilla.MARCADA){
						setImgMinaError();
						g.drawImage(img, i*dimCelda+4, j*dimCelda+1, this);
					}
					if(cm.getContenidoCelda(i, j)==Casilla.MINA){
						g.setColor(Color.RED);
						g.fillRect(i*dimCelda+1, j*dimCelda+1, dimCelda-1, dimCelda-1);
						setImgMina();
						g.drawImage(img, i*dimCelda+4, j*dimCelda+1, this);
				}
			}
		}
		else if (cm.getEstadoPartida()==CampoMinas.VICTORIA){
			
			for(int i=0;i<filas;i++)
				for(int j=0;j<columnas;j++){
					if(cm.getContenidoCelda(i, j)==Casilla.MINA){
						g.setColor(Color.GREEN);
						g.fillRect(i*dimCelda+1, j*dimCelda+1, dimCelda-1, dimCelda-1);
						setImgMina();
						g.drawImage(img, i*dimCelda+4, j*dimCelda+1, this);
					}
					
					else {
						g.setColor(Color.WHITE);
						g.fillRect(i*dimCelda+1, j*dimCelda+1, dimCelda-1, dimCelda-1);
						g.setColor(Color.BLACK);
						if(cm.getContenidoCelda(i, j)!=Casilla.CERO)
						g.drawString(Integer.toString(cm.getContenidoCelda(i, j)), i*dimCelda+7, j*dimCelda+15);
					}
				}
		}
		else if(cm.getEstadoPartida()==CampoMinas.JUGANDO)	
			for(int i=0;i<filas;i++)
				for(int j=0;j<columnas;j++){
					g.setColor(Color.BLACK);
					g.fillRect(i*dimCelda, j*dimCelda, dimCelda, dimCelda);
					if(cm.getEstadoCelda(i, j)==Casilla.DESCUBIERTA){
						g.setColor(Color.BLACK);
						g.fillRect(i*dimCelda, j*dimCelda, dimCelda, dimCelda);
						g.setColor(Color.WHITE);
						g.fillRect(i*dimCelda+1, j*dimCelda+1, dimCelda-1, dimCelda-1);
						g.setColor(Color.BLACK);
						if(cm.getContenidoCelda(i, j)!=Casilla.CERO)
							g.drawString(Integer.toString(cm.getContenidoCelda(i, j)), i*dimCelda+7, j*dimCelda+15);
					}
					else {
						g.setColor(Color.GRAY);
						g.fillRect(i*dimCelda+1, j*dimCelda+1, dimCelda-1, dimCelda-1);							
						if(cm.getEstadoCelda(i, j)==Casilla.MARCADA){
							setImgBandera();
							g.drawImage(img, i*dimCelda+1, j*dimCelda+1, this);
						}
						if(cm.getEstadoCelda(i, j)==Casilla.INTERROGACION){
							g.setColor(Color.WHITE);
							g.drawString("?", i*dimCelda+7, j*dimCelda+15);
						}
						
					}
				}
	}
	private void setImgMina(){
		try {
			   img = ImageIO.read( getClass().getResource("/img/mina.png"));
			} catch (IOException e) {}
	}
	private void setImgBandera(){
		try {
			img= ImageIO.read( getClass().getResource("/img/bandera.png"));
		} catch (IOException e) {}
	}
	private void setImgMinaError(){
		try {
			img= ImageIO.read( getClass().getResource("/img/minaError.png"));
		} catch (IOException e) {}
	}

	@Override
	public void mouseClicked(MouseEvent m) {
		
		
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		if(cm.getEstadoPartida()==CampoMinas.JUGANDO){
		fp=m.getX()/dimCelda;
		cp=m.getY()/dimCelda;
		if(m.getButton()==MouseEvent.BUTTON3){
			btn3=true;
			if(!btn1)
				try {
					//System.out.println("button3");
					cm.senalizar(fp,cp);
				} catch (CasillaAbiertaException e) {
			
			}
		}else if(m.getButton()==MouseEvent.BUTTON1){
				//System.out.println("button1");
				btn1=true;
			}
			if(btn3 ||(btn1 && btn3))
		 repaint();
		}
	}
	

	@Override
	public void mouseReleased(MouseEvent m) {
		if(cm.getEstadoPartida()==CampoMinas.JUGANDO){
		int f=m.getX()/dimCelda;
		int c=m.getY()/dimCelda;
		//btn1=m.getButton()==MouseEvent.BUTTON1;
			if(btn1 && !btn3)
				//System.out.println("button1");
				cm.descubrir(f,c);
			else if(btn1 && btn3)
				//System.out.println("buton13");
				cm.descubrirMultiple(f, c);
			btn1=btn3=false;
			repaint();
		}
		
	}
	public void configuracion(int filas,int columnas,int numMinas){
		cm.configuracion(filas, columnas, numMinas);
	}
	public void reset(){
		cm.reset();
		repaint();
	}
	
	public int getMinasFaltantes(){
		return cm.getMinasFaltantes();
	}
	public int getEstadoPartida(){
		return cm.getEstadoPartida();
			
	}

}
