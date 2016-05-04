

all: class
	javac util/*.java -d class -cp class
	javac *.java -d class/ -cp class

class:
	mkdir -p class

run:
	java -cp class/ cvrp.ClarkWright
