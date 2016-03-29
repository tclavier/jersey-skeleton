package fr.dashingames.ludicode_android.utils;

import java.lang.reflect.Method;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
 
import org.json.JSONException;

/**
 * Parses any class to JSON and populates any class from JSON using Android native JSON, 
 * without any third-party libraries.
 *
 */
public class JsonUtils {
	private static int privacy = 2;

	/**
	 * p = 0 --> Only for public fields
	 * p = 1 --> Only for public + protected fields
	 * p = 2 --> All fields: public + protected + private. Use only under your own responsability.
	 */
	public static void setPrivacyLevel(int p) {
		if ((p >= 0) && (p <= 2)) {
			privacy = p;
		}
	}

	/**
	 * privacy = 0 --> Only for public fields
	 * privacy = 1 --> Only for public + protected fields
	 * privacy = 2 --> All fields: public + protected + private. Use only under your own responsability.
	 */
	public static int getPrivacyLevel() {
		return privacy;
	}

	/**
	 * @return an object of type classname with its PUBLIC fields full with the js info
	 */
	public static Object populateObjectFromJSON(Class<?> classname, JSONObject js) {
		Object obj = null;
		try {
			obj = classname.newInstance();
		} catch (InstantiationException e1) {
			System.err.println(e1.getMessage());
			return null;
		} catch (IllegalAccessException e1) {
			System.err.println(e1.getMessage());
			return null;
		}

		Field[] fields;

		if (privacy == 0) {
			fields = classname.getFields();
		} else {
			fields = classname.getDeclaredFields();
		}

		for (Field f : fields) {
			if ((privacy == 2) && (Modifier.isPrivate(f.getModifiers())))
				f.setAccessible(true);

			try {
				if (js.has(f.getName())) {
					String type = f.getType().getSimpleName();
					if (type.equalsIgnoreCase("boolean")) {
						f.setBoolean(obj, js.getBoolean(f.getName()));
					} else if (type.equalsIgnoreCase("int")) {
						f.setInt(obj, js.getInt(f.getName()));
					} else if (type.equalsIgnoreCase("double")) {
						f.setDouble(obj, js.getDouble(f.getName()));
					} else if (type.equalsIgnoreCase("float")) {
						f.setFloat(obj, (float) js.getDouble(f.getName()));
					} else if (type.equalsIgnoreCase("string")) {
						f.set(obj, js.getString(f.getName()));
					} else if (f.getType().isArray()) {
						f.set(obj, Array.newInstance(f.getType().getComponentType(), js.getJSONArray(f.getName()).length()));
						insertArrayFromJSON(f.get(obj), js.getJSONArray(f.getName()));
					} else {
						f.set(obj, populateObjectFromJSON(f.getType(), js.getJSONObject(f.getName())));
					}
				}
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());

			} catch (IllegalAccessException e) {
				System.err.println(e.getMessage());

			} catch (JSONException e) {
				System.err.println(e.getMessage());
			}

			if ((privacy == 2) && (Modifier.isPrivate(f.getModifiers())))
				f.setAccessible(false);
		}
		return obj;
	}

	/**
	 * @param o
	 *            This object will be filled up with the JSONArray js data
	 */
	public static void insertArrayFromJSON(Object o, JSONArray js) throws IllegalArgumentException, NegativeArraySizeException, IllegalAccessException, JSONException {
		Class<?> c = o.getClass();

		String type = c.getComponentType().getSimpleName();

		for (int i = 0; i < js.length(); i++) {
			if (c.getComponentType().isArray()) {
				Array.set(o, i, Array.newInstance(c.getComponentType().getComponentType(), js.getJSONArray(i).length()));
				insertArrayFromJSON(Array.get(o, i), js.getJSONArray(i));
			} else if (!c.getComponentType().isPrimitive() && (!type.equalsIgnoreCase("string"))) {
				Array.set(o, i, populateObjectFromJSON(c.getComponentType(), js.getJSONObject(i)));
			} else {
				Array.set(o, i, js.get(i));
			}
		}
	}

	/**
	 * Méthode permettant de convertir un objet vers un
	 * objet JSON
	 * ATTENTION : ne supporte que les tableaux d'int, de double, de String ou d'Object
	 * @param obj Objet à convertir
	 * @return Objet sous forme d'objet JSON
	 */
	public static JSONObject toJSON(Object obj) {
		JSONObject json = new JSONObject();

		for (Method m : obj.getClass().getMethods()) {
			if (m.getName().matches("get.*") && !"getClass".equals(m.getName())) {
				String name = Character.toLowerCase(m.getName().charAt(3)) + m.getName().substring(4);
				if (returnsPrimitive(m)) {
					try {
						json.put(name, m.invoke(obj));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (m.getReturnType().isArray()) {
					JSONArray jsonArr = new JSONArray();
					String arrayType = m.getReturnType().getComponentType().getName();

					try {
						if ("int".equals(arrayType)) {
							int[] valuesArr = (int[]) m.invoke(obj);
							for (int i = 0; i < valuesArr.length; i++) {
								jsonArr.put(valuesArr[i]);
							}
						} else if ("double".equals(arrayType)) {
							double[] valuesArr = (double[]) m.invoke(obj);
							for (int i = 0; i < valuesArr.length; i++) {
								jsonArr.put(valuesArr[i]);
							}
						} else if ("java.lang.String".equals(arrayType)) {
							String[] valuesArr = (String[]) m.invoke(obj);
							for (int i = 0; i < valuesArr.length; i++) {
								jsonArr.put(valuesArr[i]);
							}
						} else {
							Object[] valuesArr = (Object[]) m.invoke(obj);
							for (int i = 0; i < valuesArr.length; i++) {
								jsonArr.put(toJSON(valuesArr[i]));
							}
						}

						json.put(name, jsonArr);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						json.put(name, toJSON(m.invoke(obj)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		return json;
	}

	private static boolean returnsPrimitive(Method m) {
		return m.getReturnType().isPrimitive() || "java.lang.String".equals(m.getReturnType().getName());
	}

}
