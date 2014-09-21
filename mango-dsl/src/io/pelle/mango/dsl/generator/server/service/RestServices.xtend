/*
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.server.service

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.client.ClientNameUtils
import io.pelle.mango.dsl.generator.client.web.BaseServices
import io.pelle.mango.dsl.generator.server.ServerNameUtils
import io.pelle.mango.dsl.generator.util.AttributeUtils
import io.pelle.mango.dsl.generator.util.JvmTypeUtils
import io.pelle.mango.dsl.mango.Service
import io.pelle.mango.dsl.mango.ServiceMethod
import io.pelle.mango.dsl.mango.ValueObject
import org.eclipse.xtext.xbase.compiler.ImportManager

class RestServices extends BaseServices {

	@Inject
	extension AttributeUtils

	@Inject
	extension ServerNameUtils serverNameUtils

	@Inject
	ClientNameUtils clientNameUtils

	@Inject
	extension JvmTypeUtils

	def restServiceControllerRequetVO(Service service, ServiceMethod method) '''
	package «service.packageName»;
	
	«val importManager = new ImportManager(true)»
	«FOR i : importManager.imports»
	import «i»;
	«ENDFOR»

	public class «restControllerRequestVOName(service, method)»«method.methodTypeParameter»  {
		«FOR parameter : method.params»
			«attribute(parameter.parameterType.jvmType, parameter.name)»
			«getter(parameter.parameterType.jvmType, parameter.name)»
			«setter(parameter.parameterType.jvmType, parameter.name)»
		«ENDFOR»
	}
	'''

	def restServiceController(Service service) '''
		
		package «service.packageName»;

		«val importManager = new ImportManager(true)»
		«FOR i : importManager.imports»
		import «i»;
		«ENDFOR»
		import org.springframework.web.bind.annotation.RequestMapping;
		import org.springframework.web.bind.annotation.RequestMethod;
		import org.springframework.web.bind.annotation.RequestParam;
		import org.springframework.web.bind.annotation.RequestBody;
		import org.springframework.web.bind.annotation.PathVariable;
		import org.springframework.web.bind.annotation.ResponseBody;
		import org.springframework.transaction.annotation.Transactional;
		
		@SuppressWarnings("all")
		@org.springframework.web.bind.annotation.RestController
		@org.springframework.web.bind.annotation.RequestMapping("«service.restMapping»")
		public class «service.restControllerName»  {
		
			@org.springframework.beans.factory.annotation.Autowired
			private «clientNameUtils.serviceInterfaceFullQualifiedName(service)» «service.variableName»;
			
			public void «service.setterName»(«clientNameUtils.serviceInterfaceFullQualifiedName(service)» «service.variableName»)
			{
				this.«service.variableName» = «service.variableName»;
			}
		
			«FOR method : service.remoteMethods»
				@RequestMapping(value = "«method.restMapping»", produces="application/json", method = RequestMethod.POST, consumes = "application/json")
				@ResponseBody
				@Transactional
				«IF method.params.size == 1 && method.params.hasOnlyType(typeof(ValueObject))»
					«var valueObject = method.params.get(0).parameterType.getWrappedTypeType(typeof(ValueObject))»
					public «method.methodReturn» «method.methodName»PostRequestBody(@RequestBody «clientNameUtils.voFullQualifiedName(valueObject)» requestBody) {
						«IF !method.returnsVoid»return («method.returnType.jvmType»)«ENDIF» this.«service.variableName».«method.name.toFirstLower»(requestBody);
					}
				«ELSE»
					public «method.methodReturn» «method.methodName»PostRequestBody(«IF !method.params.isEmpty»@RequestBody «restControllerRequestVOName(service, method)» requestBody«ENDIF») {
						«IF !method.returnsVoid»return («method.returnType.jvmType»)«ENDIF» this.«service.variableName».«method.name.toFirstLower»(«FOR parameter : method.params SEPARATOR ","»requestBody.«parameter.name.getterName»()«ENDFOR»);
					}
				«ENDIF»
			«ENDFOR»
		}
	'''

	def methodReturn(Service service, ServiceMethod method) '''
		«IF !method.returnsVoid»return«ENDIF» this.«service.variableName».«method.name.toFirstLower»(«FOR parameter : method.params SEPARATOR ","»«parameter.name»«ENDFOR»);
	'''

}
