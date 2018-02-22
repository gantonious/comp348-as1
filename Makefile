PACKAGE_NAME = comp346_as1_3364768

SOURCE_PATH = ./src
OUTPUT_PATH = ./out/production/as1

PART1_MAIN = ./src/part1/Main.java
PART2_MAIN = ./src/part2/Main.java
REGISTER_MAIN = ./src/filetransfer/RegisterUserMain.java

WEB_LOG_PATH =
STATS_OPTION =
URL =
FILTER =

all: java

java:
	mkdir -p $(OUTPUT_PATH)
	javac -sourcepath $(SOURCE_PATH) -d $(OUTPUT_PATH) $(PART1_MAIN) $(PART2_MAIN)

runMyPooledWebLog:
	@java -classpath $(OUTPUT_PATH) part1.Main $(WEB_LOG_PATH) $(STATS_OPTION)


runSourceViewer:
	@java -classpath $(OUTPUT_PATH) part2.Main $(URL) $(FILTER)

clean:
	rm -rf $(OUTPUT_PATH)

package:
	@mkdir -p $(PACKAGE_NAME)
	@cp -r Makefile $(SOURCE_PATH) *.md *.txt $(PACKAGE_NAME)/
	@zip -r $(PACKAGE_NAME).zip $(PACKAGE_NAME)/
	@rm -rf $(PACKAGE_NAME)