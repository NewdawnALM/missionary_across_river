package main;

import java.util.ArrayList;

import tools.MyException;

/**
 * 表示状态的抽象类
 */
public abstract class State {

	public int steps;
	public State parent;
	
	public State() {
		super();
		this.steps = 0;
		this.parent = null;
	}

	abstract public int hashCode();

	abstract public boolean equals(Object obj);
	
	public void checkSomeFields(State rhs) throws MyException {}
	
	abstract public ArrayList<State> getNextState();
	
	public ArrayList<State> __getNextState() {
		ArrayList<State> nextState = this.getNextState();
		for(State st: nextState) {
			st.steps = this.steps + 1;
			st.parent = this;
		}
		return nextState;
	}
	
	public long astar_f() {
		return this.steps;
	}
	
	abstract public long astar_g();
	
	public long astar_h() {
		return this.astar_f() + this.astar_g();
	}
}
