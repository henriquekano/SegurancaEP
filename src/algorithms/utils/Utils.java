package src.algorithms.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

public class Utils {
	
	public static String toHexString(byte[] byteArray){
		return DatatypeConverter.printHexBinary(byteArray);
	}
	
	public static String toHexString(int[] intByteArray){
		String buffer = "0x";
		String asd;
		for(int i = 0; i < intByteArray.length; i++){
			asd = Integer.toHexString(intByteArray[i]).toUpperCase();
			buffer += asd;
		}
		return buffer;
	}
	
	public static int[] stringToIntByteArray(String string){
		byte[] stringBytes = string.getBytes();
		int[] resultArray = new int[stringBytes.length];
		for(int i = 0; i < stringBytes.length; i++){
			resultArray[i] = (int)(stringBytes[i]);
		}
		return resultArray;
	}
	
	public static String intByteArrayToString(int[] intByteArray){
		byte[] bytes = new byte[intByteArray.length];
		for(int i = 0; i < intByteArray.length; i++){
			bytes[i] = (byte)(intByteArray[i]);
		}
		return new String(bytes);
	}
	
	public static int[][] toMatrix(int[] array, int rows, int columns) throws Exception{
		if(rows * columns != array.length){
			throw new Exception("Utils - Impossível fazer essa transformação");
		}else{
			int[][] matrix = new int[rows][columns];
			
			for(int i = 0; i < rows; i++){
				for(int j = 0; j < columns; j++){
					matrix[i][j] = array[i * columns + j];
				}
			}
			return matrix;
		}
	}
	
	public static int[] toIntArray(List<Integer> list){
		int[] array = new int[list.size()];
		for(int i = 0; i < list.size(); i++){
			array[i] = list.get(i).intValue();
		}
		
		return array;
	}
}
