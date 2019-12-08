package jdkbenchmark;

import java.util.concurrent.ConcurrentHashMap;

import GameBenchmark.benchmark.Character;

public class JdkBattlefield {
	private ConcurrentHashMap<String, Character> survivingCharacters;
	
	public JdkBattlefield(Character[] characters) {
		// Create a new ConcurrentHashMap of living players
		survivingCharacters = new ConcurrentHashMap<String, Character>(characters.length);
		
		// Add all of the characters to the hashmap
		for (int i = 0; i < characters.length; i++) {
			spawn(characters[i]);
		}
	}
	
	public boolean spawn(Character character) {
		survivingCharacters.putIfAbsent(character.getName(), character);
		if (character == survivingCharacters.get(character.getName())) {
			return true;
		}
		return false;
	}
	
	public void removeCharacter(Character character) {
		survivingCharacters.remove(character.getName());
	}
	
	public void damageCharacter (String name) {
		Character character = survivingCharacters.get(name);
		if (character != null) {
			boolean isDead = character.damage();
			if (isDead) {
				removeCharacter(character);
			}
		}
	}
}
