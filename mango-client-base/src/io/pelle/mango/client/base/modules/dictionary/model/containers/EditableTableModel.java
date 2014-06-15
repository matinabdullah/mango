package io.pelle.mango.client.base.modules.dictionary.model.containers;

import io.pelle.mango.client.base.modules.dictionary.container.IEditableTable;
import io.pelle.mango.client.base.modules.dictionary.model.BaseTableModel;
import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.vo.IBaseVO;

public class EditableTableModel<VOType extends IBaseVO> extends BaseTableModel<IEditableTable<VOType>> implements IEditableTableModel
{

	private static final long serialVersionUID = 1832725605229414533L;

	private String attributePath;

	private String voName;

	public EditableTableModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public String getAttributePath()
	{
		return this.attributePath;
	}

	@Override
	public String getVoName()
	{
		return this.voName;
	}

	public void setAttributePath(String attributePath)
	{
		this.attributePath = attributePath;
	}

	public void setVoName(String voName)
	{
		this.voName = voName;
	}

}
