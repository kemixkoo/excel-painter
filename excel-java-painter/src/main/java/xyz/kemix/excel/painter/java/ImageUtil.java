package xyz.kemix.excel.painter.java;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

/**
 * @author Kemix Koo <kemix_koo@163.com>
 *
 *         Created at 2017-12-07
 */
public class ImageUtil {

	public static String IMAGE_TYPE_GIF = "gif";
	public static String IMAGE_TYPE_JPG = "jpg";
	public static String IMAGE_TYPE_JPEG = "jpeg";
	public static String IMAGE_TYPE_BMP = "bmp";
	public static String IMAGE_TYPE_PNG = "png";
	public static String IMAGE_TYPE_PSD = "psd";

	/**
	 * Make the source image bigger or smaller according to the scale.
	 * 
	 * The outFomat should be jpg(jpeg), bmp, ect.
	 * 
	 * If scale >0, means bigger, else will be smaller.
	 */
	public static void scale(String srcImageFile, String outImageFile, String outFomat, int scale) throws IOException {
		/*
		 * FIXME, one line to do same things.
		 */
		Thumbnails.of(srcImageFile).scale(scale).outputFormat(outFomat).toFile(outImageFile);

		// BufferedImage src = ImageIO.read(new File(srcImageFile));
		// int width = src.getWidth();
		// int height = src.getHeight();
		// if (scale > 0) { // bigger
		// width = width * scale;
		// height = height * scale;
		// } else { // smaller, when negative
		// width = width / Math.abs(scale);
		// height = height / Math.abs(scale);
		// }
		// Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		// BufferedImage bImg = new BufferedImage(width, height,
		// BufferedImage.TYPE_INT_RGB);
		// Graphics g = bImg.getGraphics();
		// g.drawImage(image, 0, 0, null);
		// g.dispose();
		// ImageIO.write(bImg, outFomat, new File(outImageFile));
	}

