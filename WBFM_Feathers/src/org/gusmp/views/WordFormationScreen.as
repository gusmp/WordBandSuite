package org.gusmp.views 
{
	import feathers.controls.Button;
	import feathers.controls.ImageLoader;
	import feathers.controls.Label;
	import feathers.controls.LayoutGroup;
	import feathers.controls.PanelScreen;
	import feathers.controls.TextArea;
	import feathers.controls.TextInput;
	import feathers.layout.HorizontalLayout;
	import feathers.layout.VerticalLayout;
	import feathers.layout.HorizontalAlign;
	import feathers.layout.VerticalAlign;
	import org.gusmp.model.WordFormationModel;
	import org.gusmp.service.WordFormationService;
	import org.gusmp.utils.FontUtils;
	import org.gusmp.utils.StringUtils;
	
	import starling.events.Event;
	
	/**
	 * ...
	 * @author Gus
	 */
	public class WordFormationScreen extends PanelScreen
	{
		private var wordLb:Label;
		
		private var sentence1Id:TextArea;
		private var answer1Id:TextInput;
		
		private var sentence2Id:TextArea;
		private var answer2Id:TextInput;
		
		private var sentence3Id:TextArea;
		private var answer3Id:TextInput;
		
		private var checkBt:Button;
		private var continueBt:Button;
		private var showAnswerBt:Button;
		
		private var wordFormationService:WordFormationService = Main.appContext.injector.getInstance(WordFormationService);
		private var currentWordFormation:WordFormationModel;

		
		override protected function initialize():void
		{
			super.initialize();
			
			this.buildView();
			
			this.continueHandler(null);
		}
		
		private function buildView():void {
		
			this.title = "Word Formation";
			
			var mainLayout:VerticalLayout = new VerticalLayout();
			mainLayout.horizontalAlign = HorizontalAlign.CENTER;
			mainLayout.verticalAlign = VerticalAlign.MIDDLE;
			mainLayout.gap = this.stage.stageWidth * 0.01;
			this.layout = mainLayout;
			
			this.wordLb = new Label();
			this.wordLb.width = this.stage.stageWidth * 0.9;
			this.addChild(this.wordLb);
			
			this.sentence1Id = new TextArea();
			this.sentence1Id.isEditable = false;
			this.sentence1Id.width =  this.stage.stageWidth * 0.9;
			this.sentence1Id.height = this.stage.stageHeight * 0.2;
			this.addChild(this.sentence1Id);
			
			this.answer1Id = new TextInput();
			this.answer1Id.width = this.stage.stageWidth * 0.9;
			this.addChild(this.answer1Id);
			
			this.sentence2Id = new TextArea();
			this.sentence2Id.isEditable = false;
			this.sentence2Id.width =  this.stage.stageWidth * 0.9;
			this.sentence2Id.height = this.stage.stageHeight * 0.2;
			this.addChild(this.sentence2Id);
			
			this.answer2Id = new TextInput();
			this.answer2Id.width = this.stage.stageWidth * 0.9;
			this.addChild(this.answer2Id);
			
			this.sentence3Id = new TextArea();
			this.sentence3Id.isEditable = false;
			this.sentence3Id.width =  this.stage.stageWidth * 0.9;
			this.sentence3Id.height = this.stage.stageHeight * 0.2;
			this.addChild(this.sentence3Id);
			
			this.answer3Id = new TextInput();
			this.answer3Id.width = this.stage.stageWidth * 0.9;
			this.addChild(this.answer3Id);
			
			var buttonsGroup:LayoutGroup = new LayoutGroup();
			var buttonsGroupLayout:HorizontalLayout = new HorizontalLayout();
			buttonsGroupLayout.gap = this.stage.stageHeight * 0.05;
			buttonsGroupLayout.horizontalAlign = HorizontalAlign.CENTER;
			buttonsGroup.layout = buttonsGroupLayout;
			
			this.checkBt = new Button();
			this.checkBt.label = "Check!";
			this.checkBt.addEventListener( Event.TRIGGERED, checkAnswerHandler);
			
			this.continueBt = new Button();
			this.continueBt.label = "Continue";
			this.continueBt.addEventListener( Event.TRIGGERED, continueHandler);
			
			this.showAnswerBt = new Button();
			this.showAnswerBt.label = "I give up";
			this.showAnswerBt.addEventListener( Event.TRIGGERED, showAnswerHandler);
			
			buttonsGroup.addChildAt(this.checkBt, 0);
			buttonsGroup.addChildAt(this.continueBt, 1);
			buttonsGroup.addChildAt(this.showAnswerBt, 2);
			
			this.addChild(buttonsGroup);
		}
		
		private function checkAnswerHandler(event:Event): void {
			
			var success1:Boolean = false;
			var success2:Boolean = false;
			var success3:Boolean = false;
			
			if (StringUtils.trim(this.answer1Id.text.toUpperCase())  != this.currentWordFormation.getSentenceKey1()) {
				this.answer1Id.fontStyles = FontUtils.errorStyle();
			} else {
				this.answer1Id.fontStyles = FontUtils.successStyle();
				success1 = true;
			}
			
			if (StringUtils.trim(this.answer2Id.text.toUpperCase())  != this.currentWordFormation.getSentenceKey2()) {
				this.answer2Id.fontStyles = FontUtils.errorStyle();
			} else {
				this.answer2Id.fontStyles  = FontUtils.successStyle();
				success2 = true;
			}
				
			if (StringUtils.trim(this.answer3Id.text.toUpperCase())  != this.currentWordFormation.getSentenceKey3()) {
				this.answer3Id.fontStyles = FontUtils.errorStyle();
			} else {
				this.answer3Id.fontStyles  = FontUtils.successStyle();
				success3 = true;
			}
			
			if ((success1 == true) && (success2 == true) && (success3 == true)) {
				this.checkBt.isEnabled = false;
				this.showAnswerBt.isEnabled = false;
				this.currentWordFormation.setHits(this.currentWordFormation.getHits()+1);
				this.wordFormationService.updateHit(this.currentWordFormation);
			}
		}
		
		private function continueHandler(event:Event): void {
			
			this.currentWordFormation = this.wordFormationService.getNext();
			
			if (this.currentWordFormation != null) {
				
				this.checkBt.isEnabled = true;
				this.continueBt.isEnabled = true;
				this.showAnswerBt.isEnabled = true;
				
				this.clean();
				this.wordLb.text = this.currentWordFormation.getWordRoot();
				this.sentence1Id.text = this.currentWordFormation.getSentence1();
				this.sentence2Id.text = this.currentWordFormation.getSentence2();
				this.sentence3Id.text = this.currentWordFormation.getSentence3();
				
			} else {
				this.checkBt.isEnabled = false;
				this.continueBt.isEnabled = false;
				this.showAnswerBt.isEnabled = false;
				
				this.clean();
			}
		}
		
		private function showAnswerHandler(event:Event): void {
			
			this.checkBt.isEnabled = false;
			
			this.answer1Id.text = this.currentWordFormation.getSentenceKey1();
			this.answer2Id.text = this.currentWordFormation.getSentenceKey2();
			this.answer3Id.text = this.currentWordFormation.getSentenceKey3();
		}
		
		private function clean(): void {
			
			this.wordLb.text = "";
			
			this.sentence1Id.text = "";
			this.sentence2Id.text = "";
			this.sentence3Id.text = "";
			
			this.answer1Id.text = "";
			this.answer2Id.text = "";
			this.answer3Id.text = "";
			
			this.answer1Id.fontStyles = FontUtils.normalStyle();
			this.answer2Id.fontStyles = FontUtils.normalStyle();
			this.answer3Id.fontStyles = FontUtils.normalStyle();
		}
	}
}