package xyz.kemix.excel.painter.java.poi;

import java.io.IOException;

import org.junit.Test;

/**
 * 
 * @author Kemix Koo <kemix_koo@163.com>
 *
 *         Created at 2017-12-12
 */
public class JavaAwtHssfPixelPainterTest extends AbsTestJavaAwtPixelPainter {

	@Override
	protected JavaPoiPixelPainter createPainter() {
		return new JavaAwtHssfPixelPainter();
	}

	@Override
	protected String getExt() {
		return JavaPoiPixelPainter.EXT_XLS;
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_paint_singbird() throws IOException {
		super.test_paint_singbird();
	}
}
