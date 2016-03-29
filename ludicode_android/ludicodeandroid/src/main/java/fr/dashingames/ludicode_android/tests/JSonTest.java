package fr.dashingames.ludicode_android.tests;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import fr.dashingames.ludicode_android.utils.JsonUtils;

public class JSonTest {

	@Test
	public void toJSonSimpleTest() {
		try {
			JSONObject expected = new JSONObject("{name: toto, id : 42}");
			SimpleObject obj = new SimpleObject("toto", 42);
			JSONObject actual = JsonUtils.toJSON(obj);
			
			System.out.println("expected: " + expected.toString());
			System.out.println("actual: " + actual.toString());
			
			assertTrue(expected.toString().equals(actual.toString()));
		} catch (JSONException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void toJSonComplexTest() {		
		try {
			JSONObject expected = new JSONObject("{obj: {name: toto, id : 42}, number: 1337}");
			ComplexObject obj = new ComplexObject(new SimpleObject("toto", 42), 1337);
			JSONObject actual = JsonUtils.toJSON(obj);
			
			System.out.println("expected: " + expected.toString());
			System.out.println("actual: " + actual.toString());
			
			assertTrue(expected.toString().equals(actual.toString()));
		} catch (JSONException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void toJSonComplexArrayTest() {
		try {
			JSONObject expected = new JSONObject("{binarySize: 10, array: [{name: toto, id : 42},"
					+ "{name: tata, id : 24}]}");
			SimpleObject[] arr = {new SimpleObject("toto", 42), new SimpleObject("tata", 24)};
			ComplexArrayObject obj = new ComplexArrayObject(10, arr);
			JSONObject actual = JsonUtils.toJSON(obj);
			
			System.out.println("expected: " + expected.toString());
			System.out.println("actual: " + actual.toString());
			
			assertTrue(expected.toString().equals(actual.toString()));
		} catch (JSONException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void toJSonPrimitiveArrayTest() {
		try {
			JSONObject expected = new JSONObject("{pie: 3.14, array: [42, 1337]}");
			int[] arr = {42, 1337};
			PrimitiveArrayObject obj = new PrimitiveArrayObject(3.14, arr);
			JSONObject actual = JsonUtils.toJSON(obj);
			
			System.out.println("expected: " + expected.toString());
			System.out.println("actual: " + actual.toString());
			
			assertTrue(expected.toString().equals(actual.toString()));
		} catch (JSONException e) {
			e.printStackTrace();
			fail();
		}
	}

}
