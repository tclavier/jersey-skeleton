package fr.dashingames.ludicode_android.gui.views;

import fr.dashingames.ludicode_android.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * TextView pouvant s'adapter à la taille disponible
 *
 */
public class FittingTextView extends TextView {
	
	private boolean fit = false;

	public FittingTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setTypeface(null, Typeface.BOLD);
	}
	
	public void setFitTextToBox(boolean fit) {
		this.fit = fit;
	}
	
	protected void onDraw (Canvas canvas) {
		super.onDraw(canvas);
		if (fit) 
			shrinkToFit();
	}
	
	private void shrinkToFit() {
		int height = this.getHeight();
		int lines = this.getLineCount();
		Rect r = new Rect();
		int y2 = this.getLineBounds(lines-1, r);
		
		float size = this.getTextSize();
		if (y2 > height && size >= 8f) {
			this.setTextSize(size - 2f);
			shrinkToFit();
		}
	}

	public void updateNbInstructions(Activity activity, int nbInstructionsLeft) {
		String instruction = activity.getString(R.string.instruction);
		String available = activity.getString(R.string.available);
		if (nbInstructionsLeft > 1) {
			instruction += "s";
			if (available.equals("disponible"))
				available += "s";
		}
		this.setText(activity.getResources().getString(R.string.youHave) + " "
				+ nbInstructionsLeft + " " + instruction + " " + available + ".");
	}
}
