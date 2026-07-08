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
            double ratio = (double) Math.max(0, Math.min(lifespan, PLANT_LIFESPAN)) / PLANT_LIFESPAN;
            int g = 120 + (int) Math.round((255 - 120) * ratio);
            return new Color(0, g, 0);
        } else if (this.flavor == ParticleFlavor.FIRE) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FIRE_LIFESPAN)) / FIRE_LIFESPAN;
            int r = (int) Math.round(255 * ratio);
            return new Color(r, 0, 0);
        } else if (this.flavor == ParticleFlavor.FLOWER) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FLOWER_LIFESPAN)) / FLOWER_LIFESPAN;
            int r = 120 + (int) Math.round((255 - 120) * ratio);
            int g = 70 + (int) Math.round((141 - 70) * ratio);
            int b = 80 + (int) Math.round((161 - 80) * ratio);
            return new Color(r, g, b);
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
        if (this.flavor == ParticleFlavor.FIRE) {
            this.burn(neighbors);
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

    public void decrementLifespan() {
        if (this.lifespan > 0) {
            this.lifespan -= 1;
        }
        else if (this.lifespan == 0) {
            this.flavor = ParticleFlavor.EMPTY;
            this.lifespan = -1;
        }
    }

    public void burn(Map<Direction, Particle> neighbors) {
        if (neighbors.get(Direction.UP).flavor == ParticleFlavor.PLANT || neighbors.get(Direction.UP).flavor == ParticleFlavor.FLOWER) {
            int number = StdRandom.uniformInt(10);
            if (number < 4) {
                neighbors.get(Direction.UP).flavor = ParticleFlavor.FIRE;
                neighbors.get(Direction.UP).lifespan = FIRE_LIFESPAN;
            }
        }
        if (neighbors.get(Direction.DOWN).flavor == ParticleFlavor.PLANT || neighbors.get(Direction.DOWN).flavor == ParticleFlavor.FLOWER) {
            int number = StdRandom.uniformInt(10);
            if (number < 4) {
                neighbors.get(Direction.DOWN).flavor = ParticleFlavor.FIRE;
                neighbors.get(Direction.DOWN).lifespan = FIRE_LIFESPAN;
            }
        }
        if (neighbors.get(Direction.LEFT).flavor == ParticleFlavor.PLANT || neighbors.get(Direction.LEFT).flavor == ParticleFlavor.FLOWER) {
            int number = StdRandom.uniformInt(10);
            if (number < 4) {
                neighbors.get(Direction.LEFT).flavor = ParticleFlavor.FIRE;
                neighbors.get(Direction.LEFT).lifespan = FIRE_LIFESPAN;
            }
        }
        if (neighbors.get(Direction.RIGHT).flavor == ParticleFlavor.PLANT || neighbors.get(Direction.RIGHT).flavor == ParticleFlavor.FLOWER) {
            int number = StdRandom.uniformInt(10);
            if (number < 4) {
                neighbors.get(Direction.RIGHT).flavor = ParticleFlavor.FIRE;
                neighbors.get(Direction.RIGHT).lifespan = FIRE_LIFESPAN;
            }
        }
    }
}

