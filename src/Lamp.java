import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class Lamp implements Effect{

	private Layer lampLayer;
	private PGraphics layerGraphics;
	private ColorFade lampFader;
	private PApplet p;
	private int speed;
	private int timer;
	private boolean dead=false;
	private int fadeNum;
	private ColorFadeList cfList;
	
	public Lamp(PApplet p, Layer lampLayer, ColorFadeList cfList, int speed){
		this.p = p;
		this.lampLayer = lampLayer;
		this.speed = speed;
		this.cfList = cfList;
		layerGraphics = lampLayer.getLayer();
		int h = 0;
		lampFader = new ColorFade(p, h, 100, 100, 0);
		lampFader.hueFade(h+50, speed, 2);
		lampFader.alphaFade(255, speed, 2);
		cfList.addColorFade(lampFader);
		timer = p.millis();
		//System.out.println("FINISHED LAMP SETUP");

	}
	
	public void draw(){
		//System.out.println("DRAWLAM2P");
		layerGraphics.beginDraw();
		layerGraphics.clear();
		layerGraphics.colorMode(PConstants.HSB,360,100,100);
		layerGraphics.noStroke();
		layerGraphics.fill(lampFader.hue, lampFader.saturation, lampFader.brightness, lampFader.alpha);
		layerGraphics.rect(0, 0, layerGraphics.width, layerGraphics.height);
		layerGraphics.endDraw();
		lampLayer.add();
		double current = p.millis();
		if(current-timer>2*speed){
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
