package io.pelle.mango.dsl.generator.client.web

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.util.NameUtils
import io.pelle.mango.dsl.mango.ServiceMethod
import java.util.List
import org.eclipse.xtext.common.types.JvmFormalParameter

class BaseServices {

	@Inject
	extension NameUtils;

	def methodParameters(List<JvmFormalParameter> parameters) '''
		«FOR parameter : parameters SEPARATOR ", "»
			«parameter.name» «parameter.parameterType.simpleName»
		«ENDFOR»
	'''

	def serviceMethod(ServiceMethod serviceMethod) '''
		«serviceMethod.returnType.type.simpleName»  «serviceMethod.methodName»(«serviceMethod.params.methodParameters»);
	'''

}
