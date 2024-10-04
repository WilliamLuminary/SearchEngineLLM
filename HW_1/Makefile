# Makefile to zip specific files for HW1

ZIP_FILE="Yaxing Li HW 1.zip"
FILES=hw1.json hw1.csv hw1.txt

all: zip

zip:
	@echo "Zipping files: $(FILES) into $(ZIP_FILE)..."
	zip $(ZIP_FILE) $(FILES)
	@echo "Done! Please submit $(ZIP_FILE)"

clean:
	@echo "Cleaning up zip file..."
	rm -f $(ZIP_FILE)

.PHONY: all zip clean
