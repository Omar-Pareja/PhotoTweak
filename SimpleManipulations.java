package org.cis1200;


public class SimpleManipulations {

   
    public static PixelPicture rotateCW(PixelPicture pic) {
        int w = pic.getWidth();
        int h = pic.getHeight();

        Pixel[][] src = pic.getBitmap();
        Pixel[][] tgt = new Pixel[w][h]; // swap coordinates

        for (int col = 0; col < w; col++) {
            for (int row = 0; row < h; row++) {
                tgt[col][h - row - 1] = src[row][col]; // swap coordinates
            }
        }

        return new PixelPicture(tgt);
    }

  
    public static PixelPicture rotateCCW(PixelPicture pic) {
        int w = pic.getWidth();
        int h = pic.getHeight();

        Pixel[][] src = pic.getBitmap();
        Pixel[][] tgt = new Pixel[w][h];

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                tgt[w - col - 1][row] = src[row][col];
            }
        }

        return new PixelPicture(tgt);
    }

    public static PixelPicture border(PixelPicture pic, int borderWidth, Pixel borderColor) {
        int originalWidth = pic.getWidth();
        int originalHeight = pic.getHeight();
        int newWidth = originalWidth + 2 * borderWidth;
        int newHeight = originalHeight + 2 * borderWidth;
        Pixel[][] newBitmap = new Pixel[newHeight][newWidth];
        for (int row = 0; row < newHeight; row++) {
            for (int col = 0; col < newWidth; col++) {
                newBitmap[row][col] = borderColor;
            }
        }
        Pixel[][] originalBitmap = pic.getBitmap();
        for (int row = 0; row < originalHeight; row++) {
            for (int col = 0; col < originalWidth; col++) {
                newBitmap[row + borderWidth][col + borderWidth] = originalBitmap[row][col];
            }
        }
        return new PixelPicture(newBitmap);
    }

    public static PixelPicture grayScaleLuminosity(PixelPicture pic) {
        int w = pic.getWidth();
        int h = pic.getHeight();

        Pixel[][] bmp = pic.getBitmap();
        for (int col = 0; col < w; col++) {
            for (int row = 0; row < h; row++) {
                Pixel p = bmp[row][col];
                int r = p.getRed();
                int g = p.getGreen();
                int b = p.getBlue();
                int avg = (int) Math.round(0.299 * r + 0.587 * g + 0.114 * b);
                bmp[row][col] = new Pixel(avg, avg, avg);
            }
        }
        return new PixelPicture(bmp);
    }

 
    public static PixelPicture invertColors(PixelPicture pic) {
        int w = pic.getWidth();
        int h = pic.getHeight();

        Pixel[][] bmp = pic.getBitmap();
        Pixel[][] invertedBitmap = new Pixel[h][w];

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                Pixel p = bmp[row][col];
                int invertedRed = 255 - p.getRed();
                int invertedGreen = 255 - p.getGreen();
                int invertedBlue = 255 - p.getBlue();
                invertedBitmap[row][col] = new Pixel(invertedRed, invertedGreen, invertedBlue);
            }
        }

        return new PixelPicture(invertedBitmap);
    }

 

    public static PixelPicture grayScaleAverage(PixelPicture pic) {
        int w = pic.getWidth();
        int h = pic.getHeight();

        Pixel[][] bmp = pic.getBitmap();
        Pixel[][] grayBitmap = new Pixel[h][w];

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                Pixel p = bmp[row][col];
                int avg = (int) Math.round((p.getRed() + p.getGreen() + p.getBlue()) / 3.0);
                grayBitmap[row][col] = new Pixel(avg, avg, avg);
            }
        }

        return new PixelPicture(grayBitmap);
    }


    public static PixelPicture scaleColors(PixelPicture pic, double rFactor,
                                           double gFactor, double bFactor) {
        int w = pic.getWidth();
        int h = pic.getHeight();

        Pixel[][] bmp = pic.getBitmap();
        Pixel[][] scaledBitmap = new Pixel[h][w];

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                Pixel p = bmp[row][col];
                int scaledRed = (int) Math.round(p.getRed() * rFactor);
                int scaledGreen = (int) Math.round(p.getGreen() * gFactor);
                int scaledBlue = (int) Math.round(p.getBlue() * bFactor);

                // Clamp each component to ensure it's within the range 0-255
                scaledRed = Math.min(255, Math.max(0, scaledRed));
                scaledGreen = Math.min(255, Math.max(0, scaledGreen));
                scaledBlue = Math.min(255, Math.max(0, scaledBlue));

                scaledBitmap[row][col] = new Pixel(scaledRed, scaledGreen, scaledBlue);
            }
        }

        return new PixelPicture(scaledBitmap);
    }

 
    public static int weightedAverage(double alpha, int x, int y) {
        return (int) Math.round(x * alpha + y * (1 - alpha));
    }

turn the blended image
     */
    public static PixelPicture alphaBlend(double alpha, PixelPicture pic1, PixelPicture pic2) {
        if (pic1.getWidth() != pic2.getWidth() || pic1.getHeight() != pic2.getHeight()) {
            return pic1;
        }

        int width = pic1.getWidth();
        int height = pic1.getHeight();

        Pixel[][] bmp1 = pic1.getBitmap();
        Pixel[][] bmp2 = pic2.getBitmap();
        Pixel[][] blendedBitmap = new Pixel[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Pixel p1 = bmp1[row][col];
                Pixel p2 = bmp2[row][col];

                int blendedRed = weightedAverage(alpha, p1.getRed(), p2.getRed());
                int blendedGreen = weightedAverage(alpha, p1.getGreen(), p2.getGreen());
                int blendedBlue = weightedAverage(alpha, p1.getBlue(), p2.getBlue());

                blendedBitmap[row][col] = new Pixel(blendedRed, blendedGreen, blendedBlue);
            }
        }

        return new PixelPicture(blendedBitmap);
    }


 
    public static PixelPicture vignette(PixelPicture pic) {
        int w = pic.getWidth();
        int h = pic.getHeight();
        double cx = (w - 1) / 2.0;
        double cy = (h - 1) / 2.0;

        Pixel[][] bmp = pic.getBitmap();
        for (int col = 0; col < w; col++) {
            for (int row = 0; row < h; row++) {
                double dx = (double) (col - cx);
                double dy = (double) (row - cy);

                double r = Math.sqrt(cx * cx + cy * cy);
                if (r == 0) {
                    return pic;
                }

                double d = Math.sqrt((dx * dx) + (dy * dy)) / r;
                double factor = 1.0 - d * d;

                bmp[row][col] = new Pixel(
                        (int) Math.round(bmp[row][col].getRed() * factor),
                        (int) Math.round(bmp[row][col].getGreen() * factor),
                        (int) Math.round(bmp[row][col].getBlue() * factor)
                );

            }
        }
        return new PixelPicture(bmp);
    }
}
