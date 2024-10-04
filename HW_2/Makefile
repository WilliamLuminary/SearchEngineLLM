# Makefile to zip specific files

ZIP_FILE=crawl.zip
SOURCE_DIR=src/main/java/com/will
DATA_FILES=$(wildcard data/*.csv)

all:
	@echo "For this assignment, you don't need to submit all files, just the zip."
	zip

zip:
	@echo "Zipping files under $(SOURCE_DIR) and CSV files $(DATA_FILES)..."
	zip -r $(ZIP_FILE) $(SOURCE_DIR) $(DATA_FILES)

clean:
	@echo "Cleaning up zip file..."
	rm -f $(ZIP_FILE)

.PHONY: all zip clean
