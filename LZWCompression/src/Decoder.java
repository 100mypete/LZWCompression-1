import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.io.*;

public class Decoder {

	// private HashMap<Integer, String> dictionary;
	// private byte[] fileContents;
	// private int sizeOfDictionary;
	
	
	
	// public Decoder(String fileName) throws IOException {
		// sizeOfDictionary = 0;
		// //code below puts in normal chars into the table
		// dictionary = new HashMap<Integer, String> ();
		// for(int index = 0; index < 256; index++) {
		// 	char newChar = (char)index; //changes the index of ASCII to a character
		// 	String toBeAdded = "" + newChar; //changes the character to a string
		// 	dictionary.put(index, toBeAdded); //puts string into HashMap with corresponding spot in table
		// 	sizeOfDictionary++;
		// }
		
		
		
		/**
		 * puts all of the bytes of the file into an array of bytes
		 */
		// Path path = Paths.get(fileName);
		// System.out.println(path.toAbsolutePath());
		// try {
		// 	fileContents =  Files.readAllBytes(path);
		// } catch (IOException e) {
		// 	// TODO Auto-generated catch block
		// 	e.printStackTrace();
		// }
		
		
		/**
		 * turns array of bytes into one long string of 0s and 1s
		 */
		// StringBuilder strBuilder = new StringBuilder ();
		// for (int i = 0; i < fileContents.length; i ++) {
		// 	byte b1 = fileContents [i];
		// 	strBuilder.append(String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0'));
		// }
		
		
		// System.out.println(decode (strBuilder.toString())); 
		
		
	// }
	
	
	/**
	 * 
	 * 
	 * @param toBeDecoded is the String that was created by the binary file
	 * @return the decoded message
	 */
	public static void decode (String inputFile, String outputFile) throws IOException {
		
		
		StringBuilder toRet = new StringBuilder ();
		FileInputStream in = new FileInputStream(inputFile);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
		byte[] byteArray = in.readAllBytes();

		
		// converts byte array into a giant binary String
		for(int i = 0; i < byteArray.length; i++)
		{
			toRet.append(toBinary(byteArray[i]));
		}

		System.out.println(toRet);

		/**
		 * parses String into proper size of each byte
		 */
		ArrayList<Integer> parsedInts = new ArrayList<Integer>();
		for (int i = 0; i <= toRet.length() - 9; i += 9) {
			parsedInts.add(binStringToInteger(toRet.substring(i,i+9)));
		}

		System.out.println(parsedInts);

		// String current = "";
		// String next = "";
		// for (int i = 0; i < parsedInts.size()-1; i++) {
		// 	current = dictionary.get(parsedInts.get(i));
		// 	next= dictionary.get(parsedInts.get(i+1));
		// 	String firstOfNext = next.charAt(0) + "";
		// 	if (!dictionary.containsValue(current + firstOfNext)) {
		// 		dictionary.put(sizeOfDictionary, current + firstOfNext);
		// 		sizeOfDictionary++;
		// 	}
		// 	toRet.append(current);
		// }
		// toRet.append(next);

		// initial dictionary values
		HashMap<Integer, String> map = new HashMap<Integer,String>();
		for(int i = 0; i < 256; i++)
			map.put(i, "" + (char)i);
		
		// decoding part, involving dictionary and writing to outputFile
		int nextKey = 256;
		int cur = parsedInts.get(0);
		String str = map.get(cur);
		String ch = "" + str.charAt(0);
		out.print(str);
		for(int i = 1; i < parsedInts.size(); i++)
		{
			int nex = parsedInts.get(i);

			// adding keys to dictionary, reverse-engineering compression dictionary following LZW rules
			if(!map.containsKey(nex))
			{
				str = map.get(cur);
				str = str + ch;
			}
			else
			{
				str = map.get(nex);
			}
			out.print(str);
			ch = "" + str.charAt(0);
			map.put(nextKey, map.get(cur) + ch);
			nextKey++;
			cur = nex;
		}
		
		out.println();
		
		out.close();

		// return toRet.toString();
		
	}
	
	public static int binStringToInteger (String input){
   
        	int convertedInt = 0;
	        for (int i = 0; i < 9; i++) {
	
	            if (input.charAt(i) == '1') {
	                convertedInt += (1 << (8 - i));
	            }
	        }
        
		return convertedInt;
	}

	public static String toBinary(int num)
	{
		String current = Integer.toBinaryString(num);
		StringBuilder str = new StringBuilder();
		while(current.length()+str.length() < 8)
		{
			str.append("0");
		}
		if(current.length() > 8)
		{
			current = current.substring(current.length() - 8);
		}
		str.append(current);
		return str.toString();
	}
	
	public static void main (String [] args) throws IOException {
		long startTime = System.nanoTime();
		decode("output3.bin", "output3.txt");
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		System.out.println ("" + duration + " ms");
	}
}
