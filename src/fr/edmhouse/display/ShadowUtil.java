package fr.edmhouse.display;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import com.jhlabs.image.GaussianFilter;

/** Static class that holds statics methods to generate shadows. */
public class ShadowUtil {
    public static void applyQualityRenderingHints(Graphics2D g2d) {
	g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
		RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
	g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
		RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	g2d.setRenderingHint(RenderingHints.KEY_DITHERING,
		RenderingHints.VALUE_DITHER_ENABLE);
	g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
		RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
		RenderingHints.VALUE_RENDER_QUALITY);
	g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
		RenderingHints.VALUE_STROKE_PURE);
    }

    public static BufferedImage createCompatibleImage(int width, int height) {
	return createCompatibleImage(width, height, Transparency.TRANSLUCENT);
    }

    public static BufferedImage createCompatibleImage(int width, int height,
	    int transparency) {
	BufferedImage image = getGraphicsConfiguration().createCompatibleImage(
		width, height, transparency);
	image.coerceData(true);
	return image;
    }

    public static BufferedImage createCompatibleImage(BufferedImage image) {
	return createCompatibleImage(image, image.getWidth(), image.getHeight());
    }

    public static BufferedImage createCompatibleImage(BufferedImage image,
	    int width, int height) {
	return getGraphicsConfiguration().createCompatibleImage(width, height,
		image.getTransparency());
    }

    public static GraphicsConfiguration getGraphicsConfiguration() {
	return GraphicsEnvironment.getLocalGraphicsEnvironment()
		.getDefaultScreenDevice().getDefaultConfiguration();
    }

    public static BufferedImage generateBlur(BufferedImage imgSource, int size,
	    Color color, float alpha) {
	GaussianFilter filter = new GaussianFilter(size);

	int imgWidth = imgSource.getWidth();
	int imgHeight = imgSource.getHeight();

	BufferedImage imgBlur = createCompatibleImage(imgWidth, imgHeight);
	Graphics2D g2 = imgBlur.createGraphics();
	applyQualityRenderingHints(g2);

	g2.drawImage(imgSource, 0, 0, null);
	g2.setComposite(AlphaComposite
		.getInstance(AlphaComposite.SRC_IN, alpha));
	g2.setColor(color);

	g2.fillRect(0, 0, imgSource.getWidth(), imgSource.getHeight());
	g2.dispose();

	imgBlur = filter.filter(imgBlur, null);

	return imgBlur;
    }

    public static BufferedImage generateShadow(BufferedImage imgSource,
	    int size, Color color, float alpha) {
	int imgWidth = imgSource.getWidth() + (size * 2);
	int imgHeight = imgSource.getHeight() + (size * 2);

	BufferedImage imgMask = createCompatibleImage(imgWidth, imgHeight);
	Graphics2D g2 = imgMask.createGraphics();
	applyQualityRenderingHints(g2);

	int x = Math.round((imgWidth - imgSource.getWidth()) / 2f);
	int y = Math.round((imgHeight - imgSource.getHeight()) / 2f);
	g2.drawImage(imgSource, x, y, null);
	g2.dispose();

	// ---- Blur here ---
	BufferedImage imgGlow = generateBlur(imgMask, (size * 2), color, alpha);

	return imgGlow;
    }
}
