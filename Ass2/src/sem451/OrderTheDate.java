package sem451;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class OrderTheDate {
 Scanner sc=new Scanner(System.in);

 private LocalDate l = null ;

	public void DATE() {

	   try {l=LocalDate.parse(sc.next());}

	   catch(DateTimeParseException e) {
			System.out.println("Error,Wtite The Date Like This : 2023-09-18 ");
			l=LocalDate.parse(sc.next());
			}
         }
	public LocalDate getL() {
		return l;
	}
	public void setL(LocalDate l) {
		this.l = l;
	}
  }