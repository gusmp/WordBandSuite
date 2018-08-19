package org.gusmp.repository 
{
	import flash.utils.Dictionary;
	import org.gusmp.model.ConfigurationModel;
	/**
	 * ...
	 * @author Gus
	 */
	public class ConfigurationRepository 
	{
		
		[Inject]
		public var dataBaseRepository: DataBaseRepository;
		
		public function getConfiguration() : ConfigurationModel {
			
			var configurationModel:ConfigurationModel = new ConfigurationModel();
			var params:Dictionary = new Dictionary();
			params[":configurationId"] = 1;
			
			var result:Array = dataBaseRepository.executeSql("SELECT * FROM CONFIGURATION WHERE configurationId=:configurationId", params);
			
			
			if ((result != null) && (result.length > 0)) {
				
				configurationModel.setLevel(result[0]["level"]);
				configurationModel.setMaxResults(result[0]["maxResults"]);
			} else {
				
				params = new Dictionary();
				params[":level"] = "CAE";
				params[":configurationId"] = 1;
				params[":maxResults"] = 20;
				
				dataBaseRepository.execute("INSERT INTO CONFIGURATION(configurationId,level,maxResults) VALUES(:configurationId,:level,:maxResults)", params);
				
				configurationModel.setLevel("CAE");
				configurationModel.setMaxResults(20);
			}

			return configurationModel;
		}
		
		
		public function saveConfiguration(configurationModel:ConfigurationModel): void {
			
			var params:Dictionary = new Dictionary();
			params[":level"] = configurationModel.getLevel();
			params[":configurationId"] = 1;
			
			dataBaseRepository.execute("UPDATE CONFIGURATION SET level=:level WHERE configurationId=:configurationId", params);
		}
	}
}