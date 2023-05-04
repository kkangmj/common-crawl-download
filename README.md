
This is a simple tool to download corresponding binary data from CommonCrawl indexes. Forked from [CommonCrawlDocumentDownload](https://github.com/centic9/CommonCrawlDocumentDownload) project.

<br>

## What's Different
Adjust options by revising `application.yml`. 

<br>

## Further support
### Options
- Include or exclude specific file extension for download
- Adjust download speed and location. Due to download speed, sometimes CommonCrawl server sends 503 error. 

### New
- Without typing lookupURLs and downloadDocumnets, fetch index and download document at once

### Etc
- Log is huge and long to read. Soon remove unnecessary part of log

<br>

## Getting started

### Build it and create the distribution files

    cd CommonCrawlDocumentDownload
    ./gradlew check

### Fetch a list of interesting documents

    ./gradlew lookupURLs
    
Reads the current Common Crawl URL index data and extracts all URLs for 
interesting mime-types or file extensions, stores the URLs in a file 
called `commoncrawl-CC-MAIN-<year>-<crawl>.txt`

There are some options. 

#### Specify key

    ./gradlew lookupURLs -Pkey='YYYY-NN'

Default key is '2023-14'. [Here](https://index.commoncrawl.org/) is list of keys.
        
### Download documents

    ./gradlew downloadDocuments

Uses the URLs listed in `commoncrawl-CC-MAIN-<year>-<crawl>.txt` to 
download the documents from the Common Crawl.

### Deduplicate files

    ./gradlew deduplicate

Some files have equal content, this task will detect these based on file-size
and content-hash and move all duplicates to a backup-directory to leave only
unique files in place.

<br>

## Licensing

* common-crawl-download is licensed under the [BSD 2-Clause License].

[BSD 2-Clause License]: https://www.opensource.org/licenses/bsd-license.php
