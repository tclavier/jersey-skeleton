package fr.dashingames.ludicode_android.utils;

import fr.dashingames.ludicode_android.activities.GameActivity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Classe permettant de réaliser une animation
 *
 */
public class Animation {
	
	private Bitmap img;
	private int widthInFrame;
	private int heightInFrame;
	private int[] frames;
	private float frameDuration;
	private float timer = 0;
	private int frameWidth;
	private int frameHeight;
	private boolean running = false;
	private int currentFrame = -1;
	private float angle;
	
	public static int[] GREEN_FRAMES = new int[] {0, 1, 2, 3, 4, 5};
	public static int[] RED_FRAMES = new int[] {6, 7, 8, 9, 10, 11};

	public Animation(Bitmap img, int widthInFrame, int heightInFrame, 
			float frameDuration) {
		this.img = img;
		this.widthInFrame = widthInFrame;
		this.heightInFrame = heightInFrame;
		this.frameDuration = frameDuration;
		
		this.frameWidth = img.getWidth() / widthInFrame;
		this.frameHeight = img.getHeight() / heightInFrame;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	public void draw(Canvas canvas, float x, float y, float animationDimension) {
		if (!running)
			return;
		
		int sx = (int) (this.frameWidth * (frames[currentFrame] % widthInFrame));
		int sy = (int) (this.frameHeight * Math.floor(frames[currentFrame] / widthInFrame));
		Rect src = new Rect(sx, sy , (int) (sx + frameWidth), (int) (sy + frameHeight));
		Rect dest = new Rect((int) (x + animationDimension / 2), (int) (y + animationDimension), (int) (x + animationDimension * 1.5), (int) (y + animationDimension * 2));
		
		canvas.save();
		canvas.rotate(angle, x + animationDimension, y + animationDimension);
		canvas.drawBitmap(img, src, dest, null);
		canvas.restore();
	}
	
	public void update(double delta) {
		if (!running)
			return;
		
		timer += (delta * GameActivity.gameSpeed);
		if (timer >= frameDuration) {
			timer = 0;
			currentFrame = (currentFrame + 1) % frames.length;
			if (currentFrame == 0)
				running = false;
		}
		
	}
	
	public void start() {
		running = true;
		timer = 0;
		currentFrame = 0;
	}

	public int[] getFrames() {
		return frames;
	}
	public void setFramesColor(boolean color) {
		frames = color ? GREEN_FRAMES : RED_FRAMES;
	}
	public boolean isRunning() {
		return running;
	}

}
