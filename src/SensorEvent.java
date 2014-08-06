import java.sql.Timestamp;


public class SensorEvent {
	
	private int type;
	private Timestamp tstamp;

	public SensorEvent(int type){
		this.type=type;
		this.tstamp = new Timestamp(System.currentTimeMillis());
	}
	
	public String toString(){
		return "Type: "+type+", "+"TimeStamp: "+tstamp;
	}
}
