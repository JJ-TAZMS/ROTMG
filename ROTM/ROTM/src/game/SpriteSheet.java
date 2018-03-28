package game;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage image;
	int square;
	
	public SpriteSheet(BufferedImage img, int sq)
	{
		image = img;
		square = sq;
	}
	
	public BufferedImage grabImage(int x, int y, int width, int height)
	{
		BufferedImage img = image.getSubimage((x*square),(y*square),square*width,square*height);
		return img;
	}
}
