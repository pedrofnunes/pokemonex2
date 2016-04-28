import java.util.Random;


public class Batalha extends Controller {
	private boolean encontrou = false;
	public void marcaAchou(){
		encontrou = true;
	}
	public boolean achou(){
		return encontrou;
	}
	private class Andar extends Event{
		private Random rnd = new Random();
		private int prior = 0;
		char direcao;
		Mapa mapa;
		public Andar (long eventTime, char direcao, Mapa mapa){
			super(eventTime);
			this.mapa = mapa;
			this.direcao = direcao;
		}
		public int getPrior(){
			return (prior);
		}
		public void action(){
			mapa.andar (direcao);
			System.out.println("Andou");
			if (mapa.getGrama() == true){
				if (rnd.nextInt(101) > 49){
					marcaAchou();
					this.deuProblema();
					finaliza();
				}
			}
		}
	}
	private class Atacar extends Event{
		private int prior = 3;//ou 4 ou 5, dependendo da velocidade do ataque
		private Treinador src;
		private Treinador target;
		private int i;
		private Random rnd = new Random();
		public Atacar (long eventTime, int i, Treinador src, Treinador target) {
			super(eventTime);
			this.src = src;
			this.i = i;
			this.target = target;
		}
		public Atacar (long eventTime, Treinador src, Treinador target){
			super(eventTime);
			this.src = src;
			this.i = rnd.nextInt(4);
			this.target = target;
		}
		public int getPrior(){
			return (prior + src.getAtivo().getAtaque(i).getPrior());
		}
		public void action(){
			src.getAtivo().getAtaque(i).usar(target.getAtivo());
			System.out.println(src.getAtivo().getNome()+" usou "+src.getAtivo().getAtaque(i).getNome()+"! "+src.getAtivo().getAtaque(i).getDano()+" de dano em "+target.getAtivo().getNome()+". HP:"+target.getAtivo().getHP()+"/"+target.getAtivo().getMaxHP());
			if (target.getAtivo().getEstado() == false){
				System.out.println(target.getAtivo().getNome()+" desmaiou!");
				target.pokeMorreu();
				if (target.perdeu() == true){
					this.deuProblema();
					if (src.ehSelvagem() == true)
						System.out.println(target.getNome()+" n�o tem mais Pok�mons aptos! "+target.getNome()+" foge para socorrer seus Pokemons!");
					if (src.ehSelvagem() == false && target.ehSelvagem() == true)
						System.out.println(src.getNome()+" saiu da batalha com sucesso!");
					if (src.ehSelvagem() == false && target.ehSelvagem() == false)
						System.out.println(target.getNome()+" n�o tem mais Pok�mons aptos! "+src.getNome()+" � o vencedor!");
					finaliza();
				}
				else{
					target.trocaPoke(target.proxVivo());
					this.deuProblema();
					System.out.println(target.getNome()+": "+target.getAtivo().getNome()+", escolho voc�!");
				}
			}
		}
	}
	private class UsarItem extends Event{
		private int prior = 2;
		private Item item;
		private Pokemon p;
		public UsarItem (long eventTime, Item item, Pokemon p) {
			super(eventTime);
			this.item = item;
			this.p = p;
		}
		public int getPrior(){
			return (prior);
		}
		public void action(){
			item.usaItem(p);
			System.out.println(item.getNome()+" usado em "+p.getNome()+". HP:"+p.getHP()+"/"+p.getMaxHP());
		}
	}
	private class Trocar extends Event{
		private int prior = 1;
		private Treinador t;
		private int i;
		public int getPrior(){
			return (prior);
		}
		public Trocar (long eventTime, Treinador t, int i) {
			super(eventTime);
			this.t = t;
			this.i = i;
		}
		public void action(){
			System.out.println(t.getNome()+": "+t.getAtivo().getNome()+", chega!");
			t.trocaPoke(i);
			System.out.println(t.getNome()+": Vai, "+t.getAtivo().getNome()+"!");
		}
	}
	private class Fugir extends Event{
		private int prior = 0;
		private Treinador fugiu, ganhou;
		public Fugir (long eventTime, Treinador fugiu, Treinador ganhou) {
			super(eventTime);
			this.fugiu = fugiu;
			this.ganhou = ganhou;
		}
		public int getPrior(){
			return (prior);
		}
		public void action(){
			this.deuProblema();
			if (ganhou.ehSelvagem() == true)
				System.out.println(fugiu.getNome()+" fugiu com sucesso!");
			if (ganhou.ehSelvagem() == false)
				System.out.println(fugiu.getNome()+" fugiu. "+ganhou.getNome()+" � o vencedor!");
			finaliza();
		}
	}
	private class Restart extends Event{
		private int prior = -1;
		boolean achou;
		public Restart(long eventTime, boolean achou) {
			super(eventTime);
			this.achou = achou;
		}
		Mapa mapa = new Mapa(false, true, false, true, false, true, false, true, false);
		
		Item potion = new Item ("Potion", 100);
		
