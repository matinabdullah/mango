/**
 * generated by Xtext
 */
package io.pelle.mango.dsl.generator.client.dictionary;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import io.pelle.mango.client.base.modules.dictionary.model.BaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.EditableTableModel;
import io.pelle.mango.dsl.ModelUtil;
import io.pelle.mango.dsl.generator.GeneratorConstants;
import io.pelle.mango.dsl.generator.client.dictionary.DictionaryControls;
import io.pelle.mango.dsl.generator.client.dictionary.DictionaryNameUtils;
import io.pelle.mango.dsl.mango.DictionaryContainer;
import io.pelle.mango.dsl.mango.DictionaryContainerContent;
import io.pelle.mango.dsl.mango.DictionaryControl;
import io.pelle.mango.dsl.mango.DictionaryEditableTable;
import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.EntityAttribute;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class DictionaryContainerGenerator {
  @Inject
  @Extension
  private DictionaryNameUtils _dictionaryNameUtils;
  
  @Inject
  @Extension
  private DictionaryControls _dictionaryControls;
  
  public CharSequence dictionaryConstant(final DictionaryContainer dictionaryContainer) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public ");
    String _dictionaryClassFullQualifiedName = this._dictionaryNameUtils.dictionaryClassFullQualifiedName(dictionaryContainer);
    _builder.append(_dictionaryClassFullQualifiedName, "");
    _builder.append(" ");
    String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(dictionaryContainer);
    _builder.append(_dictionaryConstantName, "");
    _builder.append(" = new ");
    String _dictionaryClassFullQualifiedName_1 = this._dictionaryNameUtils.dictionaryClassFullQualifiedName(dictionaryContainer);
    _builder.append(_dictionaryClassFullQualifiedName_1, "");
    _builder.append("(this);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence dictionaryClass(final List<DictionaryContainerContent> dictionaryContainerContents) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Iterable<DictionaryContainer> _filter = Iterables.<DictionaryContainer>filter(dictionaryContainerContents, DictionaryContainer.class);
      for(final DictionaryContainer dictionaryContainer : _filter) {
        _builder.append("\t");
        CharSequence _dictionaryConstant = this.dictionaryConstant(dictionaryContainer);
        _builder.append(_dictionaryConstant, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      Iterable<DictionaryControl> _filter_1 = Iterables.<DictionaryControl>filter(dictionaryContainerContents, DictionaryControl.class);
      for(final DictionaryControl dictionaryControl : _filter_1) {
        _builder.append("\t");
        CharSequence _dictionaryConstant_1 = this._dictionaryControls.dictionaryConstant(dictionaryControl);
        _builder.append(_dictionaryConstant_1, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence dictionaryContainerContentsConstructor(final List<DictionaryContainerContent> dictionaryContainerContents) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Iterable<DictionaryContainer> _filter = Iterables.<DictionaryContainer>filter(dictionaryContainerContents, DictionaryContainer.class);
      for(final DictionaryContainer dictionaryContainer : _filter) {
        _builder.append("this.getChildren().add(");
        String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(dictionaryContainer);
        _builder.append(_dictionaryConstantName, "");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      Iterable<DictionaryControl> _filter_1 = Iterables.<DictionaryControl>filter(dictionaryContainerContents, DictionaryControl.class);
      for(final DictionaryControl dictionaryControl : _filter_1) {
        _builder.append("this.getControls().add(");
        String _dictionaryConstantName_1 = this._dictionaryNameUtils.dictionaryConstantName(dictionaryControl);
        _builder.append(_dictionaryConstantName_1, "");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      Iterable<DictionaryControl> _filter_2 = Iterables.<DictionaryControl>filter(dictionaryContainerContents, DictionaryControl.class);
      for(final DictionaryControl dictionaryControl_1 : _filter_2) {
        Object _dictionaryControlConstantSetters = this._dictionaryControls.dictionaryControlConstantSetters(dictionaryControl_1);
        _builder.append(_dictionaryControlConstantSetters, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence dictionaryContainerContentsConstants(final List<DictionaryContainerContent> dictionaryContainerContents) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Iterable<DictionaryContainer> _filter = Iterables.<DictionaryContainer>filter(dictionaryContainerContents, DictionaryContainer.class);
      for(final DictionaryContainer dictionaryContainer : _filter) {
        _builder.append("public ");
        String _dictionaryClassFullQualifiedName = this._dictionaryNameUtils.dictionaryClassFullQualifiedName(dictionaryContainer);
        _builder.append(_dictionaryClassFullQualifiedName, "");
        _builder.append(" ");
        String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(dictionaryContainer);
        _builder.append(_dictionaryConstantName, "");
        _builder.append(" = rootComposite.");
        String _dictionaryConstantName_1 = this._dictionaryNameUtils.dictionaryConstantName(dictionaryContainer);
        _builder.append(_dictionaryConstantName_1, "");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      Iterable<DictionaryControl> _filter_1 = Iterables.<DictionaryControl>filter(dictionaryContainerContents, DictionaryControl.class);
      for(final DictionaryControl dictionaryControl : _filter_1) {
        _builder.append("public ");
        CharSequence _dictionaryControlType = this._dictionaryControls.dictionaryControlType(dictionaryControl);
        _builder.append(_dictionaryControlType, "");
        _builder.append(" ");
        String _dictionaryConstantName_2 = this._dictionaryNameUtils.dictionaryConstantName(dictionaryControl);
        _builder.append(_dictionaryConstantName_2, "");
        _builder.append(" = rootComposite.");
        String _dictionaryConstantName_3 = this._dictionaryNameUtils.dictionaryConstantName(dictionaryControl);
        _builder.append(_dictionaryConstantName_3, "");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public void dictionaryGenerator(final DictionaryContainer dictionaryContainer, final IFileSystemAccess fsa) {
    EList<DictionaryContainerContent> _containercontents = dictionaryContainer.getContainercontents();
    this.dictionaryGenerator(_containercontents, fsa);
    String _dictionaryClassFullQualifiedFileName = this._dictionaryNameUtils.dictionaryClassFullQualifiedFileName(dictionaryContainer);
    CharSequence _dictionaryClass = this.dictionaryClass(dictionaryContainer);
    fsa.generateFile(_dictionaryClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _dictionaryClass);
  }
  
  public CharSequence dictionaryClass(final DictionaryContainer dictionaryContainer) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.append("package ");
    String _packageName = this._dictionaryNameUtils.getPackageName(dictionaryContainer);
    _builder.append(_packageName, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@");
    String _name = SuppressWarnings.class.getName();
    _builder.append(_name, "\t");
    _builder.append("(\"all\")");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("public class ");
    String _dictionaryClassName = this._dictionaryNameUtils.dictionaryClassName(dictionaryContainer);
    _builder.append(_dictionaryClassName, "\t");
    _builder.append(" extends de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.CompositeModel {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t\t");
    EList<DictionaryContainerContent> _containercontents = dictionaryContainer.getContainercontents();
    CharSequence _dictionaryClass = this.dictionaryClass(_containercontents);
    _builder.append(_dictionaryClass, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("public ");
    String _dictionaryClassName_1 = this._dictionaryNameUtils.dictionaryClassName(dictionaryContainer);
    _builder.append(_dictionaryClassName_1, "\t\t");
    _builder.append("(de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseModel<?> parent) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("super(\"");
    String _name_1 = dictionaryContainer.getName();
    _builder.append(_name_1, "\t\t\t");
    _builder.append("\", parent);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t\t\t");
    EList<DictionaryContainerContent> _containercontents_1 = dictionaryContainer.getContainercontents();
    CharSequence _dictionaryContainerContentsConstructor = this.dictionaryContainerContentsConstructor(_containercontents_1);
    _builder.append(_dictionaryContainerContentsConstructor, "\t\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public void dictionaryGenerator(final DictionaryEditableTable dictionaryContainer, final IFileSystemAccess fsa) {
    EList<DictionaryContainerContent> _containercontents = dictionaryContainer.getContainercontents();
    this.dictionaryGenerator(_containercontents, fsa);
    String _dictionaryClassFullQualifiedFileName = this._dictionaryNameUtils.dictionaryClassFullQualifiedFileName(dictionaryContainer);
    CharSequence _dictionaryClass = this.dictionaryClass(dictionaryContainer);
    fsa.generateFile(_dictionaryClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _dictionaryClass);
  }
  
  public CharSequence dictionaryClass(final DictionaryEditableTable dictionaryContainer) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.append("package ");
    String _packageName = this._dictionaryNameUtils.getPackageName(dictionaryContainer);
    _builder.append(_packageName, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@");
    String _name = SuppressWarnings.class.getName();
    _builder.append(_name, "\t");
    _builder.append("(\"all\")");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("public class ");
    String _dictionaryClassName = this._dictionaryNameUtils.dictionaryClassName(dictionaryContainer);
    _builder.append(_dictionaryClassName, "\t");
    _builder.append(" extends ");
    String _name_1 = EditableTableModel.class.getName();
    _builder.append(_name_1, "\t");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<DictionaryControl> _columncontrols = dictionaryContainer.getColumncontrols();
      for(final DictionaryControl dictionaryControl : _columncontrols) {
        _builder.append("\t\t");
        CharSequence _dictionaryConstant = this._dictionaryControls.dictionaryConstant(dictionaryControl);
        _builder.append(_dictionaryConstant, "\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("public ");
    String _dictionaryClassName_1 = this._dictionaryNameUtils.dictionaryClassName(dictionaryContainer);
    _builder.append(_dictionaryClassName_1, "\t\t");
    _builder.append("(");
    String _name_2 = BaseModel.class.getName();
    _builder.append(_name_2, "\t\t");
    _builder.append("<?> parent) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("super(\"");
    String _name_3 = dictionaryContainer.getName();
    _builder.append(_name_3, "\t\t\t");
    _builder.append("\", parent);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<DictionaryControl> _columncontrols_1 = dictionaryContainer.getColumncontrols();
      for(final DictionaryControl dictionaryControl_1 : _columncontrols_1) {
        _builder.append("\t\t\t");
        _builder.append("this.getControls().add(");
        String _dictionaryConstantName = this._dictionaryNameUtils.dictionaryConstantName(dictionaryControl_1);
        _builder.append(_dictionaryConstantName, "\t\t\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t\t");
        Object _dictionaryControlConstantSetters = this._dictionaryControls.dictionaryControlConstantSetters(dictionaryControl_1);
        _builder.append(_dictionaryControlConstantSetters, "\t\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("setVoName(");
    EntityAttribute _entityattribute = dictionaryContainer.getEntityattribute();
    Entity _entity = ModelUtil.getEntity(_entityattribute);
    String _voFullQualifiedName = this._dictionaryNameUtils.voFullQualifiedName(_entity);
    _builder.append(_voFullQualifiedName, "\t\t\t");
    _builder.append(".class);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("setAttributePath(\"");
    EntityAttribute _entityattribute_1 = dictionaryContainer.getEntityattribute();
    String _name_4 = _entityattribute_1.getName();
    _builder.append(_name_4, "\t\t\t");
    _builder.append("\");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public void dictionaryGenerator(final List<DictionaryContainerContent> dictionaryContainerContents, final IFileSystemAccess fsa) {
    Iterable<DictionaryContainer> _filter = Iterables.<DictionaryContainer>filter(dictionaryContainerContents, DictionaryContainer.class);
    for (final DictionaryContainer dictionaryContainer : _filter) {
      String _dictionaryClassFullQualifiedFileName = this._dictionaryNameUtils.dictionaryClassFullQualifiedFileName(dictionaryContainer);
      CharSequence _dictionaryClass = this.dictionaryClass(dictionaryContainer);
      fsa.generateFile(_dictionaryClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, _dictionaryClass);
    }
    Iterable<DictionaryControl> _filter_1 = Iterables.<DictionaryControl>filter(dictionaryContainerContents, DictionaryControl.class);
    for (final DictionaryControl dictionaryControl : _filter_1) {
    }
  }
}
