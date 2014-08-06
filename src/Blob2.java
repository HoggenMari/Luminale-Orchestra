import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class Blob2 implements Effect {

	private PApplet p;
	private NozzleLayer nozzleLayer;
	private ColorFade cf;
	private ColorFadeList cfList;
	private ColorFade cfIn, cfOut;
	private PGraphics pg;
	private boolean dead = false;
	private boolean first = true;
	private int id;

	public Blob2(PApplet p, NozzleLayer nozzleLayer, int id, ColorFade cf, ColorFadeList cfList){
		this.p = p;
		this.nozzleLayer = nozzleLayer;
		this.id = id;
		this.cf = cf;
		this.cfList = cfList;
				
		cfIn = new ColorFade(p,cf.hue+id,cf.saturation,cf.brightness,0);
		cfIn.brightnessFade(255, 500, 1);
		cfIn.alphaFade(255, 500, 1);
		this.cfList.addColorFade(cfIn);
		
		pg = nozzleLayer.getLayer();
	
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		pg.beginDraw();
		pg.clear();
		pg.colorMode(PConstants.HSB,360,255,255,255);
		pg.noStroke();
		pg.fill(cfIn.hue, cfIn.saturation, cfIn.brightness, cfIn.alpha);
		pg.rect(0, 0, pg.width, 1);
		pg.fill(cfIn.hue, cfIn.saturation, cfIn.brightness-2, cfIn.alpha);
		pg.rect(0, 1, pg.width, 1);
		pg.fill(cfIn.hue, cfIn.saturation, cfIn.brightness-4, cfIn.alpha);
		pg.rect(0, 2, pg.width, 1);
		pg.fill(cfIn.hue, cfIn.saturation, cfIn.brightness-8, cfIn.alpha);
		pg.rect(0, 3, pg.width, 1);
		pg.fill(cfIn.hue, cfIn.saturation, cfIn.brightness-10, cfIn.alpha);
		pg.rect(0, 4, pg.width, 1);
		
		if(cfIn.isDead()){
			
			if(first){
			cfOut = new ColorFade(p, cfIn.hue, cfIn.saturation, cfIn.brightness, cfIn.alpha);
			cfOut.alphaFade(0, 10000,1);
			cfOut.brightnessFade(cfIn.brightness-50, 10000, 1);
			cfList.addColorFade(cfOut);
			first=false;
			}else{
				pg.clear();
				pg.fill(cfOut.hue, cfOut.saturation, cfOut.brightness, cfOut.alpha);
				pg.rect(0, 0, pg.width, 1);
				pg.fill(cfOut.hue, cfOut.saturation, cfOut.brightness-5, cfOut.alpha);
				pg.rect(0, 1, pg.width, 1);
				pg.fill(cfOut.hue, cfOut.saturation, cfOut.brightness-10, cfOut.alpha);
				pg.rect(0, 2, pg.width, 1);
				pg.fill(cfOut.hue, cfOut.saturation, cfOut.brightness-20, cfOut.alpha);
				pg.rect(0, 3, pg.width, 1);
				pg.fill(cfOut.hue, cfOut.saturation, cfOut.brightness-30, cfOut.alpha);
				pg.rect(0, 4, pg.width, 1);
				
				if(cfOut.isDead()){
					dead  = true;
				}
			}
			
			
		}
		
		pg.endDraw();
		nozzleLayer.add();
		
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
