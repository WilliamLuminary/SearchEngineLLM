# Makefile to zip all files under com.will

ZIP_FILE=output.zip
SOURCE_DIR=com/will
DATA_DIR=data

all: zip

zip:
	@echo "Zipping files under $(SOURCE_DIR)..."
	zip -r $(ZIP_FILE) $(SOURCE_DIR) $(DATA_DIR)

clean:
	@echo "Cleaning up zip file..."
	rm -f $(ZIP_FILE)

.PHONY: all zip clean
