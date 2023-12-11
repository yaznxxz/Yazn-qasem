package sem451;
import java.util.*;



public class OrderTheTime {
	Scanner sc=new Scanner(System.in);
	private int t;

	public int getT() {
		return t;
	}
	public void setT(int t) {
		this.t = t;
	}


	public void TIME() {

		t=sc.nextInt();
	    if(t>=25) {
	    	System.out.println("Error,Wtite The Time From 1 To 24");
		   	t=sc.nextInt();  
		}
	}
}