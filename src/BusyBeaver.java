import java.util.Scanner;
import java.util.ArrayList;
public class BusyBeaver {
	public static int loc=4, stateNum=1, countStep=0, restTime=575;
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to the Busy Beaver Game!");
		System.out.println("State 0 is predefined as the halt state. Default rest time between steps is " + restTime + " milliseconds.");
		System.out.println("-----------------------------------------------------------------------------------------------");
		System.out.print("How many states to define? ");
		int numStates = input.nextInt(); input.nextLine();
		if(numStates>3){
			System.out.print("Rest time between steps?(In milliseconds): ");
			restTime = input.nextInt(); input.nextLine();
		}
		int countOne = 0;
		ArrayList<BState> stateList = new ArrayList<BState>();
		System.out.println("Define each state...");
		stateList.add(0, null);
		for(int i=0; i<numStates; i++){
			System.out.print("Define state " + (i+1) + ":\n0 behavior: ");
			String b0 = new String(input.nextLine());
			System.out.print("1 behavior: ");
			String b1 = new String(input.nextLine());
			stateList.add(new BState(b0, b1));
		}
		System.out.println("DISPLAY SPACE BELOW..."); try{Thread.sleep(250);}catch(Exception e){e.printStackTrace();}
		System.out.println("-----------------------"); try{Thread.sleep(275);}catch(Exception e){e.printStackTrace();}
		ArrayList<Boolean> tapeList = new ArrayList<Boolean>();
		for(int i=0; i<9; i++){
			tapeList.add(new Boolean(false));
		}
		do{
			displayRefresh(tapeList, true);
			countStep++;
		}while(iterate(tapeList, stateList)==true);
		displayRefresh(tapeList, true);
		for(Boolean place : tapeList){
			if(place.booleanValue()==true) countOne++;
		}
		input.close();
		System.out.println("Your set achieved " + countOne + " \"1's\" in " + countStep + " steps.");
	}
	public static void displayArrow(){
		System.out.println("\t\t \t \t \t \t^");
	};
	public static void displayRefresh(ArrayList<Boolean> tapeList, Boolean displayCount){
		if(loc<=3){
			loc++;
			tapeList.add(0, new Boolean(false));
		}
		if((loc+5)==tapeList.size()) tapeList.add(new Boolean(false));
		System.out.print("\t\t");
		for(int i=-4; i<5; i++){
			if(tapeList.get(loc+i).booleanValue()==false)
				System.out.print("0\t");
			else System.out.print("1\t");
		}
		if(displayCount) System.out.print("step: " + countStep);
		System.out.println();
		displayArrow();
		try{
			Thread.sleep(restTime);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	};
	public static boolean iterate(ArrayList<Boolean> tapeList, ArrayList<BState> stateList){
		String inst;
		if(tapeList.get(loc).booleanValue()==false) inst = stateList.get(stateNum).getB0();
		else inst = stateList.get(stateNum).getB1();
		if(inst.startsWith("0")&&(tapeList.get(loc).booleanValue()==true))
			tapeList.set(loc, new Boolean(false));
		else if(inst.startsWith("1")&&(tapeList.get(loc).booleanValue()==false))
			tapeList.set(loc, new Boolean(true));
		displayRefresh(tapeList, false);
		if(inst.charAt(1)=='0') loc--;
		else loc++;
		stateNum = Integer.parseInt(inst.substring(2));
		if(stateNum==0) return false;
		return true;
	};
}
