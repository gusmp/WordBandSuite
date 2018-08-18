package org.gusmp.mediator 
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	import mx.utils.StringUtil;
	import org.gusmp.model.WordFormationModel;
	
	import robotlegs.bender.bundles.mvcs.Mediator;
	import robotlegs.bender.extensions.mediatorMap.api.IMediator;

	import org.gusmp.views.*;
	import org.gusmp.service.*;
	
	/**
	 * ...
	 * @author Gus
	 */
	public class WordFormationMediator extends Mediator
	{

		[Inject]
		public var view:WordFormationView;
		
		[Inject]
		public var wordFormationService:WordFormationService;
		
		private var currentWordFormation:WordFormationModel;
		
		public function WordFormationMediator()
		{
			super();
		}
		
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
			
			var success1:Boolean = false;
			var success2:Boolean = false;
			var success3:Boolean = false;
			
			if (StringUtil.trim(this.view.answer1Id.text).toUpperCase()  != this.currentWordFormation.getSentenceKey1()) {
				this.view.answer1Id.setStyle("color", "#FF0000");
			} else {
				this.view.answer1Id.setStyle("color", "#00FF00");
				success1 = true;
			}
			
			if (StringUtil.trim(this.view.answer2Id.text).toUpperCase()  != this.currentWordFormation.getSentenceKey2()) {
				this.view.answer2Id.setStyle("color", "#FF0000");
			} else {
				this.view.answer2Id.setStyle("color", "#00FF00");
				success2 = true;
			}
				
			if (StringUtil.trim(this.view.answer3Id.text).toUpperCase()  != this.currentWordFormation.getSentenceKey3()) {
				this.view.answer3Id.setStyle("color", "#FF0000");
			} else {
				this.view.answer3Id.setStyle("color", "#00FF00");
				success3 = true;
			}
			
			if ((success1 == true) && (success2 == true) && (success3 == true)) {
				this.view.checkBt.enabled = false;
				this.view.showAnswerBt.enabled = false;
				this.currentWordFormation.setHits(this.currentWordFormation.getHits()+1);
				this.wordFormationService.updateHit(this.currentWordFormation);
			}
		}
		
		private function continueHandler(event:MouseEvent): void {
			
			this.currentWordFormation = this.wordFormationService.getNext();
			
			if (this.currentWordFormation != null) {
				
				this.view.checkBt.enabled = true;
				this.view.continueBt.enabled = true;
				this.view.showAnswerBt.enabled = true;
				
				this.clean();
				this.view.wordLb.text = this.currentWordFormation.getWordRoot();
				this.view.sentence1Id.text = this.currentWordFormation.getSentence1();
				this.view.sentence2Id.text = this.currentWordFormation.getSentence2();
				this.view.sentence3Id.text = this.currentWordFormation.getSentence3();
				
			} else {
				this.view.checkBt.enabled = false;
				this.view.continueBt.enabled = false;
				this.view.showAnswerBt.enabled = false;
				
				this.clean();
			}
		}
		
		private function showAnswerHandler(event:MouseEvent): void {
			
			this.view.checkBt.enabled = false;
			
			this.view.answer1Id.text = this.currentWordFormation.getSentenceKey1();
			this.view.answer2Id.text = this.currentWordFormation.getSentenceKey2();
			this.view.answer3Id.text = this.currentWordFormation.getSentenceKey3();
		}
		
		private function clean(): void {
			
			this.view.wordLb.text = "";
			
			this.view.sentence1Id.text = "";
			this.view.sentence2Id.text = "";
			this.view.sentence3Id.text = "";
			
			this.view.answer1Id.text = "";
			this.view.answer2Id.text = "";
			this.view.answer3Id.text = "";
			
			this.view.answer1Id.setStyle("color", "#FFFFFF");
			this.view.answer2Id.setStyle("color", "#FFFFFF");
			this.view.answer3Id.setStyle("color", "#FFFFFF");
		}
		
	}

}