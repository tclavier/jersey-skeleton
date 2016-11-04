package fr.iutinfo.skeleton.api;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserTest {
    @Test
    public void should_set_salt_at_build () {
        User user = new User();
        assertNotNull(user.getSalt());
        assertFalse(user.getSalt().isEmpty());
    }
}
