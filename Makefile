JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

# This uses the line continuation character (\) for readability
# You can list these all on a single line, separated by a space instead.
# If your version of make can't handle the leading tabs on each
# line, just remove them (these are also just added for readability).
CLASSES = \
	TCPClient1.java \
	TCPServer.java \
	createfile1.java 

default: classes

run_srvr:
	java TCPServer

run_clnt:
	java TCPClient1

run_crtFile:
	java createfile1

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
