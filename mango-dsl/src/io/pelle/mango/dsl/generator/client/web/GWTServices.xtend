/*
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.client.web

import com.google.gwt.user.client.rpc.RemoteService
import com.google.gwt.user.client.rpc.ServiceDefTarget
import com.google.inject.Inject
import io.pelle.mango.dsl.generator.client.ClientNameUtils
import io.pelle.mango.dsl.generator.client.ClientTypeUtils
import io.pelle.mango.dsl.mango.Model
import io.pelle.mango.dsl.mango.Service
import io.pelle.mango.dsl.mango.ServiceMethod

/**
 * Generates code from your model files on save.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#TutorialCodeGeneration
 */
class GWTServices extends BaseServices {

	@Inject 
	extension ClientNameUtils

	@Inject 
	extension ClientTypeUtils

	def gwtRemoteServiceLocator(Model model) '''
package «model.modelPackageName»;

public class «model.gwtRemoteServiceLocatorName» implements «model.gwtRemoteServiceLocatorInterfaceName» {

    private static final «model.gwtRemoteServiceLocatorName» instance = new «model.gwtRemoteServiceLocatorName»();

    private «model.gwtRemoteServiceLocatorName»() {
    }

    public static «model.gwtRemoteServiceLocatorName» getInstance() {
        return instance;
    }
    
   	private String remoteBaseUrl = null;
	
	public void setRemoteBaseUrl(String remoteBaseUrl) {
		this.remoteBaseUrl = remoteBaseUrl;
	}
    
    public String getModuleBaseUrl()
    {
	    if (remoteBaseUrl != null) {
			return remoteBaseUrl;
	    }
	    else {
	    	return com.google.gwt.core.client.GWT.getModuleBaseURL() + "../remote/rpc"; 
	    }
    } 

	«FOR service : model.eAllContents.toIterable.filter(Service)» 
	public «service.gwtAsyncServiceInterfaceFullQualifiedName» get«service.serviceName»() {
		final «service.gwtAsyncServiceInterfaceFullQualifiedName» service = («service.gwtAsyncServiceInterfaceFullQualifiedName») com.google.gwt.core.client.GWT.create(«service.gwtServiceInterfaceFullQualifiedName».class);
		«ServiceDefTarget.name» formEndpoint = («ServiceDefTarget.name») service;
		formEndpoint.setServiceEntryPoint(getModuleBaseUrl() + "/«service.serviceSpringName»");
		return service;
	}
	«ENDFOR»
}
'''

	def gwtRemoteServiceLocatorInterface(Model model) '''
package «model.modelPackageName»;

public interface «model.gwtRemoteServiceLocatorInterfaceName» {

	«FOR service : model.eAllContents.toIterable.filter(Service)» 
	«service.gwtAsyncServiceInterfaceFullQualifiedName» get«service.serviceName»();
	«ENDFOR»
}
'''

	def gwtServiceInterface(Service service) '''
		package «service.packageName»;
		
		public interface «service.gwtServiceInterfaceName» extends «RemoteService.name», «service.serviceInterfaceFullQualifiedName» {
		}
	'''

	def gwtAsyncServiceInterface(Service service) '''
		package «service.packageName»;
		
		public interface «service.gwtAsyncServiceInterfaceName» {
		
			«FOR remoteMethod : service.remoteMethods»
				«remoteMethod.serviceMethodAsync»;
			«ENDFOR»
		}
	'''

	def serviceMethodAsync(ServiceMethod serviceMethod) '''
		«serviceMethod.genericTypeDefinition.genericTypeDefinition»
		
		«IF serviceMethod.methodParameters.size == 0»
		void «serviceMethod.name.toFirstLower()»(«serviceMethod.asyncCallback»)
		«ELSE»
		void «serviceMethod.name.toFirstLower()»(«serviceMethod.methodParameters.methodParameters», «serviceMethod.asyncCallback»)
		«ENDIF»
	'''

	def asyncCallback(ServiceMethod serviceMethod) '''
		«IF serviceMethod.returnType != null»
		com.google.gwt.user.client.rpc.AsyncCallback<«serviceMethod.returnType.type»> callback
		«ELSE»
		 com.google.gwt.user.client.rpc.AsyncCallback<Void> callback
		«ENDIF»
	'''

	def gwtRemoteServiceAsyncAdapter(Service service) '''

	package «service.packageName»;
	
	public class «service.gwtAsyncAdapterName» implements «service.gwtAsyncAdapterFullQualifiedName» {

		private «service.serviceInterfaceName» «service.name.toFirstLower()»;
		
		public «service.gwtAsyncAdapterName»(«service.serviceInterfaceName» «service.name.toFirstLower()»)
		{
			this.«service.name.toFirstLower()» = «service.name.toFirstLower()»;
		}
	
		«FOR serviceMethod : service.remoteMethods»
			public «serviceMethod.serviceMethodAsync»
			{
				try
				{
				«IF serviceMethod.returnType != null»
					callback.onSuccess(this.«service.name.toFirstLower()».«serviceMethod.name.toFirstLower()»(«FOR methodParameter : serviceMethod.methodParameters SEPARATOR  ", "»«methodParameter.name.toFirstLower()»«ENDFOR»));
				«ELSE»
					this.«service.name.toFirstLower()».«serviceMethod.name.toFirstLower()»(«FOR methodParameter : serviceMethod.methodParameters SEPARATOR  ", "»«methodParameter.name.toFirstLower()»«ENDFOR»);
					callback.onSuccess(null);
				«ENDIF»
				}
				catch (Exception e)
				{
					callback.onFailure(e);
				}
			
			}
		«ENDFOR»
	
	}
	'''
	
}