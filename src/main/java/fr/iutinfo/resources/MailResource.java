package fr.iutinfo.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.beans.Feedback;
import fr.iutinfo.utils.MailSender;


@Path("/email")
@Produces(MediaType.APPLICATION_JSON)
public class MailResource {
	
	public MailResource() {}
	
	@GET
	@Path("/send")
	public Feedback createUser(String message) {
		MailSender.sendMessageTest();
		return new Feedback(true, "Oui.");
	}
}
