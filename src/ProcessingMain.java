/*
 * Created by Marius Hoggenmüller on 04.02.14
 * Copyright (c) 2014 Marius Hoggenmüller. All rights reserved.
 * 
 */


import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

import java.awt.Color;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import codeanticode.gsvideo.GSMovie;
import controlP5.CheckBox;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.ControllerGroup;
import controlP5.Textfield;
import controlP5.Textlabel;
import de.looksgood.ani.Ani;
import processing.core.*;
import processing.data.XML;
import processing.serial.*;

public class ProcessingMain extends PApplet {
	
	private static final long serialVersionUID = 1L;
	
	//Network settings
	private final String IP = "224.1.1.1";
	private final int PORT = 5026;
	private final int[] CONTROLLER_ID = {1, 2};
	
	//Init Nozzles, Node, Sculpture Objects
	private final int NODE1_LEDS[] = {75,60,75,60,60,75,60,75};
	private final int NODE2_LEDS[] = {75,75,75,75,60,60,75,75,60,90};
	private final int NODE3_LEDS[] = {75,75,75,60,90,75,75,60,75,75,75};
	private final int NODE4_LEDS[] = {60,75,60,75,60,75,75,60};
	private final int NODE5_LEDS[] = {60,90,75,60,75,75,75,90,75,75,60};
	private final int NODE6_LEDS[] = {75,75,45,90,60,60,90,75,60};
	private final int NODE7_LEDS[] = {75,75,75,75,60,60,60,60,90};
		
	Node node1, node2, node3, node4, node5, node6, node7;

	Pavillon scp;

	//Variables for GUI
	ControlP5 cp5;
	private CheckBox checkbox;
	float checkbox_array[] = {0,0,0,0,0,0,0,0,0,0,0,0,1};
	//boolean activeArray [] = {false, false, false, false, false, false, false, false, false, false, false, false, false};
	
	//Arduino Communication
	static String ARDUINO_DEVICE = "/dev/tty.usbmodem1421";
	static int lf = 10; // Linefeed in ASCII
	String myString = null; // Serial Output String
	Serial myPort; // Serial port you are using

	//Sensors
	private ArrayList<Sensor> sensorList = new ArrayList<Sensor>();
	private static int SENSORS = 5;
	private int[] sMap = {2,3,5,7,5};
	private int hoursWi = 22;
	private int minutesWi = 00;
	private int hoursFlash = 22;
	private int minutesFlash = 00;
	private Date dFlash = new Date();
	private Date dWi = new Date();
	private int hoursOff = 4;
	private Date dateOff = new Date();
	private int hoursOn = 17;
	private Date dateOn = new Date();
	
	
	private boolean allWi = true;
	private boolean allFlash = true;
	private boolean once = false;

	//Animation Stuff
	private PGraphics pg, pg2;

	private int counter2;


	private float y=0;

	private ArrayList<hsvGradient> hsv1 = new ArrayList<hsvGradient>();

	private int startHue;
	
	//DrawPathApplication
	private ArrayList<DrawPath> dpList= new ArrayList<DrawPath>();
	int max_path = 1;
	
	//Shine
	private static int SHINE_HOR_MAX = 1;
	private static int SHINE_VERT_MAX = 8;
	//private ArrayList<HorizontalShine> horizontalShineList = new ArrayList<HorizontalShine>();

	private LinkedList<Nozzle> nozzlePath;

	int h=0;
	
	private GSMovie m;

	private ArrayList<discoDot> discoDotList = new ArrayList<discoDot>();
	
	double k = 1.0;

	private RandomLampManager rlm, rlm2;

	private ArrayList<HorizontalMove> horizontalMoveList = new ArrayList<HorizontalMove>();

	private ColorFade cfYellow;

	private ColorFadeList cfList = new ColorFadeList(this);

	private NozzleLayer nLayer;

	private PGraphics layerGraphics;
	
	double x=0.1;
	double acc=0.05;

	private ColorFade cfFlower;

	private int z = 0;

	private ColorFade lampFade;

	private ColorFade sTube;
	
	private ArrayList<SimpleTube> sTubeList = new ArrayList<SimpleTube>();

	private ColorFade sTube2;

	private ColorFadeList colorTubeList = new ColorFadeList(this);

	private ColorFade hueFade;

	private NozzleLayer lampLayer;

	private NozzleLayer flowerLayer;

	private PGraphics flowerLayerGraphics;

	private ColorFade pushColor;

	private boolean up = true;

	private int alpha = 50;

	private int value1;

	private int value2;

	private boolean alphaUP = true;

	private ArrayList<Pendulum> pendulumList = new ArrayList<Pendulum>();

	private ArrayList<Strobo> stroboList = new ArrayList<Strobo>();

	private int[] flashArray = new int[5];

	private int flashTimer;

	private boolean flashActive;

