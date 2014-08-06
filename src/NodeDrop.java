import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class NodeDrop {

	private PApplet p;
	private Pavillon scp;
	private LinkedList<Nozzle> nozzlePath;
	private NozzleLayer nozzleLayer;
	private ColorFade end;
	private boolean endFirst;
	private ColorFade middle;
	private ColorFadeList cfList;
	private Node node;
	private PGraphics pg1;
	private PGraphics pg2;
	private ColorFade beginn;
	private boolean beginnFirst;
	private PGraphics pg3;
	private HorizontalFineTube nDrop;
	private boolean middleFirst = true;
	boolean dead;

	public NodeDrop(PApplet p, Pavillon scp, Node node, ColorFadeList cfList){
		this.p = p;
		this.scp = scp;
		this.nozzlePath = nozzlePath;
		this.node = node;
		this.cfList = cfList;
		
		beginn = new ColorFade(p, 0, 0, 255, 0);
		beginn.alphaFade(255, 1000, 2);
		cfList.addColorFade(beginn);
		beginnFirst = true;
		
		
		middle = new ColorFade(p, 180, 60, 255, 255);
		middle.saturationFade(0, 1000, 2);
		middle.alphaFade(255, 1000, 2);
		cfList.addColorFade(middle);
		
		pg1 = node.nozzleList.get(0).sysA;
		
		nozzlePath = scp.createNodeMinus(node);
		nozzleLayer = new NozzleLayer(p, scp, nozzlePath);
		pg2 = nozzleLayer.getLayer();
		nDrop = new HorizontalFineTube(p, pg2, middle, 30, 10, 20, 100);
		
		pg3 = node.nozzleList.get(node.nozzleList.size()-1).sysA;
		
	}
	
	public void draw(){
		
		System.out.println("GO HOR NOZZLE");
		pg1.beginDraw();
		pg1.noStroke();
		pg1.colorMode(PConstants.HSB, 360, 255, 255, 255);
		pg1.fill(beginn.hue, beginn.saturation, beginn.brightness, beginn.alpha);
		pg1.rect(0, 0, pg1.width, pg1.height);
		pg1.endDraw();
		
		/*pg2.beginDraw();
		pg2.noStroke();
		pg2.colorMode(PConstants.HSB, 360, 255, 255, 255);
		pg2.fill(middle.hue, middle.saturation, middle.brightness, middle.alpha);
		pg2.rect(0, 0, pg2.width, pg2.height);
		pg2.endDraw();*/
		
		nDrop.draw();
		nozzleLayer.add();
		
		if(nDrop.middle && middleFirst){
			end = new ColorFade(p, 0, 0, 255, 0);
			end.alphaFade(255, 2000, 2);
			cfList.addColorFade(end);
			middleFirst = false;
		}
		else if(nDrop.middle){
			pg3.beginDraw();
			pg3.noStroke();
			pg3.colorMode(PConstants.HSB, 360, 255, 255, 255);
			pg3.fill(end.hue, end.saturation, end.brightness, end.alpha);
			pg3.rect(0, 0, pg1.width, pg1.height);	
			pg3.endDraw();
			
			if(end.isDead()){
				dead = true;
			}
		
		}
		
		
		/*if(middle.isDead() && endFirst){
			middle = new ColorFade(p, 0, 0, 255, 0);
			middle.saturationFade(0, 1000, 2);
			middle.alphaFade(255, 1000, 2);
			cfList.addColorFade(middle);
			endFirst = false;
		}else{*/
		//System.out.println("DRAW END: "+cf.hue+" "+cf.saturation+" "+cf.brightness);
		//pg2.beginDraw();
		//pg2.colorMode(PConstants.HSB, 360, 255, 255, 255);
		//pg2.clear();
		//pg2.noStroke();
		//pg2.fill(end.hue, end.saturation, end.brightness, end.alpha);
		//pg2.rect(0, 0, pg2.width, pg2.height);
		//pg2.endDraw();
		//nLayer.add();
		
		//if(end.isDead()){
		//	dead = true;
		//}
	}
	
	public boolean isDead(){
		return dead;
	}
}
