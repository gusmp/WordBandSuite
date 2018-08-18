package org.gusmp.service 
{
	import org.gusmp.model.WordFormationModel;
	import org.gusmp.repository.WordFormationRepository;
	/**
	 * ...
	 * @author Gus
	 */
	public class WordFormationService 
	{
		[Inject]
		public var wordFormationRepository:WordFormationRepository;
		
		[Inject]
		public var configurationService:ConfigurationService;
		
		public function save(wordFormationModel: WordFormationModel) : void {
			
			var wfList:Array = wordFormationRepository.find(wordFormationModel);
			
			if (wfList == null) {
				wordFormationRepository.save(wordFormationModel);
			}
		}
		
		public function getNext(): WordFormationModel {
			
			return wordFormationRepository.getNext(configurationService.getConfiguration());
		}
		
		public function updateHit(wordFormationModel:WordFormationModel): void {
			wordFormationRepository.updateHit(wordFormationModel);
		}
	}
}