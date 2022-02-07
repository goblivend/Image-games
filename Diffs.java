// >>>>>>>> Begin imports >>>>>>>>

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

// <<<<<<<<< End imports <<<<<<<<<

public class Diffs {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Not enough arguments here");
            return;
        }
        if (args[0].equals("splitv")){
            SplitVertical(args[1]);
            return;
        }
        if (args[0].equals("splith")){
            SplitHorizontal(args[1]);
            return;
        }

        String filename1 = args[0];
        String filename2 = args[1];
        File f1 = new File(filename1);
        File f2 = new File(filename2);
        if (!f1.exists() || f1.isDirectory() || !f2.exists() || f2.isDirectory()) {
            System.err.println("The image does not exist");
            return;
        }
        BufferedImage ig1;
        BufferedImage ig2;
        try {
            ig1 = ImageIO.read(new File(filename1));
            //Graphics2D img1 = ig1.createGraphics();
            ig2 = ImageIO.read(new File(filename2));
            //Graphics2D img2 = ig2.createGraphics();
            for(int i = 0; i < ig2.getWidth(); i++){
                if (i>= ig1.getWidth())
                    break;
                for(int j = 0; j < ig2.getHeight(); j++){
                    if (j>= ig1.getHeight())
                        break;
                    if(ig1.getRGB(i, j) != ig2.getRGB(i, j))
                        ig2.setRGB(i, j, Color.RED.getRGB());
                }
            }
            File outputfile = new File("differences.png");
            ImageIO.write(ig2, "png", outputfile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void SplitVertical(String filename) {
        File f = new File(filename);
        if (!f.exists() || f.isDirectory()) {
            System.err.println("The image does not exist");
            return;
        }

        try {
            BufferedImage ig = ImageIO.read(new File(filename));
            BufferedImage split1 = new BufferedImage(ig.getWidth(), ig.getHeight()/2, BufferedImage.TYPE_INT_ARGB);
            BufferedImage split2 = new BufferedImage(ig.getWidth(), ig.getHeight()/2, BufferedImage.TYPE_INT_ARGB);

            for(int i = 0; i < ig.getWidth(); i++){
                for(int j = 0; j < ig.getHeight() / 2; j++){
                    split1.setRGB(i, j, ig.getRGB(i, j + ig.getHeight()/2));
                    split2.setRGB(i, j, ig.getRGB(i, j                   ));
                }
            }

            File outputfile = new File("splitted1.png");
            ImageIO.write(split1, "png", outputfile);

            outputfile = new File("splitted2.png");
            ImageIO.write(split2, "png", outputfile);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void SplitHorizontal(String filename) {
        File f = new File(filename);
        if (!f.exists() || f.isDirectory()) {
            System.err.println("The image does not exist");
            return;
        }


        try {
            BufferedImage ig = ImageIO.read(new File(filename));
            BufferedImage split1 = new BufferedImage(ig.getWidth()/2, ig.getHeight(), BufferedImage.TYPE_INT_ARGB);
            BufferedImage split2 = new BufferedImage(ig.getWidth()/2, ig.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for(int i = 0; i < ig.getWidth()/2; i++){
                for(int j = 0; j < ig.getHeight(); j++){
                    split2.setRGB(i, j, ig.getRGB(i + ig.getWidth()/2, j));
                    split1.setRGB(i, j, ig.getRGB(i                  , j));
                }
            }

            File outputfile = new File("splitted1.png");
            ImageIO.write(split1, "png", outputfile);
            outputfile = new File("splitted2.png");
            ImageIO.write(split2, "png", outputfile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}