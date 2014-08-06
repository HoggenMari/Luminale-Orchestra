import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;


public class Breath {
	
	private PApplet p;
	private int hueBreath;
	private ColorFade c1;
	private ColorFade c2;
	private ColorFade c3;
	private ColorFade c4;
	private ColorFade c5;
	private ColorFadeList cfList;
	private Pavillon scp;
	private boolean hueDown;
	private int hue;
	private int saturation;
	private int brightness;
	
	public Breath(PApplet p, Pavillon scp, ColorFadeList cfList, int hue, int saturation, int startSpeed){
		this.p = p;
		this.scp = scp;
		this.cfList = cfList;
		this.hue = hue;
		this.saturation = saturation;


		hueBreath = 35;
		//speedBreath = 5000;
		
		c1 = new ColorFade(p, hue, saturation, 255);
		c2 = new ColorFade(p, hue, saturation, 150);
		c3 = new ColorFade(p, hue, saturation, 100);
		c4 = new ColorFade(p, hue, saturation, 70);
		c5 = new ColorFade(p, hue, saturation, 55);
		
		c1.brightnessFade(80, startSpeed, 2);
		c2.brightnessFade(80, startSpeed, 2);
		c3.brightnessFade(80, startSpeed, 2);
		c4.brightnessFade(80, startSpeed, 2);
		c5.brightnessFade(80, startSpeed, 2);
		
		cfList.addColorFade(c1);
		cfList.addColorFade(c2);
		cfList.addColorFade(c3);
		cfList.addColorFade(c4);
		cfList.addColorFade(c5);
		
	}
	
	public void draw(int speedBreath){
		for(Nozzle nozzle : scp.nozzleList) {
			PGraphics pg = nozzle.sysA;
			pg.beginDraw();
			pg.background(0);

				pg.colorMode(PConstants.HSB, 360, 255, 255);	
				pg.noStroke();
				pg.fill(c1.hue,c1.saturation,c1.brightness);
				pg.rect(0, 4, pg.width, 1);
				pg.fill(c2.hue,c2.saturation,c2.brightness);
				pg.rect(0, 3, pg.width, 1);
				pg.fill(c3.hue,c3.saturation,c3.brightness);
				pg.rect(0, 2, pg.width, 1);
				pg.fill(c4.hue,c4.saturation,c4.brightness);
				pg.rect(0, 1, pg.width, 1);
				pg.fill(c5.hue,c5.saturation,c5.brightness);
				pg.rect(0, 0, pg.width, 1);
		   
			
			//pg2.colorMode(HSB, 360, 100, 100,100);
			//pg2.fill(Math.abs(cfYellow.hue-270)+30,cfYellow.saturation,cfYellow.brightness, 70);
			//pg2.rect(0, 0, pg2.width, 1);

			//pg2.endDraw();
			pg.endDraw();
			
			//System.out.println("Hue1: "+(Math.abs(cfYellow.hue-270)+30)+" Sat1: "+(Math.abs(cfYellow.saturation-100))+" Bright: "+(Math.abs(cfYellow.brightness-100)+40));
		}
		
		if(c1.isDead()){
			
			if(hueDown){
				hueBreath--;
			}else{
				hueBreath++;
			}
			if(hueBreath==240){
				hueDown = false;
			}
			if(hueBreath>=300){
				hueDown = true;
			}
			
			System.out.println("HUE: "+hueBreath+" HUEDOWN"+hueDown);
			
			/*if(speedBreath>2000){
			speedBreath-=100;
			}else{
				speedBreath=5000;
			}*/
			
			System.out.println("SPEEDBREATH: "+speedBreath);
			
			
			c1 = new ColorFade(p, hue, saturation, 255);
			c2 = new ColorFade(p, hue, saturation, 150);
			c3 = new ColorFade(p, hue, saturation, 100);
			c4 = new ColorFade(p, hue, saturation, 70);
			c5 = new ColorFade(p, hue, saturation, 55);
			
			c1.brightnessFade(55, speedBreath, 2);
			c2.brightnessFade(55, speedBreath, 2);
			c3.brightnessFade(55, speedBreath, 2);
			c4.brightnessFade(55, speedBreath, 2);
			c5.brightnessFade(55, speedBreath, 2);
			
			cfList.addColorFade(c1);
			cfList.addColorFade(c2);
			cfList.addColorFade(c3);
			cfList.addColorFade(c4);
			cfList.addColorFade(c5);
			
		}
	}

}
