package xyz.kemix.excel.painter.java.poi;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.junit.Assert;
import org.junit.Test;

import xyz.kemix.excel.painter.java.AbstractTester;

/**
 * 
 * @author Kemix Koo <kemix_koo@163.com>
 *
 *         Created at 2017-12-12
 */
public abstract class AbsTestJavaAwtPixelPainter extends AbstractTester {
	protected static final String PATH_ICONS = "/icons";
	protected static final String PATH_ECLIPSE = PATH_ICONS + "/eclipse_256x256.png";

	protected abstract JavaPoiPixelPainter createPainter();

	protected abstract String getExt();

	@Test
	public void test_paint_eclipse_withFillTypes() throws IOException {
		File imageFile = getFile(PATH_ECLIPSE);
		Assert.assertNotNull(imageFile);

		for (FillPatternType t : FillPatternType.values()) {
			File excelFile = new File(tempDir,
					FilenameUtils.getBaseName(imageFile.getName()) + '-' + t.name().toLowerCase() + getExt());

			JavaPoiPixelPainter painter = createPainter();
			painter.setFillType(t);
			painter.paint(imageFile, excelFile);

			Assert.assertTrue(excelFile.exists());
		}
	}

	@Test
	public void test_paint_singbird() throws IOException {
		doTestPaint(PATH_ICONS + "/singbird_599x450.jpg");
	}

	protected void doTestPaint(String path) throws IOException {
		File imageFile = getFile(path);
		Assert.assertNotNull(imageFile);

		File excelFile = new File(tempDir, FilenameUtils.getBaseName(imageFile.getName()) + getExt());

		JavaPoiPixelPainter painter = createPainter();
		painter.paint(imageFile, excelFile);

		Assert.assertTrue(excelFile.exists());
	}
}
