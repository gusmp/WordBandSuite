package org.gusmp.service 
{
	import org.gusmp.model.PhrasalVerbModel;
	import org.gusmp.repository.PhrasalVerbRepository;
	/**
	 * ...
	 * @author Gus
	 */
	public class PhrasalVerbService 
	{
		
		[Inject]
		public var phrasalVerbRepository:PhrasalVerbRepository;
		
		[Inject]
		public var configurationService:ConfigurationService;
		

		public function save(phrasalVerbModel: PhrasalVerbModel): void {
			
			var pvList:Array = phrasalVerbRepository.find(phrasalVerbModel);
			
			if (pvList == null) {
				phrasalVerbRepository.save(phrasalVerbModel);
			}
		}
		
		public function getNext(): PhrasalVerbModel {
			
			return phrasalVerbRepository.getNext(configurationService.getConfiguration());
		}
		
		public function updateHit(phrasalVerbModel:PhrasalVerbModel): void {
			phrasalVerbRepository.updateHit(phrasalVerbModel);
		}
	}
}