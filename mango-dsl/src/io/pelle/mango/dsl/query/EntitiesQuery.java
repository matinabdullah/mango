package io.pelle.mango.dsl.query;

import io.pelle.mango.dsl.mango.Entity;

import java.util.Collection;
import java.util.List;

public class EntitiesQuery extends BaseEObjectCollectionQuery<Entity>
{
	public EntitiesQuery(List<Entity> entities)
	{
		super(entities);
	}

	public Collection<Entity> getEntities()
	{
		return getList();
	}

}
