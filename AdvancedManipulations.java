

public class AdvancedManipulations {

    public static PixelPicture adjustContrast(PixelPicture pic, double multiplier) {
        int width = pic.getWidth();
        int height = pic.getHeight();
        Pixel[][] originalBitmap = pic.getBitmap();
        long totalSum = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Pixel pixel = originalBitmap[row][col];
                totalSum += pixel.getRed() + pixel.getGreen() + pixel.getBlue();
            }
        }
        int numPixels = width * height;
        int averageIntensity = (int) Math.round((double) totalSum / (numPixels * 3));
        Pixel[][] contrastBitmap = new Pixel[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Pixel pixel = originalBitmap[row][col];
                int red = (int) Math.round((pixel.getRed() - averageIntensity)
                        * multiplier + averageIntensity);
                int green = (int) Math.round((pixel.getGreen() - averageIntensity)
                        * multiplier + averageIntensity);
                int blue = (int) Math.round((pixel.getBlue() - averageIntensity)
                        * multiplier + averageIntensity);
                contrastBitmap[row][col] = new Pixel(red, green, blue);
            }
        }
        return new PixelPicture(contrastBitmap);
    }
    private static Pixel findClosestColor(Pixel pixel, Pixel[] palette) {
        Pixel closestColor = palette[0];
        int minDistance = pixel.distance(closestColor);

        for (Pixel color : palette) {
            int distance = pixel.distance(color);
            if (distance < minDistance) {
                minDistance = distance;
                closestColor = color;
            }
        }
        return closestColor;
    }

    public static PixelPicture reducePalette(PixelPicture pic, int numColors) {
        int width = pic.getWidth();
        int height = pic.getHeight();
        Pixel[][] originalBitmap = pic.getBitmap();

        ColorMap colorMap = new ColorMap();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Pixel pixel = originalBitmap[row][col];
                colorMap.put(pixel, colorMap.contains(pixel) ? colorMap.getValue(pixel) + 1 : 1);
            }
        }

        Pixel[] sortedPixels = colorMap.getSortedPixels();
        Pixel[] palette = new Pixel[Math.min(numColors, sortedPixels.length)];
        System.arraycopy(sortedPixels, 0, palette, 0, palette.length);

        Pixel[][] reducedBitmap = new Pixel[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Pixel originalPixel = originalBitmap[row][col];
                Pixel closestColor = findClosestColor(originalPixel, palette);
                reducedBitmap[row][col] = closestColor;
            }
        }
        return new PixelPicture(reducedBitmap);
    }

    public static PixelPicture blur(PixelPicture pic, int radius) {
        int width = pic.getWidth();
        int height = pic.getHeight();
        Pixel[][] originalBitmap = pic.getBitmap();
        Pixel[][] blurredBitmap = new Pixel[height][width];


        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                blurredBitmap[row][col] = getAveragePixel(originalBitmap,
                        row, col, radius, width, height);
            }
        }

        return new PixelPicture(blurredBitmap);
    }

    private static Pixel getAveragePixel(Pixel[][] bitmap, int row,
                                         int col, int radius, int width, int height) {
        int redSum = 0, greenSum = 0, blueSum = 0;
        int count = 0;

        int rowStart = Math.max(row - radius, 0);
        int rowEnd = Math.min(row + radius, height - 1);
        int colStart = Math.max(col - radius, 0);
        int colEnd = Math.min(col + radius, width - 1);

        for (int r = rowStart; r <= rowEnd; r++) {
            for (int c = colStart; c <= colEnd; c++) {
                Pixel pixel = bitmap[r][c];
                redSum += pixel.getRed();
                greenSum += pixel.getGreen();
                blueSum += pixel.getBlue();
                count++;
            }
        }

        int avgRed = (int) Math.round((double) redSum / count);
        int avgGreen = (int) Math.round((double) greenSum / count);
        int avgBlue = (int) Math.round((double) blueSum / count);

        return new Pixel(avgRed, avgGreen, avgBlue);
    }


    public static PixelPicture flood(PixelPicture pic, Pixel c, int row, int col) {
        return pic;
    }
}
