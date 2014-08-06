import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class Firework implements Effect{

	private PApplet p;
	private Node n;
	private ColorFadeList cfl;
	private Pavillon scp;
	private PGraphics pgB;
	private ZŸndschnurr zSchnur;
	private NozzleLayer nLayerB;
	private boolean dead = false;
	private Explode exp;
	private int counter = 0;

	private ArrayList<Explode> expList= new ArrayList<Explode>();
	private ArrayList<UpDownEffect> uList= new ArrayList<UpDownEffect>();
	private NozzleLayer nLayerA;
	private PGraphics pgA;
	private boolean explode;
	private NozzleLayer nLayerA2;
	private PGraphics pgA2;
	private ArrayList<Stars> starList = new ArrayList<Stars>();
	private boolean start = false;
	private boolean fadeBack = false;

	public Firework(PApplet p, Pavillon scp, Node n, ColorFadeList cfl){
		this.p = p;
		this.n = n;
		this.scp = scp;
		this.cfl = cfl;
		
		LinkedList<Nozzle> nozzlePath = scp.createNodePath(n);
		  //nozzlePath = createRandomPath(0,8,58,65);
		nLayerB = new NozzleLayer(p, scp, nozzlePath);
		pgB = nLayerB.getLayerB();
		
		nLayerA = new NozzleLayer(p, scp, nozzlePath);
		pgA = nLayerA.getLayer();
		
		nozzlePath = scp.createReverseNodePath(n);
		nLayerA2 = new NozzleLayer(p, scp, nozzlePath);
		pgA2 = nLayerA2.getLayer();
		
		zSchnur = new ZŸndschnurr(pgB, cfl);
		
		
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
	
		if(start){
		if(!zSchnur.isDead()){
			zSchnur.draw();
			
			nLayerB.addB();

		}else{
			//dead=true;
			
			if(counter<5){
			
				if(!explode){
			while(expList.isEmpty()){
				expList.add(new Explode(p, pgB, cfl, 100+5*counter*counter));
			}
			for(Iterator<Explode> zIterator = expList.iterator(); zIterator.hasNext();){
				Explode e = zIterator.next();
				  e.draw();  				  
				  if(e.isDead()){
					  //System.out.println("DEAD");
					  zIterator.remove();
					  //counter++;
					  explode = !explode;
				  }
			}
				}
				else{
			while(uList.isEmpty()){
				uList.add(new UpDownEffect(p, pgA, cfl, 3*counter*counter));
			}
			for(Iterator<UpDownEffect> zIterator = uList.iterator(); zIterator.hasNext();){
				UpDownEffect e = zIterator.next();
				  scp.dimm(20-2*counter);
				  e.draw();  				  
				  if(e.isDead()){
					  //System.out.println("DEAD");
					  zIterator.remove();
					  //counter++;
					  explode = !explode;
					  counter++;
				  }
			}
			
			
			
			
				}
			
				nLayerB.addB();

			} else {
				
				fadeBack = true;

				
				//scp.dimm(80);
				while(starList .isEmpty()){
					int color = p.color((int)p.random(0, 360), (int)p.random(0,40), (int)p.random(70,90));
					starList.add(new Stars(p, nLayerA2, 270, cfl));
				}
				for(Iterator<Stars> zIterator = starList.iterator(); zIterator.hasNext();){
					Stars e = zIterator.next();
					  e.draw();  				  
					  if(e.isDead()){
						  //System.out.println("DEAD");
						  zIterator.remove();
						  dead = true;

						  //counter++;
						  
					  }
				}
				
				
				
				
				
			}
			
			
			
		
		}
		
		nLayerA.add();
		nLayerA2.add();
		
		}
		
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return dead;
	}
	
	public boolean fadeBack(){
		return fadeBack;
	}
	
	public void start(){
		start  = true;
	}
	
	
	class ZŸndschnurr {
		
		private boolean dead = false;
		private PGraphics pg;
		private ArrayList<ZParticle> ZParticleList = new ArrayList<ZParticle>();

		public ZŸndschnurr(PGraphics pg, ColorFadeList cfl) {
			this.pg = pg;
			
			for(int i=0; i<50; i++){
				ZParticleList.add(new ZParticle(p,-i, cfl));
			}
		}
		
		public void draw() {
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
			
			for(Iterator<ZParticle> zIterator = ZParticleList.iterator(); zIterator.hasNext();){
				
				ZParticle e = zIterator.next();
				  				  
				  if(e.x>pg.width+1){
					  //System.out.println("DEAD");
					  zIterator.remove();
				  }
			}
			
			if(ZParticleList.isEmpty()){
				dead = true;
			}
			
		}
		
		public boolean isDead(){
			return dead;
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
			cf = new ColorFade(p, (int)p.random(-x, -10-x), (int) p.random(70,90), (int) p.random(100,100));
			cf.brightnessFade((int) p.random(70, 100), (int) p.random(0, 200));
			cfl.addColorFade(cf);
		}
		
		public void update(){
			x+=2;
		}
		
	}

	
	class Explode {
		
		private PGraphics pg;
		private ColorFadeList cfl;
		private PApplet p;
		private ColorFade cf;
		private boolean dead = false;
		private int speed;

		Explode(PApplet p, PGraphics pg, ColorFadeList cfl, int speed) {
			this.p = p;
			this.pg = pg;
			this.cfl = cfl;
			this.speed = speed;
			
			cf = new ColorFade(p,0,0,0);
		
		}
		
		public void draw() {
			
			if(cf.brightness==0){
				cf.brightnessFade(100, speed,1);
				cfl.addColorFade(cf);
			}
			
			//System.out.println("Brightness: "+cf.brightness);
			pg.beginDraw();
			pg.clear();
			//pg.background(0);
			pg.colorMode(PConstants.HSB,360,100,100);
			pg.fill(cf.hue, cf.saturation, cf.brightness);
			pg.noStroke();
			pg.rect(0, 0, pg.width, pg.height);
			
			if(cf.brightness==100){
				dead = true;
				pg.background(0);
			}

			pg.endDraw();

		}
		
		public boolean isDead(){
			return dead;
		}
		
	}


	class UpDownEffect  {

		PApplet p;
		PGraphics pg;
		private int y;
		private boolean down = false;
		private boolean deadA;
		private int speed;
		private int timer;
		private ColorFade cf;
		
		public UpDownEffect(PApplet p, PGraphics pg, ColorFadeList cfl, int speed){
			this.p = p;
			this.pg = pg;
			this.speed = 1000;
			y = 0;
			timer = p.millis();
			
			cf = new ColorFade(p, (int)p.random(0,60), (int)p.random(70,100), (int)p.random(70, 100));
			cf.hueFade((int)p.random(0,60), 500);
			cfl.addColorFade(cf);
		}
		
		public void draw(){
				/*pg.beginDraw();
				pg.clear();
				pg.noStroke();
				pg.fill(255,255);
				pg.rect(0, y, pg.width, 1);
				pg.fill(255,100);
				pg.rect(0, y+1, pg.width, 1);
				pg.fill(255,80);
				pg.rect(0, y+2, pg.width, 1);
				pg.fill(255,60);
				pg.rect(0, y+3, pg.width, 1);
				pg.fill(255,20);
				pg.rect(0, y+4, pg.width, 1);
				pg.fill(255,15);
				pg.rect(0, y+5, pg.width, 1);
				pg.fill(255,12);
				pg.rect(0, y+6, pg.width, 1);
				pg.fill(255,10);
				pg.rect(0, y+7, pg.width, 1);
				pg.fill(255,5);
				pg.rect(0, y+8, pg.width, 1);

				pg.fill(255,200);
				pg.rect(0, y-1, pg.width, 1);
				pg.fill(255,180);
				pg.rect(0, y-2, pg.width, 1);
				pg.fill(255,150);
				pg.rect(0, y-3, pg.width, 1);
				pg.fill(255,120);
				pg.rect(0, y-4, pg.width, 1);
				pg.fill(255,100);
				pg.rect(0, y-5, pg.width, 1);
				pg.fill(255,80);
				pg.rect(0, y-6, pg.width, 1);
				pg.fill(255,60);
				pg.rect(0, y-7, pg.width, 1);
				pg.fill(255,20);
				pg.rect(0, y-8, pg.width, 1);
				pg.fill(255,15);
				pg.rect(0, y-9, pg.width, 1);
				pg.fill(255,12);
				pg.rect(0, y-10, pg.width, 1);
				pg.fill(255,10);
				pg.rect(0, y-11, pg.width, 1);
				pg.fill(255,5);
				pg.rect(0, y-12, pg.width, 1);*/
			
			pg.beginDraw();
			pg.clear();
			pg.noStroke();
			pg.colorMode(PConstants.HSB,360,100,100);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,255);
			pg.rect(0, y, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,100);
			pg.rect(0, y+1, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,80);
			pg.rect(0, y+2, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,60);
			pg.rect(0, y+3, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,20);
			pg.rect(0, y+4, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,15);
			pg.rect(0, y+5, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,12);
			pg.rect(0, y+6, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,10);
			pg.rect(0, y+7, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,5);
			pg.rect(0, y+8, pg.width, 1);

			pg.fill(cf.hue-5, cf.saturation, cf.brightness,200);
			pg.rect(0, y-1, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,180);
			pg.rect(0, y-2, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,150);
			pg.rect(0, y-3, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,120);
			pg.rect(0, y-4, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,100);
			pg.rect(0, y-5, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,80);
			pg.rect(0, y-6, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,60);
			pg.rect(0, y-7, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,20);
			pg.rect(0, y-8, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,15);
			pg.rect(0, y-9, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,12);
			pg.rect(0, y-10, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,10);
			pg.rect(0, y-11, pg.width, 1);
			pg.fill(cf.hue-5, cf.saturation, cf.brightness,5);
			pg.rect(0, y-12, pg.width, 1);
			
			
			
				
				
				int current = p.millis();
				if(current-timer>speed){	
					
					if(down){
						y--;
					}else{
						y++;
					}
					if(y==-20){
						down = false;
					}
					if(y>=20){
						deadA=true;
						down = true;
					}	
					
				timer = current;
				}
				
				pg.endDraw();
			
		}
		
		
		public boolean isDead(){
			return deadA;
		}
		
		
		
		




		
		
	}

	

	
	
	
	
}
