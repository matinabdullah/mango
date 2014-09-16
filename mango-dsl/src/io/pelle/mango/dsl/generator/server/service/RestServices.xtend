/*
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.server.service

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.client.ClientNameUtils
import io.pelle.mango.dsl.generator.client.ClientTypeUtils
import io.pelle.mango.dsl.generator.client.web.BaseServices
import io.pelle.mango.dsl.generator.server.ServerNameUtils
import io.pelle.mango.dsl.generator.util.AttributeUtils
import io.pelle.mango.dsl.generator.util.ServiceUtils
import io.pelle.mango.dsl.mango.Service
import io.pelle.mango.dsl.mango.ServiceMethod

class RestServices {

	@Inject
	extension AttributeUtils

	@Inject
	extension ServerNameUtils server

	@Inject
	extension ServiceUtils

	@Inject
	extension BaseServices

	@Inject
	extension ClientTypeUtils

	ClientNameUtils clientNameUtils = new ClientNameUtils

	def restServiceControllerRequetVO(Service service, ServiceMethod method) '''
	package «service.packageName»;
	
	public class «restControllerRequestVOName(service, method)»  {
		«FOR parameter : method.params»
			«attribute(parameter.parameterType.qualifiedName, parameter.name)»
			«getter(parameter.parameterType.qualifiedName, parameter.name)»
			«setter(parameter.parameterType.qualifiedName, parameter.name)»
		«ENDFOR»
	}
	'''

	def restServiceController(Service service) '''
		
		package «service.packageName»;
		
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
				«IF method.params.size == 1 && !method.params.onlySimpleTypes»
					@RequestMapping(value = "«method.restMapping»", method = RequestMethod.POST)
					@Transactional
					public «method.returnType.qualifiedName» «method.methodName»(@RequestBody «method.params.methodParameters») {
						«service.methodReturn(method)»
					}
				«ELSEIF method.params.onlySimpleTypes»
					@RequestMapping(value = "«method.restMapping»/«FOR parameter : method.params SEPARATOR "/"»{«parameter.name.toFirstLower»}«ENDFOR»", produces="application/json", method = RequestMethod.GET)
					@ResponseBody
					@Transactional
					public «method.returnType.qualifiedName» «method.methodName»Get(«FOR parameter : method.params SEPARATOR ", "»@PathVariable «parameter.parameterType.simpleName» «parameter.name.toFirstLower»«ENDFOR») {
						«service.methodReturn(method)»
					}

					@RequestMapping(value = "«method.restMapping»", produces="application/json", method = RequestMethod.POST)
					@ResponseBody
					@Transactional
					public «method.returnType.qualifiedName» «method.methodName»Post(«FOR parameter : method.params SEPARATOR ", "»@RequestParam «parameter.parameterType.simpleName» «parameter.name.toFirstLower»«ENDFOR») {
						«service.methodReturn(method)»
					}
					
					@RequestMapping(value = "«method.restMapping»", produces="application/json", method = RequestMethod.POST, consumes = "application/json")
					@ResponseBody
					@Transactional
					public «method.returnType.qualifiedName» «method.methodName»PostRequestBody(@RequestBody «restControllerRequestVOName(service, method)» requestBody) {
						«IF method.hasReturn»return«ENDIF» this.«service.variableName».«method.name.toFirstLower»(«FOR parameter : method.params SEPARATOR ","»requestBody.get«parameter.name»()«ENDFOR»);
					}
				«ENDIF»
			«ENDFOR»
		}
	'''

	def methodReturn(Service service, ServiceMethod method) '''
		«IF method.hasReturn»return«ENDIF» this.«service.variableName».«method.name.toFirstLower»(«FOR parameter : method.params SEPARATOR ","»«parameter.name»«ENDFOR»);
	'''

}
