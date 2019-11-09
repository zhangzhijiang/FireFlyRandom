/**
 * 
 */
package com.firefly.random;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author mark.zhang
 *
 */
public enum RandomNumberFileGenerator {
	INSTANCE;

	
	Logger logger = LoggerFactory.getLogger(RandomNumberFileGenerator.class);
	
	/**
	 * @param bits - seed to generate random number
	 * @param numbers - total count of random numbers to generate
	 * @param outputFileName -- output file name for generated unsorted numbers
	 */
	public void generateRandomNumberFile(int bits, int numbers, String outputFileName) {
		if (numbers < 0)
			throw new IllegalArgumentException("total number of random numbers to generate cannot be negative.");
		if (bits < 2)
			throw new IllegalArgumentException("bits is too small to generate random numbers.");
		if (BigInteger.valueOf((numbers)).bitLength() >= bits)
			throw new IllegalArgumentException("bits is too small to generate random numbers.");
		
		File outputFile=new File(outputFileName);
		HashSet<BigInteger> hs = new HashSet<>();

		outputFile.delete();
		BigInteger num;

		try {

			PrintWriter out = new PrintWriter(new FileWriter(outputFile));
			do {
				num = new BigInteger(bits, new Random());

				if (!hs.contains(num)) {
					// System.out.println(hs.size() + " --> adding ....." + num);

					out.println(num.toString());

					hs.add(num);

					if (((hs.size()) % 100_000) == 0) {
						System.out.println(hs.size() + " random numbers generated!");
						out.flush();
					}
				} else {
					logger.info("collision happened XXXXXXXXXXXXXXXXX " + num);

				}
			} while (hs.size() < numbers);
			out.close();
			System.out.println("Total " + hs.size() + " random numbers generated! DONE!");

		} catch (IOException e) {
			System.out.println("Error while writing numbers to file " + outputFile);
		}

	}
	
	
	/**
	 * @param inputFileName - unsorted original file
	 * @param outputFileName - sorted numbers file
	 * @throws IOException
	 */
	public void generateSortedNumberFile(String inputFileName,String outputFileName) {
		if(StringUtils.isEmpty(inputFileName)) return; // no file name specified. just return;
		if(StringUtils.isEmpty(outputFileName)) return;
		
		try {
		Path path=Paths.get(inputFileName);
		File outputFile=new File(outputFileName);
		outputFile.delete(); //delete old file
		
		//read , sort, write random numbers from original file to sorted file
		 try(Stream<String> inputNumbers = Files.lines(path);PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));){
			 inputNumbers.map(BigInteger::new).sorted().forEach(outFile::println);
			 
		 }
		}catch (IOException e) {
			
			
		}
		
	}

}
