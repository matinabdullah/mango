/*
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.client.web

import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider
import io.pelle.mango.dsl.generator.GeneratorConstants
import io.pelle.mango.dsl.generator.client.dictionary.DictionaryNameUtils
import io.pelle.mango.dsl.mango.Dictionary
import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.mango.NavigationNode
import javax.inject.Inject

/**
 * Generates code from your model files on save.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#TutorialCodeGeneration
 */
class GWTClient extends BaseServices {

	@Inject 
	extension DictionaryNameUtils

	def gwtClientModule(Model model) '''
		<?xml version="1.0" encoding="UTF-8"?>
		<!DOCTYPE module PUBLIC "-//Google Inc.//DTD Google Web Toolkit 1.6.4//EN" "http://google-web-toolkit.googlecode.com/svn/tags/1.6.4/distro-source/core/src/gwt-module.dtd">
		<module>
		<inherits name="com.google.gwt.user.User" />
		
			<source path="«GeneratorConstants.CLIENT_PACKAGE_POSTFIX»" />
		
		
		</module>
	'''

	def gwtClientConfiguration(Model model) '''
	
		package «model.modelPackageName»;
		
			public class «model.gwtClientconfigurationName()» {
		
			private «model.gwtClientconfigurationName()»() {
			}
		
			public static void registerAll()
			{
				registerDictionaries();
				registerEnumerationValueParser();
				
				«IF !model.eAllContents.filter(NavigationNode).isEmpty»
					registerNavigation();
				«ENDIF»
			}

			public static void registerEnumerationValueParser()
			{
				«DictionaryModelProvider.name».registerEnumerationConverter(new «model.enumerationValueParserFullQualifiedName»());
			}


			public static void registerDictionaries()
			{
				«FOR dictionary : model.eAllContents().toIterable.filter(Dictionary)»
					«DictionaryModelProvider.name».registerDictionary(«model.dictionaryClassFullQualifiedName».«dictionary.dictionaryConstantName»);
				«ENDFOR»
			}
	
			«IF !model.eAllContents.filter(NavigationNode).isEmpty»
			public static void registerNavigation()
			{
				«io.pelle.mango.client.base.modules.navigation.NavigationTreeProvider.name».addRootNavigationElement(«model.navigationNodeClassName».ROOT);
			}
			«ENDIF»
	}
'''

}