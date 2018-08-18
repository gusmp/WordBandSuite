package org.gusmp.repository 
{

	import flash.utils.Dictionary;
	import mx.formatters.DateFormatter;
	import org.gusmp.model.ConfigurationModel;
	import org.gusmp.model.WordFormationModel;
	import org.gusmp.utils.DateUtils;
	/**
	 * ...
	 * @author Gus
	 */
	public class WordFormationRepository 
	{
		
		[Inject]
		public var dataBaseRepository: DataBaseRepository;
		
		private var dateFormater:DateFormatter;
		
		public function WordFormationRepository() {
			
			this.dateFormater = new DateFormatter();
			this.dateFormater.formatString = "YYYYMMDD";
		}
		
		
		public function save(wordFormationModel:WordFormationModel): void {
			
			var params:Dictionary = new Dictionary();
			
			params[":wordRoot"] = wordFormationModel.getWordRoot();
			params[":sentence1"] = wordFormationModel.getSentence1();
			params[":sentenceKey1"] = wordFormationModel.getSentenceKey1();
			params[":sentence2"] = wordFormationModel.getSentence2();
			params[":sentenceKey2"] = wordFormationModel.getSentenceKey2();
			params[":sentence3"] = wordFormationModel.getSentence3();
			params[":sentenceKey3"] = wordFormationModel.getSentenceKey3();
			params[":level"] = wordFormationModel.getLevel();
			params[":hits"] = 0;
			//params[":date"] = wordFormationModel.getDate(); // '2018-02-01'
			params[":date"] = this.dateFormater.format(wordFormationModel.getDate());
			
			dataBaseRepository.execute("INSERT INTO WORDFORMATION(wordRoot, sentence1, sentenceKey1, sentence2, sentenceKey2, sentence3, sentenceKey3, level,hits,date) VALUES(:wordRoot, :sentence1, :sentenceKey1, :sentence2, :sentenceKey2, :sentence3, :sentenceKey3, :level, :hits, :date)", params);
		}
		
		public function find(wordFormationModel:WordFormationModel): Array {
			
			var params:Dictionary = new Dictionary();
			params[":wordRoot"] = wordFormationModel.getWordRoot();
			params[":sentence1"] = wordFormationModel.getSentence1();
			params[":sentence2"] = wordFormationModel.getSentence2();
			params[":sentence3"] = wordFormationModel.getSentence3();
			params[":level"] = wordFormationModel.getLevel();
			
			var result:Array = dataBaseRepository.executeSql("SELECT * FROM WORDFORMATION WHERE wordRoot=:wordRoot AND sentence1=:sentence1 AND sentence2=:sentence2 AND sentence3=:sentence3 AND level=:level", params);
			
			if ((result != null) && (result.length > 0)) {
				
				var wfList:Array = new Array(); 
				
				for (var i:uint = 0; i < result.length; i++) {
					
					wfList.push(map2WordFormationModel(result[i]));
				}
				
				return wfList;
				
				
			} else {
				return null;
			}
		}
		
		public function getNext(configuration: ConfigurationModel): WordFormationModel {
			
			var params:Dictionary = new Dictionary();
			
			params[":level"] = configuration.getLevel();
			params[":maxResults"] = configuration.getMaxResults();
			
			var result:Array = dataBaseRepository.executeSql("SELECT * FROM WORDFORMATION WHERE level=:level ORDER BY hits ASC LIMIT :maxResults", params);
			
			if ((result == null) || (result.length == 0)) {
				return null;
			}
			
			var index: int
			do {
				index = Math.floor( Math.random() * 21);
			} while ((index < 0) && (index > (result.length-1)) );
			
			return map2WordFormationModel(result[index]);
			
		}
		
		public function updateHit(wordFormationModel:WordFormationModel): void {
			
			var params:Dictionary = new Dictionary();
			
			params[":wordFormationId"] = wordFormationModel.getWordFormationId();
			params[":hits"] = wordFormationModel.getHits();
			
			dataBaseRepository.execute("UPDATE WORDFORMATION SET hits=:hits WHERE wordFormationId=:wordFormationId", params);
		}
		
		private function map2WordFormationModel(result:Object): WordFormationModel {
			
			var wf:WordFormationModel = new WordFormationModel();
					
			wf.setDate(DateUtils.getDate(result["date"]));
			wf.setHits(result["hits"]);
			wf.setLevel(result["level"]);
			wf.setSentence1(result["sentence1"]);
			wf.setSentence2(result["sentence2"]);
			wf.setSentence3(result["sentence3"]);
			wf.setSentenceKey1(result["sentenceKey1"]);
			wf.setSentenceKey2(result["sentenceKey2"]);
			wf.setSentenceKey3(result["sentenceKey3"]);
			wf.setWordFormationId(result["wordFormationId"]);
			wf.setWordRoot(result["wordRoot"]);
			
			return wf;
		}
	}
}