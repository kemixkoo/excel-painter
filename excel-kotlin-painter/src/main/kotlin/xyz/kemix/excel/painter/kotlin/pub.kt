package xyz.kemix.excel.painter.kotlin

import org.apache.commons.io.FilenameUtils
import org.apache.poi.xssf.usermodel.IndexedColorMap
import xyz.kemix.excel.painter.java.poi.PoiIndexedColorMap
import java.io.File
import javax.imageio.ImageIO

/**
 *
 * @author Kemix Koo <kemix_koo@163.com>
 *
 *         Created at 2017-12-22
 *
 */
abstract class KotlinPixelPainter(val imageFile: File) {
	protected val colorIndexedMap = PoiIndexedColorMap()

	protected val bufferedImage = ImageIO.read(imageFile)

	protected val raster = bufferedImage.getRaster()
	protected val colorModel = bufferedImage.getColorModel()

	protected val minX = bufferedImage.getMinX()
	protected val width = bufferedImage.getWidth()
	protected val minY = bufferedImage.getMinY()
	protected val height = bufferedImage.getHeight()

	protected val sheetname = FilenameUtils.getBaseName(imageFile.getName())

	fun paint(excelFile: File) {
		try {
			beforePaint(excelFile)

			doPaint(excelFile)
		} finally {
			afterPaint(excelFile)
		}
	}

	protected open fun beforePaint(excelFile: File) {
		//
	}

	abstract protected fun doPaint(excelFile: File)

	protected open fun afterPaint(excelFile: File) {
		//
	}
}

