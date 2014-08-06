import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.PApplet;
import processing.core.PConstants;


public class ColorFadeList extends Thread {
	
	public CopyOnWriteArrayList<ColorFade> colorFadeList = new CopyOnWriteArrayList<ColorFade>();
	private PApplet p;
	
	public ColorFadeList(PApplet p) {
		this.p = p;
	}
		
	public void addColorFade(ColorFade cf) {
		colorFadeList.add(cf);
	}
	
	public ColorFade get(int num) {
		return colorFadeList.get(num);
	}
	
	public void start() {
		super.start();
	}

	public void run() {
		while (true) {
		      //System.out.println("PASSED TIME: "+passedTime);
		      draw();
		      try {
		        sleep((long)(0));
		      } catch (Exception e) {
		      }
		    }
	}
	
	public synchronized void draw() {
		int timer = p.millis();
		//System.out.println("TIMER IN: "+timer);
		for(ColorFade cf : colorFadeList) {
			int saturationPassedTime = timer - cf.saturationSavedTime;
			int brightnessPassedTime = timer - cf.brightnessSavedTime;
			int huePassedTime = timer - cf.hueSavedTime;
			int alphaPassedTime = timer - cf.alphaSavedTime;
			if(cf.activeSaturation && cf.saturationLoop<cf.saturationLoopMax) {
			if (saturationPassedTime >= cf.saturationTotalTime) {
				//System.out.println("SAURATIONTIME "+p.millis());
				p.colorMode(PConstants.HSB, 360, 100, 100);
			    cf.saturation = cf.saturation + cf.saturationAdd;
			    //p.background(hue, saturation, brightness);
				cf.saturationSavedTime = p.millis(); // Save the current time to restart the timer!
			if(cf.saturation == cf.saturationEnd) {
				cf.saturationEnd = cf.saturationStart;
				cf.saturationStart = cf.saturation;
				if(cf.saturationEnd < cf.saturation) {
					cf.saturationAdd = -1;
					cf.saturationLoop += 1;
				}else{ 
					cf.saturationAdd = 1;
					cf.saturationLoop += 1;
				}
			}
			}
			}
			if(cf.activeBrightness && cf.brightnessLoop<cf.brightnessLoopMax) {
			if (brightnessPassedTime >= cf.brightnessTotalTime) {
				//System.out.println("ACTIVEBRIGHTNESS: "+cf+" "+p.millis()+" "+cf.brightness);
				p.colorMode(PConstants.HSB, 360, 100, 100);
			    cf.brightness = cf.brightness + cf.brightnessAdd;
			    //p.background(hue, saturation, brightness);
				cf.brightnessSavedTime = timer; // Save the current time to restart the timer!
			}
			if(cf.brightness == cf.brightnessEnd) {
				//System.out.println("CHANGE: "+cf+" "+p.millis()+" "+cf.brightness);
				cf.brightnessEnd = cf.brightnessStart;
				cf.brightnessStart = cf.brightness;
				if(cf.brightnessEnd < cf.brightness) {
					cf.brightnessAdd = -1;
					cf.brightnessLoop += 1;
				}else{ 
					cf.brightnessAdd = 1;
					cf.brightnessLoop += 1;
				}
			}
			}
			if(cf.activeHue && cf.hueLoop<cf.hueLoopMax) {
				if (huePassedTime >= cf.hueTotalTime) {
					//System.out.println("ACTIVEHUE: "+p.millis());
					p.colorMode(PConstants.HSB, 360, 100, 100);
				    cf.hue = cf.hue + cf.hueAdd;
				    //p.background(hue, saturation, brightness);
					cf.hueSavedTime = p.millis(); // Save the current time to restart the timer!
				}
				if(cf.hue == cf.hueEnd) {
					cf.hueEnd = cf.hueStart;
					cf.hueStart = cf.hue;
					if(cf.hueEnd < cf.hue) {
						cf.hueAdd = -1;
						cf.hueLoop += 1;
					}else{ 
						cf.hueAdd = 1;
						cf.hueLoop += 1;
					}
			}
			}
			if(cf.activeAlpha && cf.alphaLoop<cf.alphaLoopMax) {
				if (alphaPassedTime >= cf.alphaTotalTime) {
					//System.out.println("ACTIVEHUE: "+p.millis());
					p.colorMode(PConstants.HSB, 360, 100, 100);
				    cf.alpha = cf.alpha + cf.alphaAdd;
				    //p.background(hue, saturation, brightness);
					cf.alphaSavedTime = p.millis(); // Save the current time to restart the timer!
				}
				if(cf.alpha == cf.alphaEnd) {
					cf.alphaEnd = cf.alphaStart;
					cf.alphaStart = cf.alpha;
					if(cf.alphaEnd < cf.alpha) {
						cf.alphaAdd = -1;
						cf.alphaLoop += 1;
					}else{ 
						cf.alphaAdd = 1;
						cf.alphaLoop += 1;
					}
			}
			}
		}
		
		for(ColorFade cf : colorFadeList){
			if(cf.loop) {
				//System.out.println("GO1 "+cf.brightnessLoop+" "+cf.brightnessLoopMax);
			if(cf.hueLoop >= cf.hueLoopMax || cf.saturationLoop >= cf.saturationLoopMax || cf.brightnessLoop >= cf.brightnessLoopMax || cf.alphaLoop >= cf.alphaLoopMax) {
				colorFadeList.remove(cf);
				//System.out.println("GO2");
				cf.setDead();
				//System.out.println("REMOVE");
			}
			}
		}
		/*for(Iterator<ColorFade> cfIterator = colorFadeList.iterator(); cfIterator.hasNext();){
			ColorFade cf = cfIterator.next();
		
			if(cf.loop) {
				//System.out.println("GO1 "+cf.brightnessLoop+" "+cf.brightnessLoopMax);
			if(cf.hueLoop >= cf.hueLoopMax || cf.saturationLoop >= cf.saturationLoopMax || cf.brightnessLoop >= cf.brightnessLoopMax) {
				cfIterator.remove();
				//System.out.println("GO2");

			}
			}
		//System.out.println("FERTIG: "+p.millis());
		}*/
	}
}
