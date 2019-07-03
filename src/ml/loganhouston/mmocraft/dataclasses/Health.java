package ml.loganhouston.mmocraft.dataclasses;

/**
* Class for storing and modifying health easily
* regeneration classes etc
*
**/

public final class Health {

    private int health = 100,
                maxHealth = 100,
                regenInterval = 1000,
                regenTimer = 0;
    private long millis;

    public Health(int health, int maxHealth) {
        if (health > maxHealth) health = maxHealth;
        this.health = health;
        this.maxHealth = maxHealth;
        millis = System.currentTimeMillis();
    }

    public Health(int health) {
        this.health = health;
    }

    public Health() {}

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (getClass() == o.getClass()) return true;
        int comparisonO = (int) o;
        if (comparisonO == this.health) return true;
        return false;
    }

    public void regenerate(int health) {
        regenerate(health, this.regenInterval);
    }


    public void regenerate(int health, int regenInterval) {
        while (this.health < maxHealth) {
            regenTimer += System.currentTimeMillis() - millis;
            millis = regenTimer + millis;

            if (regenTimer > regenInterval) {
                this.health += health;
                regenTimer = 0;
            }
        }
        ifOverSetMax();
    }

    public void damage(int damage) {
        this.health -= damage;
    }

    public void heal(int health) {
        this.health += health;
        ifOverSetMax();
    }

    public void addMaxHealth(int health) {
        this.maxHealth += health;
    }

    public void damageMaxHealth(int damage) {
        this.maxHealth -= damage;
        ifOverSetMax();
    }

    private void ifOverSetMax() {
        if (this.health > this.maxHealth) this.health = this.maxHealth;
    }


}
