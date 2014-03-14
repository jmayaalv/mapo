/**
 * 
 */
package mapo.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.mapred.JobConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jmayaalv
 * 
 */
public final class FileUtils {

	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

	private FileUtils() {
	}

	private static void listFiles(Path path, PathFilter filter, List<FileStatus> result, JobConf job) throws IOException {
		FileSystem fs = path.getFileSystem(job);
		FileStatus file = fs.getFileStatus(path);

		if (!file.isDir())
			throw new IOException("Path is not a dir");

		FileStatus[] children = fs.listStatus(path, filter);
		for (FileStatus child : children) {
			if (!child.isDir()) {
				result.add(child);
			} else {
				listFiles(child.getPath(), filter, result, job);
			}
		}
	}

	public static FileStatus[] recursiveListFiles(Path path, JobConf job, PathFilter filter) {

		List<FileStatus> listFiles = new ArrayList<FileStatus>();
		try {
			listFiles(path, filter, listFiles, job);
		} catch (Exception e) {
			logger.error("Error in recursive listing", e);
		}

		return listFiles.toArray(new FileStatus[listFiles.size()]);
	}
}
