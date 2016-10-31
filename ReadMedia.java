package com.mycompany.workout;

import java.util.Random;

public class ReadMedia {
	public static void main(String[] args) {
		try {
			while (true) {
				Random ran = new Random();
				System.out.println(ran.nextInt(2));
			}

		} catch (Exception ioE) {
			// problem reading, handle case
		}
	}
}
