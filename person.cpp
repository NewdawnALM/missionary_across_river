#include "person.h"
#include "exception.h"
#include <algorithm>
using std::min;
using std::max;
#define  For(i,s,t)  for(auto i = (s); i != (t); ++i)

int CPersonState::iTotalMissionary = -1;
int CPersonState::iTotalSavage = -1;
int CPersonState::iBoatCapacity = -1;

CPersonState::CPersonState() {}

void CPersonState::init(int _m, int _s, int _b)
{
	iMissionary = _m;
	iSavage = _s;
	iBoatPosition = _b;
	iMoveMissionary = iMoveSavage = 0;

	if(iTotalMissionary == -1) {
		throw CException(101, "the total number of missionaries has not been initialized.");
	}
	if(iTotalSavage == -1) {
		throw CException(102, "the total number of savages has not been initialized.");
	}
	if(iBoatCapacity == -1) {
		throw CException(103, "the capacity of the boat has not been initialized.");
	}
	if(iMissionary > iTotalMissionary) {
		throw CException(104, "the number of missionaries on this shore exceeded the total number.");
	}
	if(iSavage > iTotalSavage) {
		throw CException(105, "the number of savages on this shore exceeded the total number.");
	}
	if(iMissionary && iMissionary < iSavage) {
		throw CException(106, "the number of missionaries can\'t be less then the number of savages.");
	}
	if(iBoatPosition != CPersonState::THIS_SHORE && iBoatPosition != CPersonState::OPPOSITE_SHORE) {
		throw CException(107, "the value of iBoat is invalid, which must be CPersonState::THIS_SHORE \
							or CPersonState::OPPOSITE_SHORE, you can use 1 or 2 certainly.");
	}
}

bool CPersonState::operator < (const CState &rhs) const
{
	const CPersonState* const prhs = (CPersonState*)&rhs;
	if(iMissionary == prhs->iMissionary) {
		if(iSavage == prhs->iSavage) {
			return iBoatPosition < prhs->iBoatPosition;
		}
		return iSavage < prhs->iSavage;
	}
	return iMissionary < prhs->iMissionary;
}

using std::cin;
using std::cout;

vector<CState*> CPersonState::getNextState() const
{
	vector<CState*> nextState;	
	int oppo_m = iTotalMissionary - iMissionary;
	int oppo_s = iTotalSavage - iSavage;
	int mk = min(iMissionary, iBoatCapacity);
	int sk = min(iSavage, iBoatCapacity);
	For(x, 0, mk + 1) {
		For(y, 0, sk + 1) {
			if(!x && !y)	continue;
			if(iMissionary - x != 0 && iMissionary - x < iSavage - y)	continue;
			if(x + y > iBoatCapacity || (x && y > x) )	break;
			if(oppo_m + x != 0 && oppo_m + x < oppo_s + y)	break;
			CPersonState *_next = new CPersonState();
			// _next->init(iMissionary - x, iSavage - y, 3 - iBoatPosition);
			_next->init(oppo_m + x, oppo_s + y, 3 - iBoatPosition);
			_next->iMoveMissionary = x;
			_next->iMoveSavage = y;
			nextState.push_back(_next);
		}
	}
	return nextState;
}

long CPersonState::astar_g() const
{
	int remain_num;//, transport_num;
	if(iBoatPosition == CPersonState::THIS_SHORE) {
		remain_num = iMissionary + iSavage;
	} else {
		remain_num = (iTotalMissionary - iMissionary) + (iTotalSavage - iSavage);
	}
	return remain_num;// + transport_num;
}

ostream& operator << (ostream &out, const CPersonState &state)
{
	out << "(" << state.iMissionary << ", " << state.iSavage << ", " << state.iBoatPosition << ")";
	return out;
}
