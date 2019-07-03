package ml.loganhouston.mmocraft.dataclasses;

/**
* Class for storing and modifying health easily
* regeneration functions etc
**/

public final class Health {

    private int health = 100,
                maxHealth = 100,
                regenInterval = 1000,
                regenTimer = 0;
    private long millis;

    public Health() { // defaults to 100 health and 100 max health
        millis = System.currentTimeMillis();
    }

    public Health(int health) { // custom health value (lower than max)
        this.health = health;
        millis = System.currentTimeMillis();
    }

    public Health(int health, int maxHealth) { // custom health and max health values
        this(health);
        this.maxHealth = maxHealth;
        ifOverSetMax();
    }

    @Override
    public boolean equals(Object o) { // e.g. if (health.equals(0)
        if (o == null) return false;
        if (getClass() == o.getClass()) return true;
        int comparisonO = (int) o;
        if (comparisonO == this.health) return true;
        return false;
    }

    public void regenerate(int health) { // regenerate at health per second
        regenerate(health, this.regenInterval);
    }

    public void regenerate(int health, int regenInterval) { // regenerate at health per regenInterval ms
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

    public void damage(int damage) { // take health
        this.health -= damage;
    }

    public void heal(int health) { // recover health
        this.health += health;
        ifOverSetMax();
    }

    public void addMaxHealth(int health) { // upgrade max health
        this.maxHealth += health;
    }

    public void damageMaxHealth(int damage) { // downgrade max health
        this.maxHealth -= damage;
        ifOverSetMax();
    }

    private void ifOverSetMax() { // self-explanatory
        if (this.health > this.maxHealth) this.health = this.maxHealth;
    }


}
