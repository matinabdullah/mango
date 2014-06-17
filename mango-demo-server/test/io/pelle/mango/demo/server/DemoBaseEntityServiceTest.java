package io.pelle.mango.demo.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import io.pelle.mango.client.IBaseEntityService;
import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.test.client.Entity1VO;
import io.pelle.mango.test.client.Entity2VO;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoBaseEntityServiceTest extends BaseTest {

	@Autowired
	private IBaseEntityService baseEntityService;
	
	@Autowired
	private IBaseVODAO baseVODAO;

	public void setBaseEntityService(IBaseEntityService baseEntityService) {
		this.baseEntityService = baseEntityService;
	}

	@Test
	public void testGetNewVO() {
		assertNotNull(baseEntityService.getNewVO(Entity1VO.class.getName(), null));
		assertTrue(baseEntityService.getNewVO(Entity2VO.class.getName(), null) instanceof Entity2VO);
	}

	@Test
	public void testFilter() {

		baseVODAO.deleteAll(Entity1VO.class);
		
		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("aaa");

		Result<Entity1VO> result = baseEntityService.validateAndSave(entity1VO);

		assertEquals("aaa", result.getVO().getStringDatatype1());
		assertEquals(0, result.getValidationMessages().size());
		
		
		List<Entity1VO> filterResult = baseEntityService.filter(SelectQuery.selectFrom(Entity1VO.class));
		assertEquals(1, filterResult.size());
	}
	
	@Test
	public void testValidateAndSave() {

		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("aaa");

		Result<Entity1VO> result = baseEntityService.validateAndSave(entity1VO);

		assertEquals("aaa", result.getVO().getStringDatatype1());
		assertEquals(0, result.getValidationMessages().size());
	}

	@Test
	public void testValidateAndCreate() {

		Entity1VO entity1VO = new Entity1VO();
		entity1VO.setStringDatatype1("aaa");

		Result<Entity1VO> result = baseEntityService.validateAndCreate(entity1VO);

		assertEquals("aaa", result.getVO().getStringDatatype1());
		assertEquals(0, result.getValidationMessages().size());
	}

	public void setBaseVODAO(IBaseVODAO baseVODAO) {
		this.baseVODAO = baseVODAO;
	}
}
