package org.gusmp.model 
{
	/**
	 * ...
	 * @author Gus
	 */
	public class PhrasalVerbModel 
	{
		
		private var phrasalVerbId:int;
		private var meaning:String;
		private var example:String;
		private var key:String;
		private var level:String;
		private var hits:int;
		private var date:Date;
		
		
		public function setPhrasalVerbId(phrasalVerbId:int):void {
			this.phrasalVerbId = phrasalVerbId;
		}
		public function getPhrasalVerbId(): int {
			return this.phrasalVerbId;
		}
		
		
		public function setMeaning(meaning:String):void {
			this.meaning = meaning;
		}
		public function getMeaning(): String {
			return this.meaning;
		}
		
		
		public function setExample(example:String):void {
			this.example = example;
		}
		public function getExample(): String {
			return this.example;
		}
		
		
		public function setKey(key:String):void {
			this.key = key.toUpperCase();
		}
		public function getKey(): String {
			return this.key;
		}
		
		
		public function setLevel(level:String):void {
			this.level = level;
		}
		public function getLevel(): String {
			return this.level;
		}
		
		
		public function setHits(hits:int):void {
			this.hits = hits;
		}
		public function getHits(): int {
			return this.hits;
		}
		
		
		public function setDate(date:Date):void {
			this.date = date;
		}
		public function getDate(): Date {
			return this.date;
		}
	}

}