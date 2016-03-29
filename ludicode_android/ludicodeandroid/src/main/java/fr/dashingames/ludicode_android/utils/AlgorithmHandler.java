package fr.dashingames.ludicode_android.utils;

import java.util.ArrayList;
import java.util.Stack;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;
import android.widget.Toast;
import fr.dashingames.ludicode_android.R;
import fr.dashingames.ludicode_android.beans.Instruction;

/**
 * Classe permettant de gérer l'algorithme élaboré par l'utilisateur
 *
 */
public class AlgorithmHandler implements Parcelable {

	/**
	 * Tableau indiçant les instructions choisies par l'utilisateur
	 */
	private int[] chosenInstructions;
	
	/**
	 * Numero du bloc surlignés
	 */
	private int highlightedBlock = -1;
	
	/**
	 * Liste contenant toutes les instructions possibles pour l'utilisateur
	 */
	private ArrayList<Instruction> instructions;

	private Activity activity;

	private final int CLOSED_BRACKET = -1;
	private final int EMPTY_INSTRUCTION = -2;
	private final int ELSE_INSTRUCTION = -3;

	private final String CHOSEN_INSTRUCTIONS = "Chosen Instructions";
	private final String POSSIBLE_INSTRUCTIONS = "Possible Instructions";
	private int nbInstructions;

	public AlgorithmHandler(Activity activity, int nbInstructions, Instruction[] availableInstructions) {
		this.nbInstructions = nbInstructions;
		this.activity = activity;
		initInstructions(availableInstructions);
	}

	/**
	 * Initialise les instructions possibles pour le niveau courant
	 * @param availableInstructions instructions possibles
	 */
	private void initInstructions(Instruction[] availableInstructions) {
		instructions = new ArrayList<Instruction>();
		for (Instruction i : availableInstructions) {
			instructions.add(i);
		}
		chosenInstructions = new int[nbInstructions];
		resetInstructions();
	}

	/**
	 * Reset les instructions choisies
	 */
	public void resetInstructions() {
		chosenInstructions = new int[nbInstructions];
		for (int i = 0; i < chosenInstructions.length; i++)
			chosenInstructions[i] = EMPTY_INSTRUCTION;
	}

	private void showMessage(String message) {
		showMessage(message, Toast.LENGTH_LONG);
	}

