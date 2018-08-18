package org.gusmp.service 
{
	import flash.events.Event;
	import flash.net.URLLoader;
	import mx.formatters.DateFormatter;
	import org.gusmp.service.*;
	import org.gusmp.model.PhrasalVerbModel;
	import org.gusmp.model.WordFormationModel;

	/**
	 * ...
	 * @author Gus
	 */
	public class UpdateService 
	{
		
		[Inject]
		public var fileService:FileService;
		
		[Inject]
		public var phrasalVerbService:PhrasalVerbService;
		
		[Inject]
		public var wordFormationService:WordFormationService;
		
		private var updateViewWhenUpdateFinishedCallback:Function;
		
		private function finishDownloadPvCallBack(updateFile: Array, error: Boolean, errorMsg: String): void {
			
			if (error == true) {
				this.updateViewWhenUpdateFinishedCallback(error, errorMsg);
				return;
			}
			
			error = false;
			errorMsg = "";
			
			for (var i:uint = 0; i < updateFile.length; i++) {
				
				try {
					var register:Array = updateFile[i].split(";");
					//trace(updateFile[i]);
					
					var pv:PhrasalVerbModel = new PhrasalVerbModel();
					
					pv.setDate(DateFormatter.parseDateString(register[1], "DD/MM/YYYY"));
					pv.setExample(register[3]);
					pv.setHits(0);
					pv.setKey(register[4]);
					pv.setLevel(register[0]);
					pv.setMeaning(register[2]);
					
					phrasalVerbService.save(pv);
				} catch(error:Error) {
					trace("[PV] Wrong register: " + updateFile[i]);
					trace(error.message);
					error = true;
					errorMsg = "Some registers could not be loaded.";
				}
			}
			
			this.updateViewWhenUpdateFinishedCallback(error, errorMsg);
		}
		
		
		public function updatePhrasalVerbs(url:String, updateViewWhenUpdateFinishedCallback:Function) : void 
		{
			this.updateViewWhenUpdateFinishedCallback = updateViewWhenUpdateFinishedCallback;
			fileService.downloadFile(url, finishDownloadPvCallBack);
		}
		
		private function finishDownloadWfCallBack(updateFile: Array, error: Boolean, errorMsg: String): void {
			
			if (error == true) {
				this.updateViewWhenUpdateFinishedCallback(error, errorMsg);
				return;
			}
			
			error = false;
			errorMsg = "";
			
			for (var i:uint = 0; i < updateFile.length; i++) {
				
				try {
					
					var register:Array = updateFile[i].split(";");
					//trace(updateFile[i]);
					
					var wf:WordFormationModel = new WordFormationModel();
					
					wf.setDate(DateFormatter.parseDateString(register[1], "DD/MM/YYYY"));
					wf.setHits(0);
					wf.setLevel(register[0]);
					wf.setSentence1(register[3]);
					wf.setSentence2(register[4]);
					wf.setSentence3(register[5]);
					wf.setSentenceKey1(register[6]);
					wf.setSentenceKey2(register[7]);
					wf.setSentenceKey3(register[8]);
					wf.setWordRoot(register[2]);
					
					
					wordFormationService.save(wf);
				} catch(error:Error) {
					trace("[WF] Wrong register: " + updateFile[i]);
					trace(error.message);
					error = true;
					errorMsg = "Some registers could not be loaded.";
				}
			}
			
			this.updateViewWhenUpdateFinishedCallback(error, errorMsg);
		}
		
		public function updateWordFormation(url:String, updateViewWhenUpdateFinishedCallback:Function): void {
			this.updateViewWhenUpdateFinishedCallback = updateViewWhenUpdateFinishedCallback;
			fileService.downloadFile(url, finishDownloadWfCallBack);
		}
	}

}