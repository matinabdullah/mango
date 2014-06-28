package io.pelle.mango.dsl.generator.client.dictionary

import com.google.inject.Inject
import io.pelle.mango.client.base.modules.dictionary.model.BaseModel
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModel
import io.pelle.mango.client.base.modules.dictionary.model.containers.CompositeModel
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICompositeModel
import io.pelle.mango.client.base.modules.dictionary.model.editor.EditorModel
import io.pelle.mango.client.base.modules.dictionary.model.search.FilterModel
import io.pelle.mango.client.base.modules.dictionary.model.search.ResultModel
import io.pelle.mango.client.base.modules.dictionary.model.search.SearchModel
import io.pelle.mango.dsl.ModelUtil
import io.pelle.mango.dsl.generator.GeneratorConstants
import io.pelle.mango.dsl.mango.Dictionary
import io.pelle.mango.dsl.mango.DictionaryEditor
import io.pelle.mango.dsl.mango.DictionaryFilter
import io.pelle.mango.dsl.mango.DictionaryResult
import io.pelle.mango.dsl.mango.DictionarySearch
import io.pelle.mango.dsl.mango.Model
import org.eclipse.xtext.generator.IFileSystemAccess
import io.pelle.mango.dsl.emf.EmfModelQuery

class DictionaryGenerator {

	@Inject 
	extension DictionaryNameUtils

	@Inject 
	extension DictionaryControls

	@Inject 
	extension DictionaryContainerGenerator

	def dispatch dictionaryConstant(DictionaryEditor dictionaryEditor) '''
	public «dictionaryEditor.dictionaryClassFullQualifiedName» «dictionaryEditor.dictionaryConstantName» = new «dictionaryEditor.dictionaryClassFullQualifiedName»(this);
	'''

	def dispatch dictionaryConstant(Dictionary dictionary) '''
	public static final «dictionary.dictionaryClassFullQualifiedName» «dictionary.dictionaryConstantName» = new «dictionary.dictionaryClassFullQualifiedName»();
	'''


	def dictionaryGenerator(Model model, IFileSystemAccess fsa) {

		for (dictionary : model.eAllContents.toIterable.filter(Dictionary)) {
			dictionary.dictionaryGenerator(fsa)
		}

		fsa.generateFile(model.dictionaryClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, model.dictionaryClass)
	}

	def dictionaryClass(Model model) '''

	package «model.modelPackageName»;
	
	@«SuppressWarnings.name»("all")
	public class «model.dictionaryClassName» {

		private «model.dictionaryClassName»() {
		}

		«FOR dictionary : model.eAllContents.toIterable.filter(Dictionary)»
			«dictionary.dictionaryConstant»;
		«ENDFOR»

	}
	'''

	def dictionaryGenerator(Dictionary dictionary, IFileSystemAccess fsa) {

		fsa.generateFile(dictionary.dictionaryClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, dictionary.dictionaryClass)
		
		for( dictionaryLabelControl : dictionary.labelcontrols)
		{
			fsa.generateFile(dictionaryLabelControl.dictionaryClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, dictionaryLabelControl.dictionaryControlClass)
		}

		if (dictionary.dictionaryeditor != null)
		{
			dictionary.dictionaryeditor.dictionaryGenerator(fsa)
		}
	
		if (dictionary.dictionarysearch != null)
		{
			dictionary.dictionarysearch.dictionaryGenerator(fsa)
		}
		
	}

