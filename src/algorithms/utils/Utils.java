package src.algorithms.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

public class Utils {
	
	public static String toHexString(byte[] byteArray){
		return DatatypeConverter.printHexBinary(byteArray);
	}
	
	public static String toHexString(String string) {
	    return String.format("%x", new BigInteger(1, string.getBytes()));
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
		String returnString = "";
		for(int i = 0; i < intByteArray.length; i++){
			if(intByteArray[i] < 0x10){
				returnString += "0";
			}
			returnString += Integer.toHexString(intByteArray[i]);
		}
		return returnString;
	}
	
	public static int[][] toMatrix(int[] array, int rows, int columns) throws Exception{
		if(rows * columns != array.length){
			throw new Exception("Utils - Impossível fazer essa transformação");
		}else{
			int[][] matrix = new int[rows][columns];
			
			for(int i = 0; i < rows; i++){
				for(int j = 0; j < columns; j++){
					matrix[j][i] = array[i * columns + j];
				}
			}
			return matrix;
		}
	}
	
	public static int[] toArray(int[][] matrix){
		
		int[] array= new int[matrix.length * matrix[0].length];
		int rows = matrix.length;
		int columns = matrix[0].length;
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				array[j * columns + i] = matrix[i][j];
			}
		}
		return array;
	}
	
	public static int[] toIntArray(List<Integer> list){
		int[] array = new int[list.size()];
		for(int i = 0; i < list.size(); i++){
			array[i] = list.get(i).intValue();
		}
		
		return array;
	}
	
	public static List<Integer> toIntList(int[] array){
		List<Integer> list = new ArrayList<Integer>(array.length);
		for(int i = 0; i < array.length; i++){
			list.add(array[i]);
		}
		
		return list;
	}
	
	public static int[] hexStringToIntArray(String hexString){
		int[] intArray = new int[hexString.length() / 2];
		String[] hexValues = hexString.split("");
		for(int i = 0; i < hexString.length() / 2; i++){
			intArray[i] = Integer.parseInt(hexValues[2 * i]  + hexValues[2 * i + 1] ,16);
		}
		
		return intArray;
	}
	
	public static String hexStringToClearString(String hexString){
		String output = "";
		for (int i = 0; i < hexString.length(); i+=2) {
	        String str = hexString.substring(i, i+2);
	        output += (char)Integer.parseInt(str, 16);
	    }
		return output;
	}
	
	public static void printMatrix(int[][] matrix){
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix.length; j++){
				System.out.print(Integer.toHexString(matrix[i][j]) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static int[] increment(int[] intByteArray, int increment){
		int[] incremented = new int[intByteArray.length];
		int sum, vaiUm = 0;
		for(int i = intByteArray.length - 1; i >= 0; i--){
			sum = (intByteArray[i] + increment + vaiUm);
			vaiUm = sum / 0x100;
			incremented[i] = sum % 0x100;
		}
		return incremented;
	}
}
