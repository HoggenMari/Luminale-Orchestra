import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class Glitter extends HorizontalMove {


	public Glitter(PApplet p, LinkedList<Nozzle> path) {
		super(p, path);
		// TODO Auto-generated constructor stub
	}

	public void draw() {
		for(Dot dot : dotList) {
			PGraphics pg = dot.current.sysA;
			if(dot.lifetime>0){
				pg.beginDraw();
				pg.noStroke();
				pg.colorMode(PConstants.HSB, 360, 100, 100);
				pg.stroke(0, 0, 100, dot.lifetime);
				pg.strokeWeight(1);
				//int ldx = (int) p.random(dot.x-20, dot.x);
				//pg.line(ldx, ld.y, ldx, 5);
				pg.point(p.random(dot.x-20, dot.x), p.random(0, pg.height));
				pg.endDraw();
			}
		}
	}

}
