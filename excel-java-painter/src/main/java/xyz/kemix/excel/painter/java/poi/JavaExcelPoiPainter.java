package xyz.kemix.excel.painter.java.poi;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;

/**
 * 
 * @author Kemix Koo <kemix_koo@163.com>
 *
 *         Created at 2017-12-07
 */
public class JavaExcelPoiPainter {
	protected void initExcelSquare(File excelFile) {
		//
	}

	public void paint(File imageFile, File excelFile) throws IOException {
		if (!imageFile.exists()) {
			throw new FileNotFoundException(imageFile.getAbsolutePath());
		}
		final String fileName = FilenameUtils.getBaseName(imageFile.getName());

		final HSSFWorkbook workbook = new HSSFWorkbook();
		try {
			final HSSFSheet sheet = workbook.createSheet();
			workbook.setSheetName(workbook.getSheetIndex(sheet), fileName);
			sheet.setDefaultRowHeight((short) 230); // 0.16, 200=0.14
			sheet.setDefaultColumnWidth(1); // 0.16

			final HSSFPalette customPalette = workbook.getCustomPalette();

			BufferedImage bi = ImageIO.read(imageFile);

			int width = bi.getWidth();
			int height = bi.getHeight();
			int minx = bi.getMinX();
			int miny = bi.getMinY();

			Map<Short, HSSFCellStyle> stylesMap = new HashMap<Short, HSSFCellStyle>();
			int[] rgb = new int[3];
			for (int y = miny; y < height; y++) {
				HSSFRow row = sheet.createRow(y - miny);
				for (int x = minx; x < width; x++) {
					int pixel = bi.getRGB(x, y);
					if (pixel == 0) {
						continue;
					}
					rgb[0] = (pixel & 0xff0000) >> 16;
					rgb[1] = (pixel & 0xff00) >> 8;
					rgb[2] = (pixel & 0xff);

					HSSFColor color = customPalette.findSimilarColor((byte) rgb[0], (byte) rgb[1], (byte) rgb[2]);
					short index = color.getIndex();
					HSSFCellStyle style = stylesMap.get(index);
					if (style == null) {
						style = workbook.createCellStyle();
						// style.setFillBackgroundColor(index);
						style.setFillForegroundColor(index);
						style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
						stylesMap.put(index, style);
					}
					HSSFCell cell = row.createCell(x - minx);
					cell.setCellStyle(style);
					// cell.setCellValue("*");
				}
			}

			workbook.write(excelFile);
		} finally {
			workbook.close();
		}
	}
}
