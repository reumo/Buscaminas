import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


public class PanelCampoMinas extends JPanel implements MouseListener{
	private CampoMinas cm;
	private int filas;
	private int columnas;
	private int dimCelda=10;
	public PanelCampoMinas(int filas,int columnas,int numMinas) {
		addMouseListener(this);
		cm=new CampoMinas(filas, columnas, numMinas);
		setPreferredSize(new java.awt.Dimension(filas*dimCelda, columnas*dimCelda));
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
