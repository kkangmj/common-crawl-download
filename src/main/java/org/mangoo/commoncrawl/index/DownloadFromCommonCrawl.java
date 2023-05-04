package org.mangoo.commoncrawl.index;

import org.apache.commons.lang3.StringUtils;
import org.mangoo.commoncrawl.Utils;
import org.dstadler.commons.http.HttpClientWrapper;
import org.dstadler.commons.logging.jdk.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Specialized Processor which reads the position in the Common Crawl
 * from a file 'commoncrawl-CC-MAIN-&lt;year&gt;-&lt;crawl&gt;.txt' and uses the information
 * to download and unwrap the actual document in one go.
 *
 * @author dominik.stadler
 */
public class DownloadFromCommonCrawl {
	private static final Logger log = LoggerFactory.make();

	private static File COMMON_CRAWL_FILE;

	private static void init(String key) {
		COMMON_CRAWL_FILE = new File("commoncrawl-" + key + ".txt");
	}

    public static void main(String[] args) throws Exception {
		LoggerFactory.initLogging();

		Utils.ensureDownloadDir();
		init(args[0].trim());

    	try (final HttpClientWrapper client = new HttpClientWrapper("", null, 600_000);
    		BufferedReader reader = new BufferedReader(new FileReader(COMMON_CRAWL_FILE), 1024*1024)) {
			int count = 0, downloaded = 0, fileNameTooLong = 0;
			long bytes = 0;
			while(true) {
				String line = reader.readLine();
				if(line == null) {
					log.info("End of file " + COMMON_CRAWL_FILE + " reached after " + count + " items");
					break;
				}

				double percentage = (double)(bytes)/COMMON_CRAWL_FILE.length()*100;
				log.info("Downloading line " + (count+1) + ": " + String.format("%.4f", percentage) + "%, having " +
                        downloaded + " downloaded: " + StringUtils.abbreviate(line, 50) +
						(fileNameTooLong > 0 ? ", " + fileNameTooLong + " file-names too long" : ""));
				CDXItem item = CDXItem.parse(line);

				try {
					File file = Utils.downloadFileFromCommonCrawl(client.getHttpClient(), item.url, item.getDocumentLocation(), true);
					if (file != null) {
						downloaded++;
					}
				} catch (IOException e) {
					// skip files that we cannot store locally,
					// Exception text is provided by the OS and thus is localized
					// for me, add your own translation if you run into this as well
					if(e.getMessage().contains("Der Dateiname ist zu lang")) {
                        fileNameTooLong++;
                    } else {
						throw e;
					}
				}

				bytes+=line.length()+1;
				count++;
			}
    	}
    }
}