	/**
	 * Change the source image to fixing height and width.
	 * 
	 * The outFomat should be jpg(jpeg), bmp, ect.
	 * 
	 * If the original image is smaller then fixing size, if fill is true, will fill
	 * the white empty area .
	 */
	public static void scaleSize(String srcImageFile, String outImageFile, String outFomat, int height, int width,
			boolean fill) throws IOException {
		/*
		 * FIXME, one line to do almost same things.
		 */
		// Thumbnails.of(srcImageFile).scale(width,
		// height).outputFormat(outFomat).toFile(outImageFile);

		double ratio = 0.0;
		BufferedImage bImg = ImageIO.read(new File(srcImageFile));
		Image scaledImg = bImg.getScaledInstance(width, height, bImg.SCALE_SMOOTH);
		if ((bImg.getHeight() > height) || (bImg.getWidth() > width)) {
			if (bImg.getHeight() > bImg.getWidth()) {
				ratio = (new Integer(height)).doubleValue() / bImg.getHeight();
			} else {
				ratio = (new Integer(width)).doubleValue() / bImg.getWidth();
			}
			AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
			scaledImg = op.filter(bImg, null);
		}
		if (fill) {
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, width, height);
			if (width == scaledImg.getWidth(null))
				g.drawImage(scaledImg, 0, (height - scaledImg.getHeight(null)) / 2, scaledImg.getWidth(null),
						scaledImg.getHeight(null), Color.white, null);
			else
				g.drawImage(scaledImg, (width - scaledImg.getWidth(null)) / 2, 0, scaledImg.getWidth(null),
						scaledImg.getHeight(null), Color.white, null);
			g.dispose();
			scaledImg = image;
		}
		ImageIO.write((BufferedImage) scaledImg, outFomat, new File(outImageFile));
	}

	/**
	 * Cut the image from the starting coordinate to ending fixing size.
	 */
	public static void cutArea(String srcImageFile, String outImageFile, String outFomat, int x, int y, int width,
			int height) throws IOException {
		BufferedImage bi = ImageIO.read(new File(srcImageFile));
		int srcWidth = bi.getHeight();
		int srcHeight = bi.getWidth();
		if (srcWidth > 0 && srcHeight > 0) {
			Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
			ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
			Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(img, 0, 0, width, height, null);
			g.dispose();
			ImageIO.write(tag, outFomat, new File(outImageFile));
		}
	}

	/**
	 * Dice the image to same size of different piece.
	 */
	public static void dicePieces(String srcImageFile, String descDir, int rows, int cols) throws IOException {
		if (rows <= 0 || rows > 20)
			rows = 2;
		if (cols <= 0 || cols > 20)
			cols = 2;
		BufferedImage bi = ImageIO.read(new File(srcImageFile));
		int srcWidth = bi.getHeight();
		int srcHeight = bi.getWidth();
		if (srcWidth > 0 && srcHeight > 0) {
			Image img;
			ImageFilter cropFilter;
			Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
			int destWidth = srcWidth;
			int destHeight = srcHeight;
			if (srcWidth % cols == 0) {
				destWidth = srcWidth / cols;
			} else {
				destWidth = (int) Math.floor(srcWidth / cols) + 1;
			}
			if (srcHeight % rows == 0) {
				destHeight = srcHeight / rows;
			} else {
				destHeight = (int) Math.floor(srcWidth / rows) + 1;
			}
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);
					img = Toolkit.getDefaultToolkit()
							.createImage(new FilteredImageSource(image.getSource(), cropFilter));
					BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
					Graphics g = tag.getGraphics();
					g.drawImage(img, 0, 0, null);
					g.dispose();
					ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i + "_c" + j + ".jpg"));
				}
			}
		}
	}

	/**
	 * Dice the image to different size of images.
	 */
	public static void diceSize(String srcImageFile, String descDir, int destWidth, int destHeight) throws IOException {
		if (destWidth <= 0)
			destWidth = 200;
		if (destHeight <= 0)
			destHeight = 150;
		BufferedImage bi = ImageIO.read(new File(srcImageFile));
		int srcWidth = bi.getHeight();
		int srcHeight = bi.getWidth();
		if (srcWidth > destWidth && srcHeight > destHeight) {
			Image img;
			ImageFilter cropFilter;
			Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
			int cols = 0;
			int rows = 0;
			if (srcWidth % destWidth == 0) {
				cols = srcWidth / destWidth;
			} else {
				cols = (int) Math.floor(srcWidth / destWidth) + 1;
			}
			if (srcHeight % destHeight == 0) {
				rows = srcHeight / destHeight;
			} else {
				rows = (int) Math.floor(srcHeight / destHeight) + 1;
			}
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);
					img = Toolkit.getDefaultToolkit()
							.createImage(new FilteredImageSource(image.getSource(), cropFilter));
					BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
					Graphics g = tag.getGraphics();
					g.drawImage(img, 0, 0, null);
					g.dispose();
					ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i + "_c" + j + ".jpg"));
				}
			}
		}
	}

	/**
	 * Convert to different format file.
	 */
	public static void convert(String srcImageFile, String outFomat, String outImageFile) throws IOException {
		/*
		 * FIXME, one line to do same things.
		 */
		Thumbnails.of(srcImageFile).outputFormat(outFomat).toFile(outImageFile);

		// File f = new File(srcImageFile);
		// f.canRead();
		// f.canWrite();
		// BufferedImage src = ImageIO.read(f);
		// ImageIO.write(src, outFomat, new File(outImageFile));
	}

	/**
	 * do gray for the image
	 */
	public static void gray(String srcImageFile, String destImageFile) throws IOException {
		BufferedImage src = ImageIO.read(new File(srcImageFile));
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		src = op.filter(src, null);
		ImageIO.write(src, "JPEG", new File(destImageFile));
	}

	/**
	 * do press the text to fix place.
	 */
	public final static void pressText(String pressText, String srcImageFile, String destImageFile, String fontName,
			int fontStyle, Color color, int fontSize, int x, int y, float alpha) throws IOException {
		File img = new File(srcImageFile);
		Image src = ImageIO.read(img);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.drawImage(src, 0, 0, width, height, null);
		g.setColor(color);
		g.setFont(new Font(fontName, fontStyle, fontSize));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
		g.dispose();
		ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
	}

	/**
	 * Do press with text.
	 */
	public final static void pressText2(String pressText, String srcImageFile, String destImageFile, String fontName,
			int fontStyle, Color color, int fontSize, int x, int y, float alpha) throws IOException {
		File img = new File(srcImageFile);
		Image src = ImageIO.read(img);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.drawImage(src, 0, 0, width, height, null);
		g.setColor(color);
		g.setFont(new Font(fontName, fontStyle, fontSize));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
		g.dispose();
		ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
	}

	/**
	 * Do press with image
	 */
	public static void pressImage(String pressImg, String srcImageFile, String destImageFile, int x, int y, float alpha)
			throws IOException {
		File img = new File(srcImageFile);
		Image src = ImageIO.read(img);
		int wideth = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.drawImage(src, 0, 0, wideth, height, null);
		Image src_biao = ImageIO.read(new File(pressImg));
		int wideth_biao = src_biao.getWidth(null);
		int height_biao = src_biao.getHeight(null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);
		g.dispose();
		ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
	}

	public static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}
}
