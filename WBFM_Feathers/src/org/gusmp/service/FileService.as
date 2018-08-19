package org.gusmp.service 
{
	import flash.events.Event;
	import flash.filesystem.File;
	import flash.filesystem.FileStream;
	import flash.filesystem.FileMode;
	import flash.net.URLLoader;
	import flash.net.URLLoaderDataFormat;
	import flash.net.URLRequest;
	import flash.net.FileReference;
	import flash.utils.ByteArray;
	import flash.events.IOErrorEvent;
	/**
	 * ...
	 * @author Gus
	 */
	public class FileService 
	{
		
		private var urlLoader:URLLoader;
		private var callBack:Function;
		
		private function successDownloadCallback(event:Event): void {
		
			var fileData:ByteArray = new ByteArray();
			var content:String = this.urlLoader.data;
			this.callBack(content.split("\n"), false, "");
		}
		
		private function errorDownloadCallback(event: IOErrorEvent): void {
			this.callBack(null, true, event.text);
		}
			
		public function downloadFile(url:String, callBack: Function): void 
		{
			var urlReq:URLRequest = new URLRequest(url);
			this.urlLoader = new URLLoader();
			this.callBack = callBack;
			
			this.urlLoader.dataFormat = URLLoaderDataFormat.TEXT;
			this.urlLoader.addEventListener(Event.COMPLETE, successDownloadCallback);
			this.urlLoader.addEventListener(IOErrorEvent.IO_ERROR, errorDownloadCallback);
			
			this.urlLoader.load(urlReq);
		}
		
		public function deleteFile(pFile:String): void
		{
			var f:File = new File(pFile);
			f.deleteFile();
		}
		
		
		private function onFinishedRead(e:Event):void {
			var myArrayOfLines:Array = e.target.data.split(/\n/);myArrayOfLines.length
		}
		
		public function readFile(pFile:String, callBack:Function): void 
		{
			var myTextLoader:URLLoader = new URLLoader();
			myTextLoader.addEventListener(Event.COMPLETE, callBack);
			myTextLoader.load(new URLRequest(pFile));
			
		}
	}

}