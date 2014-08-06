import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class UpDown {

	PApplet p;
	Node n;
	ArrayList<UpDownParticle> particleList = new ArrayList<UpDownParticle>();
	ColorFade cf;
	int timer, speed;
	private boolean upDownSpeedBol = false;
	private int UP_DOWN_MIN_SPEED = 10;
	private int UP_DOWN_MAX_SPEED = 200;

	public UpDown(PApplet p, Node n){
		this.p = p;
		this.n = n;

		for(int i=0; i<n.nozzleList.size(); i++){
			particleList.add(new UpDownParticle(i+1));
		}
	}

	public UpDown(PApplet p, Node n, ColorFade cf, int speed){
		this.p = p;
		this.n = n;
		this.cf = cf;
		this.speed = speed;
		timer = p.millis();

		for(int i=0; i<n.nozzleList.size(); i++){
			particleList.add(new UpDownParticle(i*2));
		}
	}

	public void draw(){

		/*if(p.frameCount%10==0){
		if(upDownSpeedBol){
			speed--;
		}else{
			speed++;
		}
		if(speed==UP_DOWN_MIN_SPEED){
			upDownSpeedBol = false;
		}
		if(speed>=UP_DOWN_MAX_SPEED){
			upDownSpeedBol = true;
		}
		}*/

		for(int i=0; i<n.nozzleList.size(); i++){
			PGraphics pg = n.nozzleList.get(i).sysA;
			pg.beginDraw();
			pg.colorMode(PConstants.HSB, 360, 100, 100);
			pg.noStroke();
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,255);
			pg.rect(0, particleList.get(i).y, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,100);
			pg.rect(0, particleList.get(i).y+1, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,80);
			pg.rect(0, particleList.get(i).y+2, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,60);
			pg.rect(0, particleList.get(i).y+3, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,20);
			pg.rect(0, particleList.get(i).y+4, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,15);
			pg.rect(0, particleList.get(i).y+5, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,12);
			pg.rect(0, particleList.get(i).y+6, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,10);
			pg.rect(0, particleList.get(i).y+7, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,5);
			pg.rect(0, particleList.get(i).y+8, pg.width, 1);

			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,200);
			pg.rect(0, particleList.get(i).y-1, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,180);
			pg.rect(0, particleList.get(i).y-2, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,150);
			pg.rect(0, particleList.get(i).y-3, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,120);
			pg.rect(0, particleList.get(i).y-4, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,100);
			pg.rect(0, particleList.get(i).y-5, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,80);
			pg.rect(0, particleList.get(i).y-6, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,60);
			pg.rect(0, particleList.get(i).y-7, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,20);
			pg.rect(0, particleList.get(i).y-8, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,15);
			pg.rect(0, particleList.get(i).y-9, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,12);
			pg.rect(0, particleList.get(i).y-10, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,10);
			pg.rect(0, particleList.get(i).y-11, pg.width, 1);
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,5);
			pg.rect(0, particleList.get(i).y-12, pg.width, 1);


			/*if(particleList.get(i).y>5){
			pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,200);
			pg.rect(0, 4, pg.width, 1);	
			}
			if(particleList.get(i).y<0){
				pg.fill(cf.hue-5*i, cf.saturation, cf.brightness,200);
				pg.rect(0, 0, pg.width, 1);	
				}*/

			pg.endDraw();


			PGraphics pg2 = n.nozzleList.get(i).sysB;
			pg2.beginDraw();
			pg2.colorMode(PConstants.HSB,360,100,100);
			if(particleList.get(i).down){
				particleList.get(i).alpha -= 5;
			}else{
				particleList.get(i).alpha += 5;
			}
			//System.out.println(particleList.get(i).alpha);
			pg2.fill(255,particleList.get(i).alpha);
			pg2.noStroke();
			pg2.rect(0, 0, pg2.width, pg2.height);
			pg2.endDraw();

		}
		int current = p.millis();
		if(current-timer>speed){	



		for(int i=0; i<particleList.size(); i++){
				//if(p.frameCount%3==0){
				particleList.get(i).update();


		}
		timer = current;
		}

	}


	public void draw2(){
		for(int i=0; i<n.nozzleList.size(); i++){
			PGraphics pg = n.nozzleList.get(i).sysA;
			pg.beginDraw();
			pg.noStroke();
			pg.fill(255,255);
			pg.rect(0, particleList.get(i).y, pg.width, 1);
			pg.fill(255,200);
			pg.rect(0, particleList.get(i).y+1, pg.width, 1);
			pg.fill(255,180);
			pg.rect(0, particleList.get(i).y+2, pg.width, 1);
			pg.fill(255,150);
			pg.rect(0, particleList.get(i).y+3, pg.width, 1);
			pg.fill(255,120);
			pg.rect(0, particleList.get(i).y+4, pg.width, 1);
			pg.fill(255,100);
			pg.rect(0, particleList.get(i).y+5, pg.width, 1);
			pg.fill(255,80);
			pg.rect(0, particleList.get(i).y+6, pg.width, 1);
			pg.fill(255,60);
			pg.rect(0, particleList.get(i).y+7, pg.width, 1);
			pg.fill(255,20);
			pg.rect(0, particleList.get(i).y+8, pg.width, 1);
			pg.fill(255,15);
			pg.rect(0, particleList.get(i).y+9, pg.width, 1);
			pg.fill(255,12);
			pg.rect(0, particleList.get(i).y+10, pg.width, 1);
			pg.fill(255,10);
			pg.rect(0, particleList.get(i).y+11, pg.width, 1);
			pg.fill(255,5);
			pg.rect(0, particleList.get(i).y+12, pg.width, 1);

			pg.fill(255,200);
			pg.rect(0, particleList.get(i).y-1, pg.width, 1);
			pg.fill(255,180);
			pg.rect(0, particleList.get(i).y-2, pg.width, 1);
			pg.fill(255,150);
			pg.rect(0, particleList.get(i).y-3, pg.width, 1);
			pg.fill(255,120);
			pg.rect(0, particleList.get(i).y-4, pg.width, 1);
			pg.fill(255,100);
			pg.rect(0, particleList.get(i).y-5, pg.width, 1);
			pg.fill(255,80);
			pg.rect(0, particleList.get(i).y-6, pg.width, 1);
			pg.fill(255,60);
			pg.rect(0, particleList.get(i).y-7, pg.width, 1);
			pg.fill(255,20);
			pg.rect(0, particleList.get(i).y-8, pg.width, 1);
			pg.fill(255,15);
			pg.rect(0, particleList.get(i).y-9, pg.width, 1);
			pg.fill(255,12);
			pg.rect(0, particleList.get(i).y-10, pg.width, 1);
			pg.fill(255,10);
			pg.rect(0, particleList.get(i).y-11, pg.width, 1);
			pg.fill(255,5);
			pg.rect(0, particleList.get(i).y-12, pg.width, 1);


			if(particleList.get(i).y>5){
			pg.fill(255,200);
			pg.rect(0, 4, pg.width, 1);	
			}
			if(particleList.get(i).y<0){
				pg.fill(255,200);
				pg.rect(0, 0, pg.width, 1);	
				}
			if(p.frameCount%10==0){
			particleList.get(i).update();
			}
			pg.endDraw();
		}
	}


	public void draw3(){
		for(int i=0; i<n.nozzleList.size(); i++){
			PGraphics pg = n.nozzleList.get(i).sysA;
			pg.beginDraw();
			pg.noStroke();
			pg.fill(255,255);
			pg.rect(0, particleList.get(i).y, pg.width, 1);
			pg.fill(255,200);
			pg.rect(0, particleList.get(i).y+1, pg.width, 1);
			pg.fill(255,180);
			pg.rect(0, particleList.get(i).y+2, pg.width, 1);
			pg.fill(255,150);
			pg.rect(0, particleList.get(i).y+3, pg.width, 1);
			pg.fill(255,120);
			pg.rect(0, particleList.get(i).y+4, pg.width, 1);
			pg.fill(255,100);
			pg.rect(0, particleList.get(i).y+5, pg.width, 1);
			pg.fill(255,80);
			pg.rect(0, particleList.get(i).y+6, pg.width, 1);
			pg.fill(255,60);
			pg.rect(0, particleList.get(i).y+7, pg.width, 1);
			pg.fill(255,20);
			pg.rect(0, particleList.get(i).y+8, pg.width, 1);
			pg.fill(255,15);
			pg.rect(0, particleList.get(i).y+9, pg.width, 1);
			pg.fill(255,12);
			pg.rect(0, particleList.get(i).y+10, pg.width, 1);
			pg.fill(255,10);
			pg.rect(0, particleList.get(i).y+11, pg.width, 1);
			pg.fill(255,5);
			pg.rect(0, particleList.get(i).y+12, pg.width, 1);

			pg.fill(255,200);
			pg.rect(0, particleList.get(i).y-1, pg.width, 1);
			pg.fill(255,180);
			pg.rect(0, particleList.get(i).y-2, pg.width, 1);
			pg.fill(255,150);
			pg.rect(0, particleList.get(i).y-3, pg.width, 1);
			pg.fill(255,120);
			pg.rect(0, particleList.get(i).y-4, pg.width, 1);
			pg.fill(255,100);
			pg.rect(0, particleList.get(i).y-5, pg.width, 1);
			pg.fill(255,80);
			pg.rect(0, particleList.get(i).y-6, pg.width, 1);
			pg.fill(255,60);
			pg.rect(0, particleList.get(i).y-7, pg.width, 1);
			pg.fill(255,20);
			pg.rect(0, particleList.get(i).y-8, pg.width, 1);
			pg.fill(255,15);
			pg.rect(0, particleList.get(i).y-9, pg.width, 1);
			pg.fill(255,12);
			pg.rect(0, particleList.get(i).y-10, pg.width, 1);
			pg.fill(255,10);
			pg.rect(0, particleList.get(i).y-11, pg.width, 1);
			pg.fill(255,5);
			pg.rect(0, particleList.get(i).y-12, pg.width, 1);


			/*if(particleList.get(i).y>5){
			pg.fill(255,200);
			pg.rect(0, 4, pg.width, 1);	
			}
			if(particleList.get(i).y<0){
				pg.fill(255,200);
				pg.rect(0, 0, pg.width, 1);	
			}*/
			if(p.frameCount%5==0){
			particleList.get(i).update();
			}
			pg.endDraw();
		}
	}




	class UpDownParticle {

		int y, alpha;
		boolean down = false;

		public UpDownParticle(int y){
			this.y = y;
			down = true;
			alpha = 255;
		}

		public void update(){
			if(down){
				y--;
			}else{
				y++;
			}
			if(y==-15){
				down = false;
			}
			if(y>=15){
				down = true;
			}

			if(alpha==-15){
				alpha = 0;
			}

		}
	}

}