# List of homework directories and deadlines
HW_DIRS = ./HW_1 ./HW_2
HW_DEADLINES = "2024-09-16 00:00:00" "2024-10-06 00:00:00"

DATE_CHECK=$(shell date +%s)

define deadline_to_epoch
$(shell date -j -f "%Y-%m-%d %H:%M:%S" $(1) +%s)
endef

all: check_deadlines

check_deadlines:
	$(foreach i, $(shell seq 1 $(words $(HW_DIRS))), \
		HW_DIR=$(word $(i),$(HW_DIRS)); \
		DEADLINE=$(word $(i),$(HW_DEADLINES)); \
		EPOCH_DEADLINE=$(call deadline_to_epoch,$(DEADLINE)); \
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

# hw3:
#	$(MAKE) run_hw DIR=./HW_3

run_hw:
	@echo "Cleaning $(DIR) Makefile..."
	$(MAKE) -C $(DIR) clean
	@echo "Running all for $(DIR)..."
	$(MAKE) -C $(DIR) all
