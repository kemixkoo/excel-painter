package xyz.kemix.excel.painter.java.poi;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * 
 * @author Kemix Koo <kemix_koo@163.com>
 *
 *         Created at 2017-12-12
 * 
 */
public class JavaAwtSxssfPixelPainter extends JavaAwtXssfPixelPainter {

	@Override
	protected Workbook createWorkbook() {
		return new SXSSFWorkbook();
	}

	@Override
	protected void afterPaint() throws IOException {
		super.afterPaint();
		if (workbook instanceof SXSSFWorkbook) {
			((SXSSFWorkbook) workbook).dispose();
		}
	}

}
