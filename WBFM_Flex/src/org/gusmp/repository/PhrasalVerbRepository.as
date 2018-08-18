package org.gusmp.repository 
{

	import flash.utils.Dictionary;
	import org.gusmp.mediator.PhrasalVerbMediator;
	import org.gusmp.model.ConfigurationModel;
	import org.gusmp.model.PhrasalVerbModel;
	import org.gusmp.utils.DateUtils;
	import mx.formatters.DateFormatter;
	/**
	 * ...
	 * @author Gus
	 */
	public class PhrasalVerbRepository 
	{
		
		[Inject]
		public var dataBaseRepository: DataBaseRepository;
		
		private var dateFormater:DateFormatter;
		
		public function PhrasalVerbRepository() {
			
			this.dateFormater = new DateFormatter();
			this.dateFormater.formatString = "YYYYMMDD";
		}
		
		public function save(phrasalVerbModel:PhrasalVerbModel): void {
			
			var params:Dictionary = new Dictionary();
			
			params[":meaning"] = phrasalVerbModel.getMeaning();
			params[":example"] = phrasalVerbModel.getExample();
			params[":key"] = phrasalVerbModel.getKey();
			params[":level"] = phrasalVerbModel.getLevel();
			params[":hits"] = 0;
			//params[":date"] = phrasalVerbModel.getDate(); // '2018-02-01'
			params[":date"] = this.dateFormater.format(phrasalVerbModel.getDate());
			
			dataBaseRepository.execute("INSERT INTO PHRASALVERB(meaning,example,key,level,hits,date) VALUES(:meaning,:example,:key,:level,:hits,:date)", params);
		}
		
		public function find(phrasalVerbModel:PhrasalVerbModel): Array {
			
			var params:Dictionary = new Dictionary();
			params[":meaning"] = phrasalVerbModel.getMeaning();
			params[":example"] = phrasalVerbModel.getExample();
			params[":level"] = phrasalVerbModel.getLevel();
			
			var result:Array = dataBaseRepository.executeSql("SELECT * FROM PHRASALVERB WHERE meaning=:meaning AND example=:example AND level=:level", params);
			
			if ((result != null) && (result.length > 0)) {
				
				var pvList:Array = new Array(); 
				
				for (var i:uint = 0; i < result.length; i++) {
					
					pvList.push(map2PhrasalVerbModel(result[i]));
				}
				
				return pvList;
				
				
			} else {
				return null;
			}
		}
		
		public function getNext(configuration: ConfigurationModel): PhrasalVerbModel {
			
			var params:Dictionary = new Dictionary();
			
			params[":level"] = configuration.getLevel();
			params[":maxResults"] = configuration.getMaxResults();
			
			var result:Array = dataBaseRepository.executeSql("SELECT * FROM PHRASALVERB WHERE level=:level ORDER BY hits ASC LIMIT :maxResults", params);
			
			if ((result == null) || (result.length == 0)) {
				return null;
			}
			
			var index: int
			do {
				index = Math.floor( Math.random() * 21);
			} while ((index < 0) && (index > (result.length-1)) );
			
			return map2PhrasalVerbModel(result[index]);
			
		}
		
		public function updateHit(phrasalVerbModel:PhrasalVerbModel): void {
			
			var params:Dictionary = new Dictionary();
			
			params[":phrasalVerbId"] = phrasalVerbModel.getPhrasalVerbId();
			params[":hits"] = phrasalVerbModel.getHits();
			
			dataBaseRepository.execute("UPDATE PHRASALVERB SET hits=:hits WHERE phrasalVerbId=:phrasalVerbId", params);
		}
		
		private function map2PhrasalVerbModel(result:Object): PhrasalVerbModel {
			
			var pv:PhrasalVerbModel = new PhrasalVerbModel();
					
			pv.setDate(DateUtils.getDate(result["date"]));
			pv.setExample(result["example"]);
			pv.setHits(result["hits"]);
			pv.setKey(result["key"]);
			pv.setLevel(result["level"]);
			pv.setMeaning(result["meaning"]);
			pv.setPhrasalVerbId(result["phrasalVerbId"]);
			
			return pv;
		}
	}
}