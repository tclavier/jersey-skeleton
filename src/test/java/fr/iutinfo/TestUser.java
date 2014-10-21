package fr.iutinfo;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

public class TestUser extends JerseyTest {
	@Override
    protected Application configure() {
        return new App();
    }

	@Test
	public void testUserWithNameFoo() {
		String json = target("/user/foo").request().get(String.class);
		Assert.assertEquals("{\"name\":\"foo\"}", json);
	}
}
