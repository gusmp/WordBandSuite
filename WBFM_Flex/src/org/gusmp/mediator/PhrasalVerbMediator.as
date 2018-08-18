package org.gusmp.mediator 
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	import mx.utils.StringUtil;
	import org.gusmp.model.PhrasalVerbModel;
	
	import robotlegs.bender.bundles.mvcs.Mediator;
	import robotlegs.bender.extensions.mediatorMap.api.IMediator;

	import org.gusmp.views.*;
	import org.gusmp.service.*;
	
	/**
	 * ...
	 * @author Gus
	 */
	public class PhrasalVerbMediator extends Mediator
	{

		[Inject]
		public var view:PhrasalVerbView;
		
		[Inject]
		public var phrasalVerbService:PhrasalVerbService;
		
		private var currentPhrasalVerbModel:PhrasalVerbModel;
		
		
		override public function initialize():void
		{
			super.initialize();
			
			this.view.checkBt.addEventListener(MouseEvent.CLICK, checkAnswerHandler);
			this.view.continueBt.addEventListener(MouseEvent.CLICK, continueHandler);
			this.view.showAnswerBt.addEventListener(MouseEvent.CLICK, showAnswerHandler);
			
			this.continueHandler(null);
		}
		
		
		override public function destroy():void
		{
			super.destroy();
			
			this.view.checkBt.removeEventListener(MouseEvent.CLICK, checkAnswerHandler);
			this.view.continueBt.removeEventListener(MouseEvent.CLICK, continueHandler);
			this.view.showAnswerBt.removeEventListener(MouseEvent.CLICK, showAnswerHandler);
		}
		
		
		private function checkAnswerHandler(event:MouseEvent): void {
			
			var success:Boolean = false;
			
			if (StringUtil.trim(this.view.answerLb.text).toUpperCase() != this.currentPhrasalVerbModel.getKey()) {
				this.view.answerLb.setStyle("color", "#FF0000");
			} else {
				this.view.answerLb.setStyle("color", "#00FF00");
				success = true;
			}
			
			
			if (success == true) {
				this.view.checkBt.enabled = false;
				this.view.showAnswerBt.enabled = false;
				this.currentPhrasalVerbModel.setHits(this.currentPhrasalVerbModel.getHits()+1);
				this.phrasalVerbService.updateHit(this.currentPhrasalVerbModel);
			}
		}
		
		private function continueHandler(event:MouseEvent): void {
			
			this.currentPhrasalVerbModel = this.phrasalVerbService.getNext();
			
			if (this.currentPhrasalVerbModel != null) {
				
				this.view.checkBt.enabled = true;
				this.view.continueBt.enabled = true;
				this.view.showAnswerBt.enabled = true;
				
				this.clean();
				this.view.meaningTa.text = this.currentPhrasalVerbModel.getMeaning();
				this.view.exampleTa.text = this.currentPhrasalVerbModel.getExample();
				
			} else {
				this.view.checkBt.enabled = false;
				this.view.continueBt.enabled = false;
				this.view.showAnswerBt.enabled = false;
				
				this.clean();
			}
		}
		
		private function showAnswerHandler(event:MouseEvent): void {
			
			this.view.checkBt.enabled = false;
			
			this.view.answerLb.text = this.currentPhrasalVerbModel.getKey();
		}
		
		private function clean(): void {
			
			this.view.meaningTa.text = "";
			this.view.exampleTa.text = "";
			this.view.answerLb.text = "";
			
			this.view.answerLb.setStyle("color", "#FFFFFF");
		}
	}
}