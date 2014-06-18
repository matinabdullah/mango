/**
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.server;

import com.google.common.collect.Iterables;
import io.pelle.mango.dsl.generator.GeneratorConstants;
import io.pelle.mango.dsl.generator.server.ServerNameUtils;
import io.pelle.mango.dsl.generator.server.service.GWTServices;
import io.pelle.mango.dsl.generator.server.service.SpringServices;
import io.pelle.mango.dsl.mango.Model;
import javax.inject.Inject;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

@SuppressWarnings("all")
public class ServerGenerator implements IGenerator {
  @Inject
  @Extension
  private GWTServices _gWTServices;
  
  @Inject
  @Extension
  private SpringServices _springServices;
  
  @Inject
  @Extension
  private ServerNameUtils _serverNameUtils;
  
  public void doGenerate(final Resource resource, final IFileSystemAccess fsa) {
    TreeIterator<EObject> _allContents = resource.getAllContents();
    Iterable<EObject> _iterable = IteratorExtensions.<EObject>toIterable(_allContents);
    Iterable<Model> _filter = Iterables.<Model>filter(_iterable, Model.class);
    for (final Model model : _filter) {
      {
        String _gwtRemoteServicesApplicationContextFullQualifiedFileName = this._serverNameUtils.gwtRemoteServicesApplicationContextFullQualifiedFileName(model);
        CharSequence _gwtRemoteServicesApplicationContext = this._gWTServices.gwtRemoteServicesApplicationContext(model);
        fsa.generateFile(_gwtRemoteServicesApplicationContextFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, _gwtRemoteServicesApplicationContext);
        String _serviceSpringNameApplicationContextFullQualifiedFileName = this._serverNameUtils.serviceSpringNameApplicationContextFullQualifiedFileName(model);
        CharSequence _springServices = this._springServices.springServices(model);
        fsa.generateFile(_serviceSpringNameApplicationContextFullQualifiedFileName, GeneratorConstants.ENTITIES_GEN_OUTPUT, _springServices);
      }
    }
  }
}
