
public class Mapa {
	private boolean[][] terreno = new boolean[3][3];
	private int i = 0;
	private int j = 0;
	public Mapa (boolean a00, boolean a01, boolean a02, boolean a10, boolean a11, boolean a12, boolean a20, boolean a21, boolean a22){
		terreno[0][0] = a00;		
		terreno[0][1] = a01;
		terreno[0][2] = a02;
		terreno[1][0] = a10;
		terreno[1][1] = a11;
		terreno[1][2] = a12;
		terreno[2][0] = a20;
		terreno[2][1] = a21;
		terreno[2][2] = a22;

	}
	public void andar (char direcao){
		if (direcao == 'd' && j<2)
			j++;
		if (direcao == 'e' && j>0)
			j--;
		if (direcao == 'c' && i>0)
			i--;
		if (direcao == 'b' && i<2)
			i++;
	}
	public boolean getGrama(){
		return terreno[i][j];
	}
}
