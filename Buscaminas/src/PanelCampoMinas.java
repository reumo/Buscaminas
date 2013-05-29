import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


public class PanelCampoMinas extends JPanel implements MouseListener{
	private CampoMinas cm;
	private int filas;
	private int columnas;
	private int dimCelda=20;
	private int clickX;
	private int clickY;
	public PanelCampoMinas(int filas,int columnas,int numMinas) {
		this.filas=filas;
		this.columnas=columnas;
		addMouseListener(this);
		cm=new CampoMinas(filas, columnas, numMinas);
		setPreferredSize(new java.awt.Dimension(filas*dimCelda, columnas*dimCelda));
		
		
	}
	public void paintComponent(Graphics g){
		if(cm.getEstadoPartida()==CampoMinas.DERROTA){
			for(int i=0;i<filas;i++)
				for(int j=0;j< columnas;j++)
					if(cm.getContenidoCelda(i, j)==Casilla.MINA){
					g.setColor(Color.RED);
					g.fillRect(i*dimCelda+1, j*dimCelda+1, dimCelda-1, dimCelda-1);
				}
		}
		else if(cm.getEstadoPartida()==CampoMinas.JUGANDO)	
			for(int i=0;i<filas;i++)
				for(int j=0;j<columnas;j++){
					if(cm.getEstadoCelda(i, j)>=Casilla.CUBIERTA){
						if(cm.getEstadoCelda(i, j)==Casilla.CUBIERTA)
							g.setColor(Color.GRAY);
						else if(cm.getEstadoCelda(i, j)==Casilla.MARCADA)
							g.setColor(Color.BLUE);
						else if(cm.getEstadoCelda(i, j)==Casilla.INTERROGACION)
							g.setColor(Color.MAGENTA);
						g.fillRect(i*dimCelda+1, j*dimCelda+1, dimCelda-1, dimCelda-1);
					}
					else {
						g.setColor(Color.BLACK);
						g.fillRect(i*dimCelda+1, j*dimCelda+1, dimCelda-1, dimCelda-1);
						g.setColor(Color.WHITE);
						g.drawString(Integer.toString(cm.getContenidoCelda(i, j)), i*dimCelda+7, j*dimCelda+15);
					}
				}
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
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent m) {
		if(m.getButton()==m.BUTTON2){
			cm.clickIzquierdoDerecho(m.getX()/dimCelda,m.getY()/dimCelda);
			repaint();
		}
		else if(m.getButton()==m.BUTTON3)
			try {
				cm.clickDerecho(m.getX()/dimCelda,m.getY()/dimCelda);
				repaint();
			} catch (CasillaAbiertaException e) {}
		else if(m.getButton()==m.BUTTON1){
				/*try {*/
					cm.clickIzquierdo(m.getX()/dimCelda, m.getY()/dimCelda);
					repaint();
				/*} catch (CasillaAbiertaException e) {}*/
		}
		
	}

}
