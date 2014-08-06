import java.util.LinkedList;


public class Dot{
	int x;
	int y;
		
	int lifetime;
	LinkedList<Nozzle> clone = new LinkedList<Nozzle>();
	Nozzle current;
		
	
	public Dot(int x, int y, int lifetime, LinkedList<Nozzle> randomPath) {
		this.x = x;
		this.y = y;
		this.lifetime = lifetime;
		for(int i=0; i<randomPath.size(); i++){
			clone.add(randomPath.get(i));
		}
		current = clone.removeLast();
	}
	
	public void next(){
		if(clone.size()!=0){
		current = clone.removeLast();
		}
	}
	
	
	
}