	def dictionaryGenerator(DictionaryEditor dictionaryEditor, IFileSystemAccess fsa) {
		fsa.generateFile(dictionaryEditor.dictionaryClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, dictionaryEditor.dictionaryClass)
		dictionaryEditor.containercontents.dictionaryGenerator(fsa)		
	}

	
	def dictionaryClass(DictionaryEditor dictionaryEditor) '''

	package «dictionaryEditor.packageName»;
	
	@«SuppressWarnings.name»("all")
	public class «dictionaryEditor.dictionaryClassName» extends «EditorModel.name»<«EmfModelQuery.createEObjectQuery(dictionaryEditor).getParentByType(Dictionary).match.entity.voFullQualifiedName»> {
	
		private class RootComposite extends «CompositeModel.name» {

			«dictionaryEditor.containercontents.dictionaryClass»

			public RootComposite(«BaseModel.name»<?> parent) {
				super("«ICompositeModel.ROOT_COMPOSITE_NAME»", parent);

				«dictionaryEditor.containercontents.dictionaryContainerContentsConstructor»
			}
		}
		
		private RootComposite rootComposite = new RootComposite(this);
	
		public «dictionaryEditor.dictionaryClassName»(«BaseModel.name»<?> parent) {
			super("«dictionaryEditor.name»", parent);
		
			«IF dictionaryEditor.label != null»
				setLabel("«dictionaryEditor.label»");
			«ENDIF»
			
			setCompositeModel(rootComposite);
		}
		«dictionaryEditor.containercontents.dictionaryContainerContentsConstants»
	}
	'''


	def dispatch dictionaryConstant(DictionaryResult dictionaryResult) '''
	public «dictionaryResult.dictionaryClassFullQualifiedName» «dictionaryResult.dictionaryConstantName» = new «dictionaryResult.dictionaryClassFullQualifiedName»(this);
	'''

	def dispatch dictionaryConstant(DictionaryFilter dictionaryFilter) '''
	public «dictionaryFilter.dictionaryClassFullQualifiedName» «dictionaryFilter.dictionaryConstantName» = new «dictionaryFilter.dictionaryClassFullQualifiedName»(this);
	'''
	
	def dictionaryClass(Dictionary dictionary) '''
	
	package «dictionary.packageName»;
	
	@«SuppressWarnings.name»("all")
	public class «dictionary.dictionaryClassName()» extends «DictionaryModel.name» {

		// label controls
		«FOR dictionaryControl : dictionary.labelcontrols»
			«dictionaryControl.dictionaryConstant»
		«ENDFOR»

		«IF dictionary.dictionaryeditor != null»
			// dictionary editor
			«dictionary.dictionaryeditor.dictionaryConstant»
		«ENDIF»

		«IF dictionary.dictionarysearch != null»
			// dictionary search
			«dictionary.dictionarysearch.dictionaryConstant»
		«ENDIF»

		public «dictionary.dictionaryClassName»() {
		
			super("«dictionary.name»", null);

			setVoName(«dictionary.entity.voFullQualifiedName».class);
			
			// label controls
			«FOR labelcontrol : dictionary.labelcontrols»
				«labelcontrol.dictionaryControlConstantSetters»
				this.getLabelControls().add(«dictionaryConstantName(labelcontrol)»);
			«ENDFOR»
			
			«IF dictionary.dictionaryeditor != null»
				// dictionary editor '«dictionary.dictionaryeditor.name»'
				setEditorModel(«dictionary.dictionaryeditor.dictionaryConstantName»);
			«ENDIF»

			«IF dictionary.dictionarysearch != null»
				// dictionary search '«dictionary.dictionarysearch.name»'
				setSearchModel(«dictionary.dictionarysearch.dictionaryConstantName»);
			«ENDIF»
			
			«IF dictionary.label != null»
				setLabel("«dictionary.label»");
			«ENDIF»

			«IF dictionary.pluralLabel != null»
				setPluralLabel("«dictionary.pluralLabel»");
			«ENDIF»
			
		}
	}
	'''	
	def dispatch dictionaryConstant(DictionarySearch dictionarySearch) '''
	public «dictionarySearch.dictionaryClassFullQualifiedName» «dictionarySearch.dictionaryConstantName» = new «dictionarySearch.dictionaryClassFullQualifiedName»(this);
	'''

	def dictionaryFilterGenerator(DictionaryFilter dictionaryFilter, IFileSystemAccess fsa)  {
		fsa.generateFile(dictionaryFilter.dictionaryClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, dictionaryFilter.dictionaryClass)
		dictionaryFilter.containercontents.dictionaryGenerator(fsa)
	}

