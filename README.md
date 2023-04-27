
This is a simple tool to download corresponding binary data from CommonCrawl indexes. Forked from [CommonCrawlDocumentDownload](https://github.com/centic9/CommonCrawlDocumentDownload) project.

## What's Different
What's different from CommonCrawlDocumentDownload is options. This project supports/will support lots of options.

## Further support
### Options
- Include or exclude specific file extension for download
- Adjust download speed and location. Due to download speed, sometimes CommonCrawl server sends 503 error. 

### New
- Without typing lookupURLs and downloadDocumnets, fetch index and download document at once

### Etc
- Log is huge and long to read. Soon remove unnecessary part of log

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

If it's not set, key will be '2023-14'. [Here](https://index.commoncrawl.org/) is list of keys.
        
### Download documents

    ./gradlew downloadDocuments

Uses the URLs listed in `commoncrawl-CC-MAIN-<year>-<crawl>.txt` to 
download the documents from the Common Crawl.

### Download documents with key

    ./gradlew downloadDocuments

Uses the URLs listed in `commoncrawl-CC-MAIN-<year>-<crawl>.txt` to
download the documents from the Common Crawl.

### Deduplicate files

    ./gradlew deduplicate

Some files have equal content, this task will detect these based on file-size
and content-hash and move all duplicates to a backup-directory to leave only
unique files in place.

### Deprecated: Download documents from the old-index

    ./gradlew downloadOldIndex

Starts downloading the URL index files from the old index and looks 
at each URL, downloading binary data from the common crawl archives.

## The longer stuff

### Change it

Run unit tests

    ./gradlew check jacocoTestReport

#### Adjust which files are found

There are a few things that you can tweak:

* The file-extensions that are detected as download-able files are handled in the class `Extensions`.
* The mime-types that are detected as download-able files isare handled in the class `MimeTypes`.
* Adjust the name of the list of found files in `DownloadURLIndex.COMMON_CRAWL_FILE`.
* Adjust the location where files are downloaded to in `Utils.DOWNLOAD_DIR`.
* The starting file-index (of the approximately 300 cdx-files) is currently set as constant 
in class `org.dstadler.commoncrawl.index.DownloadURLIndex`, this way you can also 
re-start a download that was interrupted before.

### Ideas

* Old Index: By adding a new implementation of `BlockProcesser` (likely re-using existing stuff by deriving from one of the
available implementations), you can do things like streaming processing of the file instead of storing the file
locally, which will avoid using too much disk-space

### Estimates (based on Old Index)

* Size of overall URL Index is 233689120776, i.e. 217GB
* Header: 6 Bytes
* Index-Blocks: 2644
* Block-Size: 65536
* => Data-Blocks: 3563169
* Aprox. Files per Block: 2.421275
* Resulint aprox. number of files: 8627412
* Avg. size per file: 221613
* Needed storage: 1911954989425 bytes = 1.7TB!

### Release it

    ./gradlew --console=plain release && ./gradlew closeAndReleaseRepository
    
* This should automatically release the new version on MavenCentral
* Afterwards go to the [Github releases page](https://github.com/centic9/commons-dost/releases) and add release-notes

## Licensing

* common-crawl-download is licensed under the [BSD 2-Clause License].

[BSD 2-Clause License]: https://www.opensource.org/licenses/bsd-license.php
