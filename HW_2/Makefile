# Makefile to zip specific files for HW2

ZIP_FILE=crawl.zip
JAVA_FILES=$(wildcard src/main/java/com/will/**/*.java)
DATA_FILES=$(wildcard /data/*.csv)

all: zip

zip:
	@if [ -n "$(JAVA_FILES)" ]; then \
		echo "Zipping Java files and CSV files, flattening directory structure, and excluding .DS_Store..."; \
		zip -j $(ZIP_FILE) $(JAVA_FILES) $(DATA_FILES) -x "*.DS_Store"; \
		echo "Done! Please submit $(realpath $(ZIP_FILE))"; \
	else \
		echo "No files to zip!"; \
		exit 1; \
	fi

clean:
	@echo "Cleaning up zip file..."
	rm -f $(ZIP_FILE)

.PHONY: all zip clean