	def dictionaryResultGenerator(DictionaryResult dictionaryResult, IFileSystemAccess fsa)  {
		
		fsa.generateFile(dictionaryResult.dictionaryClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, dictionaryResult.dictionaryClass)
		
	}
	
	def dictionaryGenerator(DictionarySearch dictionarySearch, IFileSystemAccess fsa)  {
		
		fsa.generateFile(dictionarySearch.dictionaryClassFullQualifiedFileName, GeneratorConstants.VO_GEN_OUTPUT, dictionarySearch.dictionaryClass)
		
		dictionarySearch.dictionaryresult.dictionaryResultGenerator(fsa)

		for (dictionaryfilter : dictionarySearch.dictionaryfilters)
		{
			dictionaryfilter.dictionaryFilterGenerator(fsa)
		}

	}
	
	def dictionaryClass(DictionarySearch dictionarySearch) '''
	
	package «dictionarySearch.packageName»;
	
	@«SuppressWarnings.name»("all")
	public class «dictionarySearch.dictionaryClassName» extends «SearchModel.name» {
		
		«dictionarySearch.dictionaryresult.dictionaryConstant»

		«FOR dictionaryFilter : dictionarySearch.dictionaryfilters»
		«dictionaryFilter.dictionaryConstant»
		«ENDFOR»
		
		public «dictionarySearch.dictionaryClassName»(«BaseModel.name»<?> parent) {
			super("«dictionarySearch.name»", parent);
			
			«IF dictionarySearch.label != null»
				setLabel("«dictionarySearch.label»");
			«ENDIF»
			
			// result
			setResultModel(«dictionarySearch.dictionaryresult.dictionaryConstantName»);

			// filters
			«FOR dictionaryfilter : dictionarySearch.dictionaryfilters»
				getFilterModels().add(«dictionaryfilter.dictionaryConstantName»);
			«ENDFOR»
			
		}
		
	}
	'''	

	def dictionaryClass(DictionaryResult dictionaryResult) '''
	package «dictionaryResult.packageName»;
	
	@«SuppressWarnings.name»("all")
	public class «dictionaryResult.dictionaryClassName» extends «ResultModel.name» {

		«FOR dictionaryControl : dictionaryResult.resultcolumns»
			«dictionaryControl.dictionaryConstant »
		«ENDFOR»		
		
		public «dictionaryResult.dictionaryClassName»(«BaseModel.name»<?> parent) {
			super("«dictionaryResult.name»", parent);

			«FOR dictionaryControl : dictionaryResult.resultcolumns»
				this.getControls().add(«dictionaryControl.dictionaryConstantName»);
			«ENDFOR»		

			«FOR dictionaryControl : dictionaryResult.resultcolumns»
				«dictionaryControl.dictionaryControlConstantSetters»
			«ENDFOR»		
			
		}
		
	}
	'''

	def dictionaryClass(DictionaryFilter dictionaryFilter) '''
	package «dictionaryFilter.packageName»;
	
	@«SuppressWarnings.name»("all")
	public class «dictionaryFilter.dictionaryClassName» extends «FilterModel.name» {

		private class RootComposite extends «CompositeModel.name» {

			«dictionaryFilter.containercontents.dictionaryClass»

			public RootComposite(«BaseModel.name»<?> parent) {
				super("«ICompositeModel.ROOT_COMPOSITE_NAME»", parent);
				
				«dictionaryFilter.containercontents.dictionaryContainerContentsConstructor»
			}
		}
		
		private RootComposite rootComposite = new RootComposite(this);

		public «dictionaryFilter.dictionaryClassName»(«BaseModel.name»<?> parent) {
			super("«dictionaryFilter.name»", parent);
			
			setCompositeModel(rootComposite);
		}
		
		«dictionaryFilter.containercontents.dictionaryContainerContentsConstants»
	}
	'''

}
