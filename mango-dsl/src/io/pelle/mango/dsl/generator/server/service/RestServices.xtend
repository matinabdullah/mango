/*
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.server.service

import com.google.inject.Inject
import io.pelle.mango.dsl.generator.client.ClientNameUtils
import io.pelle.mango.dsl.generator.client.ClientTypeUtils
import io.pelle.mango.dsl.generator.client.web.BaseServices
import io.pelle.mango.dsl.generator.server.ServerNameUtils
import io.pelle.mango.dsl.generator.util.ServiceUtils
import io.pelle.mango.dsl.mango.Service
import io.pelle.mango.dsl.mango.ServiceMethod

class RestServices {

	@Inject
	extension ServerNameUtils server

	@Inject
	extension ServiceUtils

	@Inject
	extension BaseServices

	@Inject
	extension ClientTypeUtils

	ClientNameUtils clientNameUtils = new ClientNameUtils

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
			
			«FOR serviceMethod : service.remoteMethods»
				«IF serviceMethod.methodParameters.size == 1 && !serviceMethod.methodParameters.onlySimpleTypes»
					@RequestMapping(value = "«serviceMethod.restMapping»", method = RequestMethod.POST)
					@Transactional
					public «serviceMethod.genericTypeDefinition.genericTypeDefinition» «serviceMethod.serviceMethodReturnType» «serviceMethod.name.toFirstLower»(@RequestBody «serviceMethod.methodParameters.methodParameters») {
						«service.methodReturn(serviceMethod)»
					}
				«ELSEIF serviceMethod.methodParameters.onlySimpleTypes»
					@RequestMapping(value = "«serviceMethod.restMapping»/«FOR parameter : serviceMethod.methodParameters SEPARATOR "/"»{«parameter.name.toFirstLower»}«ENDFOR»", produces="application/json", method = RequestMethod.GET)
					@ResponseBody
					@Transactional
					public «serviceMethod.genericTypeDefinition.genericTypeDefinition» «serviceMethod.serviceMethodReturnType» «serviceMethod.name.toFirstLower»Get(«FOR parameter : serviceMethod.methodParameters SEPARATOR ", "»@PathVariable «parameter.type» «parameter.name.toFirstLower»«ENDFOR») {
						«service.methodReturn(serviceMethod)»
					}

					@RequestMapping(value = "«serviceMethod.restMapping»", produces="application/json", method = RequestMethod.POST)
					@ResponseBody
					@Transactional
					public «serviceMethod.genericTypeDefinition.genericTypeDefinition» «serviceMethod.serviceMethodReturnType» «serviceMethod.name.toFirstLower»Post(«FOR parameter : serviceMethod.methodParameters SEPARATOR ", "»@RequestParam «parameter.type» «parameter.name.toFirstLower»«ENDFOR») {
						«service.methodReturn(serviceMethod)»
					}

				«ENDIF»
			«ENDFOR»
		}
	'''

	def methodReturn(Service service, ServiceMethod serviceMethod) '''
		«IF serviceMethod.hasReturn»return«ENDIF» this.«service.variableName».«serviceMethod.name.toFirstLower»(«FOR parameter : serviceMethod.methodParameters SEPARATOR ","»«parameter.name.toFirstLower»«ENDFOR»);
	'''

/*    @RequestMapping(value = "checkUserNameExists/{userName}")
    @Transactional(readOnly = true)
    public boolean checkUserNameExists(@PathVariable String userName) {
        return userService.checkUserNameExists(userName);
    }

    @RequestMapping(value = "registerUser", method = RequestMethod.POST)
    @Transactional()
    public UserRegisterResultVO registerUser(@RequestBody UserRegisterVO userRegisterVO, HttpServletResponse response) {
        return userService.registerUser(userRegisterVO, Optional.of(response));
    }

    @RequestMapping(value = "filter", method = RequestMethod.POST)
    @Transactional()
    public FilterPagingResultVO filter(@RequestBody FilterPagingRequestVO filterPagingRequestVO) {
        return userDAO.filter(filterPagingRequestVO.getQueryString(), filterPagingRequestVO.getFirstResult(), filterPagingRequestVO.getMaxResults());
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseLoginResultVO login(@RequestBody UserLoginVO userLoginVO, HttpServletResponse response) {
        return userService.login(userLoginVO.getUserName(), userLoginVO.getUserPassword(), Optional.of(response));
    }

    @RequestMapping(value = "updateProfileField", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional()
    public String updateProfileField(@RequestBody ProfileFieldUpdateVO profileFieldUpdateVO) {
        Optional<BaseUser> baseUser = getCurrentUser();

        if (baseUser.isPresent())
        {
            return updateProfileField(baseUser.get().getName(), profileFieldUpdateVO);
        }
        else
        {
            throw new RuntimeException("no user found");
        }
    }

    @RequestMapping(value = "deleteAll", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional()
    public void deleteAll() {
        // TODO refactor userdao to remove dependent carts
        cartDAO.deleteAll();
        userDAO.deleteAll();
    }


    } */
}
