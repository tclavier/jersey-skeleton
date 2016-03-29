package fr.dashingames.ludicode_android.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe encapsulant les retours des requêtes REST et permettant
 * de récupérer le contenu au format texte ou JSON
 */
public class HttpResponse implements Parcelable {

    private String content;
    private boolean successful;

    public HttpResponse(String content, boolean successful) {
        this.content = content;
        this.successful = successful;
    }
    
    @Override
    public String toString() {
    	return getText();
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getText() {
        return content;
    }

    public JSONObject getJSON() {
        try {
            return new JSONObject(content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JSONArray getJSONArray() {
    	 try {
             return new JSONArray(content);
         } catch (JSONException e) {
             e.printStackTrace();
         }
         return null;
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(content);
		dest.writeBooleanArray(new boolean[] {successful});
	}
	
	public HttpResponse(Parcel in) {
		boolean[] tab = new boolean[1];
		content = in.readString();
		in.readBooleanArray(tab);
		successful = tab[0];
	}
	
	public static final Parcelable.Creator<HttpResponse> CREATOR
	= new Parcelable.Creator<HttpResponse>() {
		public HttpResponse createFromParcel(Parcel in) {
			return new HttpResponse(in);
		}

		public HttpResponse[] newArray(int size) {
			return new HttpResponse[size];
		}
	};
}
