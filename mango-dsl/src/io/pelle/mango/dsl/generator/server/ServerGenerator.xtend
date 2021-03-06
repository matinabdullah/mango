/*
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.server

import io.pelle.mango.dsl.generator.GeneratorConstants
import io.pelle.mango.dsl.generator.server.service.GWTSpringServices
import io.pelle.mango.dsl.generator.server.service.RestServices
import io.pelle.mango.dsl.generator.server.service.SpringServices
import io.pelle.mango.dsl.generator.xml.XmlNameUtils
import io.pelle.mango.dsl.generator.xml.XmlVOMapper
import io.pelle.mango.dsl.mango.Entity
import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.mango.Service
import javax.inject.Inject
import org.apache.commons.logging.Log
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import static org.apache.commons.logging.LogFactory.*

class ServerGenerator implements IGenerator {

	val Log LOG = getLog(getClass().getName())

	@Inject 
	extension EntityGenerator
	
	@Inject
	extension GWTSpringServices

	@Inject
	extension XmlVOMapper
	
	@Inject
	extension XmlNameUtils
	
	@Inject
	extension SpringServices

	@Inject
	extension RestServices

	@Inject
	extension ServerNameUtils

	override void doGenerate(Resource resource, IFileSystemAccess fsa) {
		
		for (model : resource.allContents.toIterable.filter(Model)) {
			fsa.generateFile(model.gwtAsyncAdapterRemoteServiceLocatorFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, model.gwtAsyncAdapterRemoteServiceLocator)
			fsa.generateFile(model.remoteServiceLocatorFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, model.remoteServiceLocator)
			fsa.generateFile(model.remoteServiceLocatorInterfaceFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, model.remoteServiceLocatorInterface)
			fsa.generateFile(model.gwtRemoteServicesApplicationContextFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, model.gwtRemoteServicesApplicationContext)
			fsa.generateFile(model.restRemoteServicesApplicationContextFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, model.restRemoteServicesApplicationContext)
			fsa.generateFile(model.serviceSpringNameApplicationContextFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, model.springServices)
			fsa.generateFile(model.xmlVOMapperFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, model.xmlVOMapper)
		}

		for (entity : resource.allContents.toIterable.filter(Entity)) {
			fsa.generateFile(entity.entityFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, entity.compileEntity)
			fsa.generateFile(entity.entityDAOFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, entity.compileEntityDAO)
			fsa.generateFile(entity.voDAOFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, entity.compileVODAO)
			fsa.generateFile(entity.entityDAOInterfaceFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, entity.compileEntityDAOInterface)
			fsa.generateFile(entity.voDAOInterfaceFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, entity.compileVODAOInterface)
		}

		for (service : resource.allContents.toIterable.filter(Service)) {
			fsa.generateFile(service.restControllerFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, service.restServiceController)
			fsa.generateFile(service.gwtAsyncAdapterFullQualifiedFileName, GeneratorConstants.SERVER_GEN_OUTPUT, service.gwtRemoteServiceAsyncAdapter)
			
			for (method : service.remoteMethods) {
				fsa.generateFile(restControllerRequestVOFullQualifiedFileName(service, method), GeneratorConstants.SERVER_GEN_OUTPUT, restServiceControllerRequetVO(service, method))
			}
		}
		
	}

}
