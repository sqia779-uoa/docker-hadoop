package assignment1;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Scanner;
import java.awt.Color;

public class GrayScale {
	static Scanner in = new Scanner(System.in);
    static int width = 0;
    static int  height = 0;
    public static void main(String[] args) {
        BufferedImage img = null;
        File filePath = null;
        String temp = null;

        System.out.println("Enter file path to an Image:");
        temp = in.nextLine();
        if (temp.length() > 5 || !(temp.substring(temp.lastIndexOf('.') + 1, temp.length()).equals(".png")) || !(temp.substring(temp.lastIndexOf('.') + 1, temp.length()).equals(".jpg"))) {
            try {
                System.out.println("File chosen: " + temp.substring(temp.lastIndexOf('/') + 1, temp.length()));
                filePath = new File(temp);
                img = ImageIO.read(filePath);
            } catch(IOException e) {
                e.printStackTrace();
            }

            width = img.getWidth();
            height = img.getHeight();

            int currentPixel = 0;
            int r = 0;
            int g = 0;
            int b = 0;
            int luminosity = 0;
            Color avGray = null;
            for (int i=0; i<width; i++) {
                for (int j=0; j<height; j++) {
                    currentPixel = img.getRGB(i, j);

                    r = (currentPixel>>16) & 0xff;
                    g = (currentPixel>>8) & 0xff;
                    b = currentPixel & 0xff;

                    luminosity = ((int)(0.21 * r) + (int)(0.71 * g) + (int)(0.07 * b));

                    avGray = new Color(luminosity, luminosity, luminosity);
                    currentPixel = avGray.getRGB();

                    img.setRGB(i, j, currentPixel);
                }
            }

            try{
                filePath = new File(temp.substring(0, temp.length() - 4) + "-greyscale.jpg");
                ImageIO.write(img, "jpg", filePath);
                System.out.println("File saved successfully at \"" + temp.substring(0, temp.length() - 4) + "-greyscale.jpg\"");
            } catch(IOException e){
                e.printStackTrace();
            }
        }
        else System.out.println("Error. Incorrect file path");
    }
}
