# COMP 366 Assignment 1

I declare that this assignment is my own work and that all material previously written or published in any source by any other person has been duly acknowledged in the assignment. I have not submitted this work, or a significant part thereof, previously as part of any academic program. In submitting this assignment I give permission to copy it for assessment purposes only.

Submitted by: George Antonious (3364768)

## Building Instructions

Assuming JDK 1.8 is installed on the host machine. Both parts can be built by doing:

```bash
make
```

## Part 1: MyPooledWebLog

Usage:

```bash
java MyPooledWebLog web_log_file_name stats_option
```

Where:

`web_log_file_name` is the path to the web log file
`stats_option` is the option of stats to run on the web log. The following options are available:

- `1`: Counts how many times each host was accessed
- `2`: Counts the total bytes transmitted by the webserver
- `3`: Counts the total bytes transmitted for each host served by teh webserver

## Part 2:

Usage:

```bash
java SourveViewer url filter
```

Where:

`url` is the web url to the site you want to visit
`filter` is a filter string you want to search for in the site
