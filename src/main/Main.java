package main;

import java.util.Scanner;

import tools.MyException;

public class Main {

	public static void main(String[] args) {
		int m = 0, s = 0, k = 0;
		Scanner cin = new Scanner(System.in);
		while(true) {
			System.out.println("please input the number of missionaries, savages and the capacity of the boat, separate with a space:");
			try {
				m = cin.nextInt();
				s = cin.nextInt();
				k = cin.nextInt();
			} catch(Exception ex) {
				System.out.println(ex.toString() + "\nbye~");
				break;
			}
			PersonState.totalMissionary = m;
			PersonState.totalSavage = s;
			PersonState.boatCapacity = k;
			PersonState start = new PersonState();
			PersonState end = new PersonState();
			try {
				start.init(m, s, PersonState.THIS_SHORE);
				end.init(m, s, PersonState.OPPOSITE_SHORE);
			} catch (MyException ex) {
				System.out.println(ex.toString());
			} catch(Exception ex) {
				break;
			}
			Astar game = new Astar(start, end);
			game.run();
			if(game.canSolve == true) {
				System.out.println("your game can be solve:");
				System.out.println("the total steps is: " + game.steps);
				System.out.println("and the path is:\n");
				int len = game.vecSolve.size();
				System.out.println(game.vecSolve.get(len - 1).toString());
				for(int i = len - 2; i >= 0; --i) {
					System.out.println("\n    |");
					PersonState personState = (PersonState)game.vecSolve.get(i);
					System.out.println("    |   (" + personState.moveMissionary + ", " + personState.moveSavage + ")");
					System.out.println("   \\|/\n");
					System.out.println(personState.toString());
				}
				System.out.println();
			} else {
				System.out.println("sorry, your game can't be solve, please input another state.\n");
			}
			System.out.println("the total steps is: " + game.steps);
			System.out.println("and the max states is: " + game.totalStates);
			System.out.println("and the runtime is: " + game.runTime + "\n");
		}
		cin.close();
	}

}
