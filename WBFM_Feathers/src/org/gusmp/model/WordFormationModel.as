package org.gusmp.model 
{
	/**
	 * ...
	 * @author Gus
	 */
	public class WordFormationModel 
	{
		
		private var wordFormationId:int;
		private var wordRoot:String;
		
		private var sentence1:String;
		private var sentenceKey1:String;
		
		private var sentence2:String;
		private var sentenceKey2:String;
		
		private var sentence3:String;
		private var sentenceKey3:String;
		
		private var level:String; 
		private var hits:int;
		private var date:Date;
		
		
		public function setWordFormationId(wordFormationId:int):void {
			this.wordFormationId = wordFormationId;
		}
		public function getWordFormationId(): int {
			return this.wordFormationId;
		}
		
		
		public function setWordRoot(wordRoot:String):void {
			this.wordRoot = wordRoot;
		}
		public function getWordRoot(): String {
			return this.wordRoot;
		}
		
		
		public function setSentence1(sentence1:String):void {
			this.sentence1 = sentence1;
		}
		public function getSentence1(): String {
			return this.sentence1;
		}
		
		
		public function setSentenceKey1(sentenceKey1:String):void {
			this.sentenceKey1 = sentenceKey1.toUpperCase();
		}
		public function getSentenceKey1(): String {
			return this.sentenceKey1;
		}
		
		
		public function setSentence2(sentence2:String):void {
			this.sentence2 = sentence2;
		}
		public function getSentence2(): String {
			return this.sentence2;
		}
		
		
		public function setSentenceKey2(sentenceKey2:String):void {
			this.sentenceKey2 = sentenceKey2.toUpperCase();
		}
		public function getSentenceKey2(): String {
			return this.sentenceKey2;
		}
		
		
		public function setSentence3(sentence3:String):void {
			this.sentence3 = sentence3;
		}
		public function getSentence3(): String {
			return this.sentence3;
		}
		
		
		public function setSentenceKey3(sentenceKey3:String):void {
			this.sentenceKey3 = sentenceKey3.toUpperCase();
		}
		public function getSentenceKey3(): String {
			return this.sentenceKey3;
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