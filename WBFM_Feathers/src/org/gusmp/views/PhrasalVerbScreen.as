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
	import org.gusmp.model.PhrasalVerbModel;
	import org.gusmp.service.PhrasalVerbService;
	import org.gusmp.utils.FontUtils;
	import org.gusmp.utils.StringUtils;
	
	import starling.events.Event;

	
	
	/**
	 * ...
	 * @author Gus
	 */
	public class PhrasalVerbScreen extends PanelScreen
	{
	
		private var meaningTa:TextArea;
		private var exampleTa:TextArea;
		
		private var answerLb:TextInput;
		private var checkBt:Button;
		private var continueBt:Button;
		private var showAnswerBt:Button;
		
		private var phrasalVerbService:PhrasalVerbService = Main.appContext.injector.getInstance(PhrasalVerbService);
		private var currentPhrasalVerbModel:PhrasalVerbModel;
		
		override protected function initialize():void
		{
			super.initialize();
			
			this.buildView();
			
			this.continueHandler(null);
		}
	
		private function buildView():void {
		
			this.title = "Phrasal Verbs";
			
			var mainLayout:VerticalLayout = new VerticalLayout();
			mainLayout.horizontalAlign = HorizontalAlign.CENTER;
			mainLayout.verticalAlign = VerticalAlign.MIDDLE;
			mainLayout.gap = this.stage.stageWidth * 0.05;
			this.layout = mainLayout;
			
			
			this.meaningTa = new TextArea();
			this.meaningTa.isEditable = false;
			this.meaningTa.width =  this.stage.stageWidth * 0.9;
			this.addChild(this.meaningTa);
			
			this.exampleTa = new TextArea();
			this.exampleTa.isEditable = false;
			this.exampleTa.width =  this.stage.stageWidth * 0.9;
			this.addChild(this.exampleTa);
			
			this.answerLb = new TextInput();
			this.answerLb.width = this.stage.stageWidth * 0.9;
			this.addChild(this.answerLb);
			
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
			
			var success:Boolean = false;
			
			if (StringUtils.trim(this.answerLb.text.toUpperCase())  != this.currentPhrasalVerbModel.getKey()) {
				this.answerLb.fontStyles = FontUtils.errorStyle();
			} else {
				this.answerLb.fontStyles = FontUtils.successStyle();
				success = true;
			}
			
			
			if (success == true) {
				this.checkBt.isEnabled = false;
				this.showAnswerBt.isEnabled = false;
				this.currentPhrasalVerbModel.setHits(this.currentPhrasalVerbModel.getHits()+1);
				this.phrasalVerbService.updateHit(this.currentPhrasalVerbModel);
			}
		}
		
		private function continueHandler(event:Event): void {
			
			this.currentPhrasalVerbModel = this.phrasalVerbService.getNext();
			
			if (this.currentPhrasalVerbModel != null) {
				
				this.checkBt.isEnabled = true;
				this.continueBt.isEnabled = true;
				this.showAnswerBt.isEnabled = true;
				
				this.clean();
				this.meaningTa.text = this.currentPhrasalVerbModel.getMeaning();
				this.exampleTa.text = this.currentPhrasalVerbModel.getExample();
				
			} else {
				this.checkBt.isEnabled = false;
				this.continueBt.isEnabled = false;
				this.showAnswerBt.isEnabled = false;
				
				this.clean();
			}
		}
		
		private function showAnswerHandler(event:Event): void {
			
			this.checkBt.isEnabled = false;
			
			this.answerLb.text = this.currentPhrasalVerbModel.getKey();
		}
		
		private function clean(): void {
			
			this.meaningTa.text = "";
			this.exampleTa.text = "";
			this.answerLb.text = "";
			
			this.answerLb.fontStyles = FontUtils.normalStyle();
		}
	}
}