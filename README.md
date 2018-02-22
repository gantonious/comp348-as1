# COMP 348 Assignment 1

I declare that this assignment is my own work and that all material previously written or published in any source by any other person has been duly acknowledged in the assignment. I have not submitted this work, or a significant part thereof, previously as part of any academic program. In submitting this assignment I give permission to copy it for assessment purposes only.

Submitted by: George Antonious (3364768)

## Building Instructions

Assuming JDK 1.8 is installed on the host machine, both parts can be built by doing:

```bash
make
```

## Part 1: MyPooledWebLog

### Usage

```bash
make runMyPooledWebLog WEB_LOG_PATH=[web_log_file_name] STATS_OPTION=[stats_option]
```

### Parameters

`web_log_file_name` is the path to the web log file
`stats_option` is the option of stats to run on the web log. The following options are available:
- `1`: Counts how many times each host was accessed
- `2`: Counts the total bytes transmitted by the webserver
- `3`: Counts the total bytes transmitted for each host served by the webserver

### Design

The weblog file is modeled in code as a `WebLog` which contains a list of `WebLogEntery` objects that contain specific information about each log entry. To retreive the weblog data the `WebLogFileParser` is used to open up the log and convert it into a `WebLog` object.

Rather than coupling the application to the original use cases disucssed in the assignment I went for a more generic approach. I introuduced the concept of an `ILogAnalyzer`. This interface has one simple method:

```java
void analyzeWebLog(WebLog webLog);
```

It accepts a weblog and can run any procedure on it. For this assignment three different `ILogAnalyzer` implementations were made to cover the usecases in the requirements:
- `AccessesLogAnalyzer`
- `TotalBytesByHostLogAnalyzer`
- `TotalBytesTransmittedLogAnalyzer`

The main file then creates `Map` that maps the different command line arugements to the different log analyzer implementations. This would allow this program to be expanded to analyze different stats in the web log such as percentage of server errors (i.e HTTP 500) when handling requests.

### Test Plan

To the test the weblog a known weblog was used as input for the program. This weblog is packaged with this assignmnet. Each different option was tested using the same log file to check if the output made sense.

### Test Cases

### 1. Option 1 on example_log.txt

```bash
make runMyPooledWebLog WEB_LOG_PATH=example_log.txt STATS_OPTION=1
```

Expected Output:

```
64.242.88.10 was accessed 47 time(s)
lordgun.org was accessed 1 time(s)
lj1036.inktomisearch.com was accessed 1 time(s)
lj1090.inktomisearch.com was accessed 1 time(s)
```

Actual Output:

```
64.242.88.10 was accessed 47 time(s)
lordgun.org was accessed 1 time(s)
lj1036.inktomisearch.com was accessed 1 time(s)
lj1090.inktomisearch.com was accessed 1 time(s)
```

### 2: Option 2 on example_log.txt

```bash
make runMyPooledWebLog WEB_LOG_PATH=example_log.txt STATS_OPTION=2
```

Expected Output:

```
Total bytes transmitted: 512540 byte(s).
```

Actual Output:

```
Total bytes transmitted: 512540 byte(s).
```

### 3: Option 3 on example_log.txt

```bash
make runMyPooledWebLog WEB_LOG_PATH=example_log.txt STATS_OPTION=3
```

Expected Output:

```
64.242.88.10 transmitted 505743 byte(s)
lordgun.org transmitted 2869 byte(s)
lj1036.inktomisearch.com transmitted 68 byte(s)
lj1090.inktomisearch.com transmitted 3860 byte(s)
```

Actual Output:

```
64.242.88.10 transmitted 505743 byte(s)
lordgun.org transmitted 2869 byte(s)
lj1036.inktomisearch.com transmitted 68 byte(s)
lj1090.inktomisearch.com transmitted 3860 byte(s)
```

### 4: Option 1 on empty_log.txt

```bash
make runMyPooledWebLog WEB_LOG_PATH=empty_log.txt STATS_OPTION=1
```

Expected Output:

```
```

Actual Output:

```
```

### 5: Option 2 on empty_log.txt

```bash
make runMyPooledWebLog WEB_LOG_PATH=empty_log.txt STATS_OPTION=2
```

Expected Output:

```
```

Actual Output:

```
```

### 6. Option 3 on empty_log.txt

```bash
make runMyPooledWebLog WEB_LOG_PATH=empty_log.txt STATS_OPTION=3
```

Expected Output:

```
```

Actual Output:

```
```


## Part 2: SourceViewer

### Usage

```bash
make runSourceViewer URL=[url] FILTER=[filter]
```

### Parameters

`url` is the web url to the site you want to visit
`filter` is a filter string you want to search for in the site

### Design

For this part I opened up a stream to the requested website and used a `BufferedReader` to consume the website data line by line. I would then apply the filter to each line to check if the line should be included in the output. For all the lines that include the filter string I then highlight the filter string (similar to what grep does) using unix color escape sequences.

### Test Plan

To test the source viewer a known website was passed into the program. Then the matched lines were compared with the source of the website to make sure the program did not miss any lines.

### Test Cases

### 1: Word in website

```bash
make runSourceViewer URL=http://antonious.ca FILTER=George
```

Excpected Output:

```
    <meta name="description" content="George Antonious's Personal Website">
            <p style="font-size: 225%;">Hey I'm George Antonious</p>
            Under Development | Developed by George Antonious | 2015
```

Actual Output:

```
    <meta name="description" content="George Antonious's Personal Website">
            <p style="font-size: 225%;">Hey I'm George Antonious</p>
            Under Development | Developed by George Antonious | 2015
```

### 2: Word not in website

```bash
make runSourceViewer URL=http://antonious.ca FILTER=WORDNOTINSITE
```

Excpected Output:

```
```

Actual Output:

```
```