package fr.iutinfo;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

public class RestClientTest extends JerseyTest {
    private static UserDao dao;

  @Override
  protected Application configure() {
      App app = new App();
      DBI dbi = app.dbi;
      dao = dbi.open(UserDao.class);
    return new App();
  }

  @Before
  public void cleanupDb() {
      dao.dropUserTable();
      dao.createUserTable();
  }

  @Test
  public void getUsers () {
    int port = this.getPort();
    String baseUrl = "http://localhost:"+port+"/user/";
    RestClient client = new RestClient();
    client.addUser(new User(0, "Thomas"),  baseUrl );
    client.addUser(new User(0, "Yann"),  baseUrl );
    List<User> users = client.getUrlAsUser(baseUrl );
    assertEquals(2, users.size());
  }
}
