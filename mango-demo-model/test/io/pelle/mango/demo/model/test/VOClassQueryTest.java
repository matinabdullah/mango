package io.pelle.mango.demo.model.test;

import static org.junit.Assert.assertEquals;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.StringAttributeDescriptor;
import io.pelle.mango.db.voquery.AttributesDescriptorQuery;
import io.pelle.mango.db.voquery.VOClassQuery;
import io.pelle.mango.demo.client.test.Entity1VO;

import org.junit.Test;

public class VOClassQueryTest {

	@Test
	public void testAttributesDescriptorQuery() {

		AttributesDescriptorQuery<?> attributesDescriptorQuery = VOClassQuery.createQuery(Entity1VO.class).attributesDescriptors();
		assertEquals(21, attributesDescriptorQuery.getCount());
	}

	@Test
	public void testAttributesDescriptorQueryByType() {

		AttributesDescriptorQuery<StringAttributeDescriptor> attributesDescriptorQuery = VOClassQuery.createQuery(Entity1VO.class).attributesDescriptors().byType(StringAttributeDescriptor.class);

		assertEquals(5, attributesDescriptorQuery.getCount());
		assertEquals(String.class, attributesDescriptorQuery.iterator().next().getAttributeType());
		assertEquals(String.class, attributesDescriptorQuery.iterator().next().getAttributeType());
	}

	@Test
	public void testAttributesDescriptorQueryByName() {

		AttributesDescriptorQuery<StringAttributeDescriptor> attributesDescriptorQuery = VOClassQuery.createQuery(Entity1VO.class).attributesDescriptors().byName("stringDatatype1");
		assertEquals("stringDatatype1", attributesDescriptorQuery.getSingleResult().getAttributeName());

		attributesDescriptorQuery = VOClassQuery.createQuery(Entity1VO.class).attributesDescriptors().byName("stringDATatype1");
		assertEquals("stringDatatype1", attributesDescriptorQuery.getSingleResult().getAttributeName());

	}

	@Test
	public void testAttributesDescriptorQueryNaturalKeys() {

		AttributesDescriptorQuery<?> attributesDescriptorQuery = VOClassQuery.createQuery(Entity1VO.class).attributesDescriptors().naturalKeys();

		assertEquals(1, attributesDescriptorQuery.getCount());

		IAttributeDescriptor<?> attributeDescriptor = attributesDescriptorQuery.iterator().next();
		assertEquals(String.class, attributeDescriptor.getAttributeType());
		assertEquals("stringDatatype1", attributeDescriptor.getAttributeName());
	}

}
