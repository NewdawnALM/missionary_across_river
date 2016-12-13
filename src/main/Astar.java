package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Comparator;
import java.util.HashMap;

import tools.MyException;
import tools.TimeValue;

/**
 * astar 算法主体类
 */
public class Astar {
	
	public State start;
	public State end;
	public boolean canSolve;
	public int steps;
	public ArrayList<State> vecSolve;
	public long runTime;
	public int totalStates;
	
	public Astar(State start, State end) throws MyException {
		super();
		this.start = start;
		this.end = end;
		this.canSolve = false;
		this.steps = 0;
		this.vecSolve = new ArrayList<State>();
		this.runTime = 0;
		this.totalStates = 0;
		start.checkSomeFields(end);
	}
	
	static Set<State> getStateByStartAndSteps(State start, int steps) {
		Set<State> retSet = new HashSet<>();
		// 以后再补充，懒得把c++代码翻译了
		return retSet;
	}
	
	void run() {
		TimeValue _time = new TimeValue();
		Map<State, State> mapState = new HashMap<>();
		mapState.put(this.start, this.start);
		// 最小堆
		Queue<State> queState = new PriorityQueue<>(new Comparator<State>() {
			@Override
			public int compare(State o1, State o2) {
				long diff = o1.astar_h() - o2.astar_h();
				return diff == 0 ? 0: (diff > 0 ? 1: -1);
			}
		});
		queState.add(this.start);
		
		while(!queState.isEmpty()) {
			State headState = queState.poll();
			if(headState.equals(this.end)) {
				this.canSolve = true;
				this.steps = headState.steps;
				this.vecSolve.add(headState);
				State lastState = headState.parent;
				while(lastState != null) {
					this.vecSolve.add(lastState);
					lastState = lastState.parent; 
				}
				break;
			}
			ArrayList<State> nextState = headState.__getNextState();
			for(State _next: nextState) {
				State state = mapState.get(_next);
				if(state == null) {
					queState.add(_next);
					mapState.put(_next, _next);
				} else {
					if(state.astar_f() > _next.astar_f()) {
						mapState.remove(_next);
						mapState.put(_next, _next);
						queState.add(_next);
					}
				}
			}
			if(mapState.size() > 3000 * 10000) {
				break;
			}
		}
		this.totalStates = mapState.size();
		this.runTime = _time.costTime();
	}
}
