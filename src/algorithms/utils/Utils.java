package src.algorithms.utils;

import javax.xml.bind.DatatypeConverter;

public class Utils {
	
	public static String toHexString(byte[] byteArray){
		return DatatypeConverter.printHexBinary(byteArray);
	}
}
