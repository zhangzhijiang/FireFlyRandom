package com.firefly;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.firefly.random.RandomNumberFileGenerator;

@SpringBootApplication
public class FireFlyRandomApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FireFlyRandomApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		LocalTime startTime = LocalTime.now();
		System.out.println("Generating random numbers started.....@" + startTime);
		
		RandomNumberFileGenerator rf = RandomNumberFileGenerator.INSTANCE;
		rf.generateRandomNumberFile(48, 1_000_000, "random1mOriginal.txt");
		
		LocalTime endTime = LocalTime.now();
		System.out.println("Generating random numbers ended.....@" + endTime);
		
		System.out.println(Duration.between(startTime, endTime));
		
		startTime = LocalTime.now();
		System.out.println("Start sorting numbers and writing to file .....@" + startTime);
		
		rf.generateSortedNumberFile("random1mOriginal.txt", "random1mSorted.txt");
		
		 endTime = LocalTime.now();
		System.out.println("DONE !! Sorting numbers and writing to file ended .....@" + endTime);
		
	}

}
