package io.pelle.mango.client.base.modules.dictionary.model;

import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.IBaseTableModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseTableModel<ElementType> extends BaseModel<ElementType> implements IBaseTableModel
{
	private List<IBaseControlModel> columns = new ArrayList<IBaseControlModel>();

	private static final long serialVersionUID = 7452528927479882166L;

	private Integer visibleRows = DEFAULT_VISBLE_ROWS;

	private int paingSize = DEFAULT_PAGING_SIZE;

	public BaseTableModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public Integer getVisibleRows()
	{
		return this.visibleRows;
	}

	@Override
	public List<IBaseContainerModel> getChildren()
	{
		return Collections.emptyList();
	}

	@Override
	public List<IBaseControlModel> getControls()
	{
		return this.columns;
	}

	@Override
	public int getPagingSize()
	{
		return this.paingSize;
	}

}
