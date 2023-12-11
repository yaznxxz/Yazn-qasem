package sem451;
import java.util.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.io.*;
import java.text.SimpleDateFormat;
public class KkuSystem implements OptionalReserveTasks, FileNames, ReserveTasks {
	

	static List<ReserveBlock> reservations = new ArrayList<>();
	static People people = new People();
	
	
	public void showCLIMenu() {
		
		load(); 
		Person p;
		LocalDate l = null;
		int t;
		Room r;
		boolean st;
		Scanner sc = new Scanner(System.in);
		String s;
		
		OrderTheTime time=new OrderTheTime();
		OrderTheDate o=new OrderTheDate();
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd | HH:mm:ss").format(Calendar.getInstance().getTime());
		LocalDate date = LocalDate.now();
		while(true) {
			System.out.println("\n\n\nWelcome to KKU LAB Managament System");
			System.out.println(date);
			System.out.println("=================================================");
			System.out.println("1. Make a reservation + New User");
			System.out.println("2. Update a reservation");
			System.out.println("3. Print all reservations on screen");
			System.out.println("4. Print reservations to File");
			System.out.println("5. Exit");
			System.out.println("6. Remove Reservation");
			System.out.println("7. Create New User");
			System.out.println("8. Print all users on screen");
			System.out.println("9. Remove User");
			System.out.println("10. To search a user");
			System.out.print("\nPlease Enter a number:");
			s = sc.next();
			switch(s) {
			case "1":
				try {
				System.out.println("Enter person name, id, age (press Enter after each):");
				
				p = new Person(sc.next(),sc.next(),sc.nextInt());
				
				people.addPerson(p);
				System.out.println("Enter room name:");
				r = new LabRoom(sc.next());
				System.out.println("Enter Date in yyyy-mm-dd:");
				o.DATE();
				System.out.println("At what Clock 1-24 (Only 1 hour can be booked)?");
				time.TIME();
				st=this.reserveBlock(new ReserveBlock(p,o.getL(),time.getT(),r));
				}
				catch(Exception e) {
					
				}
				break;
				
				
			case "2":
				System.out.println("Not working. Please remove and Add again.");
				break;
				
				
			case "3":
				this.printReservedBlocks(reservations);
				break;
				
				
			case "4":
				this.exportToFile2();
				break;
				
				
			case "5":
				sc.close();
				System.out.println("Saving...");
				save();
				System.out.println("Thank you.");
				System.exit(0);
				
			case "6":
				p = new Person("test","test",0);
				System.out.println("Enter room name:");
				r = new LabRoom(sc.next());
				System.out.println("Enter Date in yyyy-mm-dd:");
				o.DATE();
				System.out.println("At what Clock 1-24 (Only 1 hour can be booked)?");
				time.TIME();
				
				st=this.removeBlock(new ReserveBlock(null,o.getL(),time.getT(),r));
				break;
			
			case "7":
				try {
				System.out.println("Enter person name, id, age (press Enter after each):");
				people.addPerson(sc.next(), sc.next(), sc.nextInt());
				}
				catch(Exception e) {
					
				}
				break;
				
			case "8":
				people.printPeople();
				break;
				
			case "9":
				System.out.println("Enter user id to remove:");
				people.removePerson(sc.next());
				break;
			case "10":
			try {
                System.out.println("Enter person name, id, age (press Enter after each):");
				p = new Person(sc.next(),sc.next(),sc.nextInt());
				System.out.println(findReservedBlocksBy(p));
			}
			catch(Exception e) {
				
			}
			break;
			default:
				System.err.println("Wrong choice!\n");
				
			}
		}
		
	}
	public static void main(String args[]) {
		reservations.add(new ReserveBlock(new Person("Ahmad","0",0),LocalDate.parse("2023-12-12"),12,new LabRoom("18S")));
		new KkuSystem().showCLIMenu();
	}

	@Override
	/**
	  * Check person is not blocked
	  *	Check block's date, time, & room is not in the list
	  * then add it
	  **/
	public boolean reserveBlock(ReserveBlock rb) {
		if(this.checkExist(rb, reservations))
		{
			System.out.println("Sorry, booked in "+rb.getDate()+" at "+rb.getClock()+"!");
			return false;
		}
		else
		{
			reservations.add(rb);
			System.out.println("Done, Room "+rb.getRoom().getName()+" booked in "+rb.getDate()+" at "+rb.getClock()+".");
			return false;
		}
	}

