# Main Makefile for homework assignments (macOS version)

HW1_DIR=./HW_1
HW2_DIR=./HW_2
DATE_CHECK=$(shell date +%s)  # Get current date in seconds since epoch
HW1_DEADLINE=$(shell date -j -f "%Y-%m-%d %H:%M:%S" "2024-09-16 00:00:00" +%s)
HW2_DEADLINE=$(shell date -j -f "%Y-%m-%d %H:%M:%S" "2024-10-06 00:00:00" +%s)

all:
ifeq ($(shell [ $(DATE_CHECK) -lt $(HW1_DEADLINE) ] && echo true || echo false), true)
	@echo "Current date is before September 16 midnight, running HW1 zip..."
	$(MAKE) -C $(HW1_DIR) all
else ifeq ($(shell [ $(DATE_CHECK) -lt $(HW2_DEADLINE) ] && echo true || echo false), true)
	@echo "Current date is before October 6 midnight, running HW2 zip..."
	$(MAKE) -C $(HW2_DIR) all
else
	@echo "All homework assignments submitted."
endif

hw1:
	@echo "Calling HW1 Makefile..."
	$(MAKE) -C $(HW1_DIR) all

hw2:
	@echo "Calling HW2 Makefile..."
	$(MAKE) -C $(HW2_DIR) clean
	$(MAKE) -C $(HW2_DIR) all
