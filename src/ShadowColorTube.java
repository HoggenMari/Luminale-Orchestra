import java.util.LinkedList;

import processing.core.PApplet;


public class ShadowColorTube {
	
	private PApplet p;
	private ColorFadeList cfl;
	private LinkedList<Nozzle> nozzlePath;
	private ColorFade shadowColor;
	private FineTube ShadowTube;
	private boolean dead;

	public ShadowColorTube(PApplet p, Pavillon scp, ColorFadeList cfl){
		this.p = p;
		this.cfl = cfl;
		
		int random = (int)p.random(0,2);
		
		if(random==0){
		shadowColor = new ColorFade(p, 163, 0, 0, 255);
		shadowColor.brightnessFade(10, 3000);
		cfl.addColorFade(shadowColor);
		//nozzlePath = scp.createRandomPath(0, 17, 48, 56);
		nozzlePath = scp.createNodePath(scp.nodeList.get((int)p.random(0,6)));
		nozzlePath = scp.reversePath(nozzlePath);
		NozzleLayer nozzleLayer = new NozzleLayer(p, scp, nozzlePath);
		ShadowTube = new FineTube(p, nozzleLayer, shadowColor, 120, (int) p.random(0,20), 10, 20, 10);
		}else if(random==1){
		shadowColor = new ColorFade(p, (int) p.random(160,180), (int) p.random(0, 20), 255, 255);
		shadowColor.brightnessFade(255, 3000);
		cfl.addColorFade(shadowColor);	
		nozzlePath = scp.createNodeToNodeNozzle(scp.nodeList.get(6), scp.nodeList.get(3));
		//nozzlePath = scp.createNodePath(scp.nodeList.get((int)p.random(0,6)));
		//nozzlePath = scp.reversePath(nozzlePath);
		NozzleLayer nozzleLayer = new NozzleLayer(p, scp, nozzlePath);
		//ShadowTube = new FineTube(p, nozzleLayer, shadowColor, 10, (int) p.random(5,5), 60, 20, 30);
		ShadowTube = new FineTube(p, scp, nozzlePath, nozzleLayer, shadowColor, cfl);
		//FineTube(PApplet p, Pavillon scp, LinkedList<Nozzle> nozzlePath, NozzleLayer nozzleLayer, ColorFade cf, ColorFadeList cfList)
		}
		
		//nozzlePath = scp.createNodePath(scp.nodeList.get(0));
		//NozzleLayer nozzleLayer = new NozzleLayer(p, scp, nozzlePath);
		//ShadowTube = new FineTube(p, nozzleLayer, shadowColor, 120, 10, 10, 20, 10);
	}
	
	public void draw(){
		System.out.println("DRAW");
		if(ShadowTube.isDead()){
			  dead = true;
		  }else{
			  ShadowTube.draw();
		  }
	}
	
	public boolean isDead(){
		return dead;
	}

}
