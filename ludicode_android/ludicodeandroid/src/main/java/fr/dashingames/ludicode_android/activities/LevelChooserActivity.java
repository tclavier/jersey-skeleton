package fr.dashingames.ludicode_android.activities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.dashingames.ludicode_android.R;
import fr.dashingames.ludicode_android.beans.LevelList;
import fr.dashingames.ludicode_android.beans.User;
import fr.dashingames.ludicode_android.network.HttpResponse;
import fr.dashingames.ludicode_android.utils.JsonUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Classe représentant l'activité de choix de la liste de niveaux
 *
 */
public class LevelChooserActivity extends Activity {

	private LevelList[] levelLists;
	private ListView listView;

	private final String DEFAULT_LIST_ZERO = "{\"id\":1,\"idAuthor\":0,\"levelCount\":5,\"name\":\"Tutoriel\"}";
	private final String DEFAULT_LIST_ONE = "{\"id\":2,\"idAuthor\":0,\"levelCount\":5,\"name\":\"Intermédiaire\"}";
	private final String DEFAULT_LIST_TWO = "{\"id\":3,\"idAuthor\":0,\"levelCount\":3,\"name\":\"Expert\"}";

	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level_chooser);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			HttpResponse response = (HttpResponse) extras.getParcelable(SplashActivity.RESPONSE);
			user = extras.getParcelable(MainActivity.USER);
			if (response.isSuccessful()) {
				JSONArray jsArray = response.getJSONArray();
				levelLists = new LevelList[jsArray.length()];

				try {
					JsonUtils.insertArrayFromJSON(levelLists, jsArray);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				setDefaultLists();
			}
		}

		String[] labels = new String[levelLists.length];
		
		for (int i = 0; i < levelLists.length; i++) {
			LevelList l = levelLists[i];
			labels[i] = l.getName() + " (" + l.getLevelCount() + " " + 
			getResources().getString(R.string.levels) + ")";
		}
		
		listView = (ListView) findViewById(R.id.levelsListsListView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, labels);
		listView.setAdapter(adapter);	

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				playList(levelLists[position].getId());
			}
		});
		Log.i("progress", "user " + user);

	}

	private void setDefaultLists() {
		levelLists = new LevelList[3];
		try {
			levelLists[0] = (LevelList) JsonUtils.populateObjectFromJSON(LevelList.class, 
					new JSONObject(DEFAULT_LIST_ZERO));
			levelLists[1] = (LevelList) JsonUtils.populateObjectFromJSON(LevelList.class, 
					new JSONObject(DEFAULT_LIST_ONE));
			levelLists[2] = (LevelList) JsonUtils.populateObjectFromJSON(LevelList.class, 
					new JSONObject(DEFAULT_LIST_TWO));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle bundle) {
		user = bundle.getParcelable(MainActivity.USER);
	};

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		bundle.putParcelable(MainActivity.USER, user);
	}

	public void playList(int idx) {
		Intent intent = new Intent(this, SplashActivity.class);
		intent.putExtra(MainActivity.USER, user);
		intent.putExtra(SplashActivity.RESOURCE, "/levelLists/" + idx);
		intent.putExtra(SplashActivity.NEXT_ACTIVITY, GameActivity.class.getName());
		startActivity(intent);
	}

	public void refreshList(View view) {
		Intent intent = new Intent(view.getContext(), SplashActivity.class);
		intent.putExtra(MainActivity.USER, user);
		intent.putExtra(SplashActivity.RESOURCE, "/levelLists");
		intent.putExtra(SplashActivity.NEXT_ACTIVITY, LevelChooserActivity.class.getName());
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
