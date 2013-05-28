
public class Casilla {
	public static int CERO=0;
	public static int UNA=1;
	public static int DOS=2;
	public static int TRES=3;
	public static int CUATTRO=4;
	public static int CINCO=5;
	public static int SEIS=6;
	public static int SIETE=7;
	public static int OCHO=8;
	public static int MINA=9;
	public static int DESCUBIERTA=0;
	public static int CUBIERTA=1;
	public static int MARCADA=2;
	public static int INTERROGACION=3;
	
	private int contenido;
	private int estado;
	public Casilla(int contenido){
		this.contenido=contenido;
		estado=Casilla.CUBIERTA;
	}
	
	public int getContenido(){
		return contenido;
	}
	public void setEstado(int estado){
		this.estado=estado;
	}
	
	public int getEstado() {
		return estado;
	}
}
