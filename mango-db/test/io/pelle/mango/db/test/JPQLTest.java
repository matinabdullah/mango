package io.pelle.mango.db.test;

import static io.pelle.mango.client.base.vo.query.BaseQuery.and;
import static io.pelle.mango.client.base.vo.query.BaseQuery.or;
import static io.pelle.mango.client.base.vo.query.CountQuery.countFrom;
import io.pelle.mango.client.base.vo.query.CountQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.query.ServerCountQuery;
import io.pelle.mango.db.query.ServerSelectQuery;
import io.pelle.mango.db.test.mockup.EntityVOMapper;
import io.pelle.mango.db.test.mockup.entities.DBTest1;
import io.pelle.mango.db.test.mockup.entities.DBTest2;
import io.pelle.mango.db.test.mockup.entities.DBTest3;
import junit.framework.TestCase;

import org.junit.Test;

public class JPQLTest extends TestCase {

	@Test
	public void testJoin() {
		ServerSelectQuery<DBTest1> query = ServerSelectQuery.adapt(SelectQuery.selectFrom(DBTest1.class).join(DBTest1.TEST2S));
		assertEquals("SELECT x0 FROM DBTest1 x0 LEFT JOIN x0.test2s x1", query.getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testJoin1() {
		ServerSelectQuery<DBTest1> query = ServerSelectQuery.adapt(SelectQuery.selectFrom(DBTest1.class).join(DBTest1.TEST2S, DBTest2.TEST3));
		assertEquals("SELECT x0 FROM DBTest1 x0 LEFT JOIN x0.test2s x1 LEFT JOIN x1.test3 x2", query.getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testSelectAll() {
		ServerSelectQuery<DBTest1> query = ServerSelectQuery.adapt(SelectQuery.selectFrom(DBTest1.class));
		assertEquals("SELECT x0 FROM DBTest1 x0", query.getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testCriteria() {
		ServerSelectQuery<DBTest1> query = ServerSelectQuery.adapt(SelectQuery.selectFrom(DBTest1.class).where(DBTest1.ID.eq(1)));
		assertEquals("SELECT x0 FROM DBTest1 x0 WHERE x0.id = 1", query.getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testCriteriaAndOr() {
		ServerSelectQuery<DBTest1> query = ServerSelectQuery.adapt(SelectQuery.selectFrom(DBTest1.class).where(DBTest1.ID.eq(1).and(DBTest1.ID.eq(2).or(DBTest1.ID.eq(3)))));
		assertEquals("SELECT x0 FROM DBTest1 x0 WHERE x0.id = 1 AND x0.id = 2 OR x0.id = 3", query.getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testCriteriaAndNestedOr() {
		ServerSelectQuery<DBTest1> query = ServerSelectQuery.adapt(SelectQuery.selectFrom(DBTest1.class).where(DBTest1.ID.eq(1).and(or(DBTest1.ID.eq(2), DBTest1.ID.eq(3)))));
		assertEquals("SELECT x0 FROM DBTest1 x0 WHERE x0.id = 1 AND (x0.id = 2 OR x0.id = 3)", query.getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testCriteriaOrNestedAnd() {
		ServerSelectQuery<DBTest1> query = ServerSelectQuery.adapt(SelectQuery.selectFrom(DBTest1.class).where(DBTest1.ID.eq(1).or(and(DBTest1.ID.eq(2), DBTest1.ID.eq(3)))));
		assertEquals("SELECT x0 FROM DBTest1 x0 WHERE x0.id = 1 OR (x0.id = 2 AND x0.id = 3)", query.getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testCriteriaOr() {
		ServerSelectQuery<DBTest1> query = ServerSelectQuery.adapt(SelectQuery.selectFrom(DBTest1.class).where(DBTest1.ID.eq(1).or(DBTest1.ID.eq(2))));
		assertEquals("SELECT x0 FROM DBTest1 x0 WHERE x0.id = 1 OR x0.id = 2", query.getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testCriteriaMultipleOr() {
		ServerSelectQuery<DBTest1> query = ServerSelectQuery.adapt(SelectQuery.selectFrom(DBTest1.class).where(DBTest1.TESTSTRING.eq("xxx").or(DBTest1.TESTSTRING.eq("yyy"))));
		assertEquals("SELECT x0 FROM DBTest1 x0 WHERE x0.testString = 'xxx' OR x0.testString = 'yyy'", query.getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testCriteriaAnd() {
		ServerSelectQuery<DBTest1> query = ServerSelectQuery.adapt(SelectQuery.selectFrom(DBTest1.class).where(DBTest1.ID.eq(1).and(DBTest1.ID.eq(2))));
		assertEquals("SELECT x0 FROM DBTest1 x0 WHERE x0.id = 1 AND x0.id = 2", query.getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testEntityCriteria() {
		DBTest3 test3 = new DBTest3();
		test3.setId(9);

		ServerSelectQuery<DBTest2> query = ServerSelectQuery.adapt(SelectQuery.selectFrom(DBTest2.class).where(DBTest2.TEST3.eq(test3)));
		assertEquals("SELECT x0 FROM DBTest2 x0 WHERE x0.test3.id = 9", query.getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testEntityCriteria1() {
		DBTest3 test3 = new DBTest3();
		test3.setId(9);

		ServerSelectQuery<DBTest2> query = ServerSelectQuery.adapt(SelectQuery.selectFrom(DBTest2.class).where(DBTest2.TEST3.eq(test3)));
		assertEquals("SELECT x0 FROM DBTest2 x0 WHERE x0.test3.id = 9", query.getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testStringEquals() {
		ServerSelectQuery<DBTest2> query = ServerSelectQuery.adapt(SelectQuery.selectFrom(DBTest2.class).where(DBTest2.TEST1.eq("aaa")));
		assertEquals("SELECT x0 FROM DBTest2 x0 WHERE x0.test1 = 'aaa'", query.getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testStringEqualsIgnoreCase() {
		ServerSelectQuery<DBTest2> query = ServerSelectQuery.adapt(SelectQuery.selectFrom(DBTest2.class).where(DBTest2.TEST1.eqIgnoreCase("aaa")));
		assertEquals("SELECT x0 FROM DBTest2 x0 WHERE LOWER(x0.test1) = LOWER('aaa')", query.getJPQL(EntityVOMapper.INSTANCE));
	}

	@Test
	public void testSelectCount() {
		CountQuery<DBTest1> query = countFrom(DBTest1.class);
		assertEquals("SELECT COUNT(x0) FROM DBTest1 x0", ServerCountQuery.adapt(query).getJPQL(EntityVOMapper.INSTANCE));
	}

}
