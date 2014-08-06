import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class RandomLampManager {

	
	class RandomLamp {
		
	private PApplet p;
	private PGraphics pg;
	private ColorFade cf;
	
	public RandomLamp(PApplet p, PGraphics pg, ColorFade cf) {
		this.p = p;
		this.pg = pg;
		this.cf = cf;
	}
	
	public void drawRandomLamp(){
		//System.out.println("DRAWLAMP");
		pg.beginDraw();
		pg.background(0);
		pg.colorMode(PConstants.HSB, 360, 100, 100);
		pg.noStroke();
		pg.fill(cf.hue, cf.saturation, cf.brightness);
		pg.rect(0, 0, pg.width, pg.height);
		pg.endDraw();
	}
	
	}
	
		
		private Pavillon pavillon;
		private ArrayList<RandomLamp> randomLampList = new ArrayList<RandomLamp>();
		private ColorFadeList cfl;
		private PApplet p;
		private ColorFade colorFade;
		private int MAX_RANDOM_LAMP = 5;
		private int minLamp;
		private int maxLamp;

		public RandomLampManager(PApplet p, Pavillon pavillon, int minLamp, int maxLamp) {
			this.p = p;
			this.pavillon = pavillon;
			this.minLamp = minLamp;
			this.maxLamp = maxLamp;
			
			cfl = new ColorFadeList(p);
			colorFade = new ColorFade(p, 0, 100, 0);
			colorFade.hueFade(50, 2000, 2);
			colorFade.brightnessFade(100, 2000, 2);
			cfl.addColorFade(colorFade);
			cfl.start();
		}
		
		
		public void draw() {
			if(cfl.colorFadeList.isEmpty() && (p.frameCount%100==0)) {
				colorFade = new ColorFade(p, 0, 100, 0);
				int randomTime = (int) p.random(400, 2000);
				colorFade.hueFade(50, randomTime, 2);
				colorFade.brightnessFade(100, randomTime, 2);
				cfl.addColorFade(colorFade);
				randomLampList.clear();
				MAX_RANDOM_LAMP = (int) p.random(minLamp, maxLamp);
			}
			if(randomLampList.size() < MAX_RANDOM_LAMP) {
				int random = (int) p.random(0, pavillon.nozzleList.size());
				randomLampList.add(new RandomLamp(p, pavillon.nozzleList.get(random).sysB, colorFade));
				randomLampList.add(new RandomLamp(p, pavillon.nozzleList.get(random).sysA, colorFade));
			}
			for(RandomLamp rd : randomLampList) {
				rd.drawRandomLamp();
			}
		}
			
}
