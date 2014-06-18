package io.pelle.mango.dsl.generator.server;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import io.pelle.mango.client.base.vo.EntityDescriptor;
import io.pelle.mango.client.base.vo.IEntityDescriptor;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.LongAttributeDescriptor;
import io.pelle.mango.dsl.generator.BaseEntityGenerator;
import io.pelle.mango.dsl.generator.util.AttributeUtils;
import io.pelle.mango.dsl.generator.util.EntityUtils;
import io.pelle.mango.dsl.generator.util.NameUtils;
import io.pelle.mango.dsl.generator.util.TypeUtils;
import io.pelle.mango.dsl.mango.Cardinality;
import io.pelle.mango.dsl.mango.Entity;
import io.pelle.mango.dsl.mango.EntityAttribute;
import io.pelle.mango.dsl.mango.EntityEntityAttribute;
import io.pelle.mango.dsl.mango.StringEntityAttribute;
import io.pelle.mango.server.base.BaseEntity;
import java.util.Arrays;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class EntityGenerator extends BaseEntityGenerator {
  @Inject
  @Extension
  private AttributeUtils _attributeUtils;
  
  @Inject
  @Extension
  private NameUtils _nameUtils;
  
  @Inject
  @Extension
  private TypeUtils _typeUtils;
  
  @Inject
  @Extension
  private EntityUtils _entityUtils;
  
  public CharSequence compileEntity(final Entity entity) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _packageName = this._nameUtils.getPackageName(entity);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import javax.persistence.*;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("@Entity");
    _builder.newLine();
    _builder.append("@Table(name = \"");
    String _entityTableName = this._nameUtils.entityTableName(entity);
    _builder.append(_entityTableName, "");
    _builder.append("\")");
    _builder.newLineIfNotEmpty();
    {
      boolean _isExtendedByOtherEntity = this._entityUtils.isExtendedByOtherEntity(entity);
      if (_isExtendedByOtherEntity) {
        _builder.append("@javax.persistence.Inheritance(strategy = javax.persistence.InheritanceType.JOINED)");
        _builder.newLine();
        _builder.append("@javax.persistence.PrimaryKeyJoinColumn(name=\"");
        String _entityTableIdColumnName = this._nameUtils.entityTableIdColumnName(entity);
        _builder.append(_entityTableIdColumnName, "");
        _builder.append("\")");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Entity _extends = entity.getExtends();
      boolean _notEquals = (!Objects.equal(_extends, null));
      if (_notEquals) {
      } else {
      }
    }
    _builder.append("public class ");
    String _entityName = this._nameUtils.entityName(entity);
    _builder.append(_entityName, "");
    _builder.append(" extends ");
    {
      Entity _extends_1 = entity.getExtends();
      boolean _notEquals_1 = (!Objects.equal(_extends_1, null));
      if (_notEquals_1) {
        Entity _extends_2 = entity.getExtends();
        String _entityFullQualifiedName = this._nameUtils.entityFullQualifiedName(_extends_2);
        _builder.append(_entityFullQualifiedName, "");
      } else {
        JvmType _jvmtype = entity.getJvmtype();
        boolean _notEquals_2 = (!Objects.equal(_jvmtype, null));
        if (_notEquals_2) {
          JvmType _jvmtype_1 = entity.getJvmtype();
          String _qualifiedName = _jvmtype_1.getQualifiedName();
          _builder.append(_qualifiedName, "");
        } else {
          String _name = BaseEntity.class.getName();
          _builder.append(_name, "");
        }
      }
    }
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("public static final ");
    String _name_1 = IEntityDescriptor.class.getName();
    _builder.append(_name_1, "\t\t");
    _builder.append("<");
    String _entityFullQualifiedName_1 = this._nameUtils.entityFullQualifiedName(entity);
    _builder.append(_entityFullQualifiedName_1, "\t\t");
    _builder.append("> ");
    String _entityConstantName = this._nameUtils.entityConstantName(entity);
    _builder.append(_entityConstantName, "\t\t");
    _builder.append(" = new ");
    String _name_2 = EntityDescriptor.class.getName();
    _builder.append(_name_2, "\t\t");
    _builder.append("<");
    String _type = this._typeUtils.getType(entity);
    _builder.append(_type, "\t\t");
    _builder.append(">(");
    String _typeClass = this._typeUtils.getTypeClass(entity);
    _builder.append(_typeClass, "\t\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("public static ");
    String _name_3 = LongAttributeDescriptor.class.getName();
    _builder.append(_name_3, "\t\t");
    _builder.append(" ");
    String _attributeConstantName = this._nameUtils.attributeConstantName(IVOEntity.ID_FIELD_NAME);
    _builder.append(_attributeConstantName, "\t\t");
    _builder.append(" = new ");
    String _name_4 = LongAttributeDescriptor.class.getName();
    _builder.append(_name_4, "\t\t");
    _builder.append("(");
    String _entityConstantName_1 = this._nameUtils.entityConstantName(entity);
    _builder.append(_entityConstantName_1, "\t\t");
    _builder.append(", \"");
    _builder.append(IVOEntity.ID_FIELD_NAME, "\t\t");
    _builder.append("\");");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t\t");
    CharSequence _attributeDescriptorsFromExtends = this.attributeDescriptorsFromExtends(entity);
    _builder.append(_attributeDescriptorsFromExtends, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t\t");
    CharSequence _compileGetAttributeDescriptors = this.compileGetAttributeDescriptors(entity);
    _builder.append(_compileGetAttributeDescriptors, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      Entity _extends_3 = entity.getExtends();
      boolean _equals = Objects.equal(_extends_3, null);
      if (_equals) {
        _builder.append("\t");
        _builder.append("@Id");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("@Column(name = \"");
        String _entityTableIdColumnName_1 = this._nameUtils.entityTableIdColumnName(entity);
        _builder.append(_entityTableIdColumnName_1, "\t");
        _builder.append("\")");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("@GeneratedValue(strategy = GenerationType.TABLE, generator = \"");
        String _entityTableIdSequenceName = this._nameUtils.entityTableIdSequenceName(entity);
        _builder.append(_entityTableIdSequenceName, "\t");
        _builder.append("\")");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("@SequenceGenerator(name = \"");
        String _entityTableIdSequenceName_1 = this._nameUtils.entityTableIdSequenceName(entity);
        _builder.append(_entityTableIdSequenceName_1, "\t");
        _builder.append("\", sequenceName = \"");
        String _entityTableIdSequenceName_2 = this._nameUtils.entityTableIdSequenceName(entity);
        _builder.append(_entityTableIdSequenceName_2, "\t");
        _builder.append("\", allocationSize = 1)");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("private long id;");
        _builder.newLine();
        _builder.append("\t");
        CharSequence _terSetter = this._attributeUtils.getterSetter("long", "id");
        _builder.append(_terSetter, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      EList<EntityAttribute> _attributes = entity.getAttributes();
      for(final EntityAttribute attribute : _attributes) {
        _builder.append("\t");
        CharSequence _compileEntityAttribute = this.compileEntityAttribute(attribute);
        _builder.append(_compileEntityAttribute, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileEntityAttribute(final EntityAttribute entityAttribute) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _changeTrackingAttributeGetterSetter = this.changeTrackingAttributeGetterSetter(entityAttribute);
    _builder.append(_changeTrackingAttributeGetterSetter, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _compileEntityAttributeJpaAnnotations(final EntityAttribute entityAttribute) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("@Column(name = \"");
    String _entityTableColumnName = this._nameUtils.entityTableColumnName(entityAttribute);
    _builder.append(_entityTableColumnName, "");
    _builder.append("\")");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _compileEntityAttributeJpaAnnotations(final StringEntityAttribute entityAttribute) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Cardinality _cardinality = entityAttribute.getCardinality();
      boolean _equals = Objects.equal(_cardinality, Cardinality.ONETOMANY);
      if (_equals) {
        _builder.append("@javax.persistence.ElementCollection(fetch=javax.persistence.FetchType.EAGER)");
        _builder.newLine();
      } else {
        _builder.append("@Column(name = \"");
        String _entityTableColumnName = this._nameUtils.entityTableColumnName(entityAttribute);
        _builder.append(_entityTableColumnName, "");
        _builder.append("\")");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence _compileEntityAttributeJpaAnnotations(final EntityEntityAttribute entityAttribute) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Cardinality _cardinality = entityAttribute.getCardinality();
      boolean _equals = Objects.equal(_cardinality, Cardinality.ONETOMANY);
      if (_equals) {
        _builder.append("@OneToMany()");
        _builder.newLine();
      } else {
        _builder.append("@OneToOne()");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  public CharSequence changeTrackingAttributeGetterSetter(final EntityAttribute attribute) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _compileEntityAttributeJpaAnnotations = this.compileEntityAttributeJpaAnnotations(attribute);
    _builder.append(_compileEntityAttributeJpaAnnotations, "");
    _builder.newLineIfNotEmpty();
    String _type = this._typeUtils.getType(attribute);
    String _name = attribute.getName();
    String _initializer = this._typeUtils.getInitializer(attribute);
    CharSequence _attribute = this._attributeUtils.attribute(_type, _name, _initializer);
    _builder.append(_attribute, "");
    _builder.newLineIfNotEmpty();
    Entity _parentEntity = this._attributeUtils.getParentEntity(attribute);
    CharSequence _compileEntityAttributeDescriptor = this._typeUtils.compileEntityAttributeDescriptor(attribute, _parentEntity);
    _builder.append(_compileEntityAttributeDescriptor, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    String _type_1 = this._typeUtils.getType(attribute);
    String _name_1 = attribute.getName();
    String _attributeName = this._nameUtils.attributeName(_name_1);
    CharSequence _ter = this._attributeUtils.getter(_type_1, _attributeName);
    _builder.append(_ter, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    String _type_2 = this._typeUtils.getType(attribute);
    String _name_2 = attribute.getName();
    String _attributeName_1 = this._nameUtils.attributeName(_name_2);
    CharSequence _changeTrackingSetter = this._attributeUtils.changeTrackingSetter(_type_2, _attributeName_1);
    _builder.append(_changeTrackingSetter, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence compileEntityAttributeJpaAnnotations(final EntityAttribute entityAttribute) {
    if (entityAttribute instanceof StringEntityAttribute) {
      return _compileEntityAttributeJpaAnnotations((StringEntityAttribute)entityAttribute);
    } else if (entityAttribute instanceof EntityEntityAttribute) {
      return _compileEntityAttributeJpaAnnotations((EntityEntityAttribute)entityAttribute);
    } else if (entityAttribute != null) {
      return _compileEntityAttributeJpaAnnotations(entityAttribute);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(entityAttribute).toString());
    }
  }
}