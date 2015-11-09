package src.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import src.algorithms.utils.Utils;

public class AES {
	
	private int[][] state;
	
	private int[][] initializationVector;
	
	private int round;
	
	private static int[] originalKey;
	private static int[] expandedKey;
	
	private static final int KEY_SIZE = 16;
	
//	numero de bytes da chave original e do bloco
	private static final int N = 16;
	private static final int BLOCK_SIZE = 16;
	
//	numero de bytes da chave expandida
	private static final int B = 176;
	
	private static final int MATRIXES_COLUMN_NUMBER = 4;
	private static final int MATRIXES_ROWS_NUMBER = 4;
	private static OpMode operationMode = OpMode.EBC;
	
	enum OpMode{
		CBC,
		EBC
	};
	
	enum PaddingType{
		PKCS,
		NIST
	};

//	https://en.wikipedia.org/wiki/Rijndael_S-box
	static final int[] S_BOX = {
		0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
		0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
		0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
		0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
		0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
		0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
		0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
		0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
		0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
		0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
		0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
		0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
		0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
		0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
		0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
		0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16
	};
	
	static final int[] INV_S_BOX = {
		0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb, 
		0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb, 
		0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e, 
		0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25, 
		0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92, 
		0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84, 
		0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06, 
		0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b, 
		0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73, 
		0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e, 
		0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b, 
		0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4, 
		0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f, 
		0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef, 
		0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61, 
		0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d
	};
	
//	https://en.wikipedia.org/wiki/Rijndael_mix_columns
	
	public static final int[][] GALOIS_MATRIX = {
		{0x02, 0x03, 0x01, 0x01},
		{0x01, 0x02, 0x03, 0x01},
		{0x01, 0x01, 0x02, 0x03},
		{0x03, 0x01, 0x01, 0x02}
	};
	
	public static final int[][] INV_GALOIS_MATRIX = {
		{0x0e, 0x0b, 0x0d, 0x09},
		{0x09, 0x0e, 0x0b, 0x0d},
		{0x0d, 0x09, 0x0e, 0x0b},
		{0x0b, 0x0d, 0x09, 0x0e}
	};
	
	private static final int[] RCON_TABLE = {
		0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 
		0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 
		0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 
		0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 
		0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 
		0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 
		0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 
		0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 
		0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 
		0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 
		0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 
		0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 
		0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 
		0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 
		0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 
		0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d
	};
	
	 public static int[][] mc2 ={
			 {0x00, 0x02, 0x04, 0x06, 0x08, 0x0a, 0x0c, 0x0e, 0x10, 0x12, 0x14, 0x16, 0x18, 0x1a, 0x1c, 0x1e},
			 {0x20, 0x22, 0x24, 0x26, 0x28, 0x2a, 0x2c, 0x2e, 0x30, 0x32, 0x34, 0x36, 0x38, 0x3a, 0x3c, 0x3e},
             {0x40, 0x42, 0x44, 0x46, 0x48, 0x4a, 0x4c, 0x4e, 0x50, 0x52, 0x54, 0x56, 0x58, 0x5a, 0x5c, 0x5e},
             {0x60, 0x62, 0x64, 0x66, 0x68, 0x6a, 0x6c, 0x6e, 0x70, 0x72, 0x74, 0x76, 0x78, 0x7a, 0x7c, 0x7e},
             {0x80, 0x82, 0x84, 0x86, 0x88, 0x8a, 0x8c, 0x8e, 0x90, 0x92, 0x94, 0x96, 0x98, 0x9a, 0x9c, 0x9e},
             {0xa0, 0xa2, 0xa4, 0xa6, 0xa8, 0xaa, 0xac, 0xae, 0xb0, 0xb2, 0xb4, 0xb6, 0xb8, 0xba, 0xbc, 0xbe},
             {0xc0, 0xc2, 0xc4, 0xc6, 0xc8, 0xca, 0xcc, 0xce, 0xd0, 0xd2, 0xd4, 0xd6, 0xd8, 0xda, 0xdc, 0xde},
             {0xe0, 0xe2, 0xe4, 0xe6, 0xe8, 0xea, 0xec, 0xee, 0xf0, 0xf2, 0xf4, 0xf6, 0xf8, 0xfa, 0xfc, 0xfe},
             {0x1b, 0x19, 0x1f, 0x1d, 0x13, 0x11, 0x17, 0x15, 0x0b, 0x09, 0x0f, 0x0d, 0x03, 0x01, 0x07, 0x05},
             {0x3b, 0x39, 0x3f, 0x3d, 0x33, 0x31, 0x37, 0x35, 0x2b, 0x29, 0x2f, 0x2d, 0x23, 0x21, 0x27, 0x25},
             {0x5b, 0x59, 0x5f, 0x5d, 0x53, 0x51, 0x57, 0x55, 0x4b, 0x49, 0x4f, 0x4d, 0x43, 0x41, 0x47, 0x45},
             {0x7b, 0x79, 0x7f, 0x7d, 0x73, 0x71, 0x77, 0x75, 0x6b, 0x69, 0x6f, 0x6d, 0x63, 0x61, 0x67, 0x65},
             {0x9b, 0x99, 0x9f, 0x9d, 0x93, 0x91, 0x97, 0x95, 0x8b, 0x89, 0x8f, 0x8d, 0x83, 0x81, 0x87, 0x85},
             {0xbb, 0xb9, 0xbf, 0xbd, 0xb3, 0xb1, 0xb7, 0xb5, 0xab, 0xa9, 0xaf, 0xad, 0xa3, 0xa1, 0xa7, 0xa5},
             {0xdb, 0xd9, 0xdf, 0xdd, 0xd3, 0xd1, 0xd7, 0xd5, 0xcb, 0xc9, 0xcf, 0xcd, 0xc3, 0xc1, 0xc7, 0xc5},
             {0xfb, 0xf9, 0xff, 0xfd, 0xf3, 0xf1, 0xf7, 0xf5, 0xeb, 0xe9, 0xef, 0xed, 0xe3, 0xe1, 0xe7, 0xe5}
	 };

