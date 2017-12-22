package xyz.kemix.excel.painter.kotlin.poi

import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFColor
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import xyz.kemix.excel.painter.kotlin.KotlinPixelPainter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 *
 * @author Kemix Koo <kemix_koo@163.com>
 *
 *         Created at 2017-12-22
 *
 */
class KotlinPoiXssfPixelPainter(imageFile: File) : KotlinPixelPainter(imageFile) {
	private val stylesMap = HashMap<Int, CellStyle>();

	var wb: Workbook? = null
	var sheet: Sheet? = null

	override protected fun beforePaint(excelFile: File) {
		wb = XSSFWorkbook()
		sheet = wb?.createSheet(sheetname)

		sheet?.setDefaultRowHeight(10 * 20)
		sheet?.setDefaultColumnWidth(1)
	}

	override protected fun afterPaint(excelFile: File) {
			excelFile.getParentFile().mkdirs()
			var stream: FileOutputStream? = null
			try {
				stream = FileOutputStream(excelFile)
				wb?.write(stream)
			} finally {
				stream?.close()
				wb?.close()
			}
	}

	override protected fun doPaint(excelFile: File) {
		wb!!

		var xyData: Any
		var red: Int
		var green: Int
		var blue: Int
		var alpha: Int
		var index: Int
		var style: CellStyle?

		for (y in minY until height) {
			var row = sheet?.createRow(y - minY);
			for (x in minX until width) {
				xyData = raster.getDataElements(x, y, null)
				red = colorModel.getRed(xyData)
				green = colorModel.getGreen(xyData)
				blue = colorModel.getBlue(xyData)
				alpha = colorModel.getAlpha(xyData)

				index = colorIndexedMap.addColor(red, green, blue, alpha)
				style = stylesMap[index]

				if (style == null) {
					style = wb?.createCellStyle()
					var xssfStyle = style as XSSFCellStyle
					xssfStyle.setFillForegroundColor(XSSFColor(colorIndexedMap.getRGB(index), colorIndexedMap))
					style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

					stylesMap += (index to style)
				}

				var cell = row?.createCell(x - minX)
				cell?.setCellStyle(style)
			}
		}
	}
}
