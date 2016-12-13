#include "person.h"
#include "astar.h"
#include "exception.h"
#include <iostream>
using namespace std;

int main(int argc, char const *argv[])
{
	int m,s,k;
	while(true) {
		cout << "please input the number of missionaries, savages and the capacity of the boat, separate with a space:\n";
		if(bool(cin >> m >> s >> k) == false) {
			break;
		}
		CPersonState::iTotalMissionary = m;
		CPersonState::iTotalSavage = s;
		CPersonState::iBoatCapacity = k;
		CPersonState start, end;
		try {
			start.init(m, s, CPersonState::THIS_SHORE);
			end.init(m, s, CPersonState::OPPOSITE_SHORE);
		} catch(const CException &ex) {
			cerr << ex.code << ": " << ex.msg << "\n";
		} catch(...) {
			break;
		}
		CAstar game(start, end);
		game.run();
		if(game.bCanSolve == true) {
			cout << "your game can be solve:\n";
			cout << "the total steps is: " << game.iSteps << "\n";
			cout << "and the path is:\n";
			int len = game.vecSolve.size();
			cout << *((CPersonState*)(game.vecSolve[len - 1])) << "\n";
			for(int i = len - 2; i >= 0; --i) {
				cout << "\n    |\n";
				auto pstate = (CPersonState*)(game.vecSolve[i]);
				cout << "    |   (" << pstate->iMoveMissionary << ", " << pstate->iMoveSavage << ")\n";
				cout << "   \\|/\n\n";
				cout << *((CPersonState*)(game.vecSolve[i])) << "\n";
			}
		} else {
			cout << "sorry, your game can't be solve, please input another state.\n\n";
		}
		cout << "the total steps is: " << game.iSteps << "\n";
		cout << "and the max states is: " << game.iTotalStates << "\n";
		cout << "and the runtime is: " << game.lRunTime << "\n";
	}
	return 0;
}