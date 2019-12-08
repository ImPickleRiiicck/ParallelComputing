package GameBenchmark.benchmark;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Fight {

	public static void main(String[] args) throws FileNotFoundException {
		
		// Initial Number of characters in a match
		int numberOfCharacters = 64;
		
		// Array of characters
		Character[] characters = new Character[numberOfCharacters];
		
		final Random r = new Random();

		// Character names are located in a file, and they are read in and added to the character list
		File characterFile = new File("C:" + File.separator + "Users" + File.separator + "rober" 
				+ File.separator + "csc375-workspace" + File.separator + "benchmark" 
				+ File.separator + "src" + File.separator + "main" + File.separator 
				+ "java" + File.separator + "GameBenchmark" + File.separator + "benchmark" 
				+ File.separator + "CharacterNames.txt");
		Scanner sc = new Scanner(characterFile);
		final String[] characterNames = sc.next().split(",");
		sc.close();
		
		//System.out.println(characterNames.length);
		
		// Run through the list of characters and add them to the array. Also 
		// assign each character a random amount of hit points between 1 and 5.
		for (int i = 0; i < numberOfCharacters; i++) {
			int hp = r.nextInt(5) + 1;
			characters[i] = new Character(characterNames[i], hp);
			//System.out.println("The hit points is " + hp);
			//System.out.println("Added " + characterNames[i] + " with " + hp + " hit points.");
		}
		
		// Load the battlefield.
		final Battlefield battleField = new Battlefield(characters);
		
		// Spawn the characters. This is done at random to simulate intermediate spawn times.
		new Thread(() -> {
			while(true) {
				if (r.nextInt(999999) == 42) {
					//System.out.println("SPAWN");
					int random = r .nextInt(numberOfCharacters);
					battleField.spawn(new Character(characterNames[random], r.nextInt(5) + 1));
				}
			}
		}, "Spawning").start();
		
		// Thread that simulates characters taking damage. It continuously runs choosing a character
		// from the character list at random and they take damage.
		new Thread(() -> {
			while(true) {
				if (r.nextInt(999999) == 42) {
					//System.out.println("PEW");
					int random = r .nextInt(numberOfCharacters);
					battleField.damageCharacter(characterNames[random]);
				}
			}
		}, "Damaging").start();
	}

}
