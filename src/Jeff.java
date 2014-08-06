import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class Jeff {

	private PApplet p;
	private ColorFade cf1;
	private ColorFadeList cfList;
	private ColorFade cf2;
	private ColorFade cf3;
	private ColorFade cf4;
	private NozzleLayer nozzleLayer;
	private PGraphics pg;
	private float y=0;
	private int speed;
	private int timer;
	private boolean jeffDown;
	private ArrayList<down> down = new ArrayList<down>();
	private ArrayList<up> up = new ArrayList<up>();
	private boolean upBol = true;


	public Jeff(PApplet p, ColorFadeList cfList, NozzleLayer nozzleLayer, int speed){
		this.p = p;
		this.cfList = cfList;
		this.nozzleLayer = nozzleLayer;
		this.speed = speed;
		
		down.add(new down(p, cfList, nozzleLayer, speed));
		up.add(new up(p, cfList, nozzleLayer, speed));

	}
	
	public void draw(){
		
		
		for(Iterator<down> effectIterator = down.iterator(); effectIterator.hasNext();){
			  down e = effectIterator.next();
			  
			  e.draw();
			  
			  if(e.dead){
				  upBol = !upBol;
				  //System.out.println("DEAD");
				  effectIterator.remove();
			  }
		}
		for(Iterator<up> effectIterator = up.iterator(); effectIterator.hasNext();){
			  up e = effectIterator.next();
			  
			  e.draw();
			  
			  if(e.dead){
				  upBol = !upBol;
				  //System.out.println("DEAD");
				  effectIterator.remove();
			  }
		  }
		
		if(upBol){
			while(up.size()<1){
				up.add(new up(p, cfList, nozzleLayer, speed));
			}
		}else{
			while(down.size()<1){
				down.add(new down(p, cfList, nozzleLayer, speed));
			}
		}
	}
		
		
		/*int current = p.millis();
		if(current-timer>speed){	
			y++;
			timer = current;
		}*/
	

	class down{
		
		private PApplet p;
		private ColorFadeList cfList;
		private NozzleLayer nozzleLayer;
		private int speed;
		private PGraphics pg;
		private ColorFade cf1;
		private ColorFade cf2;
		private int y=1;
		boolean dead = false;

		public down(PApplet p, ColorFadeList cfList, NozzleLayer nozzleLayer, int speed){
			this.p = p;
			this.cfList = cfList;
			this.nozzleLayer = nozzleLayer;
			this.speed = speed;
			
			pg = nozzleLayer.getLayer();
			
			cf1 = new ColorFade(p, 0, 0, 255, 0);
			cf1.alphaFade(255, speed, 1);
			cfList.addColorFade(cf1);
			
			cf2 = new ColorFade(p, 0, 0, 255, 255);
			cf2.alphaFade(0, speed, 1);
			cfList.addColorFade(cf2);
			
		}
		
		public void draw(){
			if(cf1.isDead()){
					y++;
					
					cf1 = new ColorFade(p, 0, 0, 255, 0);
					cf1.alphaFade(255, speed, 1);
					cfList.addColorFade(cf1);
					
					cf2 = new ColorFade(p, 0, 0, 255, 255);
					cf2.alphaFade(0, speed, 1);
					cfList.addColorFade(cf2);
				}
				/*if(y==0){
					jeffDown = false;
				}
				if(y>=5){
					jeffDown = true;
				}*/
			
			if(y>=5){
				dead = true;
			}
		
			pg.beginDraw();
			pg.colorMode(PConstants.HSB, 360, 255, 255, 255);
			pg.clear();
			pg.noStroke();
			pg.fill(cf1.hue, cf1.saturation, cf1.brightness, cf1.alpha);
			pg.rect(0, y, pg.width, 1);
			pg.fill(cf2.hue, cf2.saturation, cf2.brightness, cf2.alpha);
			pg.rect(0, y-1, pg.width, 1);
			pg.endDraw();
			nozzleLayer.add();
			
		}
		
	}
	
	
	class up{
		
		private PApplet p;
		private ColorFadeList cfList;
		private NozzleLayer nozzleLayer;
		private int speed;
		private PGraphics pg;
		private ColorFade cf1;
		private ColorFade cf2;
		private int y = 4;
		boolean dead = false;

		public up(PApplet p, ColorFadeList cfList, NozzleLayer nozzleLayer, int speed){
			this.p = p;
			this.cfList = cfList;
			this.nozzleLayer = nozzleLayer;
			this.speed = speed;
			
			pg = nozzleLayer.getLayer();
			
			cf1 = new ColorFade(p, 0, 0, 255, 255);
			cf1.alphaFade(0, speed, 1);
			cfList.addColorFade(cf1);
			
			cf2 = new ColorFade(p, 0, 0, 255, 0);
			cf2.alphaFade(255, speed, 1);
			cfList.addColorFade(cf2);
			
		}
		
		public void draw(){
			if(cf1.isDead()){
					y--;
					
					cf1 = new ColorFade(p, 0, 0, 255, 255);
					cf1.alphaFade(0, speed, 1);
					cfList.addColorFade(cf1);
					
					cf2 = new ColorFade(p, 0, 0, 255, 0);
					cf2.alphaFade(255, speed, 1);
					cfList.addColorFade(cf2);
				}
				/*if(y==0){
					jeffDown = false;
				}
				if(y>=5){
					jeffDown = true;
				}*/
			
			if(y==0){
				dead = true;
			}
		
			pg.beginDraw();
			pg.colorMode(p.HSB, 360, 255, 255, 255);
			pg.clear();
			pg.noStroke();
			pg.fill(cf1.hue, cf1.saturation, cf1.brightness, cf1.alpha);
			pg.rect(0, y, pg.width, 1);
			pg.fill(cf2.hue, cf2.saturation, cf2.brightness, cf2.alpha);
			pg.rect(0, y-1, pg.width, 1);
			pg.endDraw();
			nozzleLayer.add();
			
		}
		
	}
}
