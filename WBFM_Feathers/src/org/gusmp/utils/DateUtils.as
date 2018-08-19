package org.gusmp.utils 
{
	/**
	 * ...
	 * @author Gus
	 */
	public class DateUtils 
	{
		
		/**
		 * Get a Date from a String date in format YYYYMMDD
		 * @param	dateString
		 * @return
		 */
		public static function getDate(dateString: String): Date
		{
			var year:int = int(dateString.substr(0,4));
			var month:int = int(dateString.substr(4,2))-1;
			var day:int = int(dateString.substr(6, 2));
			
			return new Date(year, month, day);
		}
		
		/**
		 * Returns an date in String with format YYYYMMDD from a Date
		 * @param	date
		 * @return
		 */
		public static function getStringInFormatYYYYMMDDFromDate(date:Date): String
		{
			var dateString:String;
			
			dateString = date.getFullYear().toString();
			
			if ((date.getMonth() + 1) < 10) {
				dateString += "0" + (date.getMonth() + 1).toString();
			} else {
				dateString += (date.getMonth() + 1).toString();
			}
			
			if (date.getDate() < 10) {
				dateString += "0" + date.getDate().toString();
			} else {
				dateString += date.getDate().toString();
			}
			
			return dateString;
		}
		
		public static function getDateFromStringInFormatDDsMMsYYYY(date:String):Date {
			
			var dateArray:Array = date.split("/");
			
			return new Date(dateArray[2], dateArray[1] - 1, dateArray[0]);
			
		}
	}
}