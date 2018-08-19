package org.gusmp.views 
{
	import feathers.controls.Label;
	import feathers.controls.Button;
	import feathers.controls.LayoutGroup;
	import feathers.controls.Screen;
	import feathers.controls.ToggleSwitch;
	import feathers.layout.HorizontalLayout;
	import feathers.layout.VerticalLayout;
	import feathers.layout.HorizontalAlign;
	import feathers.layout.VerticalAlign;
	import starling.events.Event;
	import starling.text.TextFormat;

	import org.gusmp.service.*;
	import org.gusmp.model.ConfigurationModel;
	
	/**
	 * ...
	 * @author Gus
	 */
	public class HomeScreen extends Screen
	{

		private var currentLevelLb:Label;
		private var levelTw:ToggleSwitch;
		
		private var configurationService:ConfigurationService = Main.appContext.injector.getInstance(ConfigurationService);
		private var configurationModel:ConfigurationModel;
		
		public function HomeScreen() 
		{
			this.addEventListener(Event.ADDED_TO_STAGE, this.addedToStageHandler);
		}
		
		protected function addedToStageHandler(event:Event):void
		{
			this.removeEventListener(Event.ADDED_TO_STAGE, this.addedToStageHandler);
		
			var mainLayout:VerticalLayout = new VerticalLayout();
			mainLayout.horizontalAlign = HorizontalAlign.CENTER;
			mainLayout.verticalAlign = VerticalAlign.MIDDLE;
			mainLayout.gap = this.stage.stageWidth * 0.05;
			this.layout = mainLayout;
			
			this.addChild(buildTitle());
			
			this.addChild(buildLevelSelector());
			
			this.addChild(buildLevelInfo());
			
			this.updateView();
			
		}
		
		private function buildTitle():LayoutGroup {
			
			var titleFormat:TextFormat = new TextFormat("Helvetica", 50, 0xFF820B);
			titleFormat.bold = true;
			
			var wordLb:Label = new Label();
			wordLb.text = "Word";
			wordLb.fontStyles = titleFormat;
			
			var bandLb:Label = new Label();
			bandLb.text = "Band";
			bandLb.fontStyles = titleFormat;
			
			var forLb:Label = new Label();
			forLb.text = "For";
			forLb.fontStyles = titleFormat;
			
			var mobileLb:Label = new Label();
			mobileLb.text = "Mobile";
			mobileLb.fontStyles = titleFormat;
			
			var titleGroup:LayoutGroup = new LayoutGroup();
			var titleGroupLayout:VerticalLayout = new VerticalLayout();
			titleGroupLayout.gap = 0;
			titleGroupLayout.horizontalAlign = HorizontalAlign.LEFT;
			titleGroup.layout = titleGroupLayout;
			
			titleGroup.addChild(wordLb);
			titleGroup.addChild(bandLb);
			titleGroup.addChild(forLb);
			titleGroup.addChild(mobileLb);
			
			return titleGroup;
			
		}
		
		private function buildLevelSelector(): ToggleSwitch {
			
			this.levelTw = new ToggleSwitch();
			this.levelTw.isSelected = true;
			this.levelTw.onText = "CAE";
			this.levelTw.offText = "CPE";
			this.levelTw.addEventListener( Event.CHANGE, changeLevelHandler);
			
			return levelTw;
		}
		
		private function buildLevelInfo() : LayoutGroup {
			
			var levelGroup:LayoutGroup = new LayoutGroup();
			var levelGroupLayout:HorizontalLayout = new HorizontalLayout();
			levelGroupLayout.gap = 0;
			levelGroupLayout.horizontalAlign = HorizontalAlign.CENTER;
			levelGroup.layout = levelGroupLayout;
			
			var levelTextLb:Label = new Label();
			levelTextLb.text = "Selected level: ";
			
			this.currentLevelLb = new Label();
			this.currentLevelLb.text = "";
			
			levelGroup.addChild(levelTextLb);
			levelGroup.addChild(currentLevelLb);
			
			return levelGroup;
		}
		
		private function updateView(): void {
			
			this.configurationModel = this.configurationService.getConfiguration();
			
			if (this.configurationModel.getLevel() == ConfigurationModel.CAE) {
				this.levelTw.setSelectionWithAnimation(true);
			} else {
				this.levelTw.setSelectionWithAnimation(false); 
			}
			
			this.currentLevelLb.text = this.configurationModel.getLevel();
		}
		
		
		
		private function changeLevelHandler(event:Event): void {
			
			if(this.levelTw.isSelected == true) {
				this.configurationModel.setLevel(ConfigurationModel.CAE);
			} else {
				this.configurationModel.setLevel(ConfigurationModel.CPE);
			}
			
			this.currentLevelLb.text = this.configurationModel.getLevel();
			
			this.configurationService.saveConfiguration(this.configurationModel);
			
		}
	}
}