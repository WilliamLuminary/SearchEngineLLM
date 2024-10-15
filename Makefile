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

hw1:
	$(MAKE) run_hw DIR=./HW_1

hw2:
	$(MAKE) run_hw DIR=./HW_2

 hw3:
	$(MAKE) run_hw DIR=./HW_3

run_hw:
	@echo "Cleaning $(DIR) Makefile..."
	$(MAKE) -C $(DIR) clean
	@echo "Running all for $(DIR)..."
	$(MAKE) -C $(DIR) all
