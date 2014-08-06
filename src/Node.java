import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

public class Node {
	private PApplet p;
	ArrayList<Nozzle> nozzleList = new ArrayList<Nozzle>();
	private int id;


	public Node(PApplet p) {
		this.p = p;
	}
	
	public void add(int[] leds_of_nozzle) {
		for(int i=0; i<leds_of_nozzle.length; i++){
			nozzleList.add(new Nozzle(p,leds_of_nozzle[i]));
		}
	}
	
	public void drawOnGui(int x, int y) {
		for (int i=0; i<nozzleList.size(); i++) {
			nozzleList.get(i).update();
			nozzleList.get(i).drawOnGui(x, y+i*60);
			
		}
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public int getID(){
		return id;
	}
	
	public void clearSysA(){
		for(Nozzle n: nozzleList){
			PGraphics pg = n.sysA;
			pg.beginDraw();
			pg.background(0);
			pg.endDraw();
		}
	}
	
	public void setColorA(int hue, int saturation, int brightness){
		for(Nozzle n: nozzleList){
			PGraphics pg = n.sysA;
			pg.beginDraw();
			pg.colorMode(PConstants.HSB, 360, 255, 255, 255);
			pg.noStroke();
			int c = pg.color(hue, saturation, brightness);
			pg.fill(c);
			pg.rect(0, 0, pg.width, pg.height);
			pg.endDraw();
		}
	}
	
	public void setColorB(int hue, int saturation, int brightness){
		for(Nozzle n: nozzleList){
			PGraphics pg = n.sysB;
			pg.beginDraw();
			pg.colorMode(PConstants.HSB, 360, 255, 255, 255);
			pg.noStroke();
			int c = pg.color(hue, saturation, brightness);
			pg.fill(c);
			pg.rect(0, 0, pg.width, pg.height);
			pg.endDraw();
		}
	}
	
	public void clearSysB(){
		for(Nozzle n: nozzleList){
			PGraphics pg = n.sysB;
			pg.beginDraw();
			pg.background(0);
			pg.endDraw();
		}
	}
	
	public void clear(){
		clearSysA();
		clearSysB();
	}
	
	
	public void dimmSysA(int alpha){
		for(Nozzle n: nozzleList){
			PGraphics pg = n.sysA;
			pg.beginDraw();
			pg.colorMode(PConstants.HSB);
			pg.fill(0, 0, 0, alpha);
			pg.noStroke();
			pg.rect(0, 0, pg.width, pg.height);
			pg.endDraw();
		}
	}
	
	public void dimmSysB(int alpha){
		for(Nozzle n: nozzleList){
			PGraphics pg = n.sysB;
			pg.beginDraw();
			pg.colorMode(PConstants.HSB);
			pg.fill(0, 0, 0, alpha);
			pg.noStroke();
			pg.rect(0, 0, pg.width, pg.height);
			pg.endDraw();
		}
	}
	
	public void dimm(int alpha){
		dimmSysA(alpha);
		dimmSysB(alpha);
	}
	
}
