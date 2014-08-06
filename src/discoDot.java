import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class discoDot {

	ArrayList<DiscoDotParticle> list = new ArrayList<DiscoDotParticle>();
	private Nozzle n;
	private boolean remove;
	private PApplet p;
	
	private ColorFade colorFade1, colorFade2, colorFade3, colorFade4, colorFade5;
	private ArrayList<ColorFade> fadeList= new ArrayList<ColorFade>();
	private ColorFadeList cfl;

	public discoDot(PApplet p, Nozzle n, ColorFadeList cfl) {
		this.n = n;
		this.p = p;
		
		colorFade1 = new ColorFade(p, 240, 100, 50);
		colorFade1.hueFade(360, 10000);
		colorFade1.brightnessFade(30, 2000);
		fadeList.add(colorFade1);
		//colorFade1.start();
		
		colorFade2 = new ColorFade(p, 240, 100, 60);
		colorFade2.hueFade(360, 10000);
		colorFade2.brightnessFade(30, 3000);
		fadeList.add(colorFade2);
		//colorFade2.start();
		
		colorFade3 = new ColorFade(p, 240, 100, 50);
		colorFade3.hueFade(360, 10000);
		colorFade3.brightnessFade(10, 2000);
		fadeList.add(colorFade3);
		//colorFade3.start();
		
		colorFade4 = new ColorFade(p, 240, 100, 60);
		colorFade4.hueFade(360, 10000);
		colorFade4.brightnessFade(20, 3000);
		fadeList.add(colorFade4);
		//colorFade4.start();
		
		colorFade5 = new ColorFade(p, 240, 100, 50);
		colorFade5.hueFade(360, 10000);
		colorFade5.brightnessFade(20, 2000);
		fadeList.add(colorFade5);

		
		//cfl = new ColorFadeList(p);
		cfl.addColorFade(colorFade1);
		cfl.addColorFade(colorFade2);
		cfl.addColorFade(colorFade3);
		cfl.addColorFade(colorFade4);
		cfl.addColorFade(colorFade5);
		//cfl.start();
				
		for(int i=n.sysA.width; i>0; i-=2) {
		list.add(new DiscoDotParticle(i, colorFade1));
		}
	}
	
	public void update() {
		for(Iterator<DiscoDotParticle> ldIterator = list.iterator(); ldIterator.hasNext();){
			DiscoDotParticle dot = ldIterator.next();

			dot.update();
	
				if(dot.x<0){
					//System.out.println("Bla"+ld.clone.toString());
					//ld.current = ld.clone.removeLast();
					remove = true;
					ldIterator.remove();
				}//else{
	
				//}
				
	}
		
		if(remove) {
			remove = !remove;
			int num = (int) p.random(0, 5);
			list.add(new DiscoDotParticle(n.sysA.width-1, fadeList.get(num)));
		}
}
	public void draw(){
		for(DiscoDotParticle dot : list) {
		PGraphics pg = n.sysA;
		pg.beginDraw();
		pg.colorMode(PConstants.HSB, 360, 100, 100);
		//int num = (int) p.random(0, 5);
		pg.fill(dot.c.hue, dot.c.saturation, dot.c.brightness, 255);
		pg.noStroke();
		pg.rect(dot.x, 0, 1, 1);
		pg.rect(dot.x, 2, 1, 1);
		pg.rect(dot.x, 4, 1, 1);
		//pg.point(dot.x, 4);
		pg.endDraw();
		}
	}

class DiscoDotParticle {
	
	private int x;
	public ColorFade c;
	
	DiscoDotParticle(int x, ColorFade c) {
		this.x = x;
		this.c = c;
	}
	
	public void update() {
		x--;
	}
	
	

}
}