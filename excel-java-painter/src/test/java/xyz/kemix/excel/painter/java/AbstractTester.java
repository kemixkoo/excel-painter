/**
 *  
 */
package xyz.kemix.excel.painter.java;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.After;
import org.junit.Before;

/**
 * @author Kemix Koo <kemix_koo@163.com>
 *
 *         Created at 2017-11-07
 *
 */
public class AbstractTester {
	protected File tempDir;

	@Before
	public void setup() throws IOException {
		tempDir = File.createTempFile(this.getClass().getSimpleName(), "");
		tempDir.delete();
		tempDir.mkdirs();
	}

	@After
	public void cleanup() throws IOException {
		// FileUtils.deleteDirectory(tempDir);
	}

	protected InputStream getResource(String path) {
		return this.getClass().getResourceAsStream(path);
	}

	protected File getFile(String path) {
		final URL resource = this.getClass().getResource(path);
		if (resource == null) {
			return null;
		}
		return new File(resource.getPath());
	}
}
