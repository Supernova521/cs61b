import edu.princeton.cs.algs4.StdDraw;

import java.util.HashMap;
import java.util.Map;

public class ParticleSimulator {

    public static final Map<Character, ParticleFlavor> LETTER_TO_PARTICLE = Map.of(
            's', ParticleFlavor.SAND,
            'b', ParticleFlavor.BARRIER,
            'w', ParticleFlavor.WATER,
            'p', ParticleFlavor.PLANT,
            'f', ParticleFlavor.FIRE,
            '.', ParticleFlavor.EMPTY,
            'n', ParticleFlavor.FOUNTAIN,
            'r', ParticleFlavor.FLOWER
    );

    public Particle[][] particles;
    public int width;
    public int height;

    public ParticleSimulator(int w, int h){
        this.width = w;
        this.height = h;
        particles = new Particle[width][height];
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                particles[i][j] = new Particle(ParticleFlavor.EMPTY);
            }
        }
    }

    public void drawParticles() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                StdDraw.setPenColor(particles[x][y].color());
                StdDraw.filledSquare(x, y, 0.5);
            }
        }
    }

    public boolean validIndex(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Map<Direction, Particle> getNeighbors(int x, int y) {
        Map<Direction, Particle> result = new HashMap<>();
        if (validIndex(x - 1, y)) {
            result.put(Direction.LEFT, particles[x - 1][y]);
        } else {
            result.put(Direction.LEFT, new Particle(ParticleFlavor.BARRIER));
        }
        if (validIndex(x + 1, y)) {
            result.put(Direction.RIGHT, particles[x + 1][y]);
        } else {
            result.put(Direction.RIGHT, new Particle(ParticleFlavor.BARRIER));
        }
        if (validIndex(x, y - 1)) {
            result.put(Direction.DOWN, particles[x][y - 1]);
        } else {
            result.put(Direction.DOWN, new Particle(ParticleFlavor.BARRIER));
        }
        if (validIndex(x, y + 1)) {
            result.put(Direction.UP, particles[x][y + 1]);
        } else {
            result.put(Direction.UP, new Particle(ParticleFlavor.BARRIER));
        }
        return result;
    }

    static void main() {
        ParticleSimulator particleSimulator = new ParticleSimulator(150, 150);
        StdDraw.setXscale(0, particleSimulator.width);
        StdDraw.setYscale(0, particleSimulator.height);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);
        ParticleFlavor nextParticleFlavor = ParticleFlavor.SAND;

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                Character c = StdDraw.nextKeyTyped();
                nextParticleFlavor = LETTER_TO_PARTICLE.get(c);
            }
            if (StdDraw.isMousePressed()) {
                int x = (int) StdDraw.mouseX();
                int y = (int) StdDraw.mouseY();
                if (particleSimulator.validIndex(x, y)) {
                    particleSimulator.particles[x][y] = new Particle(nextParticleFlavor);
                }
            }

            particleSimulator.drawParticles();
            StdDraw.show();
            StdDraw.pause(5);
        }
    }
}
