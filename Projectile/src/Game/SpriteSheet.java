package Game;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage image;
	
	public SpriteSheet(BufferedImage img)
	{
		image = img;
	}
	
	public BufferedImage grabImage(int row, int col, int width, int height)
	{
		int square = 8;
		BufferedImage img = image.getSubimage((row*square),(col*square),square*width,square*height);
		return img;
	}
}
