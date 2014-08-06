import java.util.LinkedList;

import de.looksgood.ani.Ani;
import de.looksgood.ani.AniSequence;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class FineTube implements Effect{

	private PApplet p;
	private NozzleLayer nozzleLayer;
	private ColorFade cf;
	private PGraphics pg;

	private float x;
	private int a1;
	private int m1;
	private int e1;
	private int current;
	private int speed;
	private int timer;
	private boolean dead = false;
	private LinkedList<Nozzle> nozzlePath;
	private PGraphics pg2;
	private NozzleLayer nLayer;
	private TopGlow tp;
	private boolean first = true;
	private ColorFade end;
	private ColorFadeList cfl;
	private ColorFade endColor;
	private ColorFadeList cfList;
	private int alphaStart;
	private int TYPE;

	public FineTube(PApplet p, NozzleLayer nozzleLayer, ColorFade cf){
		this.p = p;
		this.nozzleLayer = nozzleLayer;
		this.cf = cf;
		this.TYPE = 1;
		
		a1 = 20;
		m1 = 20;
		e1 = 20;
		
		x=-(2*a1+m1+e1);
		
		pg = nozzleLayer.getLayer();
		
		alphaStart = 100;

		
		speed=150;
		
		timer = p.millis();
		
		Ani.init(p);
		Ani.to(this, (float) 13.0, "speed", 80, Ani.LINEAR);

		  
		
		//p.frameRate(20);

	}
	
	public FineTube(PApplet p, NozzleLayer nozzleLayer, ColorFade cf, int speed1, int speed2, int a1, int m1, int e1){
		this.p = p;
		this.nozzleLayer = nozzleLayer;
		this.cf = cf;
		this.TYPE = 3;
		
		this.a1 = a1;
		this.m1 = m1;
		this.e1 = e1;
		
		x=-(2*a1+m1+e1);
		
		pg = nozzleLayer.getLayer();
		
		alphaStart = 100;

		
		this.speed=speed1;
		
		timer = p.millis();
		
		Ani.init(p);
		Ani.to(this, (float) 13.0, "speed", speed2, Ani.CIRC_IN_OUT);

		  
		
		//p.frameRate(20);

	}
	
	public FineTube(PApplet p, Pavillon scp, LinkedList<Nozzle> nozzlePath, NozzleLayer nozzleLayer, ColorFade cf, ColorFadeList cfList){
		this.p = p;
		this.nozzleLayer = nozzleLayer;
		this.cf = cf;
		this.nozzlePath = nozzlePath;
		this.cfList = cfList;
		this.TYPE = 2;
		
		a1 = 40;
		m1 = 10;
		e1 = 10;
		
		x=-(a1+m1+e1);
		
		alphaStart = 200;
		
		pg = nozzleLayer.getLayer();
		LinkedList<Nozzle> lastNozzle = new LinkedList<Nozzle>();
		lastNozzle.add(nozzlePath.getFirst()); 
		nLayer = new NozzleLayer(p, scp, lastNozzle);
		pg2 = nLayer.getLayer();

		
		speed=100;
		
		timer = p.millis();
		
		Ani.init(p);
		Ani.to(this, (float) 13.0, "speed", 60, Ani.CIRC_IN);

		  
		
		//p.frameRate(20);

	}

	public void drawType1() {
		
		//System.out.println("SPEED: "+speed);
		
		pg.beginDraw();
		pg.colorMode(PConstants.HSB,360,255,255,255);
		pg.noStroke();
		pg.clear();
		pg.smooth();
		
		
		
		
		

		int amount = cf.alpha/a1;

		for(int i=0; i<a1; i++){
			pg.fill(cf.hue, cf.saturation, cf.brightness, i*amount);
			pg.rect(x+i, 0, 1, pg.height);
		}
				
			pg.fill(cf.hue, cf.saturation, cf.brightness, a1*amount);
			pg.rect(x+a1, 0, m1, pg.height);
		
		
		amount = cf.alpha/e1;
		for(int i=0; i<e1; i++){
			pg.fill(cf.hue, cf.saturation, cf.brightness, cf.alpha-i*amount);
			pg.rect(x+i+a1+m1, 0, 1, pg.height);
		}
		pg.endDraw();
		nozzleLayer.add();
		
		current = p.millis();
		if(current-timer>speed){
		x++;
		timer=current;
		}

		if(x>pg.width+20){
			dead = true;
		}
		
	}
	
	public void drawType2() {
		
		//System.out.println("SPEED2: "+speed);
		
		pg.beginDraw();
		pg.colorMode(PConstants.HSB,360,255,255,255);
		pg.noStroke();
		pg.clear();
		pg.smooth();
		
		
		
		
		

		int amount = cf.alpha/a1;

		for(int i=0; i<a1; i++){
			pg.fill(cf.hue, cf.saturation, cf.brightness, i*amount);
			pg.rect(x+i, 0, 1, pg.height);
		}
				
			pg.fill(cf.hue, cf.saturation, cf.brightness, a1*amount);
			pg.rect(x+a1, 0, m1, pg.height);
		
		
		amount = cf.alpha/e1;
		for(int i=0; i<e1; i++){
			pg.fill(cf.hue, cf.saturation, cf.brightness, cf.alpha-i*amount);
			pg.rect(x+i+a1+m1, 0, 1, pg.height);
		}
		pg.endDraw();
		nozzleLayer.add();
		
		current = p.millis();
		if(current-timer>speed){
		x++;
		timer=current;
		}

		if(x>pg.width-(a1+m1)){
			if(first){
				end = new ColorFade(p, cf.hue, 0, 255, 0);
				end.saturationFade(0, 1000, 2);
				end.alphaFade(255, 1000, 2);
				cfList.addColorFade(end);
			first = false;
			}else{
			//System.out.println("DRAW END: "+cf.hue+" "+cf.saturation+" "+cf.brightness);
			pg2.beginDraw();
			pg2.colorMode(PConstants.HSB, 360, 255, 255, 255);
			pg2.clear();
			pg2.noStroke();
			pg2.fill(end.hue, end.saturation, end.brightness, end.alpha);
			pg2.rect(0, 0, pg2.width, pg2.height);
			pg2.endDraw();
			nLayer.add();
			
			if(end.isDead()){
				dead = true;
			}
				
			}
		}
		
		
	}
	
	
	public void drawType3() {
		
		//System.out.println("SPEED: "+speed);
		
		pg.beginDraw();
		pg.colorMode(PConstants.HSB,360,255,255,255);
		pg.noStroke();
		pg.clear();
		pg.smooth();
		
		
		
		
		

		int amount = cf.alpha/a1;

		for(int i=0; i<a1; i++){
			pg.fill(cf.hue, cf.saturation, cf.brightness, i*amount);
			pg.rect(x+i, 0, 1, pg.height);
		}
				
			pg.fill(cf.hue, cf.saturation, cf.brightness, a1*amount);
			pg.rect(x+a1, 0, m1, pg.height);
		
		
		amount = cf.alpha/e1;
		for(int i=0; i<e1; i++){
			pg.fill(cf.hue, cf.saturation, cf.brightness, cf.alpha-i*amount);
			pg.rect(x+i+a1+m1, 0, 1, pg.height);
		}
		pg.endDraw();
		nozzleLayer.add();
		
		current = p.millis();
		if(current-timer>speed){
		x++;
		timer=current;
		}

		if(x>pg.width+20){
			dead = true;
		}
		
	}
	

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
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

	@Override
	public void draw() {
		if(TYPE==1){
			drawType1();
		}else if(TYPE==2){
			drawType2();
		}else {
			drawType3();
		}
		
	}
	
}
