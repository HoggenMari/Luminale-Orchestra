import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class Emphasize implements Effect{

	private PApplet p;
	private Node n;
	private ColorFade cf;
	private ColorFadeList cfList;
	private ColorFade cfIn;
	private NozzleLayer nozzleLayer;
	private PGraphics pg;
	private PGraphics pg2;
	private ColorFade cfOut;
	private boolean first = true;
	private boolean dead = false;
	private int h;
	private int sat;
	private int bright;
	private int timer = 0;

	public Emphasize(PApplet p, NozzleLayer nozzleLayer, int hue, int saturation, int brightness, ColorFadeList cfList){
		this.p = p;
		this.nozzleLayer = nozzleLayer;
		this.cf = cf;
		this.cfList = cfList;
		
		pg = nozzleLayer.getLayer();
		
		this.h = hue;
		this.sat = saturation;
		this.bright = brightness;
		
		System.out.println("INIT EFFEKT: "+hue+" "+saturation+" "+brightness);
		
		if(h<150){
		cfIn = new ColorFade(p,h,sat,bright,0);
		cfIn.hueFade(30, 1000, 1);
		cfIn.saturationFade(255, 1000, 1);
		cfIn.brightnessFade(255, 1000, 1);
		cfIn.alphaFade(255, 1000, 1);
		this.cfList.addColorFade(cfIn);
		}else if(h>=150){
		cfIn = new ColorFade(p,h,sat,bright,0);
		cfIn.hueFade(270, 1000, 1);
		cfIn.saturationFade(255, 1000, 1);
		cfIn.brightnessFade(255, 1000, 1);
		cfIn.alphaFade(255, 1000, 1);
		this.cfList.addColorFade(cfIn);
		}
	}
	
	public void draw(){
		
		//System.out.println("DRAW EMPHASIZE: "+h);
		pg.beginDraw();
		pg.colorMode(PConstants.HSB, 360, 255, 255, 255);
		pg.clear();
		pg.noStroke();
		pg.fill(cfIn.hue, cfIn.saturation, cfIn.brightness, cfIn.alpha);
		pg.rect(0, 0, pg.width, 1);
		pg.fill(cfIn.hue, cfIn.saturation-20, cfIn.brightness, cfIn.alpha);
		pg.rect(0, 1, pg.width, 1);
		pg.fill(cfIn.hue, cfIn.saturation-40, cfIn.brightness, cfIn.alpha);
		pg.rect(0, 2, pg.width, 1);
		pg.fill(cfIn.hue, cfIn.saturation-50, cfIn.brightness, cfIn.alpha);
		pg.rect(0, 3, pg.width, 1);
		pg.fill(cfIn.hue, cfIn.saturation-60, cfIn.brightness, cfIn.alpha);
		pg.rect(0, 4, pg.width, 1);
		
		if(cfIn.isDead()){
			//System.out.println("GODEAD");
			timer ++;
			if(timer>=100){
			if(first){
			cfOut = new ColorFade(p,cfIn.hue, cfIn.saturation, cfIn.brightness, cfIn.alpha);
			cfOut.alphaFade(0, 5000, 1);
			cfList.addColorFade(cfOut);
			first = false;
			}else{
				//System.out.println("GODEADALPHA");
				pg.clear();
				pg.fill(cfOut.hue, cfOut.saturation, cfOut.brightness, cfOut.alpha);
				pg.rect(0, 0, pg.width, 1);
				pg.fill(cfOut.hue, cfOut.saturation-20, cfOut.brightness, cfOut.alpha);
				pg.rect(0, 1, pg.width, 1);
				pg.fill(cfOut.hue, cfOut.saturation-40, cfOut.brightness, cfOut.alpha);
				pg.rect(0, 2, pg.width, 1);
				pg.fill(cfOut.hue, cfOut.saturation-50, cfOut.brightness, cfOut.alpha);
				pg.rect(0, 3, pg.width, 1);
				pg.fill(cfOut.hue, cfOut.saturation-60, cfOut.brightness, cfOut.alpha);
				pg.rect(0, 4, pg.width, 1);
				if(cfOut.isDead()){
					System.out.println("DEAD");
					dead = true;
				}
			}
			}
		}
		
		
		pg.endDraw();
		nozzleLayer.add();
		
		pg2 = nozzleLayer.getLayerB();
		pg2.beginDraw();
		pg2.clear();
		pg2.colorMode(PConstants.HSB,360,255,255,255);
		pg2.noStroke();
		pg2.fill(Math.abs(cfIn.hue-270)+30,cfIn.saturation,cfIn.brightness, cfIn.alpha);
		pg2.rect(0, 0, pg2.width, pg2.height);
		if(cfIn.isDead() & !first){
			pg2.clear();
			pg2.fill(Math.abs(cfOut.hue-270)+30,cfOut.saturation,cfOut.brightness, cfOut.alpha);
			pg2.rect(0, 0, pg2.width, pg2.height);
		}
		pg2.endDraw();
		nozzleLayer.addB();
		
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
}
