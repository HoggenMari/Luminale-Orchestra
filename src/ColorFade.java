import processing.core.PApplet;


public class ColorFade {

	PApplet p;
	int hue;
	int saturation;
	int brightness;
	int alpha;
	boolean running = false;
	
	boolean loop = false;
	
	int saturationSavedTime;
	int saturationTotalTime;
	int saturationEnd;
	int saturationStart;
	int saturationAdd;
	int saturationLoop = 0;
	int saturationLoopMax = Integer.MAX_VALUE;
	boolean activeSaturation = false;
	
	int brightnessSavedTime;
	int brightnessTotalTime;
	int brightnessEnd;
	int brightnessStart;
	int brightnessAdd;
	int brightnessLoop = 0;
	int brightnessLoopMax = Integer.MAX_VALUE;
	boolean activeBrightness = false;

	int hueSavedTime;
	int hueTotalTime;
	int hueEnd;
	int hueStart;
	int hueAdd;
	int hueLoop = 0;
	int hueLoopMax = Integer.MAX_VALUE;
	boolean activeHue = false;
	
	int alphaSavedTime;
	int alphaTotalTime;
	int alphaEnd;
	int alphaStart;
	int alphaAdd;
	int alphaLoop = 0;
	int alphaLoopMax = Integer.MAX_VALUE;
	boolean activeAlpha = false;
	private boolean dead;
	
	public ColorFade(PApplet p, int hueStart, int saturationStart, int brightnessStart) {
		this.p = p;
		this.hue = hueStart;
		this.hueStart = hueStart;
		this.saturation = saturationStart;
		this.saturationStart = saturationStart;
		this.brightness = brightnessStart;
		this.brightnessStart = brightnessStart;
		this.alpha = 255;
		this.alphaStart = 255;
	}
	
	public ColorFade(PApplet p, int hueStart, int saturationStart, int brightnessStart, int alphaStart) {
		this.p = p;
		this.hue = hueStart;
		this.hueStart = hueStart;
		this.saturation = saturationStart;
		this.saturationStart = saturationStart;
		this.brightness = brightnessStart;
		this.brightnessStart = brightnessStart;
		this.alpha = alphaStart;
		this.alphaStart = alphaStart;
	}
	
	public void hueFade(int hueEnd, int duration) {
		this.hueEnd = hueEnd;
		int diff = Math.abs(hue-hueEnd);
		if(diff!=0) {
		this.hueTotalTime = duration/diff;
		if(hueEnd < hue) {
			hueAdd = -1;
		}else{ 
			hueAdd = 1;
		}
		//System.out.println(diff+" "+hueTotalTime);	
		activeHue = true;
		}
	}
	
	public void hueFade(int hueEnd, int duration, int hueLoopMax) {
		loop = true;
		this.hueEnd = hueEnd;
		this.hueLoopMax = hueLoopMax;
		int diff = Math.abs(hue-hueEnd);
		if(diff!=0) {
		this.hueTotalTime = duration/diff;
		if(hueEnd < hue) {
			hueAdd = -1;
		}else{ 
			hueAdd = 1;
		}
		//System.out.println(diff+" "+hueTotalTime);	
		activeHue = true;
		}
	}
	
	public void saturationFade(int saturationEnd, int duration) {
		this.saturationEnd = saturationEnd;
		int diff = Math.abs(saturation-saturationEnd);
		if(diff!=0) {
		this.saturationTotalTime = duration/diff;
		if(saturationEnd < saturation) {
			saturationAdd = -1;
		}else{ 
			saturationAdd = 1;
		}
		//System.out.println(diff+" "+saturationTotalTime);	
		activeSaturation = true;
		}
	}
	
	public void saturationFade(int saturationEnd, int duration, int saturationLoopMax) {
		loop = true;
		this.saturationLoopMax = saturationLoopMax;
		this.saturationEnd = saturationEnd;
		int diff = Math.abs(saturation-saturationEnd);
		if(diff!=0) {
		this.saturationTotalTime = duration/diff;
		if(saturationEnd < saturation) {
			saturationAdd = -1;
		}else{ 
			saturationAdd = 1;
		}
		//System.out.println(diff+" "+saturationTotalTime);	
		activeSaturation = true;
		}
	}
	
	public void brightnessFade(int brightnessEnd, int duration) {
		brightnessLoop = 0;
		brightnessLoopMax = Integer.MAX_VALUE;
		this.brightnessEnd = brightnessEnd;
		int diff = Math.abs(brightness-brightnessEnd);
		if(diff==0){
			diff +=1;
		}
		this.brightnessTotalTime = duration/(int)diff;
		if(brightnessEnd < brightness) {
			brightnessAdd = -1;
		}else{ 
			brightnessAdd = 1;
		}
		//System.out.println(diff+" "+brightnessTotalTime);	
		activeBrightness = true;
	}
	
	public void brightnessFade(int brightnessEnd, int duration, int brightnessLoopMax) {
		loop = true;
		this.brightnessEnd = brightnessEnd;
		this.brightnessLoopMax = brightnessLoopMax;
		int diff = Math.abs(brightness-brightnessEnd);
		if(diff==0){
			diff +=1;
		}
		this.brightnessTotalTime = duration/(int)diff;
		if(brightnessEnd < brightness) {
			brightnessAdd = -1;
		}else{ 
			brightnessAdd = 1;
		}
		//System.out.println(diff+" "+brightnessTotalTime);	
		activeBrightness = true;
	}
	
	public void alphaFade(int alphaEnd, int duration) {
		this.alphaEnd = alphaEnd;
		int diff = Math.abs(alpha-alphaEnd);
		if(diff==0){
			diff +=1;
		}
		this.alphaTotalTime = duration/(int)diff;
		if(alphaEnd < alpha) {
			alphaAdd = -1;
		}else{ 
			alphaAdd = 1;
		}
		//System.out.println(diff+" "+brightnessTotalTime);	
		activeAlpha = true;
	}
	
	public void alphaFade(int alphaEnd, int duration, int alphaLoopMax) {
		loop = true;
		this.alphaEnd = alphaEnd;
		this.alphaLoopMax = alphaLoopMax;
		int diff = Math.abs(alpha-alphaEnd);
		if(diff==0){
			diff +=1;
		}
		this.alphaTotalTime = duration/(int)diff;
		if(alphaEnd < alpha) {
			alphaAdd = -1;
		}else{ 
			alphaAdd = 1;
		}
		//System.out.println(diff+" "+brightnessTotalTime);	
		activeAlpha = true;
	}
	
	public void setDead(){
		dead = true;
	}
	
	public boolean isDead(){
		return dead;
	}
}
