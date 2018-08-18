package org.gusmp.config 
{
	import org.gusmp.repository.DataBaseRepository;
	import robotlegs.bender.extensions.mediatorMap.api.IMediatorMap;
	import robotlegs.bender.framework.api.IConfig;
	import robotlegs.bender.framework.api.IInjector;
	
	import org.gusmp.views.*
	import org.gusmp.mediator.*;
	
	import org.gusmp.service.*;
	import org.gusmp.repository.*;
	
	/**
	 * ...
	 * @author Gus
	 */
	public class AppConfig implements IConfig
	{
		[Inject]
		public var injector:IInjector;
		
		[Inject]
		public var mediatorMap:IMediatorMap;
		
		public function configure():void
		{
			// Repositories
			injector.map(DataBaseRepository).asSingleton();
			injector.map(ConfigurationRepository).asSingleton();
			injector.map(PhrasalVerbRepository).asSingleton();
			injector.map(WordFormationRepository).asSingleton();
			
			
			// Services
			injector.map(ConfigurationService).asSingleton();
			injector.map(PhrasalVerbService).asSingleton();
			injector.map(WordFormationService).asSingleton();
			injector.map(FileService).asSingleton();
			injector.map(UpdateService).asSingleton();
			
			// Views
			mediatorMap.map(MainView).toMediator(MainMediator);
			mediatorMap.map(PhrasalVerbView).toMediator(PhrasalVerbMediator);
			mediatorMap.map(WordFormationView).toMediator(WordFormationMediator);
			mediatorMap.map(SettingsView).toMediator(SettingsMediator);
		}
	}

}