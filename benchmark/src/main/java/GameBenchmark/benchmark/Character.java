package GameBenchmark.benchmark;

public class Character {
	
	String name;
	int hitPoints;
	boolean isDead;

	public Character(String name, int hitPoints) {
		this.name = name;
		this.hitPoints = hitPoints;
		this.isDead = false;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}
	
	public int getHitPoints() {
		return this.hitPoints;
	}
	
	public void setIsDead(boolean isDead) {
		this.isDead = isDead;
	}
	
	public boolean isDead() {
		return this.isDead;
	}
	
	public boolean damage() {
		this.hitPoints --;
		
		//System.out.println("Damaging " + this.name);
		
		if (this.hitPoints == 0) {
			this.isDead = true;
			//System.out.println("Killing " + this.name);
		}
		
		return isDead();
	}
	
}
