package GameBenchmark.benchmark;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Battlefield class
 * @author rober
 *
 */
public class Battlefield {

	// Variables
	private volatile HashMap<String, Character> survivingCharacters;
	private AtomicInteger seq = new AtomicInteger();
	private AtomicInteger numberOfCharacters = new AtomicInteger();
	
	/**
	 * Battlefield Constructor
	 * @param characters
	 */
	public Battlefield(Character[] characters) {
		// Create a new hashmap of living players
		survivingCharacters = new HashMap<String, Character>(characters.length);
		
		// Add all of the characters to the hashmap
		for (int i = 0; i < characters.length; i++) {
			spawn(characters[i]);
		}
	}

	/**
	 * Method to add a character to the battlefield. Thid method
	 * checks to see if they are already alive, if they are not,
	 * it respawns them.
	 * 
	 * @param character
	 * @return
	 */
	public boolean spawn(Character character) {
		for(;;) {
			
			if(survivingCharacters.get(character.getName()) == null) {
				
				int seqCurr = seq.get();
				//System.out.println(seqCurr);
				// Check is seq is even.
				if (seqCurr%2 == 0) {
					
					if (seq.compareAndSet(seqCurr, seqCurr + 1)) {
						
						// If the character did not exist in the database, let's add it!
						//System.out.println("Trying to add " + character.name + " to the battlefield.");
						survivingCharacters.putIfAbsent(character.getName(), character);
						//System.out.println("Added " + character.getName());
						
						if (character == survivingCharacters.get(character.name)) {
							numberOfCharacters.incrementAndGet();
							seq.incrementAndGet();
							return true;
						}
						seq.incrementAndGet();
						return false;
					}
				}
			} else {
				return false;
			}
		}	
	}

	/**
	 * Method to remove a character from the battlefield.
	 * This is only called after a character has taken
	 * enough damage to be declared dead.
	 * 
	 * @param character
	 */
	private void removeCharacter(Character character) {
		// Keep trying it!
		for (;;) {
			// If the character exists, try and remove them.
			if (survivingCharacters.get(character.getName()) != null) {
				
				// Temp for the current sequence
				int seqCurr = seq.get();
				
				// Check is seq is even.
				if (seqCurr%2 == 0) {
					
					if (seq.compareAndSet(seqCurr, seqCurr + 1)) {
						
						// If the character did not exist in the database, let's add it!
						survivingCharacters.remove(character.getName());
						seq.incrementAndGet();
						numberOfCharacters.decrementAndGet();
						//System.out.println(character.getName() + " was killed in action.");
						return;
					}
					return;
				}
				seq.incrementAndGet();
			}
			return;
		}
	}
	
	/**
	 * Method used to damage a character.
	 * If the character has taken enough damage then are
	 * removed from the battlefiend until the respawn.
	 * 
	 * @param name
	 */
	public void damageCharacter (String name) {
		for (;;) {
			int seqCurr = seq.get();
			if (seqCurr%2 == 0) {
				Character character = survivingCharacters.get(name);
				if (0 == seqCurr - seq.get()) {
					if (character != null) {
						//System.out.println("Bing bing bang, booka booka!");
						boolean isDead = character.damage();
						if (isDead) {
							removeCharacter(character);
							//System.out.println("Removing " + character.getName() + " from the battlefield.");
						}
					}
					return;
				}
			} return;
		}
	}
	
	public Character getCharacter(String name) {
		for (;;) {
			int seqCurr = seq.get();
			if (seqCurr%2 == 0) {
				Character character = survivingCharacters.get(name);
				if (0 == seqCurr - seq.get()) {
					return character;
				}
			}
		}
	}
}
