JFLAGS = -g
JC = javac
.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGs) $*.java

CLASSES = \
		Building.java \
		MinHeap.java \
		RisingCity.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class