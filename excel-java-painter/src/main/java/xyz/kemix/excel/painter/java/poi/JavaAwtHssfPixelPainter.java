package xyz.kemix.excel.painter.java.poi;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author Kemix Koo <kemix_koo@163.com>
 *
 *         Created at 2017-12-12
 * 
 *         for SpreadsheetVersion.EXCEL97, can't deal with big pictures.
 * 
 *         only enable to create 4000 styles and 255 cells.
 */
public class JavaAwtHssfPixelPainter extends JavaPoiPixelPainter {
	@Override
	protected Workbook createWorkbook() {
		return new HSSFWorkbook();
	}

	@Override
	protected int getColorIndex(int red, int green, int blue, int alpha) {
		// super.getColorIndex(red, green, blue, alpha);
		final HSSFPalette customPalette = ((HSSFWorkbook) workbook).getCustomPalette();
		final HSSFColor similarColor = customPalette.findSimilarColor((byte) red, (byte) green, (byte) blue);
		final short index = similarColor.getIndex();
		return index;
	}

	@Override
	protected void setFillForegroundColor(CellStyle style, int index) {
		((HSSFCellStyle) style).setFillForegroundColor((short) index);
	}

}
