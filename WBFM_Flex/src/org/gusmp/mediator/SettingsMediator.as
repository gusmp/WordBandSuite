package org.gusmp.mediator 
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	import mx.events.CloseEvent;
	import spark.components.Alert;
	import spark.components.Button;

	
	import robotlegs.bender.bundles.mvcs.Mediator;
	import robotlegs.bender.extensions.mediatorMap.api.IMediator;

	import org.gusmp.views.*;
	import org.gusmp.service.*;
	
	
	/**
	 * ...
	 * @author Gus
	 */
	public class SettingsMediator extends Mediator
	{
		[Inject]
		public var view:SettingsView;

		[Inject]
		public var updateService:UpdateService;
		
		override public function initialize():void
		{
			view.updatePvBt.addEventListener(MouseEvent.CLICK, update);
			view.updateWfBt.addEventListener(MouseEvent.CLICK, update);
		}
		
		
		override public function destroy():void
		{
			view.updatePvBt.removeEventListener(MouseEvent.CLICK, update);
			view.updateWfBt.removeEventListener(MouseEvent.CLICK, update);
		}
		
		protected function update(event:MouseEvent): void {
			
			
			var updateBt:Button = Button(event.currentTarget);
			var callback:Function;
			if (updateBt.id == this.view.updatePvBt.id) {
				callback = closePhrasalVerbAlertHandler;
			}
			else {
				callback = closeWordFormationAlertHandler;
			} 
			
			Alert.show("Do you really want to update? (Requires Internet connection)", 
				"Warning",
				Alert.YES | Alert.NO, 
				view,
				callback);
			
		}
		
		private function closePhrasalVerbAlertHandler(e:CloseEvent):void {
			
			if (e.detail == Alert.YES) {
				this.view.updatePvBt.enabled = false;
				this.view.updateWfBt.enabled = false;
				this.view.busyBi.visible = true;
				updateService.updatePhrasalVerbs(this.view.urlUpdatePvTi.text, updateViewWhenUpdateFinished);
			}
		}
		
		private function closeWordFormationAlertHandler(e:CloseEvent):void {
			
			if (e.detail == Alert.YES) {
				this.view.updatePvBt.enabled = false;
				this.view.updateWfBt.enabled = false;
				this.view.busyBi.visible = true;
				updateService.updateWordFormation(this.view.urlUpdateWfTi.text, updateViewWhenUpdateFinished);
			}
		}
		
		private function updateViewWhenUpdateFinished(error: Boolean, errorMsg: String): void {
			this.view.updatePvBt.enabled = true;
			this.view.updateWfBt.enabled = true;
			this.view.busyBi.visible = false;
			
			if (error == false) {
				Alert.show("Update completed!");
			} else {
				Alert.show(errorMsg);
			}
		}
	}

}