	 public final static int[][] mc3 ={   
			{0x00,0x03,0x06,0x05,0x0c,0x0f,0x0a,0x09,0x18,0x1b,0x1e,0x1d,0x14,0x17,0x12,0x11},
			{0x30,0x33,0x36,0x35,0x3c,0x3f,0x3a,0x39,0x28,0x2b,0x2e,0x2d,0x24,0x27,0x22,0x21},
			{0x60,0x63,0x66,0x65,0x6c,0x6f,0x6a,0x69,0x78,0x7b,0x7e,0x7d,0x74,0x77,0x72,0x71},
			{0x50,0x53,0x56,0x55,0x5c,0x5f,0x5a,0x59,0x48,0x4b,0x4e,0x4d,0x44,0x47,0x42,0x41},
			{0xc0,0xc3,0xc6,0xc5,0xcc,0xcf,0xca,0xc9,0xd8,0xdb,0xde,0xdd,0xd4,0xd7,0xd2,0xd1},
			{0xf0,0xf3,0xf6,0xf5,0xfc,0xff,0xfa,0xf9,0xe8,0xeb,0xee,0xed,0xe4,0xe7,0xe2,0xe1},
			{0xa0,0xa3,0xa6,0xa5,0xac,0xaf,0xaa,0xa9,0xb8,0xbb,0xbe,0xbd,0xb4,0xb7,0xb2,0xb1},
			{0x90,0x93,0x96,0x95,0x9c,0x9f,0x9a,0x99,0x88,0x8b,0x8e,0x8d,0x84,0x87,0x82,0x81},
			{0x9b,0x98,0x9d,0x9e,0x97,0x94,0x91,0x92,0x83,0x80,0x85,0x86,0x8f,0x8c,0x89,0x8a},
			{0xab,0xa8,0xad,0xae,0xa7,0xa4,0xa1,0xa2,0xb3,0xb0,0xb5,0xb6,0xbf,0xbc,0xb9,0xba},
			{0xfb,0xf8,0xfd,0xfe,0xf7,0xf4,0xf1,0xf2,0xe3,0xe0,0xe5,0xe6,0xef,0xec,0xe9,0xea},
			{0xcb,0xc8,0xcd,0xce,0xc7,0xc4,0xc1,0xc2,0xd3,0xd0,0xd5,0xd6,0xdf,0xdc,0xd9,0xda},
			{0x5b,0x58,0x5d,0x5e,0x57,0x54,0x51,0x52,0x43,0x40,0x45,0x46,0x4f,0x4c,0x49,0x4a},
			{0x6b,0x68,0x6d,0x6e,0x67,0x64,0x61,0x62,0x73,0x70,0x75,0x76,0x7f,0x7c,0x79,0x7a},
			{0x3b,0x38,0x3d,0x3e,0x37,0x34,0x31,0x32,0x23,0x20,0x25,0x26,0x2f,0x2c,0x29,0x2a},
			{0x0b,0x08,0x0d,0x0e,0x07,0x04,0x01,0x02,0x13,0x10,0x15,0x16,0x1f,0x1c,0x19,0x1a}   
	};

	
	public static String encrypt(String message, String key, OpMode operationMode, PaddingType paddingType){
		List<Integer> encryptedMessage = new ArrayList<Integer>();
		int[] originalMessage = Utils.hexStringToIntArray(message);
		int[] originalKey = Utils.hexStringToIntArray(key);
		
//		pra ver se naao vai ter um bloco com padding...
		int extraRound = (float)originalMessage.length % (float)BLOCK_SIZE > 0.0 ? 1 : 0;
		
		if(operationMode.equals(OpMode.EBC)){
			for(int i = 0; i < originalMessage.length / BLOCK_SIZE + extraRound; i++){
				int [] blockBuffer;
				
				blockBuffer = Arrays.copyOfRange(originalMessage, 
						i * BLOCK_SIZE, 
						i * BLOCK_SIZE + BLOCK_SIZE);
				
				if(i * BLOCK_SIZE + BLOCK_SIZE > originalMessage.length){
					blockBuffer = addPadding(
							Arrays.copyOfRange(originalMessage, 
							i * BLOCK_SIZE, 
							originalMessage.length), 
							BLOCK_SIZE, 
							paddingType);
				}
				
				try {
					int[][] lol = encryptBlock(
								Utils.toMatrix(
									blockBuffer, 
									MATRIXES_ROWS_NUMBER, 
									MATRIXES_COLUMN_NUMBER), 
								originalKey
							);
					blockBuffer = Utils.toArray(
							lol
					);
				} catch (Exception e) {
					System.out.println("erro no AES");
					e.printStackTrace();
					return null;
				}
				
				for(int j = 0; j < blockBuffer.length; j++){
					encryptedMessage.add(blockBuffer[j]);
				}
			}
		}
		int[] encryptedMessageIntArray = Utils.toIntArray(encryptedMessage);
		return Utils.intByteArrayToString(encryptedMessageIntArray);
	}
	
