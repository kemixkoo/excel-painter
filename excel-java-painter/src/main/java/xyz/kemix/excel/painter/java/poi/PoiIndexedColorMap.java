package xyz.kemix.excel.painter.java.poi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xssf.usermodel.IndexedColorMap;

/**
 * 
 * @author Kemix Koo <kemix_koo@163.com>
 *
 *         Created at 2017-12-13
 * 
 *         all color will be similar with 5 times, like, 212, 216, 128, etc,
 *         will be unify to 215.
 */
public class PoiIndexedColorMap implements IndexedColorMap {

	private final List<Integer> colors = new ArrayList<>();

	@Override
	public byte[] getRGB(int index) {
		final byte[] color = getColor(index);
		if (color != null) {
			return Arrays.copyOfRange(color, 1, color.length);
		}
		return null;
	}

	public byte[] getColor(int index) {
		final Integer rgb = colors.get(index);
		if (rgb != null) {
			byte[] rgbArr = new byte[4];
			rgbArr[0] = (byte) ((rgb & 0xff000000) >> 24);
			rgbArr[1] = (byte) ((rgb & 0xff0000) >> 16);
			rgbArr[2] = (byte) ((rgb & 0xff00) >> 8);
			rgbArr[3] = (byte) ((rgb & 0xff) >> 0);
			return rgbArr;
		}
		return null;
	}

	public int addColor(int rgb) {
		int[] rgbArr = new int[4];
		rgbArr[0] = ((rgb & 0xff000000) >> 24);
		rgbArr[1] = ((rgb & 0xff0000) >> 16);
		rgbArr[2] = ((rgb & 0xff00) >> 8);
		rgbArr[3] = ((rgb & 0xff) >> 0);

		return addColor(rgbArr[1], rgbArr[2], rgbArr[3], rgbArr[0]);
	}

	private byte unify(int v) {
		return (byte) ((v / 10) * 10 + 5);
	}

	public int addColor(int r, int g, int b, int a) {
		int value = /* ((a & 0xFF) << 24) | */((unify(r) & 0xFF) << 16) | ((unify(g) & 0xFF) << 8)
				| ((unify(b) & 0xFF) << 0);

		int index = colors.indexOf(value);
		if (index < 0) {
			colors.add(value);
			index = colors.size() - 1; // last one
		}
		return index;
	}

}
