import processing.core.PApplet;


public class InteractionChanger {
	PApplet p;
	Node n;
	private boolean up = false;
	int dimm = 0;
	private boolean finished;
	private Effect e;
	private boolean dead = false;
	private int dimmEnd;
	
	public InteractionChanger(PApplet p, Node n, Effect e, int dimmEnd){
		this.p = p;
		this.n = n;
		this.e = e;
		this.dimmEnd = dimmEnd;
	}
	
	public void draw(){
		
		//System.out.println("InteractionChanger "+dimm+" "+e.fadeBack());
		if(!up & dimm<=dimmEnd){
		dimm+=3;
		}else if(up){
		dimm-=3;
		}
		
		if(dimm>=dimmEnd & !up){
			e.start();
		}
		
		if(e.isDead() | e.fadeBack()){
			up = true;
		}
		
		if(dimm<0){
			dead = true;
		}
		
		//System.out.println(dimm);
	
		n.dimm(dimm);
	
	}
	
	public boolean isDead(){
		return dead;
	}
	
}
