
/*************************************************************************
 * 
 * Process contains process information such as process arrival time
 * expected run time, burst time and priority. 
 * 
 * @author Manzoor Ahmed
 * @author Igor Sorokin
 * @author 
 * 
 * @version 1.0
 * @data 06/17/2013
 ************************************************************************/

public class Process {

	String name;
	int burstTime;
	float arrivalTime; 		//between 0 -100
	int expectedRunTime; 	//between 0-10
	int priority;   		//between 1 4
	
	public float getArrivalTime() {
		return arrivalTime;
	}
	
	public void setArrivalTime(float arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int getExpectedRunTime() {
		return expectedRunTime;
	}
	public void setExpectedRunTime(int expectedRunTime) {
		this.expectedRunTime = expectedRunTime;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setName(String name){
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Process [arrivalTime=" + arrivalTime + ", expectedRunTime="
				+ expectedRunTime + ", priority=" + priority + "]";
	}	
}
