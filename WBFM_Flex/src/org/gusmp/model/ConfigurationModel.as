package org.gusmp.model 
{
	/**
	 * ...
	 * @author Gus
	 */
	public class ConfigurationModel 
	{
		
		public static var CAE:String = "CAE";
		public static var CPE:String = "CPE";
		
		private var level:String;
		private var maxResults:int;
		
		public function setLevel(level:String): void {
			this.level = level;
		}
		
		public function getLevel(): String {
			return this.level;
		}
		
		public function setMaxResults(maxResults:int): void {
			this.maxResults = maxResults;
		}
		
		public function getMaxResults(): int {
			return this.maxResults;
		}
	}
}