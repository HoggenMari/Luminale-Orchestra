import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

	class ZŸnden implements Effect{
		
		private boolean dead = false;
		private PGraphics pg;
		private ArrayList<ZParticle> ZParticleList = new ArrayList<ZParticle>();
		private PApplet p;
		private NozzleLayer nozzleLayer;

		public ZŸnden(PApplet p, NozzleLayer nozzleLayer, ColorFadeList cfl) {
			this.nozzleLayer = nozzleLayer;
			this.p = p;
			
			pg = nozzleLayer.getLayerB();
			
			for(int i=0; i<50; i++){
				ZParticleList.add(new ZParticle(p,-i, cfl));
			}
		}
		
		public void draw() {
			
			if(!dead){
			pg.beginDraw();
			pg.clear();
			//pg.background(0);
			pg.colorMode(PConstants.HSB,360,100,100);
			int amount = 255/ZParticleList.size();
			for(int i=0; i<ZParticleList.size(); i++){
				ZParticle z = ZParticleList.get(i);
				pg.fill(z.cf.hue, z.cf.saturation, z.cf.brightness, 255-amount*i);
				pg.noStroke();
				pg.rect(z.x, (int) p.random(0,0), 1, 1);
				z.update();
			}
			pg.endDraw();
			nozzleLayer.addB();
			
			for(Iterator<ZParticle> zIterator = ZParticleList.iterator(); zIterator.hasNext();){
				
				ZParticle e = zIterator.next();
				  				  
				  if(e.x>pg.width+1){
					  //System.out.println("DEAD");
					  zIterator.remove();
				  }
			}
			}
			if(ZParticleList.isEmpty()){
				dead = true;
			}
			
		}
		
		public boolean isDead(){
			return dead;
		}

		@Override
		public void start() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean fadeBack() {
			// TODO Auto-generated method stub
			return false;
		}
		
		
	}
	
	
	class ZParticle {
		
		int x;
		PApplet p;
		ColorFade cf;
		
		public ZParticle(PApplet p, int x, ColorFadeList cfl){
			this.p = p;
			this.x = x;
			
			p.colorMode(PConstants.HSB,360,100,100);
			cf = new ColorFade(p, (int)p.random(-x, -10-x), (int) p.random(0,10), (int) p.random(100,100));
			cf.brightnessFade((int) p.random(70, 100), (int) p.random(0, 200));
			cfl.addColorFade(cf);
		}
		
		public void update(){
			x+=2;
		}
		
	}