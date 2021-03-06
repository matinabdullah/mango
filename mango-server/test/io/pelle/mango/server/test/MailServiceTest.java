package io.pelle.mango.server.test;

import io.pelle.mango.server.mail.MailService;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MailServiceTest {

	@Autowired
	private MailService mailService;

	@Test
	@Ignore
	public void testSendMail() {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", "Steve");
		model.put("link", "http://www.example.org");
		model.put("username", "steve23");

		mailService.sendMail("steve@yahoo.com", "classpath://mailtemplate.vm", model);

	}

}
