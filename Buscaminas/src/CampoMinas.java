import java.util.Random;


public class CampoMinas {
	private int estadoPartida;
	private Casilla campoMinas[][];
	private int casillasParaLaVictoria;
	private int numMinas;
	private int filas;
	private int columnas;
	private int nm;
	public static int JUGANDO =0;
	public static int VICTORIA=1;
	public static int DERROTA=2;
	
	public CampoMinas(int filas,int columnas,int numMinas){
		campoMinas=new Casilla[this.filas=filas][this.columnas=columnas];
		this.numMinas=numMinas;
		reset();
		
		
	
	}
	public void reset(){
		//relleno de casillas vacias
		
		casillasParaLaVictoria=filas*columnas-numMinas;	
		for(int i=0;i<filas;i++)
			for(int j=0;j<columnas;j++)
				campoMinas[i][j]=new Casilla(Casilla.CERO);
		rellenoaleatorio();
		estadoPartida=JUGANDO;
	}
	public void configuracion(int filas,int columnas,int numMinas){
		this.filas=filas;
		this.columnas=columnas;
		this.numMinas=numMinas;
		
	}
	private void rellenoaleatorio(){
		//relleno aleatorio de minas
		Random r = new Random();
		int fila;
		int columna;
		nm = numMinas;
			while(nm>0){
				fila= r.nextInt(filas);
				columna=r.nextInt(columnas);
				if(campoMinas[fila][columna].getContenido()==Casilla.CERO){
					campoMinas[fila][columna]=new Casilla(Casilla.MINA);
					nm--;
				}
			}
		nm=numMinas;
			//relleno de cuantas minas hay adiacente
			for(int i=0;i<filas;i++)
				for(int j=0;j<columnas;j++)
					if(campoMinas[i][j].getContenido()==Casilla.CERO)
						campoMinas[i][j]=new Casilla(calculoMinasCercanas(i,j));
	}
	private int calculoMinasCercanas(int fila,int columna){
		int cont=0;
		for(int fi=fila-1;fi<=fila+1;fi++)
			for(int co=columna-1;co<=columna+1;co++)
				try{
					if((co!=columna || fi!=fila) && (campoMinas[fi][co].getContenido()==Casilla.MINA))
						cont++;			
				}
					catch(IndexOutOfBoundsException e){}		
		return cont;	
		}
	
	public int getContenidoCelda(int fila,int columna){
		return campoMinas[fila][columna].getContenido();
		
	}
	public int getEstadoCelda(int fila,int columna){
		return campoMinas[fila][columna].getEstado();
	}

	public void descubrir(int fila,int columna)/* throws CasillaAbiertaException*/{
		if(campoMinas[fila][columna].getEstado()==Casilla.CUBIERTA || campoMinas[fila][columna].getEstado()==Casilla.INTERROGACION){
			campoMinas[fila][columna].setEstado(Casilla.DESCUBIERTA);
			if(campoMinas[fila][columna].getContenido()==Casilla.MINA)
				derrota();
			else if(--casillasParaLaVictoria==0)
					victoria();
			else if(campoMinas[fila][columna].getContenido()==Casilla.CERO)
				for(int fi=fila-1;fi<=fila+1;fi++)
					for(int co=columna-1;co<=columna+1;co++)
						if(co!=columna || fi!=fila)
						try{
							
								descubrir(fi,co);
			
						}
							catch(IndexOutOfBoundsException e){}
			}
		}
		/*else throw new CasillaAbiertaException();
		}*/
	
	public void senalizar(int fila,int columna) throws CasillaAbiertaException{
			//nm=numMinas;
			if(campoMinas[fila][columna].getEstado()==Casilla.CUBIERTA && nm>0){
				campoMinas[fila][columna].setEstado(Casilla.MARCADA);
				nm--;
			}
			else if(campoMinas[fila][columna].getEstado()==Casilla.MARCADA){
				campoMinas[fila][columna].setEstado(Casilla.INTERROGACION);
				nm++;
			}
			else if(campoMinas[fila][columna].getEstado()==Casilla.INTERROGACION)
					campoMinas[fila][columna].setEstado(Casilla.CUBIERTA);
		
			else throw new CasillaAbiertaException();
	}
	private int banderasVecinas(int fila,int columna){
		int cont=0;
		for(int fi=fila-1;fi<=fila+1;fi++){
			for(int co=columna-1;co<=columna+1;co++)
				try{
					if((co!=columna || fi!=fila) && campoMinas[fi][co].getEstado()==Casilla.MARCADA)
						/*try {*/
							cont++;
						/*} catch (CasillaAbiertaException e) {
							
						}*/
	
				}
					catch(IndexOutOfBoundsException e){}
		}
		return cont;
	}
	public void descubrirMultiple(int fila,int columna){
		if(campoMinas[fila][columna].getContenido()==banderasVecinas(fila, columna))
			for(int fi=fila-1;fi<=fila+1;fi++)
				for(int co=columna-1;co<=columna+1;co++)
					try{
						if((co!=columna || fi!=fila) && campoMinas[fi][co].getEstado()!=Casilla.MARCADA)
							/*try {*/
								descubrir(fi,co);
							/*} catch (CasillaAbiertaException e) {
								
							}*/
		
					}
						catch(IndexOutOfBoundsException e){}
			
	}
	
	public int getEstadoPartida(){
		return estadoPartida;
	}
	
	
	private void victoria() {
		
		estadoPartida=VICTORIA;
		
	}
	private void derrota() {
		estadoPartida=DERROTA;
		
	}
	public int getCasillasParaLaVictoria() {
		return casillasParaLaVictoria;
	}
	public int getMinasFaltantes(){
		if(estadoPartida==VICTORIA)
			return 0;
		return nm;
	}
	
	

}