	private ControlP5 cp5Sensor;
	private ArrayList<CheckBox> checkBoxSensorList = new ArrayList<CheckBox>();
	private float[][] checkBoxSensorArray= {{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};
    private boolean flashActiveArray[] = {true,true,true,true,true};
    private boolean wiActiveArray[] = {true,true,true,true,true};
    
    private ArrayList<ArrayList<Effect>> effectList = new ArrayList<ArrayList<Effect>>();
    
    private ArrayList<Node> nodeList = new ArrayList<Node>();

	private ArrayList<Lamp> lampList = new ArrayList<Lamp>();

	private ColorFade backGround;
	
	private int[] Farbe = new int[3];

	private TubeAnimation tubeAnimation; 
	
	
	
	
	
	float Xx = 256;
	float Yy = 256;
	int diameter = 50;
	
	
	
	
	
	/*
	 * TUBE ANIMATION VARIABLES
	 */
	int background_color;
	Tween t1;

	//TUBE ANIMATION SETUP
	public void setupTubeAnimation(){
		Motion.setup(this);

		  t1 = new Tween(100)
		  .addColor(this, "background_color", color(60, 0, 0))
		  .play();
	}
	
	//TUBE ANIMATION DRAW
	public void drawTubeAnimation(){
		for(Nozzle n : scp.nozzleList){
			PGraphics pg = n.sysA;
			pg.beginDraw();
			pg.colorMode(HSB, 360, 100, 100);
			pg.noStroke();
			pg.fill(background_color);
			pg.rect(0, 0, pg.width, pg.height);
			pg.endDraw();
		}
		
		if(!t1.isPlaying()){
			int hue = (int) random(0,360);
			int saturation = (int) random(0,100);
			int brightness = (int) random(0,100);
			t1.getColor("background_color").setEnd(color(hue, saturation, brightness));
			t1.play();
		}
	}
	
	Tube tube;
	int dimm = 0;
	
	//ANIMATION CHANGER
	public void AnimationChanger(){
		nozzlePath = scp.createRowPath(0,65);
		  //nozzlePath = createRandomPath(0,8,58,65);
		  NozzleLayer nLayer = new NozzleLayer(this, scp, nozzlePath);
		tube = new Tube(this, nLayer, color(0,0,0), 1000, 1);
	}
	
	public void drawAnimationChanger(){
		System.out.println("ANIMATION-CHANGER");
		
		scp.dimm(dimm);
		dimm++;
	}
	
	/*
	 * UPDOWN-ANIMATION
	 */
	private ArrayList<UpDown> upDown = new ArrayList<UpDown>();
	private ColorFade upDownCl;
	private boolean upDownSpeedBol = false;
	private int UP_DOWN_MAX_SPEED = 150;
	private int UP_DOWN_MIN_SPEED = 50;

	private boolean animationChanger;

	private ArrayList<AnimationChanger> animationChanger1 = new ArrayList<AnimationChanger>();

	private ArrayList<Firework> fWList = new ArrayList<Firework>();

	private ArrayList<InteractionChanger> interactionChanger;

	private ColorFade cfYellow2;

	private boolean yelBlu;

	private int yelBlutimer;

	private ColorFade c1;

	private ColorFade t2;

	private ColorFade t3;

	private ColorFade t4;

	private ColorFade t5;

	private ColorFade c2;

	private ColorFade c3;

	private ColorFade c4;

	private ColorFade c5;

	private int hueBreath;

	private int speedBreath;

	private ColorFade cb1;

	private ColorFade cb2;

	private ColorFade cb3;

	private ColorFade cb4;

	private ColorFade cb5;

	private int hueBBreath;

	private FineTube FineTube;

	private ColorFade ftbg;

	private int ftbgHue;

	private ColorFade ft1;

	private int ftHue;

	private ColorFade ft2;

	private int ftHue2;

	private XML xml;

	private ColorFade shadowColor;

	private ArrayList<ShadowColorTube> ShadowTube = new ArrayList<ShadowColorTube>();

	private boolean manuell = false;

	private int bigFineTube;

	private ColorFade shadowInnerColor;

	private boolean hueDown;

	private Breath ShadowBreath;

	private int shadowSpeed = 5000;

	private ColorFade shadowInnerColor3;

	private ColorFade shadowInnerColor4;

	private ArrayList<NodeDrop> nodeDropList = new ArrayList<NodeDrop>();

	private UpDown uD;

	private ColorFade hFT;

	private HorizontalFineTube horFineTube;

	private ArrayList<TopGlow> topGlowList = new ArrayList<TopGlow>();

	private ColorFade shadowInnerColor5;

	private Breath2 ShadowBreath2;

	private Jeff jeffEffect;

	private Zünden zünden;

	private ColorFade redGlow;

	

	//Initiate as Application
	public static void main(String args[]) {
	    PApplet.main(new String[] { "--present", "ProcessingMain" });
	  }
	
	public void setup() {
		
		System.out.println(System.getProperty("java.library.path"));
		
		size(1200,750);
	
		initArduino();
		  
		//frameRate(10);
		
		//Init GUI with Textfields, Buttons
		cp5 = new ControlP5(this);

		cp5.addTextfield("IP")
		   .setPosition(10,10)
		   .setSize(50,15)
		   .setColor(color(255,255,255))
		   .setColorCaptionLabel(color(0,0,0))
		   .setText(IP);
		;
		
		cp5.addTextfield("PORT")
		   .setPosition(70,10)
		   .setSize(30,15)
		   .setColor(color(255,255,255))
		   .setColorCaptionLabel(color(0,0,0))
		   .setText(Integer.toString(PORT));
		;
		
		cp5.addTextfield("ID-1")
		   .setPosition(110,10)
		   .setSize(20,15)
		   .setColor(color(255,255,255))
		   .setColorCaptionLabel(color(0,0,0))
		   .setText(Integer.toString(CONTROLLER_ID[0]));
		;
		
		cp5.addTextfield("ID-2")
		   .setPosition(140,10)
		   .setSize(20,15)
		   .setColor(color(255,255,255))
		   .setColorCaptionLabel(color(0,0,0))
		   .setText(Integer.toString(CONTROLLER_ID[1]));
		;
		
		
		cp5.addButton("OK")
		   .setPosition(170,10)
		   .setSize(20,15)
		;
		
		cp5.addButton("SETUP")
			.setPosition(1050, 10)
			.setSize(20,15)
		;
		
		checkbox = cp5.addCheckBox("checkBox").setPosition(220, 10)
				.setColorForeground(color(120)).setColorActive(color(200))
				.setColorLabel(color(0,0,255)).setSize(15, 15).setItemsPerRow(7)
				.setSpacingColumn(45).setSpacingRow(20).addItem("Black", 0).addItem("YellowBlue", 50)
				.addItem("Breath1", 100).addItem("Breath2", 150).addItem("FineTube", 200)
				.addItem("Stars", 250).addItem("Shadows", 300).addItem("Shadows2", 350)
				.addItem("Shadows3", 400).addItem("Shadows4", 450).addItem("Shadows5", 500);		
		
		
		cp5.addToggle("Manuell")
	     .setPosition(900,10)
	     .setSize(30,15)
	     .setValue(false)
	     .setMode(ControlP5.SWITCH)
	     ;
		
		

		pg = createGraphics(12, 5);
		pg2 = createGraphics(12, 5);
		
		node1 = new Node(this);
		node2 = new Node(this);
		node3 = new Node(this);
		node4 = new Node(this);
		node5 = new Node(this);
		node6 = new Node(this);
		node7 = new Node(this);

		node1.add(NODE1_LEDS);
		node2.add(NODE2_LEDS);
		node3.add(NODE3_LEDS);
		node4.add(NODE4_LEDS);
		node5.add(NODE5_LEDS);
		node6.add(NODE6_LEDS);
		node7.add(NODE7_LEDS);
		
		nodeList.add(node1);
		nodeList.add(node2);
		nodeList.add(node3);
		nodeList.add(node4);
		nodeList.add(node5);
		nodeList.add(node6);
		nodeList.add(node7);
		
		scp = new Pavillon(this, IP, PORT, CONTROLLER_ID);
		scp.add(node1, node2, node3, node4, node5, node6, node7);
		scp.setAdj();
		scp.start();
		
		cp5Sensor = new ControlP5(this);
		
		for(int i=0; i<SENSORS; i++){
			sensorList.add(new Sensor(this, i+1, 1050, 50+i*130));
			String name = "checkBoxSensor"+i;
			String nameF = "Flash"+i;
			String nameW = "Wi"+i;
			checkBoxSensorList.add(cp5Sensor.addCheckBox(name).setPosition(1050, 55+i*130)
					.setColorForeground(color(120)).setColorActive(color(200))
					.setColorLabel(color(0,0,0)).setSize(15, 15).setItemsPerRow(7)
					.setSpacingColumn(45).setSpacingRow(15).addItem(nameF, 0)
					.addItem(nameW, 50));
		
		}
				
		
		AnimationChanger();
		
		
		counter2=0;
								
		startHue = 120;

		for(Nozzle n : scp.nozzleList){
			  hsv1.add(new hsvGradient(this, n, startHue-2*n.id, 120, 120));
		}
		
		m = new GSMovie(this, "honess.avi");
		m.loop();
		
		
		setupPathosLight();
		setupYellowBlue();
		setupHueBackground();		
		setUpLamp();
		setupFlower();
		setupSimpleTube();
		setupPush();
		setupBackGroundContrast();
		//setupCompTube();
		setupUpDownAnimation();
		setupTubeAnimation();
		setupBreath();
		setupBreath2();
		setupFineTube();
		setupShadows();
		setupShadows3();
		setupShadows4();
		setupHorizontalFineTube();
		setupShadows5();
		
		//frameRate(10);
		cfList.start();
		
		nozzlePath = scp.createPath(4,3,2,1,0);
		HLayer nLayer = new HLayer(this, scp, nozzlePath);
		stroboList.add(new Strobo(this, nLayer, 25));
		
		for(int i=0; i<7; i++){
			effectList.add(new ArrayList<Effect>());
		}
		
		tubeAnimation = new TubeAnimation(this, scp, cfList);
		tubeAnimation.setupMode2();
		
		Motion.setup(this);
		
		interactionChanger = new ArrayList<InteractionChanger>();
		
		  Ani.init(this);
		
		  Ani.to(this, (float) 1.0, "Xx", mouseX, Ani.QUINT_IN);
		  Ani.to(this, (float) 1.0, "Yy", mouseY, Ani.QUINT_IN);
		  
		  nozzlePath = scp.createNodePath(nodeList.get(0));
		  NozzleLayer nozzleLayer = new NozzleLayer(this, scp, nozzlePath);
		  //FineTube = new FineTube(this, nozzleLayer, c1);
		  
		  FineTube = new FineTube(this, scp, nozzlePath, nozzleLayer, cb1, cfList);
		  
		  nozzlePath = scp.createNodePath(nodeList.get(0));
		  
		  NozzleLayer nozzleLayer2 = new NozzleLayer(this, scp, nozzlePath);
		  
		  
		//Arduino ein/ausschalten
		
		  
		  dWi.setHours(hoursWi);
		  dWi.setMinutes(minutesWi);
		  dFlash.setHours(hoursFlash);
		  dFlash.setMinutes(minutesFlash);
		  
		  
		  //ArduinoZeitschaltUhr();
		  
		  nodeDropList.add(new NodeDrop(this, scp, node1, cfList));
		  
		  uD = new UpDown(this, node1, ft1, 5000);
		  		  
		  //println("Time :"+d.getHours()+" "+d2.getHours());
		  
		  nozzlePath = scp.createRowPath(0, 65);
		  NozzleLayer nozzleLayer3 = new NozzleLayer(this, scp, nozzlePath);
		  jeffEffect = new Jeff(this, cfList, nozzleLayer3, 500);
		  
		  nozzlePath = scp.createNodePath(nodeList.get(0));
		  NozzleLayer nozzleLayerB = new NozzleLayer(this, scp, nozzlePath);
		  zünden = new Zünden(this, nozzleLayerB, cfList);
	}
		
	public void draw() {
		
		//ArduinoZeitschaltUhr();

		
		colorMode(HSB,360,100,100);
		
		//FLASH
		if(abs(millis()-flashTimer)>=100 && flashActive){
			int minValue = flashArray[0];  
			int minPos = 0;
			  for(int i=1;i<flashArray.length;i++){  
			    if(flashArray[i] < minValue){  
			      minValue = flashArray[i];
			      minPos = i;
			    }  
			  }  
			for(int i=0; i<flashArray.length; i++){
				flashArray[i] = 1023;
			}
			flashActive = false;
			//System.out.println("FLASH mit "+(minPos+1)+" "+minValue);
			sensorList.get(minPos).setFlash();
		}
		
		for(Sensor s : sensorList){
			//System.out.println("SENSOR_DISABLE: "+s.getID()+" WI: "+s.inactiveWi+" Flash: "+s.inactiveFlash);
			//System.out.println("SENSOR: "+s.getID()+" STATE: "+s.getState());
			if(s.getState() && (!s.inactiveWi | !s.inactiveFlash)){
				if(checkbox_array[1]==1){
					if(effectList.get(s.getID()-1).isEmpty()){
					String name = "Interaktion 1";
					int sensorID = s.getID()-1;
					int nodeID = sMap[sensorID];
					writeXML(name, sensorID, nodeID);
					nozzlePath = scp.createNodePath(nodeList.get(nodeID-1));
					NozzleLayer nozzleLayer = new NozzleLayer(this, scp, nozzlePath);
					int hue = Integer.valueOf(cfYellow.hue);
					int sat = Integer.valueOf(cfYellow.saturation);
					int bri = Integer.valueOf(cfYellow.brightness);
					effectList.get(s.getID()-1).add(new Emphasize(this, nozzleLayer, hue, sat, bri, cfList));
					}
					s.setState(false);
				}
				else if(checkbox_array[2]==1){
					String name = "Interaktion 2";
					int sensorID = s.getID()-1;
					int nodeID = sMap[sensorID];
					writeXML(name, sensorID, nodeID);
					nozzlePath = scp.createNodePath(nodeList.get(nodeID-1));
					NozzleLayer nozzleLayer = new NozzleLayer(this, scp, nozzlePath);
					//PGraphics pg = nozzleLayer.getLayer();
					effectList.get(s.getID()-1).add(new TopGlow(this, nozzleLayer, cfList, 2, 255));
					nozzleLayer.add();
					s.setState(false);
				}
				else if(checkbox_array[3]==1){
					if(effectList.get(s.getID()-1).isEmpty()){
					String name = "Interaktion 3";
					int sensorID = s.getID()-1;
					int nodeID = sMap[sensorID];
					writeXML(name, sensorID, nodeID);
					nozzlePath = scp.createNodePath(nodeList.get(nodeID-1));
					NozzleLayer nozzleLayer = new NozzleLayer(this, scp, nozzlePath);
					int nId = (s.getID()-1)*8;
					System.out.println("NID: "+nId);
					effectList.get(s.getID()-1).add(new Blob2(this, nozzleLayer, nId, cb1, cfList));
					}
					s.setState(false);
				}
				else if(checkbox_array[4]==1){
					if(effectList.get(s.getID()-1).isEmpty()){
					String name = "Interaktion 4";
					int sensorID = s.getID()-1;
					int nodeID = sMap[sensorID];
					writeXML(name, sensorID, nodeID);
					if(bigFineTube<10){
					nozzlePath = scp.createNodePath(nodeList.get(nodeID-1));
					bigFineTube++;
					}else{
					nozzlePath = scp.createNodeToNodeNozzle(nodeList.get(nodeID-1), node1);
					bigFineTube=0;
					}
					NozzleLayer nozzleLayer = new NozzleLayer(this, scp, nozzlePath);
					effectList.get(s.getID()-1).add(new FineTube(this,scp,nozzlePath,nozzleLayer,ft2, cfList));
					}
					s.setState(false);

				}
				else if(checkbox_array[5]==1){
					if(effectList.get(s.getID()-1).isEmpty()){
					String name = "Interaktion 5";
					int sensorID = s.getID()-1;
					int nodeID = sMap[sensorID];
					writeXML(name, sensorID, nodeID);
					nozzlePath = scp.createNodePath(nodeList.get(nodeID-1));
					NozzleLayer nozzleLayer = new NozzleLayer(this, scp, nozzlePath);
					effectList.get(s.getID()-1).add(new Stars(this,nozzleLayer,160, cfList));
					effectList.get(s.getID()-1).add(new Zünden(this, nozzleLayer, cfList));
					}
					s.setState(false);
				}
					if(effectList.get(s.getID()-1).isEmpty()){
					String name = "Interaktion 5";
					int sensorID = s.getID()-1;
					int nodeID = sMap[sensorID];
					writeXML(name, sensorID, nodeID);
					//nozzlePath = scp.createNodePath(nodeList.get(nodeID-1));
					nozzlePath = scp.createNodeToNodeNozzle(nodeList.get(nodeID-1), node1);
					NozzleLayer nozzleLayer = new NozzleLayer(this, scp, nozzlePath);
					effectList.get(s.getID()-1).add(new Stars(this,nozzleLayer,160, cfList));
					effectList.get(s.getID()-1).add(new Zünden(this, nozzleLayer, cfList));
					}
					s.setState(false);
				
			}
		}

		 
		  for(AnimationChanger aC : animationChanger1){
		  if(!aC.finished){
			  aC.draw();
		  }
		  } 
		  
		  drawShadows5();

		  
		  if(checkbox_array[0]==1){
			  scp.clearSysA();
		  }
		  else if(checkbox_array[1]==1){
			  drawYellowBlue();
		  }else if(checkbox_array[2]==1){
			  drawBreath();
		  }else if(checkbox_array[3]==1){
			  drawBreath2();
			  
		  }
		  
		  else if(checkbox_array[4]==1){
			  drawFineTube();
			  /*scp.clearSysA();
			  if(FineTube.isDead()){
				  nozzlePath = scp.createNodePath(nodeList.get(0));
				  NozzleLayer nozzleLayer = new NozzleLayer(this, scp, nozzlePath);
				  FineTube = new FineTube(this, scp, nozzlePath, nozzleLayer, ft1, cfList);
			  }else{
			  FineTube.draw2();
			  }*/
		  }
		  else if(checkbox_array[6]==1){
			  drawShadows();
		  }
		  else if(checkbox_array[7]==1){
			  drawShadows2();
		  }
		  else if(checkbox_array[8]==1){
			  drawShadows3();
		  }
		  else if(checkbox_array[9]==1){
			  drawShadows4();
		  }
		  else if(checkbox_array[10]==1){
			  drawShadows5();
		  }

		  /*else if(checkbox_array[10]==1){
			  drawShadows3();
		  }*/
		  
		  for(int i=0; i<interactionChanger.size(); i++){
			  for(Iterator<InteractionChanger> interactionIterator = interactionChanger.iterator(); interactionIterator.hasNext();){
				  InteractionChanger iC = interactionIterator.next();
				  
				  iC.draw();
				  
				  if(iC.isDead()){
					  //System.out.println("DEAD");
					  interactionIterator.remove();
				  }
			  }
		 }
		  
		  
		  
		  //scp.clearSysA();
		  redGlow = new ColorFade(this, 160, 40, 255);
		  redGlow.brightnessFade(0, 2000);
		  cfList.addColorFade(redGlow);
		  
		  scp.clearSysB();
		  //scp.setColorB(redGlow.hue, redGlow.saturation, redGlow.brightness);
		  //jeffEffect.draw();
		  
		  //scp.clearSysB();
		  //zünden.draw();
		  
		  //scp.clearSysA();
		  //nodeDrop.draw();
		  //uD.draw2();
		  
		  //drawHorizontalFineTube();
		  
		  //NODEDROP
		  //scp.clearSysA();		  
		  for(Iterator<NodeDrop> effectIterator = nodeDropList.iterator(); effectIterator.hasNext();){
			  NodeDrop e = effectIterator.next();
			  
			  e.draw();
			  
			  if(e.isDead()){
				  //System.out.println("DEAD");
				  effectIterator.remove();
			  }
		  }
		  if(frameCount%100==0){
		  while(nodeDropList.size()<1){
			  nodeDropList.add(new NodeDrop(this, scp, node2, cfList));
		  }
		  }
		  
		  
		  
		  
		  /*node4.nozzleList.get(0).setColorA(0, 255, 255);
		  node4.nozzleList.get(1).setColorA(50, 255, 255);
		  node4.nozzleList.get(2).setColorA(100, 255, 255);
		  node4.nozzleList.get(3).setColorA(150, 255, 255);
		  node4.nozzleList.get(4).setColorA(200, 255, 255);
		  node4.nozzleList.get(5).setColorA(250, 255, 255);
		  node4.nozzleList.get(6).setColorA(300, 255, 255);
		  node4.nozzleList.get(7).setColorA(350, 255, 255);*/
		  
		  /*node1.setColorA(0, 0, 255);
		  node2.setColorA(0, 0, 255);
		  node3.setColorA(0, 0, 255);
		  node4.setColorA(0, 0, 255);
		  node5.setColorA(0, 0, 255);
		  node6.setColorA(0, 0, 255);
		  node7.setColorA(0, 0, 255);
		  
		  
		  node1.setColorB(180, 0, 255);
		  node2.setColorB(180, 0, 255);
		  node3.setColorB(180, 0, 255);
		  node4.setColorB(180, 0, 255);
		  node5.setColorB(180, 0, 255);
		  node6.setColorB(180, 0, 255);
		  node7.setColorB(180, 0, 255);
		  
		  node1.setColorA(0, 255, 255);
		  node2.setColorA(0, 255, 255);
		  node3.setColorA(0, 255, 255);
		  node4.setColorA(0, 255, 255);
		  node5.setColorA(0, 255, 255);
		  node6.setColorA(0, 255, 255);
		  node7.setColorA(0, 255, 255);
		  
		  
		  node1.setColorB(180, 255, 255);
		  node2.setColorB(180, 255, 255);
		  node3.setColorB(180, 255, 255);
		  node4.setColorB(180, 255, 255);
		  node5.setColorB(180, 255, 255);
		  node6.setColorB(180, 255, 255);
		  node7.setColorB(180, 255, 255);*/
		  
		  //scp.setColor(0, 0, 255);
		  
		  //scp.setColorB(0, 0, 255);
		  
		  //scp.setColor(0, 0, 100);
		  //drawShadows();
		  //drawUpDownAnimation();
		  
		  Date cTime = new Date();
		  dateOff.setHours(hoursOff);
		  dateOn.setHours(hoursOn);
		  
		 
		  for(int i=0; i<effectList.size(); i++){
			  for(Iterator<Effect> effectIterator = effectList.get(i).iterator(); effectIterator.hasNext();){
				  Effect e = effectIterator.next();
				  
				  e.draw();
				  
				  if(e.isDead()){
					  //System.out.println("DEAD");
					  effectIterator.remove();
				  }
			  }
			  }
		  
		  /*if(cTime.compareTo(dateOn)<0 && cTime.compareTo(dateOff)>0){
			  scp.clearSysA();
			  scp.clearSysB();
		  }*/
		  
		  colorMode(RGB);
		  background(255);
		  
		  node1.drawOnGui(10, 50);
		  node2.drawOnGui(150, 50);
		  node3.drawOnGui(300, 50);
		  node4.drawOnGui(450, 50);
		  node5.drawOnGui(600, 50);
		  node6.drawOnGui(750, 50);
		  node7.drawOnGui(900, 50);
		  
		  for(Sensor s : sensorList){
			  s.drawOnGui();
		  }
		  
		  
		  fill(0);
		  text("FrameRate: "+frameRate, 720, 20);
		  text("gggCreated by Marius Hoggenmüller on 04.02.14. Copyright (c) 2014 Marius Hoggenmüller, LMU Munich. All rights reserved.", 10, 740);
		  
		  image(m, 0, 0, 100, 100);
	}

	private void writeXML(String name, int sensorID, int nodeID) {
		// TODO Auto-generated method stub
		
		//XML Logger
		/*xml = loadXML("/Users/luminale/Documents/workspace/GSVideo-test/src/evaluation2.xml");
		
		int day = day();    // Values from 1 - 31
		int month = month();  // Values from 1 - 12
		int year = year();
		int hour = hour();
		int minute = minute();  // Values from 0 - 59
		int second = second();  // Values from 0 - 59
		
		XML interaction = xml.addChild("interaction");
		interaction.setContent(name);
		XML sensor = interaction.addChild("sensor");
		sensor.setContent(Integer.toString(sensorID));
		XML node = interaction.addChild("node");
		node.setContent(Integer.toString(nodeID));
		XML date = interaction.addChild("date");
		XML dayX = date.addChild("day");
		dayX.setContent(Integer.toString(day));
		XML monthX = date.addChild("month");
		monthX.setContent(Integer.toString(month));
		XML yearX = date.addChild("year");
		yearX.setContent(Integer.toString(year));
		XML hourX = date.addChild("hour");
		hourX.setContent(Integer.toString(hour));
		XML minuteX = date.addChild("minute");
		minuteX.setContent(Integer.toString(minute));
		XML secondX = date.addChild("second");
		secondX.setContent(Integer.toString(second));
		
		//xml.addChild(interaction);
		saveXML(xml, "/Users/luminale/Documents/workspace/GSVideo-test/src/evaluation.xml");*/
		
	}

	//SETUP ARDUINO
	public void initArduino() {
		for (int i = 0; i < Serial.list().length; i++) {
			System.out.println("Device " + i + " " + Serial.list()[i]);
			if(Serial.list()[i].contains("/dev/tty.usbmodem")){
			ARDUINO_DEVICE = Serial.list()[i];
			}
		}

		try {
			myPort = new Serial(this, ARDUINO_DEVICE, 14400);
			myPort.clear();
		} catch (Exception e) {
			System.out.println("Serial konnte nicht initialisiert werden");
		}
	}
	
	//DRAW PENDULUM
	/*public void drawPendulum(){
	for(Iterator<Pendulum> pTIterator = pendulumList .iterator(); pTIterator.hasNext();){
		  Pendulum p = pTIterator.next();
		  
		  p.draw();
		  
		  if(p.isDead()){
			  //System.out.println("DEAD");
			  pTIterator.remove();
		  }
	  }
	  
	  while(pendulumList.size()<0){
		  nozzlePath = scp.createPath(4,3,2,1,0);
		  //nozzlePath = createRowPath(0,65);
		  HLayer nLayer = new HLayer(this, scp, nozzlePath);
		  pendulumList.add(new Pendulum(nLayer));
	  }
	}*/
	
	//DRAW SIMPLETUBE
	public void drawLamp(){
		for(Iterator<Lamp> lampIterator = lampList  .iterator(); lampIterator.hasNext();){
			  Lamp l = lampIterator.next();
			  
			  l.draw();
			  
			  if(l.isDead()){
				  //System.out.println("DEAD");
				  lampIterator.remove();
			  }
		  }
		  
		  while(lampList.size()<5){
			  nozzlePath = scp.createNodePath(nodeList.get((int)random(0,7)));
			  HLayer nLayer = new HLayer(this, scp, nozzlePath);
			  lampList.add(new Lamp(this, nLayer, cfList, (int) random(1000,1000)));
		  }
		}
		
	//SETUP PUSH
	public void setupPush(){
		pushColor = new ColorFade(this, 360, 100, 100);
		pushColor.hueFade(140, 10000);
		cfList.addColorFade(pushColor);
	}
	
	//DRAW PUSH
	public void drawPush(){
		for(Nozzle n : scp.nozzleList){
			PGraphics pg = n.sysA;
			pg.beginDraw();
			pg.colorMode(HSB,360,100,100,100);
			pg.noStroke();
			pg.fill(pushColor.hue-n.id, pushColor.saturation, 100-alpha);
			//pg.rect(0, y-1, pg.width, 1);
			pg.fill(pushColor.hue-n.id, pushColor.saturation, alpha);
			pg.rect(0, y, pg.width, 1);
			pg.endDraw();
		}
		
		if(up){
		if(alphaUP ){
		if(y==0){
		alpha+=13;
		}else if(y==1){
		alpha+=11;
		}else if(y==2){
		alpha+=9;
		}else if(y==3){
		alpha+=7;
		}else if(y==4){
		alpha+=3;
		}
		}else{
		if(y==0){
		alpha-=13;
		}else if(y==1){
		alpha-=11;
		}else if(y==2){
		alpha-=9;
		}else if(y==3){
		alpha-=7;
		}else if(y==4){
		alpha-=3;
		}
		}
		}else{
		if(alphaUP ){
		if(y==0){
		alpha+=13;
		}else if(y==1){
		alpha+=11;
		}else if(y==2){
		alpha+=9;
		}else if(y==3){
		alpha+=7;
		}else if(y==4){
		alpha+=3;
		}
		}else{
		if(y==0){
		alpha-=13;
		}else if(y==1){
		alpha-=11;
		}else if(y==2){
		alpha-=9;
		}else if(y==3){
		alpha-=7;
		}else if(y==4){
		alpha-=3;
		}
		}	
		}
		if(alpha>=100){
			alphaUP = false;
		}
		//if(frameCount%10==0){
		if(!alphaUP && alpha<=80){
			alphaUP = true;
			alpha=20;
		if(up){
			y+=1;
		}else{
			y-=1;
		}
		}
		//}
		if(y<=0){
			up = true;
			value1=100;
			value2=0;
		}
		if(y>=4){
			up=false;
			value1=0;
			value2=100;
		}
	}
	
	
	public void setupShadows(){

		
		shadowInnerColor = new ColorFade(this, 163, 150, 255);
		shadowInnerColor.saturationFade(10, 5000, 2);
		shadowInnerColor.brightnessFade(50, 5000,2);
		cfList.addColorFade(shadowInnerColor);
		
		ShadowBreath = new Breath(this, scp, cfList, 163, 30, 5000);
	}
	
	public void drawShadows(){
		
		if(shadowInnerColor.isDead()){
			shadowInnerColor = new ColorFade(this, 163, 150, 100);
			shadowInnerColor.saturationFade(10, shadowSpeed, 2);
			shadowInnerColor.brightnessFade(50, shadowSpeed,2);
			cfList.addColorFade(shadowInnerColor);
			
			if(shadowSpeed>2000){
				shadowSpeed-=100;
			}else{
				shadowSpeed=5000;
			}
		}
		
		scp.clearSysA();
		//scp.setColor(163, 30, 255);
		ShadowBreath.draw(shadowSpeed);
		
		scp.clearSysB();
		scp.setColorB(shadowInnerColor.hue, shadowInnerColor.saturation, shadowInnerColor.brightness);
		for(Iterator<ShadowColorTube> shadowIterator = ShadowTube.iterator(); shadowIterator.hasNext();){
			ShadowColorTube shadow = shadowIterator.next();
			  
			shadow.draw();
			  
			  if(shadow.isDead()){
				  shadowIterator.remove();
			  }
		  }
		
		System.out.println("ShadowTubeSize: "+ShadowTube.size());
		
		while(ShadowTube.size()<1){
			ShadowTube.add(new ShadowColorTube(this, scp, cfList));
		}
		
		
		  /*if(ShadowTube.isDead()){
			  nozzlePath = scp.createNodePath(nodeList.get(0));
			  NozzleLayer nozzleLayer = new NozzleLayer(this, scp, nozzlePath);
			  ShadowTube = new FineTube(this, nozzleLayer, shadowColor, 120, 10, 10, 20, 10);
		  }else{
			  ShadowTube.draw();
		  }*/
	}
	
	public void setupShadows2(){	
		shadowInnerColor = new ColorFade(this, 163, 150, 255);
		shadowInnerColor.saturationFade(10, 5000, 2);
		shadowInnerColor.brightnessFade(50, 5000,2);
		cfList.addColorFade(shadowInnerColor);
		
		ShadowBreath = new Breath(this, scp, cfList, 163, 30, 5000);
	}

	public void drawShadows2(){
		
		if(shadowInnerColor.isDead()){
			shadowInnerColor = new ColorFade(this, 163, 150, 255);
			shadowInnerColor.saturationFade(10, shadowSpeed, 2);
			shadowInnerColor.brightnessFade(50, shadowSpeed,2);
			cfList.addColorFade(shadowInnerColor);
			
			if(shadowSpeed>2000){
				shadowSpeed-=100;
			}else{
				shadowSpeed=5000;
			}
		}
		
		scp.clearSysA();
		//scp.setColor(163, 30, 255);
		ShadowBreath.draw(shadowSpeed);
		
		scp.clearSysB();
		scp.setColorB(shadowInnerColor.hue, shadowInnerColor.saturation, shadowInnerColor.brightness);
		for(Iterator<ShadowColorTube> shadowIterator = ShadowTube.iterator(); shadowIterator.hasNext();){
			ShadowColorTube shadow = shadowIterator.next();
			  
			shadow.draw();
			  
			  if(shadow.isDead()){
				  shadowIterator.remove();
			  }
		  }
		
		System.out.println("ShadowTubeSize: "+ShadowTube.size());
		
		while(ShadowTube.size()<1){
			ShadowTube.add(new ShadowColorTube(this, scp, cfList));
		}
		
		
		  /*if(ShadowTube.isDead()){
			  nozzlePath = scp.createNodePath(nodeList.get(0));
			  NozzleLayer nozzleLayer = new NozzleLayer(this, scp, nozzlePath);
			  ShadowTube = new FineTube(this, nozzleLayer, shadowColor, 120, 10, 10, 20, 10);
		  }else{
			  ShadowTube.draw();
		  }*/
	}

	public void setupShadows3(){

		shadowInnerColor3 = new ColorFade(this, 163, 150, 50);
		shadowInnerColor3.saturationFade(10, 5000, 2);
		shadowInnerColor3.brightnessFade(50, 5000,2);
		cfList.addColorFade(shadowInnerColor3);
		
		
		//ShadowBreath = new Breath(this, scp, cfList, 163, 30, 5000);
	}
	
	public void drawShadows3(){
		
		
		if(shadowInnerColor3.isDead()){
			shadowInnerColor3 = new ColorFade(this, 163, 150, 50);
			shadowInnerColor3.saturationFade(10, shadowSpeed, 2);
			shadowInnerColor3.brightnessFade(50, shadowSpeed,2);
			cfList.addColorFade(shadowInnerColor3);
			
			if(shadowSpeed>2000){
				shadowSpeed-=100;
			}else{
				shadowSpeed=5000;
			}
		}
		
		scp.clearSysA();
		scp.setColor(163, 30, 0);
		//ShadowBreath.draw(shadowSpeed);
		
		scp.clearSysB();
		scp.setColorB(shadowInnerColor3.hue, shadowInnerColor3.saturation, shadowInnerColor3.brightness);
		for(Iterator<ShadowColorTube> shadowIterator = ShadowTube.iterator(); shadowIterator.hasNext();){
			ShadowColorTube shadow = shadowIterator.next();
			  
			shadow.draw();
			  
			  if(shadow.isDead()){
				  shadowIterator.remove();
			  }
		  }
		
		System.out.println("ShadowTubeSize: "+ShadowTube.size());
		
		while(ShadowTube.size()<1){
			ShadowTube.add(new ShadowColorTube(this, scp, cfList));
		}
		
		
		  /*if(ShadowTube.isDead()){
			  nozzlePath = scp.createNodePath(nodeList.get(0));
			  NozzleLayer nozzleLayer = new NozzleLayer(this, scp, nozzlePath);
			  ShadowTube = new FineTube(this, nozzleLayer, shadowColor, 120, 10, 10, 20, 10);
		  }else{
			  ShadowTube.draw();
		  }*/
	}
	
	public void setupShadows4(){
		shadowInnerColor4 = new ColorFade(this, 163, 150, 255);
		shadowInnerColor4.saturationFade(10, 5000, 2);
		shadowInnerColor4.brightnessFade(50, 5000,2);
		cfList.addColorFade(shadowInnerColor4);
		
		ShadowBreath = new Breath(this, scp, cfList, 163, 30, 5000);
	}

	public void drawShadows4(){
		
		
		if(shadowInnerColor4.isDead()){
			shadowInnerColor4 = new ColorFade(this, 163, 150, 50);
			shadowInnerColor4.saturationFade(10, shadowSpeed, 2);
			shadowInnerColor4.brightnessFade(50, shadowSpeed,2);
			cfList.addColorFade(shadowInnerColor4);
			
			if(shadowSpeed>2000){
				shadowSpeed-=100;
			}else{
				shadowSpeed=5000;
			}
		}
		
		for(int i=0; i<scp.nozzleList.size(); i++) {
			PGraphics pg = scp.nozzleList.get(i).sysA;
			pg.beginDraw();
			pg.background(0);

			pg.colorMode(HSB, 360, 255, 255);	
			pg.noStroke();
			pg.fill(160+i,20,40);
			pg.rect(0, 0, pg.width, pg.height);
			pg.endDraw();
			
			PGraphics pg2 = scp.nozzleList.get(i).sysB;
			pg2.beginDraw();
			pg2.background(0);

			pg2.colorMode(HSB, 360, 255, 255);	
			pg2.noStroke();
			pg2.fill(160+i,80,100);
			pg2.rect(0, 0, pg2.width, pg2.height);
			pg2.endDraw();
			
		}
		
		//scp.clearSysA();
		//scp.setColor(163, 30, 0);
		//ShadowBreath.draw(shadowSpeed);
		
		//scp.clearSysB();
		//scp.setColorB(shadowInnerColor3.hue, shadowInnerColor3.saturation, shadowInnerColor3.brightness);
		for(Iterator<ShadowColorTube> shadowIterator = ShadowTube.iterator(); shadowIterator.hasNext();){
			ShadowColorTube shadow = shadowIterator.next();
			  
			shadow.draw();
			  
			  if(shadow.isDead()){
				  shadowIterator.remove();
			  }
		  }
		
		System.out.println("ShadowTubeSize: "+ShadowTube.size());
		
		while(ShadowTube.size()<1){
			ShadowTube.add(new ShadowColorTube(this, scp, cfList));
		}
		
		
		  /*if(ShadowTube.isDead()){
			  nozzlePath = scp.createNodePath(nodeList.get(0));
			  NozzleLayer nozzleLayer = new NozzleLayer(this, scp, nozzlePath);
			  ShadowTube = new FineTube(this, nozzleLayer, shadowColor, 120, 10, 10, 20, 10);
		  }else{
			  ShadowTube.draw();
		  }*/
	}

	public void setupShadows5(){
		shadowInnerColor5 = new ColorFade(this, 163, 150, 255);
		shadowInnerColor5.saturationFade(10, 5000, 2);
		shadowInnerColor5.brightnessFade(50, 5000,2);
		cfList.addColorFade(shadowInnerColor5);
		
		ShadowBreath2 = new Breath2(this, scp, cfList, 183, 40, 5000);
	}
	
	public void drawShadows5(){

		if(shadowInnerColor5.isDead()){
			shadowInnerColor5 = new ColorFade(this, 163, 150, 50);
			shadowInnerColor5.saturationFade(10, shadowSpeed, 2);
			shadowInnerColor5.brightnessFade(50, shadowSpeed,2);
			cfList.addColorFade(shadowInnerColor5);
			
			if(shadowSpeed>2000){
				shadowSpeed-=100;
			}else{
				shadowSpeed=5000;
			}
		}
		
		for(int i=0; i<scp.nozzleList.size(); i++) {
			PGraphics pg = scp.nozzleList.get(i).sysA;
			pg.beginDraw();
			pg.background(0);

			pg.colorMode(HSB, 360, 255, 255);	
			pg.noStroke();
			pg.fill(160+i,20,40);
			pg.rect(0, 0, pg.width, pg.height);
			pg.endDraw();
			
			PGraphics pg2 = scp.nozzleList.get(i).sysB;
			pg2.beginDraw();
			pg2.background(0);

			pg2.colorMode(HSB, 360, 255, 255);	
			pg2.noStroke();
			pg2.fill(160+i,80,100);
			pg2.rect(0, 0, pg2.width, pg2.height);
			pg2.endDraw();
			
		}
		
		scp.clearSysA();
		//scp.setColor(163, 30, 0);
		ShadowBreath2.draw(shadowSpeed);
		
		//scp.clearSysB();
		//scp.setColorB(shadowInnerColor3.hue, shadowInnerColor3.saturation, shadowInnerColor3.brightness);
		for(Iterator<ShadowColorTube> shadowIterator = ShadowTube.iterator(); shadowIterator.hasNext();){
			ShadowColorTube shadow = shadowIterator.next();
			  
			shadow.draw();
			  
			  if(shadow.isDead()){
				  shadowIterator.remove();
			  }
		  }
		
		System.out.println("ShadowTubeSize: "+ShadowTube.size());
		
		while(ShadowTube.size()<1){
			ShadowTube.add(new ShadowColorTube(this, scp, cfList));
		}
		
	}
	
	public void setupHorizontalFineTube(){
		hFT = new ColorFade(this, 0,0,255);
		cfList.addColorFade(hFT);
		
		nozzlePath = scp.createNodePath(nodeList.get(0));
		NozzleLayer nozzleLayer = new NozzleLayer(this, scp, nozzlePath);
		PGraphics pg = nozzleLayer.getLayer();
		horFineTube = new HorizontalFineTube(this, pg, hFT, 40, 10, 40, 100);
	}
	
	public void drawHorizontalFineTube(){
		horFineTube.draw();
		
	}
	//SETUP FLOWER
	public void setupFlower(){
		cfFlower = new ColorFade(this, 220, 100, 100);
		cfFlower.hueFade(180, 2000);
		cfList.addColorFade(cfFlower);
		nozzlePath = scp.createPath(7,6,5,4,3,2,1,0);
		flowerLayer = new NozzleLayer(this, scp, nozzlePath);
		flowerLayerGraphics = flowerLayer.getLayer();
	}
	
	//SETUP PATHOSLIGHT
	public void setupPathosLight() {
		//create DiscoDots
		for(Nozzle n : scp.nozzleList) {
			discoDotList.add(new discoDot(this, n, cfList));
		}
		//create LampManager
		//rlm = new RandomLampManager(this, scp, 3, 12);
		//rlm2 = new RandomLampManager(this, scp, 3, 12);
	}
	
	//DRAW PATHOSLIGHT
	public void drawPathosLight() {
		//clear Old Frame
		//scp.clearSysA();
		//scp.dimm(80);
		//LampManager
				//rlm.draw();
				//rlm2.draw();
		
		//DiscoDots
		int timer = (int) random(10,10);
		for(discoDot d : discoDotList ) {
			if(frameCount%timer==0){
			d.update();
			}
			d.draw();
		}
		
	}
		
	
	//SETUP UPDOWN ANIMATION
	public void setupUpDownAnimation() {
		upDownCl = new ColorFade(this, 0, 0, 10);
		upDownCl.hueFade(360, 100000);
		cfList.addColorFade(upDownCl);
		for(int i=0; i<7; i++){
		upDown.add(new UpDown(this, scp.nodeList.get(i), upDownCl, 100));
		}
	}
	//DRAW UPDOWN ANIMATION
	public void drawUpDownAnimation() {
		scp.clearSysB();
		scp.dimm(30);
		
		for(UpDown u : upDown){
			u.draw();
			//System.out.println(u.speed);
			
		}
	}
	
	//DRAW SHINE
	public void drawShine() {
		for(Iterator<HorizontalMove> hMIterator = horizontalMoveList.iterator(); hMIterator.hasNext();){
			  HorizontalMove hM = hMIterator.next();
			  
			  hM.update();
			  hM.draw();
			  
			  if(hM.isDead()){
				  hMIterator.remove();
			  }
		  }

		  while(horizontalMoveList.size()<0){
			  nozzlePath = scp.createPath(7,6,5,4,3,2,1,0);
			  //nozzlePath = createRandomPath();
			  horizontalMoveList.add(new Shine(this, nozzlePath));  
			  horizontalMoveList.add(new Glitter(this, nozzlePath)); 
		  }
	}
	
	public void setupFineTube(){
	
		ftbgHue = 302;
		ftHue = 36;
		ftHue2 = 36;
		
		ftbg = new ColorFade(this, ftbgHue, 180, 150);
		cfList.addColorFade(ftbg);
		
		ft1 = new ColorFade(this, ftHue, 0, 180, 100);
		cfList.addColorFade(ft1);
		
		ft2 = new ColorFade(this, ftHue2, 0, 160, 210);
		ft2.hueFade(ftHue2-10, 2000);
		cfList.addColorFade(ft2);
	
	}
	
	public void drawFineTube(){
		for(int i=0; i<scp.nozzleList.size(); i++) {
			PGraphics pg = scp.nozzleList.get(i).sysA;
			pg.beginDraw();
			//scp.dimm(80);
			pg.clear();
			pg.colorMode(HSB, 360, 255, 255);	
			pg.noStroke();
			pg.fill(ftbg.hue, ftbg.saturation, ftbg.brightness);
			pg.rect(0, 0, pg.width, pg.height);
			
			pg.endDraw();
			
		}
			  //scp.clearSysA();
			  if(FineTube.isDead()){
				  //nozzlePath = scp.createRandomPath(0, 17, 48, 56);
				  nozzlePath = scp.createNodePath(node3);
				  NozzleLayer nozzleLayer = new NozzleLayer(this, scp, nozzlePath);
				  FineTube = new FineTube(this, nozzleLayer, ft1);
			  }else{
			  FineTube.draw();
			  }
		
	}
	
	public void setupBreath2(){
		
		hueBreath = 240;
		hueBBreath = 0;
		speedBreath = 1000;
		
		c1 = new ColorFade(this, hueBreath, 255, 255);
		c2 = new ColorFade(this, hueBreath, 255, 150);
		c3 = new ColorFade(this, hueBreath, 255, 100);
		c4 = new ColorFade(this, hueBreath, 255, 70);
		c5 = new ColorFade(this, hueBreath, 255, 55);
		
		c1.brightnessFade(55, 5000, 2);
		c2.brightnessFade(55, 5000, 2);
		c3.brightnessFade(55, 5000, 2);
		c4.brightnessFade(55, 5000, 2);
		c5.brightnessFade(55, 5000, 2);
		
		cfList.addColorFade(c1);
		cfList.addColorFade(c2);
		cfList.addColorFade(c3);
		cfList.addColorFade(c4);
		cfList.addColorFade(c5);
		
		
		cb1 = new ColorFade(this, hueBBreath, 200, 150);

		//cb1.brightnessFade(40, 5000, 2);
		
		cfList.addColorFade(cb1);

		
		

	}
	
	public void drawBreath2(){
		for(int i=0; i<scp.nozzleList.size(); i++) {
			PGraphics pg = scp.nozzleList.get(i).sysA;
			PGraphics pg2 = scp.nozzleList.get(i).sysB;
			pg.beginDraw();
			pg.background(0);

				pg.colorMode(HSB, 360, 255, 255);	
				pg.noStroke();
				pg.fill(c1.hue+i/2,c1.saturation,c1.brightness);
				pg.rect(0, 4, pg.width, 1);
				pg.fill(c2.hue+i/2,c2.saturation,c2.brightness);
				pg.rect(0, 3, pg.width, 1);
				pg.fill(c3.hue+i/2,c3.saturation,c3.brightness);
				pg.rect(0, 2, pg.width, 1);
				pg.fill(c4.hue+i/2,c4.saturation,c4.brightness);
				pg.rect(0, 1, pg.width, 1);
				pg.fill(c5.hue+i/2,c5.saturation,c5.brightness);
				pg.rect(0, 0, pg.width, 1);
		   
			
			//pg2.colorMode(HSB, 360, 100, 100,100);
			//pg2.fill(Math.abs(cfYellow.hue-270)+30,cfYellow.saturation,cfYellow.brightness, 70);
			//pg2.rect(0, 0, pg2.width, 1);

			//pg2.endDraw();
			pg.endDraw();
			
			pg2.beginDraw();
			pg2.colorMode(HSB, 360, 255, 255);
			pg2.noStroke();
			pg2.fill(cb1.hue+i, cb1.saturation, cb1.brightness);
			pg2.rect(0,0,pg2.width,pg2.height);
			pg2.endDraw();
			
			
			
			//System.out.println("Hue1: "+(Math.abs(cfYellow.hue-270)+30)+" Sat1: "+(Math.abs(cfYellow.saturation-100))+" Bright: "+(Math.abs(cfYellow.brightness-100)+40));
		}
		
		if(c1.isDead()){
			
			hueBreath++;
			hueBBreath++;
			
			System.out.println("HueBreath: "+hueBreath+" HueBBreath: "+hueBBreath);
			
			
			c1 = new ColorFade(this, hueBreath, 255, 255);
			c2 = new ColorFade(this, hueBreath, 255, 150);
			c3 = new ColorFade(this, hueBreath, 255, 100);
			c4 = new ColorFade(this, hueBreath, 255, 70);
			c5 = new ColorFade(this, hueBreath, 255, 55);
			
			c1.brightnessFade(55, 5000, 2);
			c2.brightnessFade(55, 5000, 2);
			c3.brightnessFade(55, 5000, 2);
			c4.brightnessFade(55, 5000, 2);
			c5.brightnessFade(55, 5000, 2);
			
			cfList.addColorFade(c1);
			cfList.addColorFade(c2);
			cfList.addColorFade(c3);
			cfList.addColorFade(c4);
			cfList.addColorFade(c5);

			
			
			
			cb1 = new ColorFade(this, hueBBreath, 200, 150);
			
			cb1.brightnessFade(40, 5000, 2);
			
			cfList.addColorFade(cb1);
			
			
			
		}
	}
	
	public void setupBreath(){
		
		hueBreath = 35;
		speedBreath = 5000;
		
		c1 = new ColorFade(this, hueBreath, 0, 255);
		c2 = new ColorFade(this, hueBreath, 0, 150);
		c3 = new ColorFade(this, hueBreath, 0, 100);
		c4 = new ColorFade(this, hueBreath, 0, 70);
		c5 = new ColorFade(this, hueBreath, 0, 55);
		
		c1.brightnessFade(80, 5000, 2);
		c2.brightnessFade(80, 5000, 2);
		c3.brightnessFade(80, 5000, 2);
		c4.brightnessFade(80, 5000, 2);
		c5.brightnessFade(80, 5000, 2);
		
		cfList.addColorFade(c1);
		cfList.addColorFade(c2);
		cfList.addColorFade(c3);
		cfList.addColorFade(c4);
		cfList.addColorFade(c5);

	}
	
	public void drawBreath(){
	
		for(Nozzle nozzle : scp.nozzleList) {
			PGraphics pg = nozzle.sysA;
			pg.beginDraw();
			pg.background(0);

				pg.colorMode(HSB, 360, 255, 255);	
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
		
		while(topGlowList.size()<10){
			nozzlePath = scp.createRandomSingleNozzle();
			NozzleLayer nozzleLayer = new NozzleLayer(this, scp, nozzlePath);
			//PGraphics pg = nozzleLayer.getLayer();
			topGlowList.add(new TopGlow(this, nozzleLayer, cfList, 50, 255));
		}
		
		for(Iterator<TopGlow> hMIterator = topGlowList.iterator(); hMIterator.hasNext();){
			  TopGlow hM = hMIterator.next();
			  
			  hM.draw();
			  
			  if(hM.isDead()){
				  hMIterator.remove();
			  }
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
			
			if(speedBreath>2000){
			speedBreath-=100;
			}else{
				speedBreath=5000;
			}
			
			System.out.println("SPEEDBREATH: "+speedBreath);
			
			
			c1 = new ColorFade(this, hueBreath, 220, 255);
			c2 = new ColorFade(this, hueBreath, 220, 150);
			c3 = new ColorFade(this, hueBreath, 220, 100);
			c4 = new ColorFade(this, hueBreath, 220, 70);
			c5 = new ColorFade(this, hueBreath, 220, 55);
			
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
	
	//SETUP SIMPLETUBE
	public void setupSimpleTube(){
		colorMode(HSB,360,255,255);
		sTube = new ColorFade(this, 270, 200, 0);
		//sTube.hueFade(h+50, 1000);
		
		//sTube2 = new ColorFade(this, 0, 100, 50);
		//sTube2.alphaFade(40, 1000);
		
		//colorTubeList.start();
		
		cfList.addColorFade(sTube);
		//cfList.addColorFade(sTube2);
	}
	
	//DRAW SIMPLETUBE
	public void drawSimpleTube(){
	for(Iterator<SimpleTube> sTIterator = sTubeList.iterator(); sTIterator.hasNext();){
		  SimpleTube sT = sTIterator.next();
		  
		  sT.draw();
		  
		  if(sT.isDead()){
			  //System.out.println("DEAD");
			  sTIterator.remove();
		  }
	  }
	  
	  while(sTubeList.size()<1){
		  //nozzlePath = scp.createRowPath(0,65);
		  nozzlePath = scp.createNodePath(node1);
		  //nozzlePath = createRandomPath(0,8,58,65);
		  NozzleLayer nLayer = new NozzleLayer(this, scp, nozzlePath);
		  sTubeList.add(new SimpleTube(this, nLayer, sTube, (int)random(20,100), 1));
	  }
	}
	
	//SETUP COMP SIMPLETUBE
		public void setupCompTube(){
			float[] hsb = Color.RGBtoHSB(abs(Farbe[0]-255), abs(Farbe[1]-255), abs(Farbe[2]-255), null);
			float hue = hsb[0]*360;          // .58333
			float saturation = hsb[1]*100;   // .66667
			float brightness = hsb[2]*100;   // .6
			
			sTube = new ColorFade(this, (int)hue, (int)saturation, (int)brightness);
			sTube.saturationFade((int)saturation-80, 1000);
			
			sTube2 = new ColorFade(this, (int)hue, (int)10, (int)brightness);
			//sTube.hueFade(h+50, 1000);
			
			
			cfList.addColorFade(sTube);
			cfList.addColorFade(sTube2);
		}
		
		//DRAW COMP SIMPLETUBE
		public void drawCompSimpleTube(){
		for(Iterator<SimpleTube> sTIterator = sTubeList.iterator(); sTIterator.hasNext();){
			  SimpleTube sT = sTIterator.next();
			  
			  sT.draw();
			  
			  if(sT.isDead()){
				  //System.out.println("DEAD");
				  sTIterator.remove();
			  }
		  }
		  
		  while(sTubeList.size()<1){
			  //nozzlePath = createRandomPath(0,8,58,65);
			  //nozzlePath = createPath(7,6,5,4,3,2,1,0);
			  nozzlePath = scp.createNodePath(node1);
			  NozzleLayer nLayer = new NozzleLayer(this, scp, nozzlePath);
			  sTubeList.add(new SimpleTube(this, nLayer, sTube, (int)random(5,80), 1+Math.random()*0.5));
			  /*nozzlePath = createPath(0,1,2,3,4,5,6,7);
			  nLayer = new NozzleLayer(this, scp, nozzlePath);
			  sTubeList.add(new SimpleTube(this, nLayer, sTube2, (int)random(50,150), 1+Math.random()*0.5));*/
		  }
		}
	
	//SETUP HUEBACKGROUND
	public void setupHueBackground(){
		hueFade = new ColorFade(this, 240, 90, 90);
		hueFade.hueFade(150, 5000);
		hueFade.brightnessFade(40, 10000);
		cfList.addColorFade(hueFade);
	}
	
	public void drawHueBackground(){
		for(Nozzle n : scp.nozzleList){
			PGraphics pg = n.sysA;
			pg.beginDraw();
			pg.colorMode(HSB, 360, 100, 100);
			pg.noStroke();
			pg.fill(hueFade.hue-n.id, hueFade.saturation, hueFade.brightness);
			pg.rect(0, 0, pg.width, pg.height);
			pg.endDraw();
		}
	}
	
	public void setupBackGroundContrast(){
		Farbe[0] = (int) random(0,255);
		Farbe[1] = (int) random(0,255);
		Farbe[2] = (int) random(0,255);
		float[] hsb = Color.RGBtoHSB(Farbe[0], Farbe[1], Farbe[2], null);
		float hue = hsb[0]*360;          // .58333
		float saturation = hsb[1]*100;   // .66667
		float brightness = hsb[2]*100;   // .6
		System.out.println(hue+" "+saturation+" "+brightness+" ");
		backGround = new ColorFade(this, (int)hue, (int)saturation, (int)brightness);
		backGround.hueFade((int)hue+30, 3000);
		backGround.brightnessFade(50, 3000);
		cfList.addColorFade(backGround);
	}
	
	public void drawBackGroundContrast(){
		for(Nozzle n : scp.nozzleList){
			PGraphics pg = n.sysA;
			pg.beginDraw();
			pg.colorMode(HSB, 360, 100, 100);
			pg.noStroke();
			pg.fill(backGround.hue+(int)(0.5*n.id), backGround.saturation, backGround.brightness);
			pg.rect(0, 0, pg.width, pg.height);
			pg.endDraw();
		}
	}
	
	//SETUP LAMP
	public void setUpLamp() {
		nozzlePath = scp.createPath(7,6,5,4,3,2,1,0);
		lampLayer = new NozzleLayer(this, scp, nozzlePath);
		layerGraphics = lampLayer.getLayer();
		int h = 0;
		lampFade = new ColorFade(this, h, 100, 0);
		lampFade.hueFade(h+50, 1000, 2);
		lampFade.brightnessFade(100, 1000, 2);
		cfList.addColorFade(lampFade);
	}
	
	//DRAW LAMO
	public void NodeLamp() {
		layerGraphics.beginDraw();
		layerGraphics.colorMode(HSB,360,100,100);
		layerGraphics.noStroke();
		layerGraphics.fill(lampFade.hue, lampFade.saturation, lampFade.brightness);
		layerGraphics.rect(0, 0, layerGraphics.width, layerGraphics.height);
		layerGraphics.endDraw();
		lampLayer.add();
	}
	
	// Called every time a new frame is available to read
	public void movieEvent(GSMovie movie) {
	    movie.read();
	  }

	public void discoDots() {
		int timer = (int) random(1,1);
		for(discoDot d : discoDotList ) {
			if(frameCount%timer==0){
			d.update();
			}
			d.draw();
		}
	}
	

	public void setupYellowBlue() {
		yelBlutimer = 0;
		cfYellow = new ColorFade(this, 270, 180, 255);
		cfYellow.hueFade(30, 10000, 1);
		cfYellow.saturationFade(0, 5000, 2);
		//cfYellow.brightnessFade(100, 5000, 2);
		cfList.addColorFade(cfYellow);
		
		yelBlu = true;
		/*cfYellow2 = new ColorFade(this, 30, 100, 100);
		cfYellow2.saturationFade(0, 5000);
		cfYellow2.brightnessFade(40, 5000);
		cfYellow2.hueFade(270, 10000);
		cfList.addColorFade(cfYellow2);*/

	}
	
	public void drawYelloBlue2() {
		
	}
	
	public void drawYellowBlue() {
		for(Nozzle nozzle : scp.nozzleList) {
			PGraphics pg = nozzle.sysA;
			pg.beginDraw();
			PGraphics pg2 = nozzle.sysB;
			pg.beginDraw();
			pg.background(0);
			pg2.background(0);
			pg2.beginDraw();
			for(int iy=0; iy<pg.height; iy++){
				pg.colorMode(HSB, 360, 255, 255,255);	
				pg.fill(cfYellow.hue-5*iy,cfYellow.saturation,cfYellow.brightness-5*iy, 255);
				pg.noStroke();
				pg2.noStroke();
				pg.rect(0, iy, pg.width, 1);
		   }
			pg2.colorMode(HSB, 360, 255, 255,255);
			pg2.fill(Math.abs(cfYellow.hue-270)+30,cfYellow.saturation,cfYellow.brightness, 255);
			pg2.rect(0, 0, pg2.width, 1);

			pg2.endDraw();
			pg.endDraw();
			
			//System.out.println("Hue1: "+(Math.abs(cfYellow.hue-270)+30)+" Sat1: "+(Math.abs(cfYellow.saturation-100))+" Bright: "+(Math.abs(cfYellow.brightness-100)+40));
		}
		
		if(cfYellow.isDead()){
			yelBlutimer++;
			if(yelBlutimer>200){
			if(yelBlu){
			cfYellow = new ColorFade(this, 30, 180, 255);
			cfYellow.hueFade(270, 10000, 1);
			cfYellow.saturationFade(0, 5000, 2);
			cfList.addColorFade(cfYellow);
			yelBlu = !yelBlu;
			}else{
			cfYellow = new ColorFade(this, 270, 180, 255);
			cfYellow.hueFade(30, 10000, 1);
			cfYellow.saturationFade(0, 5000, 2);
			cfList.addColorFade(cfYellow);
			yelBlu = !yelBlu;	
			}
			yelBlutimer=0;
			}
			
		}
		
		//scp.setColor(cfYellow.hue, cfYellow.saturation, cfYellow.brightness);
	}
	
	public void yellowCold(){
		//Animate SystemB
	  	  counter2 = -400+((frameCount%800)*1);
	  	  int color1=60;
	  	  int color2=180;
	  	  int dimm=0; //(frameCount%300)/2;
		  if(counter2<=0){
		  for(Nozzle nozzle : scp.nozzleList){
			  pg2 = nozzle.sysA;
			  pg2.beginDraw();
			  pg2.colorMode(RGB);
			  pg2.background(255);
			  if(counter2<=-200){
				  //System.out.println("T1: "+(350-(counter2+400)));
				  for(int iy=0; iy<pg2.height; iy++){
						pg2.colorMode(HSB);	
						pg2.fill(color1-5*iy,200-(counter2+400),255,255);
						pg2.noStroke();
						pg2.rect(0, iy, pg.width, 1);
				   }
			  //pg2.background(counter2,0,0);
			  }else {
				  //System.out.println("T2: "+(50+counter2));
				  for(int iy=0; iy<pg2.height; iy++){
						pg2.colorMode(HSB);	
						pg2.fill(color2+5*iy,200+counter2,255,255);
						pg2.noStroke();
						pg2.rect(0, iy, pg.width, 1);
						pg2.colorMode(RGB);	
						pg2.fill(255,255,255,200-counter2);
						pg2.noStroke();
						pg2.rect(0, iy, pg.width, 1);
				   }
			  }
			  pg2.colorMode(RGB);
			  pg2.fill(0,0,0,dimm);
			  pg2.noStroke();
			  pg2.rect(0, 0, pg.width, pg.height);
			  pg2.endDraw();
			  
			  /*for(int ix=0; ix<pg2.width; ix++){
			  pg2.beginDraw();
			  pg2.colorMode(RGB);
			  pg2.fill(0,0,0,255);
			  pg2.rect(ix,y,1,1);
			  pg2.endDraw();
			  }*/
			  
		  }
		  }else{
		  //counter2 = (frameCount%100)*4;
		  for(Nozzle nozzle : scp.nozzleList){
			  pg2 = nozzle.sysA;
			  pg2.beginDraw();
			  pg2.colorMode(RGB);
			  pg2.background(0);
			  if(counter2<200){
				  //System.out.println("T3: "+(50-counter2));
				  for(int iy=0; iy<pg2.height; iy++){
						pg2.colorMode(HSB);	
						pg2.fill(color2+5*iy,200-counter2,255,255);
						pg2.noStroke();
						pg2.rect(0, iy, pg.width, 1);
						pg2.colorMode(RGB);	
						pg2.fill(255,255,255,200-counter2);
						pg2.noStroke();
						pg2.rect(0, iy, pg.width, 1);
				   }
			  //pg2.background(counter2,0,0);
			  }else {
				  //System.out.println("T4: "+(-50+counter2));
				  for(int iy=0; iy<pg2.height; iy++){
						pg2.colorMode(HSB);	
						pg2.fill(color1-5*iy,-200+counter2,255,255);
						pg2.noStroke();
						pg2.rect(0, iy, pg.width, 1);
				   }
			  }
			  pg2.colorMode(RGB);
			  pg2.fill(0,0,0,0);
			  pg2.noStroke();
			  pg2.rect(0, 0, pg.width, pg.height);
			  pg2.endDraw();
			  
		  }
		  
		 }
	  //scp.dimm(100);
	}

	//ARDUINO SERIAL EVENT
	public void serialEvent(Serial myPort) {
		try {
			myString = myPort.readStringUntil(lf);
			//System.out.println(myString);
			if (myString != null) {
				String[] spl1 = split(myString, '\n');
				String[] spl2 = split(spl1[0], '/');
				//System.out.println(spl1[0]);

				if(spl2[0].compareTo("FLASH")==0){
					//System.out.println("GO: "+spl2[1]);
					spl2 = split(spl2[1], '-');
					int id = Integer.parseInt(spl2[0]);
					spl2 = split(spl2[1], ',');
					int value = Integer.parseInt(spl2[0]);
					//System.out.println("FLASH mit "+id+" value: "+value);
					flashArray [id-1]=value;
					flashActive = true;
					flashTimer = millis();
				}
				if(spl2[0].compareTo("WII")==0){
					//System.out.println("GO");
					spl2 = split(spl2[1], '-');
					int id = Integer.parseInt(spl2[0]);
					spl2 = split(spl2[1], ':');
					int[] posX = new int[spl2.length];
					int[] posY = new int[spl2.length];
					String output = "WII mit "+id+" ";
					for (int i = 0; i < spl2.length; i++) {
						String[] spl3 = split(spl2[i], ',');
						if (spl2.length >= 2) {
							posX[i] = parseWithDefault(spl3[0], 0);
							posY[i] = parseWithDefault(spl3[1], 0);
						}
						output += posX[i]+":"+posY[i]+",";
					}
					//System.out.println("OUTPUT: "+output);
					sensorList.get(id-1).setWi(posX, posY); 
				}
				
			}
		} catch (Exception e) {
			println("Initialization exception");
		}
	}
	
	// Auxiliary function for parsing Arduino Data
	public static int parseWithDefault(String number, int defaultVal) {
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return defaultVal;
		}
	}
	
	@SuppressWarnings("deprecation")
	public void controlEvent(ControlEvent theEvent) {
		
		if (theEvent.isFrom(checkBoxSensorList.get(0))) {
			if(checkBoxSensorArray[0][0] != checkBoxSensorList.get(0).getArrayValue(0)){
				flashActiveArray[0] =! flashActiveArray[0];
				System.out.println("SENSOR0"+checkBoxSensorList.get(0).getArrayValue(0));
				//sendArduinoFlash(myPort);
				sensorList.get(0).inactiveFlash = !sensorList.get(0).inactiveFlash;
			} else if(checkBoxSensorArray[0][1] != checkBoxSensorList.get(0).getArrayValue(1)){
				wiActiveArray[0] =! wiActiveArray[0];
				System.out.println("SENSOR0"+checkBoxSensorList.get(0).getArrayValue(1));
				//sensorList.get(0).setState(false);
				//sendArduinoWi(myPort);
				sensorList.get(0).inactiveWi = !sensorList.get(0).inactiveWi;
				System.out.println(sensorList.get(0).inactiveWi);
			}
			checkBoxSensorArray[0] = checkBoxSensorList.get(0).getArrayValue();
		} else if (theEvent.isFrom(checkBoxSensorList.get(1))) {
			if(checkBoxSensorArray[1][0] != checkBoxSensorList.get(1).getArrayValue(0)){
				flashActiveArray[1] =! flashActiveArray[1];
				System.out.println("SENSOR1"+checkBoxSensorList.get(1).getArrayValue(0));
				//sendArduinoFlash(myPort);
				sensorList.get(1).inactiveFlash = !sensorList.get(1).inactiveFlash;
			} else if(checkBoxSensorArray[1][1] != checkBoxSensorList.get(1).getArrayValue(1)){
				wiActiveArray[1] =! wiActiveArray[1];
				System.out.println("SENSOR0"+checkBoxSensorList.get(1).getArrayValue(1));
				//sensorList.get(1).setState(false);
				//sendArduinoWi(myPort);
				sensorList.get(1).inactiveWi = !sensorList.get(1).inactiveWi;
			}
			checkBoxSensorArray[1] = checkBoxSensorList.get(1).getArrayValue();
		} else if (theEvent.isFrom(checkBoxSensorList.get(2))) {
			if(checkBoxSensorArray[2][0] != checkBoxSensorList.get(2).getArrayValue(0)){
				flashActiveArray[2] =! flashActiveArray[2];
				System.out.println("SENSOR2"+checkBoxSensorList.get(2).getArrayValue(0));
				//sendArduinoFlash(myPort);
				sensorList.get(2).inactiveFlash = !sensorList.get(2).inactiveFlash;
			} else if(checkBoxSensorArray[2][1] != checkBoxSensorList.get(2).getArrayValue(1)){
				wiActiveArray[2] =! wiActiveArray[2];
				System.out.println("SENSOR0"+checkBoxSensorList.get(2).getArrayValue(1));
				//sensorList.get(2).setState(false);
				//sendArduinoWi(myPort);
				sensorList.get(2).inactiveWi = !sensorList.get(2).inactiveWi;
			}
			checkBoxSensorArray[2] = checkBoxSensorList.get(2).getArrayValue();
		} else if (theEvent.isFrom(checkBoxSensorList.get(3))) {
			if(checkBoxSensorArray[3][0] != checkBoxSensorList.get(3).getArrayValue(0)){
				flashActiveArray[3] =! flashActiveArray[3];
				System.out.println("SENSOR3"+checkBoxSensorList.get(3).getArrayValue(0));
				//sendArduinoFlash(myPort);
				sensorList.get(3).inactiveFlash = !sensorList.get(3).inactiveFlash;
			} else if(checkBoxSensorArray[3][1] != checkBoxSensorList.get(3).getArrayValue(1)){
				wiActiveArray[3] =! wiActiveArray[3];
				System.out.println("SENSOR0"+checkBoxSensorList.get(3).getArrayValue(1));
				//sensorList.get(3).setState(false);
				//sendArduinoWi(myPort);
				sensorList.get(3).inactiveWi = !sensorList.get(3).inactiveWi;
			}
			checkBoxSensorArray[3] = checkBoxSensorList.get(3).getArrayValue();
		} else if (theEvent.isFrom(checkBoxSensorList.get(4))) {
			if(checkBoxSensorArray[4][0] != checkBoxSensorList.get(4).getArrayValue(0)){
				flashActiveArray[4] =! flashActiveArray[4];
				System.out.println("SENSOR4"+checkBoxSensorList.get(4).getArrayValue(0));
				//sendArduinoFlash(myPort);
				sensorList.get(4).inactiveFlash = !sensorList.get(4).inactiveFlash;
			} else if(checkBoxSensorArray[4][1] != checkBoxSensorList.get(4).getArrayValue(1)){
				wiActiveArray[4] =! wiActiveArray[4];
				System.out.println("SENSOR0"+checkBoxSensorList.get(4).getArrayValue(1));
				//sensorList.get(4).setState(false);
				//sendArduinoWi(myPort);
				sensorList.get(4).inactiveWi = !sensorList.get(4).inactiveWi;
			}
			checkBoxSensorArray[4] = checkBoxSensorList.get(4).getArrayValue();
		}
		
		if(theEvent.getGroup().getName() == "checkBox") {
		    print("got an event from "+theEvent.getName()+"\t");
		    //System.out.println(theEvent.getGroup().getArrayValue().length);
		    if(checkbox_array[0] != theEvent.getGroup().getArrayValue(0)) {
		    	//activeArray[0] =! activeArray[0];
		    	System.out.println("BUTTON1");
		    } else if(checkbox_array[1] != theEvent.getGroup().getArrayValue(1)) {
		    	//activeArray[1] =! activeArray[1];
		    	System.out.println("BUTTON2");
		    } else if(checkbox_array[2] != theEvent.getGroup().getArrayValue(2)) {
		    	//activeArray[2] =! activeArray[2];
		    	System.out.println("BUTTON3");
		    } else if(checkbox_array[3] != theEvent.getGroup().getArrayValue(3)) {
		    	//activeArray[3] =! activeArray[3];
		    	System.out.println("BUTTON4");
		    } else if(checkbox_array[4] != theEvent.getGroup().getArrayValue(4)) {
		    	//activeArray[4] =! activeArray[4];
		    	System.out.println("BUTTON5");
		    } else if(checkbox_array[5] != theEvent.getGroup().getArrayValue(5)) {
		    	//activeArray[5] =! activeArray[5];
		    	System.out.println("BUTTON6");
		    } else if(checkbox_array[6] != theEvent.getGroup().getArrayValue(6)) {
		    	//activeArray[6] =! activeArray[6];
		    	System.out.println("BUTTON7");
		    } else if(checkbox_array[7] != theEvent.getGroup().getArrayValue(7)) {
		    	//activeArray[7] =! activeArray[7];
		    	System.out.println("BUTTON8");
		    } else if(checkbox_array[8] != theEvent.getGroup().getArrayValue(8)) {
		    	//activeArray[8] =! activeArray[8];
		    	System.out.println("BUTTON9");
		    } else if(checkbox_array[9] != theEvent.getGroup().getArrayValue(9)) {
		    	//activeArray[9] =! activeArray[9];
		    	System.out.println("BUTTON10");
		    } else if(checkbox_array[10] != theEvent.getGroup().getArrayValue(10)) {
		    	//activeArray[10] =! activeArray[10];
		    	System.out.println("BUTTON11");
		    } else if(checkbox_array[11] != theEvent.getGroup().getArrayValue(11)) {
		    	//activeArray[11] =! activeArray[11];
		    	System.out.println("BUTTON12");
		    } else if(checkbox_array[12] != theEvent.getGroup().getArrayValue(12)) {
		    	//activeArray[12] =! activeArray[12];
		    	System.out.println("BUTTON13");
		    }
		    checkbox_array = checkbox.getArrayValue();
			scp.clearSysA();
			scp.clearSysB();
		    
		    
		}
		//println("\t "+theEvent.getValue());
		//System.out.println("ACTIVE_ARRAY: "+activeArray[0]+" "+activeArray[1]);
		
	
		//System.out.println(theEvent.getGroup().getName());
		
	
	}
	
	public void sendArduinoFlash(Serial myPort){
		System.out.println("UPDATE FLASH TO ARDUINO");
		String flash = "UPDATE-FLASH:";
		for(int i=0; i<flashActiveArray.length; i++){
			if(flashActiveArray[i]==true){
				flash += "true,";
			}else{
				flash += "false,";
			}
		}
		flash += '\n';
		myPort.write(flash);
		System.out.println(flash);
	}
	
	public void sendArduinoWi(Serial myPort){
		System.out.println("UPDATE WI TO ARDUINO");
		String wi = "UPDATE-WI:";
		for(int i=0; i<wiActiveArray.length; i++){
			if(wiActiveArray[i]==true){
				wi += "true,";
			}else{
				wi += "false,";
			}
		}
		wi += '\n';
		myPort.write(wi);
		System.out.println(wi);
	}
	
	public void sendArduinoSetup(Serial myPort){
		System.out.println("UPDATE SETUP TO ARDUINO");
		String setup = "UPDATE-SETUP:SETUP\n";
		myPort.write(setup);
		System.out.println(setup);
	}

		// function buttonA will receive changes from 
		// controller with name buttonA
	public void OK(int theValue) {
		  println("a button event from buttonA: "+theValue);
		  scp.setIP_ADRESS(cp5.get(Textfield.class,"IP").getText());
		  scp.setPORT(Integer.parseInt(cp5.get(Textfield.class,"PORT").getText()));
		  int[] id = {Integer.parseInt(cp5.get(Textfield.class,"ID-1").getText()),
				      Integer.parseInt(cp5.get(Textfield.class,"ID-2").getText())};
		  scp.setID(id);
	}
	
	public void SETUP(int theValue) {
		sendArduinoSetup(myPort);
	}
	
	public void mousePressed(){
		  animationChanger1.add(new AnimationChanger(this, scp));
	}
	
	public void ArduinoZeitschaltUhr(){
		Date cTime = new Date();
		  if(cTime.compareTo(dWi)<0){
			  if(allWi==true && !once){
				  System.out.println("KLEINER");
				  //for(int i=0; i<wiActiveArray.length; i++){
				  //	  wiActiveArray[i] = false;
				  //}
				  //sendArduinoWi(myPort);
				  allWi = false;
				  for(Sensor s : sensorList){
					  s.inactiveWi = true;
				  }
			  }
			  }
				  
		  else{
			  if(allWi==false){
				  System.out.println("GRÖSSER");
				  //for(int i=0; i<wiActiveArray.length; i++){
				  //	  wiActiveArray[i] = true;
				  //}
				  //sendArduinoWi(myPort);
				  allWi = true;
				  for(Sensor s : sensorList){
					  s.inactiveWi = false;
				  }
			  }
		  }

		  if(cTime.compareTo(dFlash)<0){
			  if(allFlash==true && !once){
				  System.out.println("KLEINER");
				  //for(int i=0; i<flashActiveArray.length; i++){
				  //	  flashActiveArray[i] = false;
				  //}
				  //sendArduinoFlash(myPort);
				  allFlash = false;
				  for(Sensor s : sensorList){
					  s.inactiveFlash = true;
				  }
			  }
		  }else{
			  if(allFlash==false){
				  System.out.println("GRÖSSER");
				  //for(int i=0; i<flashActiveArray.length; i++){
				  //	  flashActiveArray[i] = true;
				  //}
				  //sendArduinoFlash(myPort);
				  allFlash = true;
				  for(Sensor s : sensorList){
					  s.inactiveFlash = false;
				  }
			  }
		  }
		  
		  once = true;
	}
	
	
	public void Manuell(boolean theFlag) {
		  if(theFlag){
			  manuell = true;
			  checkbox_array = checkbox.getArrayValue();
		  }
		  else{
			  manuell = false;
		  }
		  //	  checkbox_array = checkbox.getArrayValue();
		  //}
		  println("a toggle event."+theFlag);
		}


}