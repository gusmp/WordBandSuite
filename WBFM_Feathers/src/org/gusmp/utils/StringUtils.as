package org.gusmp.utils 
{
	/**
	 * ...
	 * @author Gus
	 */
	public class StringUtils 
	{
		
		public static function trim(str:String): String 
		{
			return str.replace(/^\s+|\s+$/g, '');
		}
		
	}

}