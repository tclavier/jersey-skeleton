package fr.dashingames.ludicode_android.gui.adapters;

import java.util.List;

import fr.dashingames.ludicode_android.R;
import fr.dashingames.ludicode_android.activities.GameActivity;
import fr.dashingames.ludicode_android.beans.Instruction;
import fr.dashingames.ludicode_android.gui.views.FittingTextView;
import fr.dashingames.ludicode_android.gui.views.RowBackgroundView;
import fr.dashingames.ludicode_android.utils.AlgorithmHandler;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Adapter permettant d'insérer des instructions dans une listView
 *
 */
public class AlgorithmAdapter<T> extends ArrayAdapter<T> {

	private final int MINIMAL_HEIGHT = 80;

	private ListView algorithmView;
	private GameActivity activity;
	private int resource;
	private List<Instruction> instructions; 
	private AlgorithmHandler handler;

	@SuppressWarnings("unchecked")
	public AlgorithmAdapter(GameActivity activity, int resource, List<T> objects, 
			ListView algorithmView, AlgorithmHandler handler) {
		super(activity, resource, objects);
		this.activity = activity;
		instructions = (List<Instruction>) objects;
		this.resource = resource;
		this.handler = handler;
		this.algorithmView = algorithmView;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row = convertView;
		InstructionHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			row = inflater.inflate(resource, parent, false);

			holder = new InstructionHolder();
			holder.image_remove = (ImageButton)row.findViewById(R.id.delete);
			holder.image_add = (ImageButton) row.findViewById(R.id.insert);
			holder.text = (FittingTextView)row.findViewById(R.id.instruction);
            holder.background = (RowBackgroundView) row.findViewById(R.id.rowBackground);

			row.setTag(holder);
		} else
			holder = (InstructionHolder) row.getTag();

		Instruction instruction = instructions.get(position);
		
		configureText(holder, instruction, position);
		configureImageRemove(holder, position);
		configureImageAdd(holder, position);
		configureImageBackground(holder, instruction, position);
		
		return row;
	}

	private void configureImageBackground(InstructionHolder holder,
			Instruction instruction, int position) {
		holder.background.setHandler(handler);
		holder.background.setId(position);
		holder.background.setInstruction(instruction);
	}

	private void configureImageAdd(InstructionHolder holder, final int position) {
		holder.image_add.setImageResource(R.drawable.add_line);
		holder.image_add.setMinimumHeight(MINIMAL_HEIGHT);
		holder.image_add.setMinimumWidth(algorithmView.getWidth() / 7);
		holder.image_add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				activity.clickItem(position);
			}
		});
	}

	private void configureImageRemove(InstructionHolder holder, final int position) {
		holder.image_remove.setImageResource(R.drawable.com_facebook_close);
		holder.image_remove.setMinimumHeight(MINIMAL_HEIGHT);
		holder.image_remove.setMinimumWidth(algorithmView.getWidth() / 7);
		holder.image_remove.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				activity.removeLine(position);
			}
		});
	}

	private void configureText(InstructionHolder holder, Instruction instruction,
			final int position) {
		holder.text.setText("");
		holder.text.setHeight(MINIMAL_HEIGHT);
		holder.text.setMinimumHeight(MINIMAL_HEIGHT);
		holder.text.setWidth((algorithmView.getWidth() / 7) * 5);
		holder.text.setMinimumWidth((algorithmView.getWidth() / 7) * 5);
		holder.text.setBackgroundColor(Color.TRANSPARENT);
		
		// On affiche l'aide qui si la ligne et vide et n'est pas dans un bloc
		if (handler.isLineEmpty(position) && instruction.getEnglobingBlock() == null)
			holder.text.setHint(activity.getResources().getString(R.string.hint));
		else
			holder.text.setHint("");

		holder.text.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				activity.clickItem(position);
			}
		});
	}

	static class InstructionHolder
	{
		ImageView image_remove;
		ImageView image_add;
		FittingTextView text;
        RowBackgroundView background;
	}

}
