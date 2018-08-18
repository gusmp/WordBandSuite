package org.gusmp.mediator 
{
	//import flash.events.Event;
	import flash.events.MouseEvent;
	import org.gusmp.model.ConfigurationModel;
	import org.gusmp.service.ConfigurationService;
	import robotlegs.bender.bundles.mvcs.Mediator;
	import robotlegs.bender.extensions.mediatorMap.api.IMediator;
	

	import org.gusmp.views.MainView;
	/**
	 * ...
	 * @author Gus
	 */
	public class MainMediator extends Mediator
	{
		
		[Inject]
		public var view:MainView;
		
		[Inject]
		public var configService:ConfigurationService;
		
		private var configurationModel:ConfigurationModel;
		
		override public function initialize():void
		{
			this.view.levelTw.addEventListener(MouseEvent.CLICK, selectLevel);
			this.configurationModel = configService.getConfiguration();
			
			if (this.configurationModel.getLevel() == ConfigurationModel.CAE) {
				this.view.levelTw.selected = false;
			} else {
				this.view.levelTw.selected = true;
			}
			
			this.view.currentLevelLb.text = this.configurationModel.getLevel();
			
		}
		
		
		override public function destroy():void
		{
			super.destroy();
			view.levelTw.removeEventListener(MouseEvent.CLICK,selectLevel);
		}
		
		protected function selectLevel(event:MouseEvent): void {
			
			if(this.view.levelTw.selected == true) {
				this.configurationModel.setLevel(ConfigurationModel.CAE);
			} else {
				this.configurationModel.setLevel(ConfigurationModel.CPE);
			}
			
			this.view.currentLevelLb.text = this.configurationModel.getLevel();
			
			configService.saveConfiguration(this.configurationModel);
			
		}
	}
}