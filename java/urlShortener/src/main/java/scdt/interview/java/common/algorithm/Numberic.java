package scdt.interview.java.common.algorithm;

/**
 * @author Laerfu Zhang
 */

public class Numberic {

	private static final char[] DIGITAL_NORMAL = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
			'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z' };

	// 防止碰库，简单混淆一下。
	private static final char[] DIGITAL_MIX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'U', 'V', 'W', 'X', 'Y',
			'Z', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'u', 'v', 'w', 'x', 'y', 'z' };

	/**
	 * 递归实现进制转换，字典混淆
	 * @param seq 要转换的数字
	 * @param rad 进制
	 * @return 结果 例：NORMAL字典下，getMixRadStr(10, 16) -> a 
	 */
	public static String getRadStr(long seq, int rad, boolean getMixed) {
		char[] DIGITAL_DIC = DIGITAL_NORMAL;
		if(getMixed) {
			DIGITAL_DIC = DIGITAL_MIX;
		}
		if(seq < rad) {
			return String.valueOf(DIGITAL_DIC[(int) seq]);
		} else {
			return getRadStr(seq/rad, rad, getMixed) + DIGITAL_DIC[(int) (seq % rad)];
		}
	}
	
}