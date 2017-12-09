package xyz.kemix.excel.painter.java.poi;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Kemix Koo <kemix_koo@163.com>
 *
 *         Created at 2017-12-07
 */
public class JavaExcelPoiPainterTest extends AbstractTester {

	@Test
	public void test_paint() throws IOException {
		File imageFile = getFile("/icons/eclipse.png");
		Assert.assertNotNull(imageFile);

		File excelFile = new File(tempDir, FilenameUtils.getBaseName(imageFile.getName()) + ".xls");

		JavaExcelPoiPainter painter = new JavaExcelPoiPainter();
		painter.paint(imageFile, excelFile);

		Assert.assertTrue(excelFile.exists());
	}
}
