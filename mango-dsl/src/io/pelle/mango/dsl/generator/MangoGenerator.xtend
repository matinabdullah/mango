/*
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.client.ClientGenerator
import io.pelle.mango.dsl.generator.server.ServerGenerator
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.Model
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator

/**
 * Generates code from your model files on save.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#TutorialCodeGeneration
 */
class MangoGenerator implements IGenerator {

	@Inject 
	extension EntityGenerator

	@Inject 
	extension VOMapperGenerator

	@Inject 
	extension NameUtils
	
	@Inject 
	extension ClientGenerator clientGenerator

	@Inject 
	extension ServerGenerator serverGenerator

	@Inject 
	extension SpringGenerator

	override void doGenerate(Resource resource, IFileSystemAccess fsa) {

		clientGenerator.doGenerate(resource, fsa);
		serverGenerator.doGenerate(resource, fsa);

		for (entity : resource.allContents.toIterable.filter(Entity)) {
			fsa.generateFile(entity.entityFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, entity.compileEntity)
		}

		for (model : resource.allContents.toIterable.filter(Model)) {
			fsa.generateFile(model.springDBApplicationContextFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, model.compileSpringDBApplicationContext)
			fsa.generateFile(model.springPersistenceXMLFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, model.compilePersistenceXml)
			fsa.generateFile(model.baseApplicationContextFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, model.compileBaseApplicationContext)
			fsa.generateFile(model.voMapperFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, model.compileVOMapper)
		}
		
	}

}
