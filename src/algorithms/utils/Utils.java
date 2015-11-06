package src.algorithms.utils;

import java.util.List;

import javax.xml.bind.DatatypeConverter;

public class Utils {
	
	public static String toHexString(byte[] byteArray){
		return DatatypeConverter.printHexBinary(byteArray);
	}
	
	public static int[] toIntArray(List<Integer> list){
		int[] array = new int[list.size()];
		for(int i = 0; i < list.size(); i++){
			array[i] = list.get(i).intValue();
		}
		
		return array;
	}
}
