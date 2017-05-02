import sys
import os

path = sys.argv[1]

#out = open(path+"out",'a');
os.chdir(path)
#out.write("test")
for filename in os.listdir(path):
	if os.path.isdir(filename):
		#print "isdir";
		continue;
	f = open(filename,'r');
	for line in f:
		if '#include' in line:
			print "\""+line[10:-2]+"\"" + "->" + "\"" + filename + "\"";
			#print "\"" + filename + "\"" + "[shape=circle, style=filled, fillcolor=red]"
			#out.write(line[11:-2])
	f.close();

#out.flush();

