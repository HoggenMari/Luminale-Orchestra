import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class Blob implements Effect{

	private PApplet p;
	private NozzleLayer nozzleLayer;
	private ColorFade cf;
	private PGraphics pg;
	float yValue;
	private int hue;
	private int saturation;
	private int brightness;
	private ColorFade cf2;
	private ColorFadeList cfList;
	private boolean first=true;

	public Blob(PApplet p, NozzleLayer nozzleLayer, ColorFade cf, ColorFadeList cfList){
		this.p = p;
		this.nozzleLayer = nozzleLayer;
		this.cf = cf;
		this.cfList = cfList;
		yValue = 0;
		
		this.hue = hue;
		this.saturation = saturation;
		this.brightness = brightness;
		
		
		pg = nozzleLayer.getLayer();
		
		//Ani.init(p);
		//Ani.to(this, (float) 1.0, "yValue", 254, Ani.QUAD_OUT);
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
		System.out.println("xValue:"+yValue);
		if(yValue<254){
		pg.beginDraw();
		pg.colorMode(PConstants.HSB,360,255,255,255);
		pg.clear();
		pg.noStroke();
		//int amount = 255/100;
		int zero = (int) Math.sqrt(255/0.1);
		for(int i=0; i<255; i++){
			pg.fill(cf.hue, cf.saturation, cf.brightness, i);
			pg.rect(0, yValue-i, pg.width, 1);
		}
		}
		
		if(yValue<254){
		yValue++;
		}else{
			
		if(first){
		cf2 = new ColorFade(p, cf.hue, cf.saturation, cf.brightness, 255);
		cf2.alphaFade(0, 5000,1);
		cfList.addColorFade(cf2);
		first=false;
		}
		else{
			pg.clear();
			pg.fill(cf2.hue, cf2.saturation, cf2.brightness, cf2.alpha);
			pg.rect(0, 0, pg.width, 1);
			pg.fill(cf2.hue, cf2.saturation, cf2.brightness, cf2.alpha-1);
			pg.rect(0, 1, pg.width, 1);
			pg.fill(cf2.hue, cf2.saturation, cf2.brightness, cf2.alpha-2);
			pg.rect(0, 2, pg.width, 1);
			pg.fill(cf2.hue, cf2.saturation, cf2.brightness, cf2.alpha-3);
			pg.rect(0, 3, pg.width, 1);
			pg.fill(cf2.hue, cf2.saturation, cf2.brightness, cf2.alpha-4);
			pg.rect(0, 4, pg.width, 1);
		}
			
		}
		
		pg.endDraw();
		nozzleLayer.add();
		
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
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
