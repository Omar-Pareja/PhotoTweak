package org.cis1200;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class MyPixelTest {
    
    @Test
    public void testConstructInBounds() {
        Pixel p = new Pixel(40, 50, 60);
        assertEquals(40, p.getRed());
        assertEquals(50, p.getGreen());
        assertEquals(60, p.getBlue());
    }

    @Test
    public void testConstructArrayLongerThan3() {
        int[] arr = { 10, 20, 30, 40 };
        Pixel p = new Pixel(arr);
        assertEquals(10, p.getRed());
        assertEquals(20, p.getGreen());
        assertEquals(30, p.getBlue());
    }

    @Test
    public void testConstructClipNegativeValues() {
        Pixel p = new Pixel(-10, -20, -30);
        assertEquals(0, p.getRed());
        assertEquals(0, p.getGreen());
        assertEquals(0, p.getBlue());
    }

    @Test
    public void testConstructClipValuesAbove255() {
        Pixel p = new Pixel(300, 400, 500);
        assertEquals(255, p.getRed());
        assertEquals(255, p.getGreen());
        assertEquals(255, p.getBlue());
    }

    @Test
    public void testConstructArrayShorterThan3() {
        int[] arr = { 100 };
        Pixel p = new Pixel(arr);
        assertEquals(100, p.getRed());
        assertEquals(0, p.getGreen());
        assertEquals(0, p.getBlue());
    }

    @Test
    public void testConstructWithNullArray() {
        Pixel p = new Pixel(null);
        assertEquals(0, p.getRed());
        assertEquals(0, p.getGreen());
        assertEquals(0, p.getBlue());
    }

    @Test
    public void testGetComponents() {
        Pixel p = new Pixel(120, 130, 140);
        int[] components = p.getComponents();
        assertArrayEquals(new int[]{120, 130, 140}, components);
    }

    @Test
    public void testGetComponentsEncapsulation() {
        Pixel p = new Pixel(10, 20, 30);
        int[] components = p.getComponents();
        components[0] = 255; // Attempt to modify the array
        assertEquals(10, p.getRed());
        assertEquals(20, p.getGreen());
        assertEquals(30, p.getBlue());
    }

    @Test
    public void testDistance() {
        Pixel p1 = new Pixel(0, 0, 0);
        Pixel p2 = new Pixel(255, 255, 255);
        assertEquals(765, p1.distance(p2)); // (255 + 255 + 255)
    }

    @Test
    public void testDistanceWithNullPixel() {
        Pixel p = new Pixel(50, 50, 50);
        assertEquals(-1, p.distance(null));
    }

    @Test
    public void testToString() {
        Pixel p = new Pixel(200, 150, 100);
        assertEquals("(200, 150, 100)", p.toString());
    }

    @Test
    public void testSameRGBWithIdenticalPixels() {
        Pixel p1 = new Pixel(80, 90, 100);
        Pixel p2 = new Pixel(80, 90, 100);
        assertTrue(p1.sameRGB(p2));
    }

    @Test
    public void testSameRGBWithDifferentPixels() {
        Pixel p1 = new Pixel(80, 90, 100);
        Pixel p2 = new Pixel(70, 80, 90);
        assertFalse(p1.sameRGB(p2));
    }

}
