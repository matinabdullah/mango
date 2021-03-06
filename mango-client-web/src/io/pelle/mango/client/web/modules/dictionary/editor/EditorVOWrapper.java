/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package io.pelle.mango.client.web.modules.dictionary.editor;

import io.pelle.mango.client.base.VOBeanUtil;
import io.pelle.mango.client.base.db.vos.ISimpleVO;
import io.pelle.mango.client.base.modules.dictionary.IVOWrapper;
import io.pelle.mango.client.base.vo.IBaseVO;

public class EditorVOWrapper<VOType extends IBaseVO> implements IVOWrapper<VOType>
{
	private boolean dirty = false;

	private VOType vo;

	private IDirtyCallback dirtyCallback;
	
	public EditorVOWrapper()
	{
	}

	public EditorVOWrapper(VOType vo)
	{
		super();
		this.vo = vo;
	}

	public long getId()
	{
		return this.vo.getId();
	}

	public VOType getVO()
	{
		return this.vo;
	}

	public void markClean()
	{
		this.dirty = false;
	}

	public void markDirty()
	{
		this.dirty = true;
		
		if (dirtyCallback != null) {
			dirtyCallback.onDirtyChange();
		}
	}

	public boolean isDirty()
	{
		return this.dirty;
	}

	@Override
	public Object get(String attribute)
	{
		return this.vo.get(attribute);
	}

	@Override
	public void set(String attribute, Object value)
	{
		VOBeanUtil.set(this.vo, attribute, value);

		markDirty();
	}

	public void setVO(VOType vo)
	{
		this.vo = vo;
		markClean();
	}

	@Override
	public VOType getContent()
	{
		return this.vo;
	}
	
	public void setDirtyCallback(IDirtyCallback dirtyCallback) {
		this.dirtyCallback = dirtyCallback;
	}
}
