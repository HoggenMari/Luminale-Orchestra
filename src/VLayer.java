import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;


public class VLayer extends Layer{

	public VLayer(PApplet p, Pavillon scp, LinkedList<Nozzle> nozzlePath) {
		super(p, scp, nozzlePath);
		// TODO Auto-generated constructor stub
	}
	
	public PGraphics getLayer() {
		int totalHeight = 0;
		int maxWidth = 0;
		for(Nozzle n : nozzlePath) {
			totalHeight += n.sysA.height;
			if(n.sysA.width>maxWidth){
				maxWidth = n.sysA.width;
			}
		}
		pg = p.createGraphics(maxWidth, totalHeight);
		return pg;
	}
	
	public void add() {
		int currentY = 0;
		for(int i=nozzlePath.size()-1; i>-1; i--) {
			Nozzle n = nozzlePath.get(i);
			PGraphics p = scp.nozzleList.get(n.id).sysA;
			PImage img = pg.get(0, currentY, pg.width, currentY+p.height);
			p.beginDraw();
			p.image(img,0,0);
			p.endDraw();
			currentY += p.height;
		}
	}

}
