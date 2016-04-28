
public class teste {
	private int i = 2;
	public teste (int i){
		this.i = i;
	}
	public void printa(){
		System.out.println(i);
	}

public static void main(String[] args){
	teste testa = new teste(5);
	testa.printa();
}

}