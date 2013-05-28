import java.util.Random;


public class CampoMinas {
	private int estadoPartida;
	private Casilla campoMinas[][];
	private int casillasParaLaVictoria;
	private int numMinas;
	
	public static int JUGANDO =0;
	public static int VICTORIA=1;
	public static int DERROTA=2;
	
	public CampoMinas(int filas,int columnas,int numMinas){
		campoMinas=new Casilla[filas][columnas];
		estadoPartida=CampoMinas.JUGANDO;
		this.numMinas=numMinas;
		casillasParaLaVictoria=filas*columnas-numMinas;
		int fila;
		int columna;
		Random r = new Random();
		/*for(int i=0;i<filas;i++)
			for(int j=0;j<columnas;j++)
				campoMinas[i][j]=new Casilla(Casilla.CERO);*/
		while(numMinas>0){
			fila= r.nextInt(filas);
			columna=r.nextInt(columnas);
			if(campoMinas[fila][columna]==null){
				campoMinas[fila][columna]=new Casilla(Casilla.MINA);
			numMinas--;
			}
		}
		for(int i=0;i<filas;i++)
			for(int j=0;j<columnas;j++)
				campoMinas[i][j]=new Casilla(calculoMinasCercanas(i,j));
	
	}
	private int calculoMinasCercanas(int fila,int columna){
		int cont=0;
		for(int fi=fila-1;fi<=fila+1;fi++)
			for(int co=columna-1;co<=columna+1;columna++)
				try{
					if((co!=columna || fi!=fila)
						&& (campoMinas[fila][columna].getContenido()==Casilla.MINA))
					cont++;			
				}
					catch(IndexOutOfBoundsException e){}		
		return cont;	
		}
	
	public void clickIzquierdo(int fila,int columna) throws CasillaAbiertaException{
		if(campoMinas[fila][columna].getEstado()!=Casilla.DESCUBIERTA){
			campoMinas[fila][columna].setEstado(Casilla.DESCUBIERTA);
			if(campoMinas[fila][columna].getContenido()==Casilla.CERO)
				for(int fi=fila-1;fi<=fila+1;fi++)
					for(int co=columna-1;co<=columna+1;columna++)
						try{
							if(co!=columna || fi!=fila)
								clickIzquierdo(fi,co);
			
						}
							catch(IndexOutOfBoundsException e){}
			else if(campoMinas[fila][columna].getContenido()==Casilla.MINA)
				derrota();
			else if(--casillasParaLaVictoria==0)
					victoria();
				
		}
		else throw new CasillaAbiertaException();
		}
	public void clickDerecho(int fila,int columna) throws CasillaAbiertaException{
		if(campoMinas[fila][columna].getEstado()!=Casilla.DESCUBIERTA){
			if(campoMinas[fila][columna].getEstado()==Casilla.CUBIERTA){
				campoMinas[fila][columna].setEstado(Casilla.MARCADA);
				numMinas--;
			}
			else if(campoMinas[fila][columna].getEstado()==Casilla.MARCADA){
				campoMinas[fila][columna].setEstado(Casilla.INTERROGACION);
				numMinas++;
			}
			else campoMinas[fila][columna].setEstado(Casilla.DESCUBIERTA);
		}
		else throw new CasillaAbiertaException();
	}
	public void clickIzquierdoDerecho(int fila,int columna){
		if(campoMinas[fila][columna].getEstado()==Casilla.DESCUBIERTA && campoMinas[fila][columna].getContenido()!=Casilla.CERO)
			for(int fi=fila-1;fi<=fila+1;fi++)
				for(int co=columna-1;co<=columna+1;columna++)
					try{
						if((co!=columna || fi!=fila)&&campoMinas[fila][columna].getEstado()!=Casilla.MARCADA)
							try {
								clickIzquierdo(fi,co);
							} catch (CasillaAbiertaException e) {
								
							}
		
					}
						catch(IndexOutOfBoundsException e){}
			
	}
	
	public int getEstadoPartida(){
		return estadoPartida;
	}
	
	public int getNumMinas(){
		return numMinas;
	}
	private void victoria() {
		estadoPartida=CampoMinas.VICTORIA;
		
	}
	private void derrota() {
		estadoPartida=CampoMinas.DERROTA;
		
	}
	public int getCasillasParaLaVictoria() {
		return casillasParaLaVictoria;
	}
	
	

}
