import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PGraphics;


public abstract class Layer {

	LinkedList<Nozzle> nozzlePath;
	PGraphics pg;
	PApplet p;
	Pavillon scp;
	
	public Layer(PApplet p, Pavillon scp, LinkedList<Nozzle> nozzlePath) {
		this.p = p;
		this.scp = scp;
		this.nozzlePath = nozzlePath;
	}
	
	public abstract PGraphics getLayer();
	
	public abstract void add();
}
