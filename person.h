#ifndef  __PERSON_H
#define  __PERSON_H

#include "state.h"
#include <iostream>
using std::ostream;

class CPersonState: public CState
{
	friend ostream& operator << (ostream&, const CPersonState&);
public:
	CPersonState();
	virtual bool operator < (const CState &) const;
	virtual vector<CState*> getNextState() const;
	virtual long astar_g() const;
	void init(int, int, int);
public:
	static int iTotalMissionary;	// the total number of missionaries
	static int iTotalSavage;		// the total number of savages
	static int iBoatCapacity;		// the capacity of the boat
private:
	int iMissionary;		// the number of missionaries in the shore where boat anchors
	int iSavage;			// the number of savages in the shore where boat anchors
	int iBoatPosition;		// the position of boat, this shore or opposite shore
public:
	enum POSITION
	{
		THIS_SHORE = 1, OPPOSITE_SHORE
	};
	int iMoveMissionary;
	int iMoveSavage;
};

#endif
