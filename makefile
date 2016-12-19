CC = g++
COMOPT = -std=c++11 -g

INCLUDEDIR = -I./tools

LIBDIR = -L./tools
LIBS = -ltools
LINK = $(LIBDIR) $(LIBS)

# OBJS = $(patsubst %.cpp, %.o, $(wildcard *.cpp))
OBJS += person.o astar.o state.o

OUTPUT += across_river

all: $(OUTPUT)

across_river: $(OBJS) main.o
	make -C tools
	$(CC) -o $@ $^ $(LINK)

%.o: %.cpp
	$(CC) -o $@ -c $< $(COMOPT) $(INCLUDEDIR)

clean:
	make clean -C tools
	rm -f *.o
	rm -f $(OUTPUT)
