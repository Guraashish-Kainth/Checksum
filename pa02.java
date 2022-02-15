/*=============================================================================
| Assignment: pa02 - Calculating an 8, 16, or 32 bit
| checksum on an ASCII input file
|
| Author: Guraashish Kainth
| Language: Java
|
| To Compile: javac pa02.java
|
| To Execute: java -> java pa02 inputFile.txt 8
| where inputFile.txt is an ASCII input file
| and the number 8 could also be 16 or 32
| which are the valid checksum sizes, all
| other values are rejected with an error message
| and program termination
|
| Note: All input files are simple 8 bit ASCII input
|
| Class: CIS3360 - Security in Computing - Fall 2021
| Instructor: McAlpin
| Due Date: 11/21/21
|
+=============================================================================*/


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class pa02 {
	static int characterCnt;
	public static void main(String args[]) throws FileNotFoundException {
		char inputText[] = new char[512];
		String fileName = args[0];
		int checkSumSize = Integer.parseInt(args[1]);
		int checksum = 0;
		characterCnt = 0;
		File input = new File(fileName);
		Scanner fileInput = new Scanner(input);
		fileInput.useDelimiter("");
		characterCnt = getInput(fileInput, inputText);		
		inputText[characterCnt] = '\n';
		characterCnt++;
		int pre_c_Count = characterCnt;
		checksum = calculateCheckSum(inputText, checkSumSize, checksum);
		for(int i = pre_c_Count; i < characterCnt; i++) {
			inputText[i] = 'X';
		}
		
		printCheckSum(inputText, characterCnt, checksum, checkSumSize);
		
	}

	private static int calculateCheckSum(char[] inputText, int checkSumSize, int checksum) {
		if(checkSumSize == 8) {
			return checkSum8(inputText, checksum);
		}
		else if(checkSumSize == 16) {
			return checkSum16(inputText, checksum);
		}
		else if(checkSumSize == 32) {
			return checkSum32(inputText, checksum);
		}
		
		else {
			System.err.printf("Valid checksum sizes are 8, 16, or 32\n");
			System.exit(0);
			return 0;
		}	
	}

	private static int checkSum32(char[] inputText, int checksum) {
		String hexValue = new String();		
		int i = 0;
		int x = 0;
		while(inputText[i + 3] != '\0' && inputText[i + 3] != '\n') {
			x = (int) inputText[i];
			hexValue = Integer.toHexString(x);
			x = (int) inputText[i + 1];
			hexValue += Integer.toHexString(x);
			x = (int) inputText[i + 2];
			hexValue += Integer.toHexString(x);
			x = (int) inputText[i + 3];
			hexValue += Integer.toHexString(x);
			x = Integer.parseInt(hexValue, 16);
			checksum += x;
			i = i + 4;
		}
		
		if(inputText[i] == '\0' || inputText[i] == '\n') {
			hexValue = "0a";
			x = (int) 'X';
			hexValue += Integer.toHexString(x);
			x = (int) 'X';
			hexValue += Integer.toHexString(x);
			x = (int) 'X';
			hexValue += Integer.toHexString(x);
			x = Integer.parseInt(hexValue, 16);
			checksum += x;
			characterCnt += 3;
		}
		else if(inputText[i + 1] == '\0' || inputText[i + 1] == '\n'){
			x = (int) inputText[i];
			hexValue = Integer.toHexString(x);
			hexValue += "0a";			
			x = (int) 'X';
			hexValue += Integer.toHexString(x);
			x = (int) 'X';
			hexValue += Integer.toHexString(x);
			x = Integer.parseInt(hexValue, 16);
			checksum += x;
			characterCnt += 2;
		}
		
		else if(inputText[i + 2] == '\0' || inputText[i + 2] == '\n'){
			x = (int) inputText[i];
			hexValue = Integer.toHexString(x);
			x = (int) inputText[i + 1];
			hexValue += Integer.toHexString(x);
			hexValue += "0a";			
			x = (int) 'X';
			hexValue += Integer.toHexString(x);
			x = Integer.parseInt(hexValue, 16);
			checksum += x;
			characterCnt += 1;
		}
		
		else {
			x = (int) inputText[i];
			hexValue = Integer.toHexString(x);
			x = (int) inputText[i + 1];
			hexValue += Integer.toHexString(x);
			x = (int) inputText[i + 2];
			hexValue += Integer.toHexString(x);	
			hexValue += "0a";
			x = Integer.parseInt(hexValue, 16);			
			checksum += x;
		}
		
		if (Integer.toHexString(checksum).length() > 8){	
			int y = Integer.toHexString(checksum).length();
	         hexValue = Integer.toHexString(checksum).substring(y - 8, y);
	         checksum = Integer.parseInt(hexValue, 16);
	         
	     }
		
		return checksum;
	}

	private static int checkSum16(char[] inputText, int checksum) {
		String hexValue = new String();		
		int i = 0;
		int x = 0;
		while(inputText[i + 1] != '\0' && inputText[i + 1] != '\n') {
			x = (int) inputText[i];
			hexValue = Integer.toHexString(x);
			x = (int) inputText[i + 1];
			hexValue += Integer.toHexString(x);
			x = Integer.parseInt(hexValue, 16);
			checksum += x;
			i = i + 2;
		}
		if(inputText[i] == '\0' || inputText[i] == '\n') {
			hexValue = "0a";
			x = (int) 'X';
			hexValue += Integer.toHexString(x);
			x = Integer.parseInt(hexValue, 16);
			checksum += x;
			characterCnt += 1;
		}
		else {
			x = (int) inputText[i];
			hexValue = Integer.toHexString(x);			
			hexValue += "0a";
			x = Integer.parseInt(hexValue, 16);
			checksum += x;
		}
		
		if (Integer.toHexString(checksum).length() > 4){	
			int y = Integer.toHexString(checksum).length();
	         hexValue = Integer.toHexString(checksum).substring(y - 4, y);
	         checksum = Integer.parseInt(hexValue, 16);
	         
	     }	
		
		return checksum;
	}

	private static int checkSum8(char[] inputText, int checksum) {
		String hexValue = new String();		
		int i = 0;
		int x = 0;
		while(inputText[i] != '\0' && inputText[i] != '\n') {
			x = (int) inputText[i];
			hexValue = Integer.toHexString(x);
			x = Integer.parseInt(hexValue, 16);
			checksum += x;
			i++;
		}
		hexValue = "0a";
		x = Integer.parseInt(hexValue, 16);
		checksum += x;
		i++;
		
		if (Integer.toHexString(checksum).length() > 2){
			int y = Integer.toHexString(checksum).length();
	         hexValue = Integer.toHexString(checksum).substring(y - 2, y);
	         checksum = Integer.parseInt(hexValue, 16);
	         
	     }
		
		return checksum;
	}

	private static void printCheckSum(char[] inputText, int characterCnt, int checksum, int checkSumSize) {
		int charLineCounter = 0;		
		//For loop to print all of the plaintext
		System.out.printf("\n");
		for(int i = 0; i < characterCnt; i++) {
			if(charLineCounter == 80) {
				System.out.printf("\n");
				charLineCounter = 0;
			}
			 System.out.printf("%c", inputText[i]);
			 charLineCounter++;
		}
		System.out.printf("\n%d bit checksum is %s for all  %s chars\n", checkSumSize, Integer.toHexString(checksum), characterCnt);
		
		
	}

	private static int getInput(Scanner fileInput, char[] inputText) {
		int characterCnt = 0;
		String temp;
		while(fileInput.hasNext()) {
	    	//Grabbing the next char
			temp = fileInput.next();
			if(!(Character.isISOControl(temp.charAt(0)))) {
				inputText[characterCnt] = temp.charAt(0);
				characterCnt++;	    	
			}
	    }
		return characterCnt;	    
	}
}

/*=============================================================================
| I Guraashish Kainth affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/
