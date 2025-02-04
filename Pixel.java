package org.cis1200;


public class Pixel implements Comparable<Pixel> {

    
    public static final Pixel BLACK = new Pixel(0, 0, 0);

   
    public static final Pixel BLUE = new Pixel(0, 0, 255);

    public static final Pixel RED = new Pixel(255, 0, 0);

  
    public static final Pixel GREEN = new Pixel(0, 255, 0);

    public static final Pixel WHITE = new Pixel(255, 255, 255);

    private final int red;
    private final int green;
    private final int blue;

    
    private static int clip(int value) {
        return Math.max(0, Math.min(255, value));
    }

    
    public Pixel(int r, int g, int b) {
        this.red = clip(r);
        this.green = clip(g);
        this.blue = clip(b);
    }

 
    public Pixel(int[] c) {
        this.red = clip(c != null && c.length > 0 ? c[0] : 0);
        this.green = clip(c != null && c.length > 1 ? c[1] : 0);
        this.blue = clip(c != null && c.length > 2 ? c[2] : 0);
    }

    public int getRed() {
        return red;
    }

    
    public int getGreen() {
        return green;
    }

   
    public int getBlue() {
        return blue;
    }

  
    public int[] getComponents() {
        return new int[]{red, green, blue};
    }

    public int distance(Pixel px) {
        if (px == null) {
            return -1;
        }
        return Math.abs(this.red - px.red)
                + Math.abs(this.green - px.green)
                + Math.abs(this.blue - px.blue);
    }

    
    @Override
    public String toString() {
        return "(" + red + ", " + green + ", " + blue + ")";
    }

  
    public boolean sameRGB(Pixel px) {
        return px != null && this.red == px.red && this.green == px.green && this.blue == px.blue;
    }

   
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (getClass() == other.getClass()) {
            return this.sameRGB((Pixel) other);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int h = 0;
        int[] components = getComponents();

        for (int k = 0; k < components.length; k++) {
            h += k * 255 + components[k];
        }
        return h;
    }

    @Override
    public int compareTo(Pixel o) {
        int rc = getRed() - o.getRed();
        int gc = getGreen() - o.getGreen();
        int bc = getBlue() - o.getBlue();

        if (rc != 0) {
            return rc;
        } else if (gc != 0) {
            return gc;
        } else {
            return bc;
        }
    }
}
