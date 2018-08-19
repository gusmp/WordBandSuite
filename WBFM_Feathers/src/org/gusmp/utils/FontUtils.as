package org.gusmp.utils 
{
	import starling.text.TextFormat;
	
	/**
	 * ...
	 * @author Gus
	 */
	public class FontUtils 
	{
		
		private static const fontName:String = "Source Sans Pro,Helvetica,_sans";
		private static const size:int = 12;
		private static const alignment:String = "left";
		
		private static const fontFormatError:TextFormat = new TextFormat(FontUtils.fontName);
		private static const fontFormatSuccess:TextFormat = new TextFormat(FontUtils.fontName);
		private static const fontFormatNormal:TextFormat = new TextFormat(FontUtils.fontName);
		
		public static function errorStyle(): TextFormat
		{
			fontFormatError.color = 0xFF0000;
			fontFormatError.horizontalAlign = FontUtils.alignment;
			fontFormatError.size = FontUtils.size;
			
			return fontFormatError;
			
		}
		
		public static function successStyle(): TextFormat
		{
			fontFormatSuccess.color = 0x00FF00;
			fontFormatSuccess.horizontalAlign = FontUtils.alignment;
			fontFormatSuccess.size = FontUtils.size;
			
			return fontFormatSuccess;
		}
		
		
		public static function normalStyle() : TextFormat
		{
			fontFormatNormal.color = 0xFFFFFF;
			fontFormatNormal.horizontalAlign = FontUtils.alignment;
			fontFormatNormal.size = FontUtils.size;
			
			return fontFormatNormal;
		}
	}
}