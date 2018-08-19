package org.gusmp.views 
{
	import feathers.controls.TabNavigator;
	import feathers.controls.TabNavigatorItem;
	import feathers.themes.MetalWorksMobileTheme;
	import starling.display.Sprite;
	import starling.events.Event;
	import feathers.motion.Cube;
	
	
	public class MainView extends Sprite
	{
		public function MainView() 
		{
			this.addEventListener(Event.ADDED_TO_STAGE, this.addedToStageHandler);
		}
		
		protected function addedToStageHandler(event:Event):void
		{
			this.removeEventListener(Event.ADDED_TO_STAGE, this.addedToStageHandler);
			
			new MetalWorksMobileTheme();
			
			var navigator:TabNavigator = new TabNavigator();
			
			var homeTab:TabNavigatorItem = new TabNavigatorItem(HomeScreen, "Home" );
			navigator.addScreen( "homeTabId", homeTab );
			
			var phrasalVerbTab:TabNavigatorItem = new TabNavigatorItem(PhrasalVerbScreen, "Phrasal Verb" );
			navigator.addScreen( "phrasalVerbId", phrasalVerbTab );
			
			var wordFormationTab:TabNavigatorItem = new TabNavigatorItem(WordFormationScreen, "Word Formation" );
			navigator.addScreen( "wordFormationTabId", wordFormationTab );
			
			var settingsTab:TabNavigatorItem = new TabNavigatorItem(SettingsScreen, "Settings" );
			navigator.addScreen( "settingsId", settingsTab );
			
			this.addChild(navigator);
			
			navigator.transition = Cube.createCubeUpTransition();
			
			navigator.showScreen("homeTabId");
			navigator.validate();
		}
	}
}