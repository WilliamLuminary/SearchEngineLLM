# Homework Submission Script

This script allows you to automatically create a zip file for the homework that is still within the deadline. You can
use the make all command to check the deadlines of each homework directory and submit the corresponding homework files
that are still due.

## Usage

To zip the homework files that are still within the submission window, run the following command:

```bash
make all
```

This command will:

- Check the deadlines of each homework directory.
- a homework deadline has not passed, it will clean and run the corresponding Makefile inside the directory.
- Finally, a zip file containing the files needed for submission will be created.

To manually zip `HW_1`:

```bash
make hw1
```

Notes
- Deadlines are checked based on the system’s current date.