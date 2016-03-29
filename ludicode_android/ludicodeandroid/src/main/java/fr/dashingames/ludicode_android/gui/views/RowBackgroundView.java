package fr.dashingames.ludicode_android.gui.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.SurfaceView;
import fr.dashingames.ludicode_android.beans.Instruction;
import fr.dashingames.ludicode_android.utils.AlgorithmHandler;

/**
 * Classe permettant de redessiner le background des rows de la listView pour 
 * les instructions
 *
 */
public class RowBackgroundView extends SurfaceView {
	// Largeur des "connecteurs" entre les blocs
	private static final int CONNECTOR_WIDTH = 25;
	
	// Espace (en largeur) entre les connecteurs quand une instruction est contenue dans plusieurs blocs 
	private static final int CONNECTOR_SPACE = 10;
	
	// Marge entre les blocs et le bord gauche de la liste
	private static final int MARGIN = 10;
	
	// Marge intérieure entre le texte et le bloc
	private static final int PADDING = 15;
	
	// Taille en largeur des instructions indiquant la fin d'un bloc
	private static final int CLOSING_INSTRUCTION_WIDTH = 200;
	
	// Taille du texte sur les instructions
	private static final int TEXT_SIZE = 28;
	
    private Instruction instruction;
    private AlgorithmHandler handler;
    private int id;
    
    // Variable utilisées lors du dessin (evite la réallocation)
    private Rect textBounds = new Rect();
    private Paint paint = new Paint();
    private RectF rectF = new RectF();
    private List<Instruction> englobingInstructions = new ArrayList<Instruction>();
    
	public RowBackgroundView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
        setWillNotDraw(false);
        // Defini le style du text
        paint.setTextSize(TEXT_SIZE);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
	}
	
	/**
	 * @return Un tableau des blocs englobants l'instruction
	 */
	private List<Instruction> getEnglobingBlocks() {
        englobingInstructions.clear();
        Instruction inst = instruction.getEnglobingBlock();
        while (inst != null) {
        	englobingInstructions.add(0, inst);
        	inst = inst.getEnglobingBlock();
        }
        
        return englobingInstructions;
	}
	
	/**
	 * Dessine une instruction simple
	 * @param canvas Canvas sur lequel dessiner
	 * @param x Position en x du bloc
	 * @param height Hauteur maximum de la ligne
	 */
	private void drawInstruction(Canvas canvas, int x, int height) {
    	// Si c'est un bloc, on dessine le rectangle "vertical" pour faire la connexion avec les autres blocs
    	if (instruction.isBlock())
    		canvas.drawRect(x, height/2, x + CONNECTOR_WIDTH, height, paint);
    	
    	// Si c'est un "sinon" on dessine le rectangle "vertical" pour faire la connexion avec les autres blocs (au dessus)
    	if (instruction.getCode().equals("} else {"))
    		canvas.drawRect(x, 0, x + CONNECTOR_WIDTH, height/2, paint);
    	
        rectF.set(x, PADDING, textBounds.width() + PADDING * 2 + x, height - PADDING);
        
        // On dessine le rectangle normal
        canvas.drawRoundRect(rectF, 10, 10, paint);
		
        // On dessine un contour jaune si l'instruction est séléctionné
        if (id == handler.getHighlightedBlock()) {
	        paint.setStyle(Paint.Style.STROKE);
	        paint.setStrokeWidth(5);
	        paint.setColor(Color.YELLOW);
	        canvas.drawRoundRect(rectF, 10, 10, paint);
        }
        
        paint.setStyle(Paint.Style.FILL);
        
        // On dessine le texte
        paint.setColor(Color.WHITE);
        canvas.drawText(instruction.getName(), x + PADDING, -textBounds.top + (height - textBounds.height())/2, paint);
	}
	
	/**
	 * Dessine une instruction signifiant la fin d'un bloc
	 * @param canvas Canvas sur lequel dessiner
	 * @param x Position en x du bloc
	 * @param height Hauteur maximum de la ligne
	 */
	private void drawClosingInstruction(Canvas canvas, int x, int height) {
    	// On dessine le rectangle "vertical" pour faire la connexion avec les autres blocs (au dessus)
    	canvas.drawRect(x, 0, x + CONNECTOR_WIDTH, height/2, paint);
    	
    	// On dessine le rectangle pour signifier la fin d'un bloc
    	rectF.set(x, PADDING * 2, x + CLOSING_INSTRUCTION_WIDTH, height - PADDING * 2);
        canvas.drawRoundRect(rectF, 10, 10, paint);
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
        int height = getHeight();
        int x = MARGIN;

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(android.R.color.background_light));

        canvas.drawPaint(paint);
        
        if (instruction == null) 
        	return;
        
        // D'abord on dessine les blocs englobants s'il y en a
        for (Instruction inst : getEnglobingBlocks()) {
        	paint.setColor(Color.HSVToColor(new float[]{inst.getColor(), 0.45f, 0.65f}));
        	canvas.drawRect(x, 0, x + CONNECTOR_WIDTH, height, paint);
        	x += CONNECTOR_WIDTH + CONNECTOR_SPACE;
        }

        // Si la ligne est vide, on ne dessine rien
        if (instruction.getName().length() == 0) return;

		// Sinon, on dessine l'instruction Blockly-style
        
        // On calcul la taille du text
        paint.getTextBounds(instruction.getName(), 0, instruction.getName().length(), textBounds);
        
        // On utilise la teinte de l'instruction
        paint.setColor(Color.HSVToColor(new float[]{instruction.getColor(), 0.45f, 0.65f}));

        // On dessine le bloc si ce n'est pas une accolade fermante
        if (!instruction.getName().equals("}")) {
        	drawInstruction(canvas, x, height);
        } else {
        	drawClosingInstruction(canvas, x, height);
        }
	}

	@Override
	protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {        
		// Faire ici les calculs nécessaires s'il y a un changement de taille du layout
		super.onSizeChanged(width, height, oldWidth, oldHeight);
	}

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }
    
    public void setHandler(AlgorithmHandler handler) {
    	this.handler = handler;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
}