package packagename.projectname;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.db.test.BaseDBTest;
import org.springframework.http.MediaType;

import packagename.projectname.client.TodoVO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:/ProjectNameSpringServices-gen.xml", "classpath:/ProjectNameRestRemoteServices-gen.xml", "classpath:/MangoLoggerApplicationContext.xml", "classpath:/MangoApplicationContext.xml", "classpath:/MangoSpringServices-gen.xml", "classpath:/ProjectNameApplicationContext.xml", "classpath:/ProjectNameDB-gen.xml", "classpath:/ProjectNameBaseApplicationContext-gen.xml" })
public class ProjectNameServiceRestTest extends BaseDBTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private IBaseEntityService baseEntityService;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	public ProjectNameServiceRestTest() {
		super("projectname");
	}

	@Test
	public void testGetAll() throws Exception {
		
		baseEntityService.deleteAll(TodoVO.class.getName());
		
		mockMvc.perform(post("/projectnameservice/save").content("{ \"title\": \"buy milk\" }").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
		
		mockMvc.perform(post("/projectnameservice/getall").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.[0]title").value("buy milk"));
	}

}