# List of homework directories and deadlines
HW_DIRS = ./HW_1 ./HW_2 ./HW_3
HW_DEADLINES = 2024-09-16 2024-10-06 2024-10-25

DATE_CHECK=$(shell date +%s)

all: check_deadlines

check_deadlines:
	$(foreach i, $(shell seq 1 $(words $(HW_DIRS))), \
		HW_DIR=$(word $(i),$(HW_DIRS)); \
		DEADLINE=$(word $(i),$(HW_DEADLINES)); \
		EPOCH_DEADLINE=$$(date -j -f "%Y-%m-%d" $$DEADLINE +%s); \
		if [ $(DATE_CHECK) -lt $$EPOCH_DEADLINE ]; then \
			$(MAKE) run_hw DIR=$$HW_DIR; \
			exit 0; \
		fi; \
	)
	@echo "All homework assignments submitted."

run_hw:
	@if [ -d "$(DIR)" ]; then \
		if [ -f "$(DIR)/Makefile" ]; then \
			echo "Cleaning $(DIR) Makefile..."; \
			$(MAKE) -C $(DIR) clean; \
			echo "Running all for $(DIR)..."; \
			$(MAKE) -C $(DIR) all; \
		else \
			echo "No Makefile found in $(DIR)."; \
			exit 1; \
		fi; \
	else \
		echo "$(DIR) does not exist."; \
		exit 1; \
	fi

hw1:
	$(MAKE) run_hw DIR=./HW_1

hw2:
	$(MAKE) run_hw DIR=./HW_2

hw3:
	$(MAKE) run_hw DIR=./HW_3