package xyz.kemix.excel.painter.kotlin.jxl

import jxl.Workbook
import jxl.format.Colour
import jxl.write.Blank
import jxl.write.WritableCell
import jxl.write.WritableCellFormat
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
import xyz.kemix.excel.painter.kotlin.KotlinPixelPainter
import java.io.File


/**
 *
 * @author Kemix Koo <kemix_koo@163.com>
 *
 *         Created at 2017-12-21
 *
 */
class KotlinJxlPixelPainter(imageFile: File) : KotlinPixelPainter(imageFile) {
	var wb: WritableWorkbook?=null
	var sheet: WritableSheet?=null

	override protected fun beforePaint(excelFile: File) {
		wb = Workbook.createWorkbook(excelFile)
		sheet = wb!!.createSheet(sheetname, 0)
	}

	override protected fun afterPaint(excelFile: File) {
		wb?.write()
		wb?.close()
	}

	override protected fun doPaint(excelFile: File) {
//		var xyData: Any;
//		var red: Int
//		var green: Int
//		var blue: Int
//		var alpha: Int
		var cell: WritableCell
		var cellFormat: WritableCellFormat

		for (y in minY..height) {
			for (x in minX..width) {
//				xyData = raster.getDataElements(x, y, null)
//				red = colorModel.getRed(xyData);
//				green = colorModel.getGreen(xyData);
//				blue = colorModel.getBlue(xyData);
//				alpha = colorModel.getAlpha(xyData);

				//FIXME, for test, seems can't set customize color
				cellFormat = WritableCellFormat()
				cellFormat.setBackground(Colour.BLUE) 
				cell = Blank(x - minX, y - minY, cellFormat)
				sheet?.addCell(cell)
			}
		}
	}
}

