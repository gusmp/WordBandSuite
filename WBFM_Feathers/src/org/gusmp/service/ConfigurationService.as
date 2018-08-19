package org.gusmp.service 
{
	import org.gusmp.model.ConfigurationModel
	import org.gusmp.repository.ConfigurationRepository;
	;
	/**
	 * ...
	 * @author Gus
	 */
	public class ConfigurationService 
	{
		
		[Inject]
		public var configurationRepository:ConfigurationRepository;
		
		private var configurationModel: ConfigurationModel = null;
		
		public function getConfiguration(): ConfigurationModel {
			
			if (this.configurationModel == null) {
				this.configurationModel = configurationRepository.getConfiguration();
			}
			return this.configurationModel;
			
		}
		
		public function saveConfiguration(configurationModel: ConfigurationModel): void {
			configurationRepository.saveConfiguration(configurationModel);
			this.configurationModel = configurationModel;
		}
	}
}