	public static int[][] encryptBlock(int[][] block, int[] originalKey){
		int[] expandedKey = keySchedule(originalKey);
		int round = 0;
		try {
			printShit(block, Utils.toMatrix(Arrays.copyOfRange(expandedKey, round * KEY_SIZE, round * KEY_SIZE + KEY_SIZE), 4, 4), 0);
			block = addRoundKey(block, Utils.toMatrix(
					Arrays.copyOfRange(expandedKey, round * KEY_SIZE, round * KEY_SIZE + KEY_SIZE), 
					MATRIXES_ROWS_NUMBER, 
					MATRIXES_COLUMN_NUMBER)
			);
			printShit(block, Utils.toMatrix(Arrays.copyOfRange(expandedKey, round * KEY_SIZE, round * KEY_SIZE + KEY_SIZE), 4, 4), 1);
			
			for(int i = 0; i < 9; i++){
				round++;
				byteSub(block);
				System.out.println("AFTER BYTE SUB " + round);
				Utils.printMatrix(block);
				shiftRows(block);
				System.out.println("AFTER SHIFT ROWS " + round);
				Utils.printMatrix(block);
				block = mixColumn(block);
				System.out.println("AFTER MIX COLUMN " + round);
				Utils.printMatrix(block);
				block = addRoundKey(block, Utils.toMatrix(
						Arrays.copyOfRange(expandedKey, round * KEY_SIZE, round * KEY_SIZE + KEY_SIZE), 
						MATRIXES_ROWS_NUMBER, 
						MATRIXES_COLUMN_NUMBER)
				);
				System.out.println("AFTER ADD ROUND KEY " + round);
				Utils.printMatrix(block);
				printShit(block, Utils.toMatrix(Arrays.copyOfRange(expandedKey, round * KEY_SIZE, round * KEY_SIZE + KEY_SIZE), 4, 4), round);
			}
			
			round++;
			byteSub(block);
			shiftRows(block);
			block = addRoundKey(block, Utils.toMatrix(
					Arrays.copyOfRange(expandedKey, round * KEY_SIZE, round * KEY_SIZE + KEY_SIZE), 
					MATRIXES_ROWS_NUMBER, 
					MATRIXES_COLUMN_NUMBER)
			);
			printShit(block, Utils.toMatrix(Arrays.copyOfRange(expandedKey, round * KEY_SIZE, round * KEY_SIZE + KEY_SIZE), 4, 4), 0);
			
			return block;
		} catch (Exception e) {
			System.out.println("Algum erro na encryptacao do bloco no AES...");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static int[][] addRoundKey(int[][] block, int[][] roundKey){
		try {
			return xorMatrixes(block, roundKey);
		} catch (Exception e) {
			System.out.println("erro no addRoundKey");
			e.printStackTrace();
		}
		return null;
	}
	
//	  Recebe um bloco (16 "bytes") e so subtitui pelo valor do S_BOX
	public static void byteSub(int[][] block){

		for(int i = 0; i < MATRIXES_ROWS_NUMBER; i ++){
			for(int j = 0; j < MATRIXES_COLUMN_NUMBER; j ++){
				block[i][j] = S_BOX[block[i][j]];
			}
		}
	}

	public static void shiftRows(int[][] matrix){
		for(int i = 0; i < matrix.length; i++){
			rotateLeft(matrix[i], i);
		}
	}
	
	public static int[][] mixColumn(int[][] block){
		int[] sp = new int[4];
	    for (int c = 0; c < 4; c++) {
			sp[0] = galoisMultiplication(0x02, block[0][c]) ^ galoisMultiplication(0x03, block[1][c]) ^ block[2][c] ^ block[3][c];
			sp[1] = block[0][c] ^ galoisMultiplication(0x02, block[1][c]) ^ galoisMultiplication(0x03, block[2][c]) ^ block[3][c];
			sp[2] = block[0][c] ^ block[1][c] ^ galoisMultiplication(0x02, block[2][c]) ^ galoisMultiplication(0x03, block[3][c]);
			sp[3] = galoisMultiplication(0x03, block[0][c]) ^ block[1][c] ^ block[2][c] ^ galoisMultiplication(0x02, block[3][c]);
			for (int i = 0; i < 4; i++) {
				while(sp[i] > 0xFF){
					sp[i] -= 0xFF;
				}
				block[i][c] = sp[i];
			};
	    }
	    return block;
		
//		int[] temp = {0, 0, 0, 0};
//		for(int j = 0; j < block.length; j++){
//			for(int i = 0; i < block[0].length; i++){
//				temp[i] = lineXColumn(GALOIS_MATRIX, block, i, j);
//				while(temp[i] > 0xFF){
//					temp[i] -= 0xFF;
//				}
//			}
//			
//			for(int i = 0; i < block[0].length; i++){
//				block[i][j] = temp[i];
//			}
//		}
//		return block;
		
//		try {
//			return galoisMatrixMultiplication(GALOIS_MATRIX, block);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
	}
	
	public static void rotateLeft(int[] array, int shifts){
		int arrayLength = array.length;
		int temp;
		for(int i = 0; i < shifts; i++){
			temp = array[0];
			for(int j = 0; j < array.length - 1; j++){
				
				array[j % arrayLength] = array[(j + 1) % arrayLength];
			}
			array[arrayLength - 1] = temp;
		}
	}

	
	
	/*
	 * PRIVATE STUFF
	 */
	
	/*
	 * Fu�oes do modo de opera��o
	 */
	public static int[] addPadding(int[] block, int blockSize, PaddingType paddingType){
		
		int[] newBlock = new int[blockSize];
		int endOfBlock = block.length;
		
		for(int i = 0; i < endOfBlock; i++){
			newBlock[i] = block[i];
		}
		
		if(paddingType.equals(PaddingType.NIST)){
			for(int i = endOfBlock; i < blockSize; i++){
				newBlock[i] = blockSize - block.length;
			}
			
		}else if(paddingType.equals(PaddingType.PKCS)){
			newBlock[endOfBlock] = 1;
			for(int i = endOfBlock + 1; i < blockSize; i++){
				newBlock[i] = 0;
			}
		}
		
		return newBlock;
	}
	
	/*
	 * Funcoes para o key scheduling
	 */
	private static void rotWord(int[] word) throws Exception{
		if(word.length != 4){
			throw new Exception("RotWord - word tem 4 bytes!");
		}
		
		rotateLeft(word, 1);
	}
	
	private static void subWord(int[] word) throws Exception{
		if(word.length != 4){
			throw new Exception("RotWord - word tem 4 bytes!");
		}
		
		for(int i = 0; i < 4; i++){
			word[i] = S_BOX[word[i]];
		}
	}
	
	private static int rcon(int round){
		return RCON_TABLE[round];
	}
	
	private static int[] ek(int[] expandedKey, int offset){
		return Arrays.copyOfRange(expandedKey, offset, offset + 4);
	}
	
	private static int[] k(int[] originalKey, int offset){
		return ek(originalKey, offset);
	}
	
//	https://en.wikipedia.org/wiki/Rijndael_key_schedule
	private static void keyScheduleCore(int[] roundKey, int round){
		
		try {
			rotWord(roundKey);
			subWord(roundKey);
			roundKey[0] ^= rcon(round);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static int[] keySchedule(int[] originalKey){
		List<Integer> expandedKey = new ArrayList<Integer>();
		int[] t;
		
		for(int i = 0; i < N; i++){
			expandedKey.add(originalKey[i]);
		}
		
		int roundIteration = 1;
		
		while(expandedKey.size() < B){
			t = Arrays.copyOfRange(Utils.toIntArray(expandedKey), expandedKey.size() - 4, expandedKey.size());
			keyScheduleCore(t, roundIteration);
			roundIteration++;
			for(int i = 0; i < t.length; i++){
				t[i] ^= expandedKey.get(expandedKey.size() - N + i);
			}
			for(int i = 0; i < t.length; i++){
				expandedKey.add(t[i]);
			}
			
			for(int i = 0; i < 3; i++){
				t = Arrays.copyOfRange(Utils.toIntArray(expandedKey), expandedKey.size() - 4, expandedKey.size());
				for(int j = 0; j < t.length; j++){
					t[j] ^= expandedKey.get(expandedKey.size() - 16 + j);
				}
				for(int j = 0; j < t.length; j++){
					expandedKey.add(t[j]);
				}
			}
		}
		
		return Utils.toIntArray(expandedKey);
	}
	
	/*
	 * Fun�oes auxiliares ou intermediarias
	 */
	private static int[][] xorMatrixes(int[][] matrix1, int[][] matrix2) throws Exception{
		if(matrix1.length != matrix2.length || matrix2[0].length != matrix1[0].length){
			System.out.println("Erro no xor de matrizes");
			throw new Exception();
		}
		
		int matrixResult[][] = new int[matrix1.length][matrix1[0].length];
		for(int i = 0; i < matrix1.length; i++){
			for(int j = 0; j < matrix1[i].length; j++){
				matrixResult[i][j] = matrix1[i][j] ^ matrix2[i][j];
			}
		}
		return matrixResult;
	}
	
//	� a multiplica��o de matrizes no mundo do Galois, onde tudo pode acontecer
	private static int[][] galoisMatrixMultiplication(int[][] matrix1, int[][] matrix2) throws Exception{
		
		if(matrix1.length != matrix2.length || matrix2[0].length != matrix1[0].length){
			System.out.println("Erro na mult de matrizes");
			throw new Exception();
		}
		
		int[][] transformedMatrix= new int[matrix2.length][matrix2[0].length];
		int temp = 0;
		
		for(int i = 0; i < matrix1.length; i++){
			for(int j = 0; j < matrix1[0].length; j++){
				temp = lineXColumn(matrix1, matrix2, j, i);
				while(temp > 0xFF){
					temp -= 0xFF;
				}
				transformedMatrix[j][i] = temp;
			}
		}
		
		return transformedMatrix;
		
	}

	private static int galoisMultiplication(int a, int b){
//		int product = 0;
//		int aDupe = a;
//		int bDupe = b;
//		boolean aHiBitSet;
//		
//		for(int i = 0; i < 8; i++){
//			if((bDupe & 1) == 1){
//				product ^= aDupe;
//			}
//			aHiBitSet = (aDupe & 0b10000000) == 1;
//			
//			aDupe <<= 1;
//			if(aHiBitSet){
//				aDupe ^= 0x1b;
//			}
//			
//			bDupe >>= 1;
//		}
//		return product;
		if (a == 1) {
            return b;
        } else if (a == 2) {
            return mc2[b / 16][b % 16];
        } else if (a == 3) {
            return mc3[b / 16][b % 16];
        }
        return 0;
	}
	
//	  calcula um elemento na multiplicacao de matriz
//	  Considera que as matrizes sao compativeis
	private static int lineXColumn(int[][] matrix1, int[][] matrix2, int line, int column){
		int result = 0;
		
		for(int i = 0; i < matrix1[line].length; i++){
			result ^= galoisMultiplication(matrix1[line][i], matrix2[i][column]);
		}
		
		return result;
	}
	
	private static void printShit(int[][] block, int[][] key, int i){
		System.out.println("state " + i + ": ");
		System.out.println("block: ");
		Utils.printMatrix(block);
		System.out.println("round key: ");
		Utils.printMatrix(key);
	}
	
	public static void main(String args[]){
		String a = AES.encrypt("54776F204F6E65204E696E652054776F1234", "5468617473206D79204B756E67204675", OpMode.EBC, PaddingType.NIST);
		System.out.println(a);
	}
}