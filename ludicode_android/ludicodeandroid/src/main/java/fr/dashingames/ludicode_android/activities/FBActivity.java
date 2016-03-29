package fr.dashingames.ludicode_android.activities;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

import fr.dashingames.ludicode_android.R;

/**
 * Activité de test de facebook
 *
 */
public class FBActivity extends FragmentActivity {
	private Button button_backToMenu;
	private Button button_fbConnexion;
	private boolean isFetching = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fb);

		LoginButton lButton;
		button_fbConnexion = (Button) findViewById(R.id.b_loginFb);
		button_backToMenu = (Button) findViewById(R.id.b_backToMenu);
		button_fbConnexion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				performFacebookLogin();
			}
		});
		button_backToMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});
	}


	private void performFacebookLogin() {
		final Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, Arrays.asList("user_friends"));
		Session session = Session.openActiveSession(this, true, new Session.StatusCallback() {
			@Override
			public void call(Session session, SessionState state, Exception exception) {
				if(session.isOpened() && !isFetching) {
					isFetching = true;
					Log.d("FACEBOOK", "Session open");
					session.requestNewReadPermissions(newPermissionsRequest);

					meRequest(session);
					myFriendsRequest(session);
				}
			}

		});
	}

	public void meRequest(Session session) {
		final ProfilePictureView ppvUser = (ProfilePictureView) findViewById(R.id.ppv_user);
		Request.newMeRequest(session, new Request.GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) {
				Log.d("FACEBOOK", user.getInnerJSONObject().toString());
				ppvUser.setProfileId(user.getId());
			}
		}).executeAsync();
	}

	public void myFriendsRequest(Session session) {
		Request.newMyFriendsRequest(session, new Request.GraphUserListCallback() {
			@Override
			public void onCompleted(List<GraphUser> users, Response response) {
				Log.d("FACEBOOK", "friend list completed -> " + response.getRawResponse());
				if(response.getError() != null)
					Log.e("FACEBOOK", response.getError().getErrorMessage());
				for(GraphUser user : users)
					Log.d("FACEBOOK", user.toString());
			}
		}).executeAsync();
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}

}