	/**
	 * Search all list elements if there is any blocked rooms
	 * similar to the parameter return true.
	 * @param rb block you want to add
	 * @param rooms list of blocked rooms
	 * @return true if it finds a match in the list
	 */
	public boolean checkExist(ReserveBlock rb, List<ReserveBlock> rooms) {
		for(ReserveBlock room: rooms) {
			if(room.equals(rb))
				return true;
		}
		return false;
	}
	
	@Override
	public boolean updateBlock(ReserveBlock rb) {
		if(rb.getBy().isBlocked())
		{
			System.out.println("User cannot make a reservation!");
			return false;
		}
		else if(!this.checkExist(rb, reservations))
		{
			System.out.println("Sorry, no one has booked it in "+rb.getDate()+" at "+rb.getClock()+"!");
			System.out.println("Making a new reservation ...");
			return reserveBlock(rb);
		}
		else
		{
			removeBlock(rb);
			if(reserveBlock(rb))
			{
				System.out.println("Updated.");
				return true;
			}
			
			else 
			{
				System.out.println("Failed to update!");
				return false;
			}
		}
	}

	@Override
	public boolean removeBlock(ReserveBlock rb) {
		int in = -1;
		if(reservations.isEmpty()) {
			System.out.println("List is Empty!");
			return false;
		}
		for(int i =0; i<reservations.size();i++)
				{
					if(rb.getDate().equals(reservations.get(i).getDate()))
					{
						if(rb.getClock()==reservations.get(i).getClock()) 
						{
							if(rb.getRoom().getName().equalsIgnoreCase(reservations.get(i).getRoom().getName()))
							{
								in = i;
								break;
							}
						}
					}
				}
		if(in!=-1)
			{
				System.out.println("Removed "+reservations.get(in));
				reservations.remove(in);
				return true;
			}
		System.out.println("Sorry, could not find the block to remove!");
		return false;
	}

	@Override
	public void removeAllEndedBlocks() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printReservedBlocks(List<ReserveBlock> blocks) {
		if(blocks.isEmpty())
			System.out.println("No reservations have been made!");
		else
			for(ReserveBlock block:blocks) {
				System.out.println(block);
			}
		
	}

	@Override
	public boolean loadDataFromFile() {
		reservations.clear();
		ObjectInputStream o;
			try {
				o = new ObjectInputStream(new FileInputStream(SESSIONS_FILE));
				reservations=(List<ReserveBlock>) o.readObject();
				System.out.println("Finished Loading data.");
				System.out.println(reservations.size()+" session(s) imported.");
				o.close();
				return true;
			} catch (FileNotFoundException e) {
				System.out.println("File Not Found to load!");
			} catch (IOException e) {
				System.out.println("Could not load from file!");
				e.printStackTrace();
			} catch(Exception e) {
				System.out.println("Unknow error in load file!");
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean saveDataToFile() {
		if(KkuSystem.reservations.isEmpty())
		{
			System.out.println("Nothing to save!");
			return true;
		}
		else {
			ObjectOutputStream o,o2;
			try {
				o = new ObjectOutputStream(new FileOutputStream(SESSIONS_FILE));
				o.writeObject(reservations);
				o.close();
				
				o2 = new ObjectOutputStream(new FileOutputStream(PEOPLE_FILE));
				o2.writeObject(people);
				o2.close();
				return true;
			} catch (FileNotFoundException e) {
				System.out.println("File Not Found to save!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public boolean exportToFile2() {
		if(KkuSystem.reservations.isEmpty())
		{
			System.out.println("Nothing to export!");
			return true;
		}
		else {
			try {
				PrintWriter o = new PrintWriter(PRINT_FILE);
				String timeStamp = new SimpleDateFormat("yyyy-MM-dd | HH:mm:ss").format(Calendar.getInstance().getTime());
				o.println("\n================="+timeStamp+"==================");
				for(ReserveBlock bl: reservations)
				{
					o.println(bl);
				}
				o.close();
				System.out.println("Finished exporting.");
				return true;
			} catch (FileNotFoundException e) {
				System.out.println("FileNot Found to export!");
				e.printStackTrace();
			}
		}
		return false;
	}
	public void save() {
		try {
			this.saveDataToFile();
			people.saveDataToFile();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void load() {
		try {
			this.loadDataFromFile();
			people.loadDataFromFile();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	
	public List<ReserveBlock> findReservedBlocksBy(Person person) {
		List<ReserveBlock> byPerson=new ArrayList();
		for(ReserveBlock b:reservations) {
			if(b.getBy().equals(person))
				byPerson.add(b);
		}
		
		return byPerson;
	}

}