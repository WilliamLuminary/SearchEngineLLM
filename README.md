# CS 572 Homework Repository

This repository contains the homework assignments for CS 572. Each homework assignment is organized into separate
directories (HW_1, HW_2, etc.), and a script is provided to assist with the submission process.

The submission script ensures that homework assignments are packaged into a zip file if they are submitted before the
deadline. It checks each assignment’s deadline, cleans up any unnecessary files, and generates the appropriate zip file
for submission.

## Homework Submission Script

This repository includes a Makefile that automates the process of preparing your homework for submission. The script
will check each homework directory to determine if the submission deadline has passed. If the deadline is still valid,
the script will clean the directory and create a zip file for submission.

## Usage

To automatically check deadlines and submit the homework that is still within the submission window, run the following
command:

```bash
make all
```

This command will:

- Check the deadlines of each homework directory.
- If a homework deadline has not passed, it will clean the directory and run the Makefile to zip the necessary files.
- Finally, a zip file containing the files required for submission will be created in the corresponding homework
  directory.

To manually zip the files for a specific homework (e.g., HW_1):

```bash
make hw1
```

Notes
- Deadlines are checked based on the system’s current date.
- Each homework directory should contain a Makefile with clean and all targets to ensure proper functionality.

By following these instructions, you can easily manage and submit your homework assignments for CS 572.