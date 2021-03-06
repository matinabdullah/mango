package io.pelle.mango.demo.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IIntegerControlModel;
import io.pelle.mango.demo.client.MangoDemoClientConfiguration;
import io.pelle.mango.demo.client.MangoDemoDictionaryModel;
import io.pelle.mango.demo.client.test.ENUMERATION1;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class DictionaryModelGeneratorTest {

	@Test
	public void testDictionaryName() {
		assertEquals("TestDictionary1", MangoDemoDictionaryModel.TEST_DICTIONARY1.getName());
	}

	@BeforeClass
	public static void init() {
		MangoDemoClientConfiguration.registerAll();
	}

	@Test
	public void testLayoutColumnsFilter() {
		assertEquals(2, MangoDemoDictionaryModel.COUNTRY.COUNTRY_SEARCH.COUNTRY_FILTER.getCompositeModel().getLayout().getColumns());
	}

	@Test
	public void testDictionary1Label() {
		assertEquals(null, MangoDemoDictionaryModel.DEMO_DICTIONARY1.getLabel());
	}

	@Test
	public void testDictionary2OveriddenLabel() {
		assertEquals("Entity2 Label", MangoDemoDictionaryModel.DEMO_DICTIONARY2.getLabel());
	}

	@Test
	public void testDictionary2OveriddenPluralLabel() {
		assertEquals("Entity2 Labels", MangoDemoDictionaryModel.DEMO_DICTIONARY2.getPluralLabel());
	}

	@Test
	public void testLayoutColumnsEditor() {
		assertEquals(2, MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.getCompositeModel().getLayout().getColumns());
	}

	@Test
	public void testMaxLengthInheritanceFromDatatype() {
		assertEquals(42, MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1.getMaxLength());
	}

	@Test
	public void testCountryCountryName() {
		assertEquals(32, MangoDemoDictionaryModel.COUNTRY.COUNTRY_EDITOR.COUNTRY_NAME.getWidth());
	}

	@Test
	public void testStringDatatype1GetAttributePath() {
		assertEquals("stringDatatype1", MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1.getAttributePath());
	}

	@Test
	public void testNaturalKeyIsMandatory() {
		assertTrue(MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL1.isMandatory());
	}

	@Test
	public void testLabelInheritanceFromDatatype() {
		assertEquals("Boolean1", MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.BOOLEAN_CONTROL1.getLabel());
	}

	@Test
	public void testControlGroup() {
		assertEquals("Textcontrol1", MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_SEARCH1.DICTIONARY_FILTER1.CONTROL_GROUP1.TEXTCONTROL1.getName());
	}

	@Test
	public void testWidthFromStringDatatype3() {
		assertEquals(31, MangoDemoDictionaryModel.TEST_DICTIONARY1.DICTIONARY_EDITOR1.TEXTCONTROL3.getWidth());
	}

	@Test
	public void testEnumerationValueConverterGetEnumerationValue() {
		assertEquals(null, DictionaryModelProvider.getEnumerationValue(ENUMERATION1.class.getName(), null));
		assertEquals(ENUMERATION1.ENUMERATIONVALUE1, DictionaryModelProvider.getEnumerationValue(ENUMERATION1.class.getName(), "ENUMERATIONVALUE1"));

	}

	@Test
	public void testDictionary1IntegerControl1DefaultControlInputType() {
		assertEquals(IIntegerControlModel.CONTROL_TYPE.TEXTCONTROL, MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.INTEGER_CONTROL1.getControlType());
	}

	@Test
	public void testDictionary1IntegerControl1RatingControlInputType() {
		assertEquals(IIntegerControlModel.CONTROL_TYPE.RATING, MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.INTEGER_CONTROL2.getControlType());
	}

	@Test
	public void testDictionary1TextControl4DefaultWidth() {
		assertEquals(16, MangoDemoDictionaryModel.DEMO_DICTIONARY1.DEMO_EDITOR1.TEXT_CONTROL4.getWidth());
	}

	@Test
	public void testEnumerationConverterGetEnumerationValues() {
		Map<String, String> enumerationValues = DictionaryModelProvider.getEnumerationValues(ENUMERATION1.class.getName());
		assertEquals(2, enumerationValues.size());

		assertEquals("ENUMERATIONVALUE1", enumerationValues.get("ENUMERATIONVALUE1"));
		assertEquals("Value2", enumerationValues.get("ENUMERATIONVALUE2"));
	}
}
