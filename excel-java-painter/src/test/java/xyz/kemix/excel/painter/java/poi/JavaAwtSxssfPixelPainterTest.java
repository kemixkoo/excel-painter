package xyz.kemix.excel.painter.java.poi;

import java.io.IOException;

import org.junit.Test;

/**
 * 
 * @author Kemix Koo <kemix_koo@163.com>
 *
 *         Created at 2017-12-12
 */
public class JavaAwtSxssfPixelPainterTest extends AbsTestJavaAwtPixelPainter {

	@Override
	protected JavaPoiPixelPainter createPainter() {
		return new JavaAwtSxssfPixelPainter();
	}

	@Override
	protected String getExt() {
		return JavaPoiPixelPainter.EXT_XLSX;
	}

	@Test
	public void test_paint_bluebird() throws IOException {
		doTestPaint(PATH_ICONS + "/bluebird_571x648.jpg");
	}

	@Test
	public void test_paint_bird() throws IOException {
		doTestPaint(PATH_ICONS + "/bird_872x1000.jpg");
	}

	@Test
	public void test_paint_firebird() throws IOException {
		doTestPaint(PATH_ICONS + "/firebird_1024x1024.jpg");
	}

	@Test
	public void test_paint_duck() throws IOException {
		doTestPaint(PATH_ICONS + "/duck_918x1201.jpg");
	}

}
