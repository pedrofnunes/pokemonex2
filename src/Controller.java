class EventSet {
	private	Event[] events = new Event[100];
	private	int index = 0;
	private	int next = 0;
	public void add(Event e) {
		if (index >= events.length)
			return;
		events[index++] = e;
	}
	public Event getNext() {
		boolean looped = false;
		int start = next;
		do {
			next = (next + 1) % events.length;
		if (start == next) 
			looped = true;
		if ((next == (start + 1) % events.length) && looped)
			return null;
		} while (events[next] == null);
		return events[next];
	}
	public void removeCurrent() {
		events[next] =	 null;
	}
	public void voltaNext(){
		if (next == 0)
			return;
		next = next - 1;
	}
}

public class Controller {
	private EventSet es = new EventSet();
	public void addEvent(Event c) {
		es.add(c);
	}
	public void run() {
		Event e, f;
		f = null;
		while((e = es.getNext()) != null) {
			es.removeCurrent();
			f = es.getNext();
			if (f == null)
				es.voltaNext();
			es.removeCurrent();
			if (f != null){
				if (e.getPrior() <= f.getPrior()) {
					if(e.ready()){
						e.action();
					}
					//aqui ele tem que ver se morreu o pokemon. se ele morrer AQUI, o proximo evento nao rola, pq seria o ataque dele
					if(e.getProblema() == false && f.ready()){
						f.action();
					}
				}
				else{
					if(f.ready()){
						f.action();
					}
					if(f.getProblema() == false && e.ready()){
						e.action();
					}
				}
			}
			else{
				if(e.ready()){
					e.action();
					}
			}
		}
	}
	public void finaliza(){
		while(es.getNext() != null){
		es.removeCurrent();
		}
	}
}
	