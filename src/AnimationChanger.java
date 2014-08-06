import processing.core.PApplet;


public class AnimationChanger {

	int dimm = 0;
	private PApplet p;
	private Pavillon scp;
	private boolean up = false;
	boolean finished = false;
	
	public AnimationChanger(PApplet p, Pavillon scp){
		this.p = p;
		this.scp = scp;
		
		
	}
	
	public void draw(){
		if(!up){
		dimm+=3;
		}else{
		dimm-=3;
		}
		
		if(dimm>=255){
			up = true;
		}else if(dimm<=0){
			finished = true;
		}
		
		//System.out.println(dimm);
	
		scp.dimm(dimm);
	
	}
}
