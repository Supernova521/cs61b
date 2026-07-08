import edu.princeton.cs.algs4.StdRandom;

import java.util.Map;
import java.awt.Color;

public class Particle {
    public static final int PLANT_LIFESPAN = 150;
    public static final int FLOWER_LIFESPAN = 75;
    public static final int FIRE_LIFESPAN = 10;
    public static final Map<ParticleFlavor, Integer> LIFESPANS =
            Map.of(ParticleFlavor.FLOWER, FLOWER_LIFESPAN,
                    ParticleFlavor.PLANT, PLANT_LIFESPAN,
                    ParticleFlavor.FIRE, FIRE_LIFESPAN);
    public ParticleFlavor flavor;
    public int lifespan;

    public Particle(ParticleFlavor p){
        this.flavor = p;
        if (p == ParticleFlavor.FLOWER || p == ParticleFlavor.PLANT || p == ParticleFlavor.FIRE){
            this.lifespan = LIFESPANS.get(p);
        }
        else{
            this.lifespan = -1;
        }
    }

    public Color color(){
        if (this.flavor == ParticleFlavor.EMPTY){
            return Color.BLACK;
        } else if (this.flavor == ParticleFlavor.SAND) {
            return Color.YELLOW;
        } else if (this.flavor == ParticleFlavor.BARRIER){
            return Color.GRAY;
        } else if (this.flavor == ParticleFlavor.WATER) {
            return Color.BLUE;
        } else if (this.flavor == ParticleFlavor.FOUNTAIN) {
            return Color.CYAN;
        } else if (this.flavor == ParticleFlavor.PLANT) {
            return new Color(0, 255, 0);
        } else if (this.flavor == ParticleFlavor.FIRE) {
            return new Color(255, 0, 0);
        } else if (this.flavor == ParticleFlavor.FLOWER) {
            return new Color(255, 141, 161);
        }
        return null;
    }

    void moveInto(Particle other) {
        other.flavor = this.flavor;
        other.lifespan = this.lifespan;
        this.flavor = ParticleFlavor.EMPTY;
        this.lifespan = -1;
    }

    public void fall(Map<Direction, Particle> neighbors) {
        Particle p = neighbors.get(Direction.DOWN);
        if (p.flavor == ParticleFlavor.EMPTY) {
            moveInto(p);
        }
    }

    public void action(Map<Direction, Particle> neighbors) {
        if (this.flavor == ParticleFlavor.EMPTY) {
            return;
        }
        if (this.flavor != ParticleFlavor.BARRIER) {
            this.fall(neighbors);
        }
        if (this.flavor == ParticleFlavor.WATER) {
            this.flow(neighbors);
        }
        if (this.flavor == ParticleFlavor.PLANT || this.flavor == ParticleFlavor.FLOWER) {
            this.grow(neighbors);
        }
    }

    public void flow(Map<Direction, Particle> neighbors) {
        int number = StdRandom.uniformInt(3);
        if (number == 0) {
            return;
        }
        else if (number == 1) {
            if (neighbors.get(Direction.LEFT).flavor == ParticleFlavor.EMPTY) {
                this.moveInto(neighbors.get(Direction.LEFT));
            }
        }
        else if (number == 2) {
            if (neighbors.get(Direction.RIGHT).flavor == ParticleFlavor.EMPTY) {
                this.moveInto(neighbors.get(Direction.RIGHT));
            }
        }
    }

    public void grow(Map<Direction, Particle> neighbors) {
        int number = StdRandom.uniformInt(10);
        if (number == 0) {
            if (neighbors.get(Direction.UP).flavor == ParticleFlavor.EMPTY) {
                neighbors.get(Direction.UP).flavor = this.flavor;
                neighbors.get(Direction.UP).lifespan = this.lifespan;
            }
        }
        else if (number == 1) {
            if (neighbors.get(Direction.LEFT).flavor == ParticleFlavor.EMPTY) {
                neighbors.get(Direction.LEFT).flavor = this.flavor;
                neighbors.get(Direction.LEFT).lifespan = this.lifespan;
            }
        }
        else if (number == 2) {
            if (neighbors.get(Direction.RIGHT).flavor == ParticleFlavor.EMPTY) {
                neighbors.get(Direction.RIGHT).flavor = this.flavor;
                neighbors.get(Direction.RIGHT).lifespan = this.lifespan;
            }
        }
    }
}