		Ataque bite = new Ataque ("Bite", 60, 1);
		Ataque bodySlam = new Ataque ("Body Slam", 85, 1);
		Ataque confusion = new Ataque ("Confusion", 60, 1);
		Ataque dragonbreath = new Ataque ("Dragonbreath", 80, 1);
		Ataque earthquake = new Ataque ("Earthquake", 100, 1);
		Ataque fireBlast = new Ataque ("Fire Blast", 150, 2);
		Ataque flamethrower = new Ataque ("Flamethrower", 90, 1);
		Ataque fly = new Ataque ("Fly", 70, 1);
		Ataque headbutt = new Ataque ("Headbutt", 65, 1);
		Ataque hyperBeam = new Ataque ("Hyper Beam", 150, 2);
		Ataque iceBeam = new Ataque ("Ice Beam", 90, 1);
		Ataque leafBlade = new Ataque ("Leaf Blade", 80, 1);
		Ataque megaPunch = new Ataque ("Mega Punch", 70, 1);
		Ataque megaKick = new Ataque ("Mega Kick", 100, 1);
		Ataque metalClaw = new Ataque ("Metal Claw", 70, 1);
		Ataque psychic = new Ataque ("Psychic", 90, 1);
		Ataque pursuit = new Ataque ("Pursuit", 40, 0);
		Ataque quickAttack = new Ataque ("Quick Attack", 40, 0);
		Ataque razorLeaf = new Ataque ("Razor Leaf", 60, 1);
		Ataque rockSlide = new Ataque ("Rock Slide", 60, 1);
		Ataque seismicToss = new Ataque ("Seismic Toss", 100, 1);
		Ataque shadowBall = new Ataque ("Shadow Ball", 80, 1);
		Ataque sludge = new Ataque ("Sludge", 60, 1);
		Ataque solarbeam = new Ataque ("Solarbeam", 150, 2);
		Ataque spark = new Ataque ("Spark", 50, 0);
		Ataque surf = new Ataque ("Surf", 95, 1);
		Ataque tackle = new Ataque ("Tackle", 40, 1);
		Ataque thunderbolt = new Ataque ("Thunderbolt", 95, 1);
		Ataque thundershock = new Ataque ("Thundershock", 40, 1);
		Ataque waterGun = new Ataque ("Water Gun", 40, 0);
		Ataque zapCannon = new Ataque ("Zap Cannon", 150, 2);

		Pokemon pikachu = new Pokemon ("Pikachu", 150, zapCannon, thunderbolt, spark, quickAttack);
		Pokemon charizard = new Pokemon ("Charizard", 300, fireBlast, flamethrower, fly, metalClaw);
		Pokemon chansey = new Pokemon ("Chansey", 650, thundershock, tackle, pursuit, waterGun);
		Pokemon alakazan = new Pokemon ("Alakazan", 350, psychic, shadowBall, confusion, pursuit);
		Pokemon blastoise = new Pokemon ("Blastoise", 300, surf, waterGun, iceBeam, earthquake);
		Pokemon snorlax = new Pokemon ("Snorlax", 450, bodySlam, earthquake, hyperBeam, headbutt);

		Pokemon rhydon = new Pokemon ("Rhydon", 400, earthquake, rockSlide, seismicToss, headbutt);
		
		Treinador red = new Treinador ("Red", 6, pikachu, charizard, chansey, alakazan, blastoise, snorlax);
		Treinador selvagem = new Treinador ("Selvagem", 1,  rhydon, true);

		public void action(){
			long tm = System.currentTimeMillis();
			if (achou == false){
				addEvent(new Andar(tm, 'd', mapa));
				addEvent(new Andar(tm, 'd', mapa));
				addEvent(new Andar(tm, 'e', mapa));
				addEvent(new Andar(tm, 'c', mapa));
				addEvent(new Andar(tm, 'b', mapa));
				addEvent(new Andar(tm, 'b', mapa));
				addEvent(new Andar(tm, 'e', mapa));
				}
			if (achou == true){
				addEvent(new Atacar(tm, 3, red, selvagem)); 
				addEvent(new Atacar(tm, selvagem, red)); 
				addEvent(new Atacar(tm, 1, red, selvagem)); 
				addEvent(new Atacar(tm, selvagem, red)); 
				addEvent(new Atacar(tm, 2, red, selvagem)); 
				addEvent(new Atacar(tm, selvagem, red)); 
				addEvent(new Atacar(tm, 3, red, selvagem)); 
				addEvent(new Atacar(tm, selvagem, red)); 
				addEvent(new Atacar(tm, 2, red, selvagem)); 
				addEvent(new Atacar(tm, selvagem, red)); 
				addEvent(new Atacar(tm, 0, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 1, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 2, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 3, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 1, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 0, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 1, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 3, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 2, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 0, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 1, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 3, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 2, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 0, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 1, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 0, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 1, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 2, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 3, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 2, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 0, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 1, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 0, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
				addEvent(new Atacar(tm, 1, red, selvagem));
				addEvent(new Atacar(tm, selvagem, red));
			}
		}

	}
	public static void main(String[] args){
		Batalha bat = new Batalha(); 
		long tm = System.currentTimeMillis(); 
		while (bat.achou() == false){
			bat.addEvent(bat.new Restart(tm, bat.achou())); 
			bat.run();
		}
		bat.addEvent(bat.new Restart(tm, bat.achou())); 
		bat.run();
	}
}
