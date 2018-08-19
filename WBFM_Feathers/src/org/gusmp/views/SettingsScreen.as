package org.gusmp.views 
{
	import feathers.controls.Button;
	import feathers.controls.PanelScreen;
	import feathers.controls.TextInput;
	import feathers.controls.Alert;
	import feathers.data.ArrayCollection;
	import feathers.layout.VerticalLayout;
	import feathers.layout.HorizontalAlign;
	import feathers.layout.VerticalAlign;
	import starling.events.Event;

	import org.gusmp.service.UpdateService;
	import org.gusmp.enum.AlertEnum;
	
	/**
	 * ...
	 * @author Gus
	 */
	public class SettingsScreen extends PanelScreen
	{
	
		private var urlUpdatePvTi:TextInput;
		private var updatePvBt:Button;
		
		private var urlUpdateWfTi:TextInput;
		private var updateWfBt:Button;
		
		private var updateService:UpdateService = Main.appContext.injector.getInstance(UpdateService);

		
		override protected function initialize():void
		{
			super.initialize();
			
			this.buildView();
			
		}
		
		private function buildView():void {
		
			this.title = "Settings";
			
			var mainLayout:VerticalLayout = new VerticalLayout();
			mainLayout.horizontalAlign = HorizontalAlign.CENTER;
			mainLayout.verticalAlign = VerticalAlign.MIDDLE;
			mainLayout.gap = this.stage.stageWidth * 0.02;
			this.layout = mainLayout;
			
			this.urlUpdatePvTi = new TextInput();
			this.urlUpdatePvTi.width = this.stage.stageWidth * 0.9;
			this.urlUpdatePvTi.text = "http://192.168.1.133:8081/WBFMServer/getpv";
			this.addChild(this.urlUpdatePvTi);
			
			this.updatePvBt = new Button();
			this.updatePvBt.label = "Update Phrasal Verbs";
			this.updatePvBt.addEventListener( Event.TRIGGERED, update);
			this.addChild(this.updatePvBt);
			
			this.urlUpdateWfTi = new TextInput();
			this.urlUpdateWfTi.width = this.stage.stageWidth * 0.9;
			this.urlUpdateWfTi.text = "http://192.168.1.133:8081/WBFMServer/getwf";
			this.addChild(this.urlUpdateWfTi);
			
			this.updateWfBt = new Button();
			this.updateWfBt.label = "Update Word Formation";
			this.updateWfBt.addEventListener( Event.TRIGGERED, update);
			this.addChild(this.updateWfBt);
			
		}
		
		protected function update(event:Event): void {
			
			var updateBt:Button = Button(event.currentTarget);
			var callback:Function;
			if (updateBt.label == this.updatePvBt.label) {
				callback = closePhrasalVerbAlertHandler;
			}
			else {
				callback = closeWordFormationAlertHandler;
			}
			
			Alert.show( "Do you really want to update? (Requires Internet connection)", 
				"Warning", 
				new ArrayCollection(
					[
						{ label: AlertEnum.YES, triggered: callback },
						{ label: AlertEnum.NO, triggered: callback },
					]) 
			);
		}
			
		private function closePhrasalVerbAlertHandler(e:Event, data:Object):void {
			
			var bt:Button = Button(e.currentTarget);
			
			if (bt.label == AlertEnum.YES) {
				this.updatePvBt.isEnabled = false;
				this.updateWfBt.isEnabled = false;
				//this.busyBi.visible = true;
				updateService.updatePhrasalVerbs(this.urlUpdatePvTi.text, updateViewWhenUpdateFinished);
			}
		}
		
		private function closeWordFormationAlertHandler(e:Event, data:Object):void {
			
			var bt:Button = Button(e.currentTarget);
			
			if (bt.label == AlertEnum.YES) {
				this.updatePvBt.isEnabled = false;
				this.updateWfBt.isEnabled = false;
				//this.busyBi.visible = true;
				updateService.updateWordFormation(this.urlUpdateWfTi.text, updateViewWhenUpdateFinished);
			}
		}
		
		private function updateViewWhenUpdateFinished(error: Boolean, errorMsg: String): void {
			
			this.updatePvBt.isEnabled = true;
			this.updateWfBt.isEnabled = true;
			//this.busyBi.visible = false;
			
			var msg:String = "";
			if (error == false) {
				msg = "Update completed!";
			} else {
				msg = errorMsg;
			}
			
			Alert.show(msg, 
				"", 
				new ArrayCollection(
					[
						{ label: AlertEnum.OK }
					]) 
			);
		}
	}
}