	/**
	 * Affiche un message sous forme d'un toast
	 * @param message message
	 * @param duration Toast.LONG ou Toast.SHORT
	 */
	private void showMessage(String message, int duration) {
		Toast toast = Toast.makeText(activity, message, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * Ajoute l'instruction instruction à la position position
	 * @param instruction instruction à ajouter
	 * @param position position de l'instruction
	 */
	public void addInstruction(Instruction instruction, int position) {
		if (instruction.isBlock()) {
			int limitId = instruction.isBlockWithElse() ? chosenInstructions.length - 2 : chosenInstructions.length - 1;
			if (position >= limitId) {
				showMessage(activity.getResources().getString(R.string.errorNotEnoughSpace));
				return;
			} else if (!canAddInstruction()) {
				showMessage(activity.getResources().getString(R.string.errorTooMuchInstructions));
			}
			if (instruction.isBlockWithElse())
				addBlockWithElseInstruction(instruction, position);
			else
				addBlockInstruction(instruction, position);
		} else
			chosenInstructions[position] = instructions.indexOf(instruction);
	}

	/**
	 * Insère une instruction de type block dans l'algorithme
	 * @param instruction instruction à ajouter
	 * @param position ligne à laquelle ajouter l'instruction
	 */
	private void addBlockInstruction(Instruction instruction, int position) {
		boolean mustShift = false;
		if (!hasSpaceForBlock(false, position)) {
			if (canShiftWithoutLoss(1))
				mustShift = true;
			else {
				showMessage(activity.getResources().getString(R.string.errorLooseData));
				return;
			}
		}
		addNewLineToInstructions();
		if (mustShift)
			shiftInstructions(position + 1, 2);

		chosenInstructions[position] = instructions.indexOf(instruction);
		chosenInstructions[position + 2] = CLOSED_BRACKET;
	}

	/**
	 * Insère une instruction de type block else dans l'algorithme
	 * @param instruction instruction à ajouter
	 * @param position ligne à laquelle ajouter l'instruction
	 */
	private void addBlockWithElseInstruction(Instruction instruction, int position) {
		boolean mustShift = false;
		if (!hasSpaceForBlock(true, position)) {
			if (canShiftWithoutLoss(2))
				mustShift = true;
			else {
				showMessage(activity.getResources().getString(R.string.errorLooseData));
				return;
			}
		}
		for (int i = 0; i < 2; i++)
			addNewLineToInstructions();

		if (mustShift)
			shiftInstructions(position + 1, 4);

		chosenInstructions[position] = instructions.indexOf(instruction);
		chosenInstructions[position + 2] = ELSE_INSTRUCTION;
		chosenInstructions[position + 4] = CLOSED_BRACKET;
	}

	/**
	 * Détermine si on a suffisamment de place pour insérer un bloc
	 * 
	 * @param position position de départ du test
	 * @return true si on a la place, false sinon
	 */
	private boolean hasSpaceForBlock(boolean isElseBlock, int position) {
		int nbLinesNeeded = isElseBlock ? 3 : 2;
		for (int i = position; i < position + nbLinesNeeded; i++) {
			if (i == chosenInstructions.length)
				continue;
			if (chosenInstructions[i] != EMPTY_INSTRUCTION)
				return false;
		}
		return true;
	}

	/**
	 * Détermine si le shift fera perdre des données
	 * @param shiftExtent étendue du shift (nombre de lignes de décalage)
	 * @return true si l'on peut shifter sans perte, false sinon
	 */
	private boolean canShiftWithoutLoss(int shiftExtent) {
		for (int i = chosenInstructions.length - 1; i >= chosenInstructions.length - shiftExtent; i--)
			if (chosenInstructions[i] != EMPTY_INSTRUCTION)
				return false;
		return true;
	}

	/**
	 * Agrandis le nombre de lignes permettant de choisir des instructions d'une ligne
	 */
	private void addNewLineToInstructions() {
		int[] tmp = new int[chosenInstructions.length + 1];
		for (int i = 0; i < chosenInstructions.length; i++)
			tmp[i] = chosenInstructions[i];
		chosenInstructions = tmp;
		chosenInstructions[chosenInstructions.length - 1] = EMPTY_INSTRUCTION;
	}

	private void removeLineFromInstructions() {
		int[] tmp = new int[chosenInstructions.length - 1];
		for (int i = 0; i < tmp.length; i++)
			tmp[i] = chosenInstructions[i];
		chosenInstructions = tmp;
	}

	/**
	 * Déplace les instructions vers le bas de dist lignes
	 * et mets des lignes vides là ou les lignes décalées
	 * laissent de la place.
	 * @param position position de départ du décalage
	 * @param dist nombre de lignes à décaler
	 */
	private void shiftInstructions(int position, int dist) {
		for (int i = chosenInstructions.length - dist - 1; i >= position; i--) {
			chosenInstructions[i + dist] = chosenInstructions[i];
		}
		for (int i = position; i < position + dist; i++)
			chosenInstructions[i] = EMPTY_INSTRUCTION;
	}

	public ArrayList<Instruction> getPossibleInstructions() {
		return instructions;
	}

	/**
	 * Calcule les instructions choisies en fonction des indices dans le tableau chosenInstructions
	 * @return liste contenant les instructions choisies
	 */
	public ArrayList<Instruction> getChosenInstructions() {
		ArrayList<Instruction> chosen = new ArrayList<Instruction>();
		Stack<Instruction> blocks = new Stack<Instruction>();

		for (int i = 0; i < chosenInstructions.length; i++) {
			int id = chosenInstructions[i];
			Instruction toAdd;
			if (id == CLOSED_BRACKET)
				toAdd = new Instruction("}", "", Instruction.IS_NOT_BLOCK, 0);
			else if (id == EMPTY_INSTRUCTION)
				toAdd = new Instruction("", "", Instruction.IS_NOT_BLOCK, 0);
			else if (id == ELSE_INSTRUCTION)
				toAdd = new Instruction("sinon", "} else {", Instruction.IS_BLOCK_ELSE, 0);
			else
				toAdd = instructions.get(id);

			if (id == CLOSED_BRACKET || id == ELSE_INSTRUCTION) {
				if (!blocks.isEmpty()) {
					// L'accolade fermante doit avoir la meme couleur
					toAdd.setColor(blocks.peek().getColor());
					blocks.pop();
				}
			}
			Instruction instruction = new Instruction(toAdd.getName(), toAdd.getCode(),
					toAdd.getBlock(), toAdd.getColor());
			
			if (instruction.isBlockWithElse()) {
				instruction.setName(instruction.getName().replace("... sinon", ""));
			}

			// On defini le "bloc englobant" s'il y en a un
			if (!blocks.isEmpty())
				instruction.setEnglobingBlock(blocks.peek());

			chosen.add(instruction);

			if (toAdd.isBlock())
				blocks.push(instruction);
		}
		return chosen;
	}

	/**
	 * Ajout une ligne vide à la position position
	 * @param position id de la ligne vide
	 */
	public void addEmptyLine(int position) {
		if (canShiftWithoutLoss(1))
			shiftInstructions(position, 1);
		else
			showMessage(activity.getResources().getString(R.string.errorLooseData));
	}

	/**
	 * Détermine si la ligne d'indice position est vide ou non
	 * @param position ligne testée
	 * @return true si la ligne est vide, false sinon
	 */
	public boolean isLineEmpty(int position) {
		return chosenInstructions[position] == EMPTY_INSTRUCTION;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList(POSSIBLE_INSTRUCTIONS, instructions);
		bundle.putIntArray(CHOSEN_INSTRUCTIONS, chosenInstructions);
		dest.writeBundle(bundle);
	}

	public AlgorithmHandler() { }

	public AlgorithmHandler(Parcel in) {
		Bundle bundle = in.readBundle();
		chosenInstructions = bundle.getIntArray(CHOSEN_INSTRUCTIONS);
		instructions = bundle.getParcelableArrayList(POSSIBLE_INSTRUCTIONS);
	}

	public static final Parcelable.Creator<AlgorithmHandler> CREATOR
	= new Parcelable.Creator<AlgorithmHandler>() {
		public AlgorithmHandler createFromParcel(Parcel in) {
			return new AlgorithmHandler(in);
		}

		public AlgorithmHandler[] newArray(int size) {
			return new AlgorithmHandler[size];
		}
	};

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	/**
	 * Récupère l'algorithme correspondant aux instructions choisies
	 * @return chaine contenant l'algorithme à effectuer
	 */
	public String getAlgorithm() {
		String algo = "";
		for (int i = 0; i < chosenInstructions.length; i++) {
			int instructionIdx = chosenInstructions[i];
			
			if (instructionIdx == EMPTY_INSTRUCTION)
				continue;
			
			// On indique le n� de ligne
			algo += "game.interpreter.executedBlock = " + i + ";";
						
			if (instructionIdx == CLOSED_BRACKET)
				algo += "};";
			else if (instructionIdx == ELSE_INSTRUCTION)
				algo += "} else {";
			else {
				String code = instructions.get(instructionIdx).getCode(); 
				algo += code.replaceAll("%line%", i + "");
				if (instructions.get(instructionIdx).isBlock()) {
					algo += "{";
					algo += "\nif (!game.interpreter.increment(" + i + ")) return;\n";
				}
			}
		}
		return algo;
	}

	/**
	 * Détermine si l'utilisateur peut insérer une nouvelle instruction
	 * @return true si on peut ajouter une instruction, false sinon
	 */
	private boolean canAddInstruction() {
		return countChosenInstructions() < nbInstructions;
	}

	/**
	 * Compte le nombre d'instructions choisies par l'utilisateur
	 * @return le nombre d'instructions choisies
	 */
	public int countChosenInstructions() {
		int nb = 0;
		for (Integer i : chosenInstructions)
			if (i != EMPTY_INSTRUCTION && i != CLOSED_BRACKET && i != ELSE_INSTRUCTION)
				nb++;
		return nb;
	}

	/**
	 * Supprime la ligne à l'indice position
	 * @param position 
	 */
	public void removeLine(int position) {
		int idx = chosenInstructions[position];
		if (idx == EMPTY_INSTRUCTION)
			shiftInstructionsBackwards(position, 1);
		else if (idx == CLOSED_BRACKET || idx == ELSE_INSTRUCTION)
			showMessage("Vous ne pouvez pas supprimer cette ligne. Supprimez plutôt "
					+ "l'instruction du début du bloc", Toast.LENGTH_SHORT);
		else {
			Instruction instruction = instructions.get(idx);
			shiftInstructionsBackwards(position, 1);
			if (instruction.isBlockWithElse()) {
				removeCorrespondingElement(position);
				removeCorrespondingElement(position);
			}else if (instruction.isBlock()) 
				removeCorrespondingElement(position);
		}
	}

	/**
	 * Supprime l'accolade fermante correspondant à l'instruction qui se trouve à la ligne position
	 * @param position id de la ligne testée
	 */
	private void removeCorrespondingElement(int position) {
		int indentation = 1;
		for (int i = position; i < chosenInstructions.length; i++) {
			int id = chosenInstructions[i];
			if (id == CLOSED_BRACKET || id == ELSE_INSTRUCTION)
				indentation--;
			else {
				if (id != EMPTY_INSTRUCTION) {
					Instruction tmp = instructions.get(chosenInstructions[i]);
					if (tmp.isBlock())
						indentation++;
				}
			}
			if (indentation == 0) {
				shiftInstructionsBackwards(i, 1);
				removeLineFromInstructions();
				return;
			}
		}
	}

	/**
	 * Décale les instructions vers l'indice 0
	 * @param position position de la limite inférieure du décalage
	 * @param idx quantité de cases de décalage
	 */
	private void shiftInstructionsBackwards(int position, int idx) {
		for (int i = position; i < chosenInstructions.length - 1; i++)
			chosenInstructions[i] = chosenInstructions[i + idx];

		for (int i = chosenInstructions.length - 1; i > chosenInstructions.length - 1 - idx; i--)
			chosenInstructions[i] = EMPTY_INSTRUCTION;

	}

	public void setHighlightedBlock(int position) {
		highlightedBlock = position;
	}
	
	public int getHighlightedBlock() {
		return highlightedBlock;
	}

}
