package main;

import java.util.ArrayList;

import tools.MyException;

public class PersonState extends State {
	
	static public int totalMissionary;	// the total number of missionaries
	static public int totalSavage;		// the total number of savages
	static public int boatCapacity;		// the capacity of the boat
	
	private int missionary;		// the number of missionaries in the shore where boat anchors
	private int savage;			// the number of savages in the shore where boat anchors
	private int boatPosition;		// the position of boat, this shore or opposite shore
	
	public static final int THIS_SHORE = 1;
	public static final int OPPOSITE_SHORE = 2;
	
	public int moveMissionary;
	public int moveSavage;

	public PersonState() {
		super();
	}
	
	public void init(int _m, int _s, int _b) throws MyException {
		this.missionary = _m;
		this.savage = _s;
		this.boatPosition = _b;
		this.moveMissionary = moveSavage = 0;

		if(totalMissionary == -1) {
			throw new MyException(101, "the total number of missionaries has not been initialized.");
		}
		if(totalSavage == -1) {
			throw new MyException(102, "the total number of savages has not been initialized.");
		}
		if(boatCapacity == -1) {
			throw new MyException(103, "the capacity of the boat has not been initialized.");
		}
		if(missionary > totalMissionary) {
			throw new MyException(104, "the number of missionaries on this shore exceeded the total number.");
		}
		if(savage > totalSavage) {
			throw new MyException(105, "the number of savages on this shore exceeded the total number.");
		}
		if(missionary != 0 && missionary < savage) {
			throw new MyException(106, "the number of missionaries can\'t be less then the number of savages.");
		}
		if(boatPosition != THIS_SHORE && boatPosition != OPPOSITE_SHORE) {
			throw new MyException(107, "the value of iBoat is invalid, which must be CPersonState::THIS_SHORE or CPersonState::OPPOSITE_SHORE, you can use 1 or 2 certainly.");
		}
	}

	@Override
	public String toString() {
		return "PersonState [missionary=" + missionary + ", savage=" + savage + ", boatPosition=" + boatPosition
				+ ", moveMissionary=" + moveMissionary + ", moveSavage=" + moveSavage + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + boatPosition;
		result = prime * result + missionary;
		result = prime * result + moveMissionary;
		result = prime * result + moveSavage;
		result = prime * result + savage;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonState other = (PersonState) obj;
		if (boatPosition != other.boatPosition)
			return false;
		if (missionary != other.missionary)
			return false;
		if (savage != other.savage)
			return false;
		return true;
	}

	@Override
	public ArrayList<State> getNextState() {
		ArrayList<State> nextState = new ArrayList<>();
		int oppo_m = totalMissionary - missionary;
		int oppo_s = totalSavage - savage;
		int mk = Math.min(missionary, boatCapacity);
		int sk = Math.min(savage, boatCapacity);
		for(int x = 0; x <= mk; ++x) {
			for(int y = 0; y <= sk; ++y) {
				if(x == 0 && y == 0)	continue;
				if(missionary - x != 0 && missionary - x < savage - y)	continue;
				if(x + y > boatCapacity || (x != 0 && y > x) )	break;
				if(oppo_m + x != 0 && oppo_m + x < oppo_s + y)	break;
				PersonState _next = new PersonState();
				_next.init(oppo_m + x, oppo_s + y, 3 - boatPosition);
				_next.moveMissionary = x;
				_next.moveSavage = y;
				nextState.add(_next);
			}
		}
		return nextState;
	}

	@Override
	public long astar_g() {
		int remain_num;//, transport_num;
		if(boatPosition == THIS_SHORE) {
			remain_num = missionary + savage;
		} else {
			remain_num = (totalMissionary - missionary) + (totalSavage - savage);
		}
		return remain_num;// + transport_num;
	}

}
