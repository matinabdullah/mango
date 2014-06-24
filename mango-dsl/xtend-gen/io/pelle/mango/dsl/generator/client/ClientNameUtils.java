package io.pelle.mango.dsl.generator.client;

import io.pelle.mango.dsl.ModelUtil;
import io.pelle.mango.dsl.generator.GeneratorConstants;
import io.pelle.mango.dsl.generator.util.NameUtils;
import io.pelle.mango.dsl.mango.Dictionary;
import io.pelle.mango.dsl.mango.DictionaryEditor;
import io.pelle.mango.dsl.mango.DictionarySearch;
import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.Enumeration;
import io.pelle.mango.dsl.mango.Model;
import io.pelle.mango.dsl.mango.PackageDeclaration;
import java.util.Arrays;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class ClientNameUtils extends NameUtils {
  protected String _getPackageName(final PackageDeclaration packageDeclaration) {
    String _xifexpression = null;
    EObject _eContainer = packageDeclaration.eContainer();
    if ((_eContainer instanceof Model)) {
      String _name = packageDeclaration.getName();
      String _packageName = this.getPackageName(_name);
      String _plus = (_packageName + ".");
      _xifexpression = (_plus + GeneratorConstants.CLIENT_PACKAGE_POSTFIX);
    } else {
      EObject _eContainer_1 = packageDeclaration.eContainer();
      String _packageName_1 = this.getPackageName(_eContainer_1);
      String _name_1 = packageDeclaration.getName();
      String _packageName_2 = this.getPackageName(_name_1);
      _xifexpression = this.combinePackageName(_packageName_1, _packageName_2);
    }
    return _xifexpression;
  }
  
  public String modelPackageName(final Model model) {
    PackageDeclaration _singleRootPackage = ModelUtil.getSingleRootPackage(model);
    String _name = _singleRootPackage.getName();
    String _packageName = this.getPackageName(_name);
    String _plus = (_packageName + ".");
    return (_plus + GeneratorConstants.CLIENT_PACKAGE_POSTFIX);
  }
  
  public String voName(final Entity entity) {
    String _name = entity.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    return (_firstUpper + "VO");
  }
  
  public String voFullQualifiedName(final Entity entity) {
    String _packageName = this.getPackageName(entity);
    String _plus = (_packageName + ".");
    String _voName = this.voName(entity);
    return (_plus + _voName);
  }
  
  public String voFullQualifiedFileName(final Entity entity) {
    String _voFullQualifiedName = this.voFullQualifiedName(entity);
    String _replaceAll = _voFullQualifiedName.replaceAll("\\.", "/");
    return (_replaceAll + ".java");
  }
  
  public String enumerationName(final Enumeration enumeration) {
    String _name = enumeration.getName();
    return _name.toUpperCase();
  }
  
  public String enumerationFullQualifiedName(final Enumeration enumeration) {
    String _packageName = this.getPackageName(enumeration);
    String _plus = (_packageName + ".");
    String _enumerationName = this.enumerationName(enumeration);
    return (_plus + _enumerationName);
  }
  
  public String enumerationFullQualifiedFileName(final Enumeration enumeration) {
    String _enumerationFullQualifiedName = this.enumerationFullQualifiedName(enumeration);
    String _replaceAll = _enumerationFullQualifiedName.replaceAll("\\.", "/");
    return (_replaceAll + ".java");
  }
  
  public String getPackageName(final Object packageDeclaration) {
    if (packageDeclaration instanceof Dictionary) {
      return _getPackageName((Dictionary)packageDeclaration);
    } else if (packageDeclaration instanceof PackageDeclaration) {
      return _getPackageName((PackageDeclaration)packageDeclaration);
    } else if (packageDeclaration instanceof DictionaryEditor) {
      return _getPackageName((DictionaryEditor)packageDeclaration);
    } else if (packageDeclaration instanceof DictionarySearch) {
      return _getPackageName((DictionarySearch)packageDeclaration);
    } else if (packageDeclaration instanceof String) {
      return _getPackageName((String)packageDeclaration);
    } else if (packageDeclaration instanceof EObject) {
      return _getPackageName((EObject)packageDeclaration);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(packageDeclaration).toString());
    }
  }
}
