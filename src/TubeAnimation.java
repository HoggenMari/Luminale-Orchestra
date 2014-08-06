import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class TubeAnimation {
	
	private ArrayList<Tube> sTubeList = new ArrayList<Tube>();
	private ArrayList<Effect> effectList = new ArrayList<Effect>();
	private PApplet p;
	private Pavillon scp;
	private ColorFadeList cfList;
	private ColorFade glow;
	private ColorFade backGround;
	private int hue = 0, saturation = 90, brightness = 100;
	private boolean colorChange = false;
	private boolean in = false;
	private float hsb[] = new float[3];

	Tween t;
	int background_color, active_color;

	public TubeAnimation(PApplet p, Pavillon scp, ColorFadeList cfList){
		this.p = p;
		this.scp = scp;
		this.cfList = cfList;
		
		
	}
	
	public void setupMode1(){
		glow = new ColorFade(p, 270, 80, 100);
		glow.brightnessFade(0, 2000);
		cfList.addColorFade(glow);
		
		backGround = new ColorFade(p, 260, 100, 40);
		backGround.brightnessFade(20, 2000);
		cfList.addColorFade(backGround);
		
		
		background_color = p.color(p.random(0,0),p.random(100,100),p.random(100, 100));
		float red = background_color >> 16 & 0xFF;
		float green = background_color >> 8 & 0xFF;
		float blue = background_color & 0xFF;
		System.out.println(red+" "+green+" "+blue);
		float red_com = Math.abs(red - 255);
		float green_com = Math.abs(green - 255);
		float blue_com = Math.abs(blue - 255);
		System.out.println(red_com+" "+green_com+" "+blue_com);
		Color.RGBtoHSB((int)red_com, (int)green_com, (int)blue_com, hsb);
		System.out.println(hsb[0]+" "+hsb[1]+" "+hsb[2]);
		active_color = p.color(hsb[0]*360,hsb[1]*100,hsb[2]*100);
				
		Motion.setup(p);
		
		t = new Tween(300)
	    .addColor(this, "background_color", p.color(60, 0, 120))
	    .play(); 
	}
	
	public void setupMode2(){
		glow = new ColorFade(p, 0, 0, 100);
		glow.brightnessFade(0, 2000);
		cfList.addColorFade(glow);
		
		backGround = new ColorFade(p, 260, 100, 100);
		backGround.saturationFade(70, 5000);
		cfList.addColorFade(backGround);
		
		
		
		background_color = p.color(p.random(0,0),p.random(100,100),p.random(100, 100));
		float red = background_color >> 16 & 0xFF;
		float green = background_color >> 8 & 0xFF;
		float blue = background_color & 0xFF;
		System.out.println(red+" "+green+" "+blue);
		float red_com = Math.abs(red - 255);
		float green_com = Math.abs(green - 255);
		float blue_com = Math.abs(blue - 255);
		System.out.println(red_com+" "+green_com+" "+blue_com);
		Color.RGBtoHSB((int)red_com, (int)green_com, (int)blue_com, hsb);
		System.out.println(hsb[0]+" "+hsb[1]+" "+hsb[2]);
		active_color = p.color(hsb[0]*360,hsb[1]*100,hsb[2]*100);
		
		
		
		Motion.setup(p);
		
		t = new Tween(300)
	    .addColor(this, "background_color", p.color(60, 0, 120))
	    .addColor(this, "active_color", p.color(60, 0, 120))
	    .play(); 
		
	}
	
	public void draw(){
		
		if(!t.isPlaying()){
			t.getColor("background_color").setEnd(p.color((int)p.random(0,360), (int)p.random(70,100), (int)p.random(50,80)));
			t.play();
		}
		
	
		
		backGround();
		tube();
		
		glow();
		
		if(p.frameCount%300==0){
			colorChange = true;
			//hue = (int) p.random(0,360);
		}
		
		//colorChange();
	}
	
	
	public void draw2(){
		
		if(!t.isPlaying()){
			
			p.colorMode(p.HSB, 360, 100, 100);
			int newColor = p.color((int)p.random((int)p.random(0,0),(int)p.random(0,0)), (int)p.random(0,0), (int)p.random(0,0));
			
			float red = newColor >> 16 & 0xFF;
			float green = newColor >> 8 & 0xFF;
			float blue = newColor & 0xFF;
			System.out.println("GO FOR NEW COLOR "+red+" "+green+" "+blue);
			float red_com = Math.abs(red - 255);
			float green_com = Math.abs(green - 255);
			float blue_com = Math.abs(blue - 255);
			System.out.println(red_com+" "+green_com+" "+blue_com);
			Color.RGBtoHSB((int)red_com, (int)green_com, (int)blue_com, hsb);
			System.out.println(hsb[0]+" "+hsb[1]+" "+hsb[2]);
			//active_color = p.color(hsb[0]*360,hsb[1]*100,hsb[2]*100);
			
			
			
			t.getColor("background_color").setEnd(newColor);
			t.getColor("active_color").setEnd(p.color(hsb[0]*360, hsb[1]*100, hsb[2]*100));
			t.play();
		}
		
	
		
		backGround();
		tube();
		
		glow();
		
		if(p.frameCount%300==0){
			colorChange = true;
			//hue = (int) p.random(0,360);
		}
		
		//colorChange();
	}


	
	private void tube(){
		

		System.out.println(sTubeList.size());
		
		  for(Iterator<Tube> sTIterator = sTubeList.iterator(); sTIterator.hasNext();){
			  Tube sT = sTIterator.next();
			  sT.draw();
			  if(sT.isDead()){
				  //System.out.println("DEAD");
				  sTIterator.remove();
			  }
		}
		
		
		
		while(sTubeList.size()<3){	
			
			
			
			/*float red = background_color >> 16 & 0xFF;
			float green = background_color >> 8 & 0xFF;
			float blue = background_color & 0xFF;
			System.out.println(red+" "+green+" "+blue);
			float red_com = Math.abs(red - 255);
			float green_com = Math.abs(green - 255);
			float blue_com = Math.abs(blue - 255);
			System.out.println(red_com+" "+green_com+" "+blue_com);
			Color.RGBtoHSB((int)red_com, (int)green_com, (int)blue_com, hsb);
			System.out.println(hsb[0]+" "+hsb[1]+" "+hsb[2]);
			active_color = p.color(hsb[0]*360,hsb[1]*100,hsb[2]*100);*/
			
			
			
			int zf = (int)p.random(0,7);
			LinkedList<Nozzle> nozzlePath = scp.createNodePath(scp.nodeList.get(zf));
			if(p.frameCount%1==1){
			nozzlePath = scp.createRandomPath(8*zf,8*zf+8,58,65);
			}else if(p.frameCount%1==0){
			nozzlePath = scp.createNodePath(scp.nodeList.get(zf));
			}
			NozzleLayer nLayer = new NozzleLayer(p, scp, nozzlePath);
			sTubeList.add(new Tube(p, nLayer, active_color, (int)p.random(50,80), 1+Math.random()*0.5));
			}
		
			
			
		
	}

	private void backGround(){
		
		for(int i=0; i<scp.nodeList.size(); i++){
			for(int j=0; j<scp.nodeList.get(i).nozzleList.size(); j++){
				LinkedList<Nozzle> nozzlePath = scp.createNodePath(scp.nodeList.get(i));
				NozzleLayer nLayer = new NozzleLayer(p, scp, nozzlePath);
				PGraphics pg = scp.nodeList.get(i).nozzleList.get(j).sysA;
				pg.colorMode(PConstants.RGB);
				pg.beginDraw();
				pg.colorMode(PConstants.HSB, 360, 100, 100);
				pg.noStroke();
				pg.fill(background_color);
				pg.rect(0, 0, pg.width, pg.height);
				pg.endDraw();
			}
		}
		
	}
	
	
	public void glow(){
		for(int i=0; i<scp.nodeList.size(); i++){
			for(int j=0; j<scp.nodeList.get(i).nozzleList.size(); j++){
				LinkedList<Nozzle> nozzlePath = scp.createNodePath(scp.nodeList.get(i));
				NozzleLayer nLayer = new NozzleLayer(p, scp, nozzlePath);
				PGraphics pg = scp.nodeList.get(i).nozzleList.get(j).sysB;
				pg.beginDraw();
				pg.colorMode(PConstants.HSB, 360, 100, 100);
				pg.noStroke();
				int invert=0;
				if(i%2==0){
					invert = 100;
				}
				pg.fill(glow.hue, glow.saturation, Math.abs(invert-glow.brightness));
				pg.rect(0, 0, pg.width, pg.height);
				pg.endDraw();
			}
		}
	}

}
