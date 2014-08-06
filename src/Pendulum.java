import processing.core.PConstants;
import processing.core.PGraphics;


public class Pendulum implements Effect{
	
	double speed = 1;
	int timer = 30;
	int width = 50;
	int x = 0;
	int y = 0;
	private Layer nozzleLayer;
	private PGraphics pg;
	private boolean dead = false;
	private boolean value = false;
	private int hue1;
	private int hue2;
	private boolean start = false;
	private Pavillon scp;
	private boolean fadeBack;
	
	public Pendulum(Pavillon scp, Layer nozzleLayer){
		this.scp= scp;
		this.nozzleLayer = nozzleLayer;
		pg = nozzleLayer.getLayer();
		hue1 = 270;
		hue2 = 30;
	}
	
	public void drawHorizontal(){
		pg.beginDraw();
		pg.clear();
		pg.colorMode(PConstants.HSB, 360, 100, 100,255);
		pg.noStroke();
		if(value){
		for(int i=0; i<pg.width-x; i++){
		pg.fill(i,100,100,100);
		pg.rect((((pg.width/2)-(pg.width-x)/2))+i, y, 1, width);
		}
		}else{
		for(int i=0; i<pg.width-x; i++){
		pg.fill(pg.width-x-i,100,100,100);
		pg.rect((((pg.width/2)-(pg.width-x)/2))+i, y, 1, width);
		}	
		}
		nozzleLayer.add();
		speed = speed + 0.5;
		
		x = x + (int)speed;

		  // if we've hit the floor... 
		  if (x > pg.width) {
		    // set the position to be on the floor
		    x = pg.width; 
		    // and make the y speed 90% of what it was, 
		    // but in the opposite direction
		    speed = speed * -0.9;
		    System.out.println("GO1");
		    value = !value;
		  } 
		
		timer++;
		if(timer==500){
			dead = true;
		}
	}
	
	public void drawVertical(){
		pg.beginDraw();
		pg.clear();
		pg.colorMode(PConstants.HSB, 360, 100, 100,255);
		pg.noStroke();
		//for(int i=0; i<pg.height-x; i++){
		pg.fill(0,100,100,100);
		pg.rect(x, (((pg.height/2)-(pg.height-y)/2)), width, pg.height-y);
		//}
		nozzleLayer.add();
		speed = speed + 0.5;
		
		y = y + (int)speed;

		  // if we've hit the floor... 
		  if (y > pg.height) {
		    // set the position to be on the floor
		    y = pg.height; 
		    // and make the y speed 90% of what it was, 
		    // but in the opposite direction
		    speed = speed * -0.9;
		    value = !value;
		  } 

		    System.out.println("GO1: "+speed+" "+pg.width+" "+pg.height);

		timer++;
		if(timer==400){
			dead = true;
		}
	}
	
	public void drawTubePendulum(){
		pg.beginDraw();
		pg.clear();
		pg.colorMode(PConstants.HSB, 360, 100, 100,255);
		pg.noStroke();
		if(value){
		for(int i=0; i<pg.width-x; i++){
		pg.fill(220,100,100,100);
		pg.rect(0+x, y, pg.width, width);
		}
		}else{
		for(int i=0; i<pg.width-x; i++){
		pg.fill(0,100,100,100);
		pg.rect(0+x, y, pg.width, width);
		}	
		}
		nozzleLayer.add();
		speed = speed + 0.5;
		
		x = x + (int)speed;

		  // if we've hit the floor... 
		  if (x > pg.width) {
		    // set the position to be on the floor
		    x = pg.width; 
		    // and make the y speed 90% of what it was, 
		    // but in the opposite direction
		    speed = speed * -0.9;
		    System.out.println("GO1");
		    value = !value;
		  } 
		
		timer++;
		if(timer==200){
			dead = true;
		}
	}
	
	
	public void drawDTubePendulum(){
		pg.beginDraw();
		pg.clear();
		pg.colorMode(PConstants.HSB, 360, 100, 100,100);
		pg.noStroke();
		if(value){
		System.out.println("VALUE");
		for(int i=0; i<pg.width-x; i++){
		pg.fill(hue1-i,80,100,timer*3);
		pg.rect(0+x+i, y, 1, width);
		}
		}else{
		System.out.println("NOTVALUE");
		for(int i=0; i<pg.width-x; i++){
		pg.fill(hue2-i,80,100,timer*3);
		pg.rect(i, y, 1, width);
		}	
		}
		nozzleLayer.add();
		speed = speed + 0.2;
		
		x = x + (int)speed;

		  // if we've hit the floor... 
		  if (x > pg.width) {
		    // set the position to be on the floor
		    x = pg.width; 
		    // and make the y speed 90% of what it was, 
		    // but in the opposite direction
		    speed = speed * -0.93;
		    System.out.println("GO1");
		    value = !value;
			timer--;

		  } 
		
		  if(timer<=8){
			  fadeBack = true;
		  }
		if(timer<=0){
			dead = true;
		}
	}
	
	
	public void draw2DTubePendulum(){
		pg.beginDraw();
		pg.clear();
		pg.colorMode(PConstants.HSB, 360, 100, 100,100);
		pg.noStroke();
		if(value){
		for(int i=0; i<pg.width-x; i++){
		pg.fill(hue1+i,100,100,timer*3);
		pg.rect(0+x+i, y, 1, width);
		}
		}else{
		for(int i=0; i<pg.width-x; i++){
		pg.fill(hue2-i,100,100,timer*3);
		pg.rect(i, y, 1, width);
		}	
		}
		nozzleLayer.add();
		speed = speed + 0.2;
		
		x = x + (int)speed;

		  // if we've hit the floor... 
		  if (x > pg.width) {
		    // set the position to be on the floor
		    x = pg.width; 
		    // and make the y speed 90% of what it was, 
		    // but in the opposite direction
		    speed = speed * -1;
		    System.out.println("GO1");
		    value = !value;
			if(timer>6){
		    timer=timer-3;
			}else{
				timer--;
			}
			

		  } 
		  
			System.out.println("Timer "+timer);

		
		if(timer<=0){
			dead = true;
		}
	}
	
	
	public boolean isDead(){
		System.out.println("isDead "+dead);
		System.out.println("Start  "+start);
		return dead;
	}

	@Override
	public void draw() {
		System.out.println("DRAW");

		if(start){
		System.out.println("DRAW");
		// TODO Auto-generated method stub
		//draw2DTubePendulum();
		drawDTubePendulum();
		//drawHorizontal();
		}
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		start = true;
		
	}

	@Override
	public boolean fadeBack() {
		// TODO Auto-generated method stub
		return fadeBack;
	}

}
