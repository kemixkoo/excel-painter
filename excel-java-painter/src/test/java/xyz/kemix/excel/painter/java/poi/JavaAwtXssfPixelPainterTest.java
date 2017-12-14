package xyz.kemix.excel.painter.java.poi;

import java.io.IOException;

import org.junit.Test;

/**
 * 
 * @author Kemix Koo <kemix_koo@163.com>
 *
 *         Created at 2017-12-07
 */
public class JavaAwtXssfPixelPainterTest extends AbsTestJavaAwtPixelPainter {

	@Override
	protected JavaPoiPixelPainter createPainter() {
		return new JavaAwtXssfPixelPainter();
	}

	@Override
	protected String getExt() {
		return JavaPoiPixelPainter.EXT_XLSX;
	}

	@Test
	public void test_paint_bluebird() throws IOException {
		doTestPaint(PATH_ICONS + "/bluebird_571x648.jpg");
	}
}
