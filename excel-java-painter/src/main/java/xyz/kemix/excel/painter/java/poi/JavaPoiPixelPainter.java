package xyz.kemix.excel.painter.java.poi;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;

/**
 * 
 * @author Kemix Koo <kemix_koo@163.com>
 *
 *         Created at 2017-12-12
 */
public abstract class JavaPoiPixelPainter {
	public static final String EXT_XLSX = '.' + XSSFWorkbookType.XLSX.getExtension();
	public static final String EXT_XLS = '.' + "xls";

	protected long stylesNum = 0;
	private FillPatternType fillType = FillPatternType.SOLID_FOREGROUND;

	protected BufferedImage bufferedImg;
	protected WritableRaster imgRaster;
	protected int width, height, minX, minY;

	protected String sheetName;
	protected Workbook workbook;
	protected Sheet sheet;
	protected ColorModel imgColorModel;

	protected PoiIndexedColorMap colorIndexedMap = new PoiIndexedColorMap();
	protected Map<Integer, CellStyle> stylesMap = new HashMap<Integer, CellStyle>();

	public FillPatternType getFillType() {
		return fillType;
	}

	public void setFillType(FillPatternType fillType) {
		this.fillType = fillType;
	}

	public void paint(File imageFile, File excelFile) throws IOException {
		try {
			loadImage(imageFile);
			initExcel();

			doPaint();

			saveExcel(excelFile);
		} finally {
			afterPaint();
		}
	}

	/**
	 * load the image via AWT.
	 */
	protected void loadImage(File imageFile) throws IOException {
		if (!imageFile.exists()) {
			throw new FileNotFoundException(imageFile.getAbsolutePath());
		}
		// load image
		bufferedImg = ImageIO.read(imageFile);
		imgRaster = bufferedImg.getRaster();
		imgColorModel = bufferedImg.getColorModel();

		minX = bufferedImg.getMinX();
		width = bufferedImg.getWidth();
		minY = bufferedImg.getMinY();
		height = bufferedImg.getHeight();

		sheetName = FilenameUtils.getBaseName(imageFile.getName());
	}

	/**
	 * change the sheet for each cell with same height and width.
	 */
	protected void initExcel() {
		// create workbook and sheet
		workbook = createWorkbook();
		sheet = workbook.createSheet(sheetName);

		sheet.setDefaultRowHeight((short) (10 * 20));
		sheet.setDefaultColumnWidth(1);
	}

	protected abstract Workbook createWorkbook();

	protected void doPaint() {
		if (workbook == null) {
			return;
		}
		Object inData;
		int red, green, blue, alpha;
		int index;
		CellStyle style;
		Cell cell;

		for (int y = minY; y < height; y++) {
			Row row = sheet.createRow(y - minY);
			for (int x = minX; x < width; x++) {
				inData = imgRaster.getDataElements(x, y, null);
				red = imgColorModel.getRed(inData);
				green = imgColorModel.getGreen(inData);
				blue = imgColorModel.getBlue(inData);
				alpha = imgColorModel.getAlpha(inData);

				index = getColorIndex(red, green, blue, alpha);
				style = stylesMap.get(index);
				if (style == null) {
					stylesNum++;
					style = workbook.createCellStyle();
					setFillForegroundColor(style, index);// must set forgeround first
					style.setFillPattern(getFillType());
					// style.setFillBackgroundColor(index);
					stylesMap.put(index, style);
				}

				cell = row.createCell(x - minX);
				cell.setCellStyle(style);
			}
		}
	}

	protected int getColorIndex(int red, int green, int blue, int alpha) {
		int index = colorIndexedMap.addColor(red, green, blue, alpha);
		return index;
	}

	protected abstract void setFillForegroundColor(CellStyle style, int index);

	/**
	 * after paint, will do save to excel file.
	 */
	protected void saveExcel(File excelFile) throws IOException {
		if (workbook == null) {
			return;
		}
		FileOutputStream stream = null;
		try {
			excelFile.getParentFile().mkdirs();

			stream = new FileOutputStream(excelFile);
			workbook.write(stream);
		} finally {
			if (stream != null)
				stream.close();
		}
	}

	protected void afterPaint() throws IOException {
		if (workbook != null) {
			workbook.close();
		}
	}
}
