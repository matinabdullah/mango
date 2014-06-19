package io.pelle.mango.client.web.modules.dictionary.editor;

import io.pelle.mango.client.base.db.vos.Result;
import io.pelle.mango.client.base.messages.IValidationMessage;
import io.pelle.mango.client.base.modules.dictionary.editor.IDictionaryEditor;
import io.pelle.mango.client.base.modules.dictionary.editor.IEditorUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.hooks.BaseEditorHook;
import io.pelle.mango.client.base.modules.dictionary.hooks.DictionaryHookRegistry;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.dictionary.model.editor.IEditorModel;
import io.pelle.mango.client.base.util.CollectionUtils;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.IExpression;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.base.vo.query.expressions.ExpressionFactory;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.DictionaryElementUtil;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditorModule.EditorMode;
import io.pelle.mango.client.web.modules.dictionary.events.VOSavedEvent;
import io.pelle.mango.client.web.util.BaseAsyncCallback;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DictionaryEditor<VOType extends IBaseVO> extends BaseRootElement<IEditorModel> implements IDictionaryEditor<VOType> {

	private final EditorVOWrapper<VOType> voWrapper = new EditorVOWrapper<VOType>();

	private List<IEditorUpdateListener> updateListeners = new ArrayList<IEditorUpdateListener>();

	private Map<String, Object> context;

	private IDictionaryModel dictionaryModel;

	public DictionaryEditor(IDictionaryModel dictionaryModel, Map<String, Object> context) {
		super(dictionaryModel.getEditorModel(), null);

		this.context = context;
		this.dictionaryModel = dictionaryModel;
	}

	@Override
	public EditorVOWrapper<VOType> getVOWrapper() {
		return this.voWrapper;
	}

	@Override
	public BaseRootElement<?> getRootElement() {
		return this;
	}

	private EditorMode getEditorMode() {
		if (this.voWrapper.getVO() == null || this.voWrapper.getVO().getId() == IBaseVO.NEW_VO_ID) {
			return EditorMode.INSERT;
		} else {
			return EditorMode.UPDATE;
		}
	}

	public boolean isDirty() {
		return this.voWrapper.isDirty();
	}

	public void newVO(final AsyncCallback<Void> callback) {
		AsyncCallback<VOType> newVOCallback = new BaseErrorAsyncCallback<VOType>(callback) {

			/** {@inheritDoc} */
			@Override
			public void onSuccess(VOType result) {
				setVO(result);

				if (callback != null) {
					callback.onSuccess(null);
				}

			}
		};

		MangoClientWeb.getInstance().getRemoteServiceLocator().getBaseEntityService().getNewVO(this.dictionaryModel.getVOClass().getName(), CollectionUtils.copyMap(this.context), (AsyncCallback<IBaseVO>) newVOCallback);

	}

	public void load(long id, final AsyncCallback<Void> callback) {

		SelectQuery<VOType> selectQuery = new SelectQuery<VOType>(dictionaryModel.getVOClass());
		IExpression expression = ExpressionFactory.createLongExpression(dictionaryModel.getVOClass(), IBaseVO.ID_FIELD_NAME, id);
		selectQuery.where(expression);
		
		if (DictionaryHookRegistry.getInstance().hasEditorHook(getModel().getParent().getName())) {
		}

		AsyncCallback<List<VOType>> filterCallback = new BaseErrorAsyncCallback<List<VOType>>() {

			/** {@inheritDoc} */
			@Override
			public void onSuccess(List<VOType> result) {
				if (result.size() == 1) {
					setVO(result.get(0));
				} else {
					callback.onFailure(new RuntimeException("error loading vo"));
				}

				if (callback != null) {
					callback.onSuccess(null);
				}

			}
		};

		MangoClientWeb.getInstance().getRemoteServiceLocator().getBaseEntityService().filter(selectQuery, filterCallback);

	}

	public void save() {
		save(null);
	}

	private final BaseAsyncCallback<Result<VOType>, Result<VOType>> internalSaveCallback = new BaseAsyncCallback<Result<VOType>, Result<VOType>>() {
		/** {@inheritDoc} */
		@Override
		public void onSuccess(Result<VOType> result) {
			if (result.getValidationMessages().isEmpty()) {
				setVO(result.getVO());
				MangoClientWeb.EVENT_BUS.fireEvent(new VOSavedEvent(result.getVO()));
			} else {
				DictionaryEditor.this.addValidationMessages(result.getValidationMessages());
			}

			super.callParentCallbacks(result);
		}
	};

	private void setVO(VOType vo) {
		DictionaryEditor.this.voWrapper.setVO(vo);
		DictionaryEditor.this.voWrapper.markClean();
		fireUpdateListeners();
	}

	protected void addValidationMessages(List<IValidationMessage> validationMessages) {
		for (IValidationMessage validationMessage : validationMessages) {
			if (validationMessage.getContext().containsKey(IValidationMessage.ATTRIBUTE_CONTEXT_KEY)) {
				String attributePath = validationMessage.getContext().get(IValidationMessage.ATTRIBUTE_CONTEXT_KEY).toString();

				Collection<BaseDictionaryElement<?>> baseDictionaryElements = DictionaryElementUtil.getElementsForAttributePath(getRootElement(), attributePath);

				for (BaseDictionaryElement<?> baseDictionaryElement : baseDictionaryElements) {
					baseDictionaryElement.addValidationMessage(validationMessage);
				}
			}
		}

	}

	public void save(AsyncCallback<Result<VOType>> asyncCallback) {
		this.internalSaveCallback.addParentCallback(asyncCallback);

		// if (!this.dataBindingContext.hasErrors())
		// {
		if (DictionaryHookRegistry.getInstance().hasEditorHook(getModel().getName())) {
			final Stack<BaseEditorHook<IBaseVO>> runningHooks = new Stack<BaseEditorHook<IBaseVO>>();
			final Stack<Boolean> hookResults = new Stack<Boolean>();

			for (final BaseEditorHook<IBaseVO> baseEditorHook : DictionaryHookRegistry.getInstance().getEditorHook(getModel().getName())) {
				runningHooks.add(baseEditorHook);

				baseEditorHook.onSave(new BaseErrorAsyncCallback<Boolean>() {
					@Override
					public void onSuccess(Boolean doSave) {
						hookResults.push(doSave);
						runningHooks.remove(baseEditorHook);

						if (runningHooks.isEmpty() && Iterables.all(hookResults, Predicates.equalTo(true))) {
							internalSave();
						}
					}
				}, this.voWrapper.getVO());
			}
		} else {
			internalSave();
		}
		// }
	}

	private void internalSave() {
		switch (getEditorMode()) {
		case INSERT:
			MangoClientWeb.getInstance().getRemoteServiceLocator().getBaseEntityService().validateAndCreate(this.voWrapper.getVO(), this.internalSaveCallback);
			break;
		case UPDATE:
			MangoClientWeb.getInstance().getRemoteServiceLocator().getBaseEntityService().validateAndSave(this.voWrapper.getVO(), this.internalSaveCallback);
			break;
		default:
			throw new RuntimeException("editor mode '" + getEditorMode().toString() + "' not implemented");
		}
	}

	public void refresh() {
	}

	@Override
	public VOType getVO() {
		return this.voWrapper.getVO();
	}

	@Override
	public void addUpdateListener(IEditorUpdateListener updateListener) {
		this.updateListeners.add(updateListener);
	}

	private void fireUpdateListeners() {
		for (IEditorUpdateListener updateListener : this.updateListeners) {
			updateListener.onUpdate();
		}
	}

}
