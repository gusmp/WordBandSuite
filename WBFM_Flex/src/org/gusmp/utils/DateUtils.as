package org.gusmp.utils 
{
	/**
	 * ...
	 * @author Gus
	 */
	public class DateUtils 
	{
		
		public static function getDate(dateString: String): Date
		{
			var year:int = int(dateString.substr(0,4));
			var month:int = int(dateString.substr(4,2))-1;
			var day:int = int(dateString.substr(6, 2));
			
			return new Date(year, month, day);
		}
	}
}