# Makefile to zip specific files for HW2

ZIP_FILE=crawl.zip
JAVA_FILES=$(wildcard src/main/java/com/will/**/*.java)
DATA_FILES=$(wildcard data/*.csv)
TEXT_FILES=$(wildcard data/*.txt)

all: clean zip

zip:
	@if [ -n "$(DATA_FILES)" ]; then \
		echo "Zipping CSV files and TXT files and excluding .DS_Store..."; \
		zip -j $(ZIP_FILE) $(DATA_FILES) $(TEXT_FILES) -x "*.DS_Store"; \
		echo "Done! Please submit $(realpath $(ZIP_FILE))"; \
	else \
		echo "No files to zip!"; \
		exit 1; \
	fi

clean:
	@echo "Cleaning up zip file..."
	-@rm -f $(ZIP_FILE)

.PHONY: all zip clean
