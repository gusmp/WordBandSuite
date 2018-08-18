package org.gusmp.repository 
{
	
	import flash.data.SQLConnection;
	import flash.data.SQLResult;
	import flash.data.SQLStatement;
	import flash.data.SQLMode;
	import flash.utils.Dictionary;
	import org.gusmp.model.ConfigurationModel;
	
	import flash.filesystem.File;
	
	/**
	 * ...
	 * @author Gus
	 */
	public class DataBaseRepository 
	{
		
		private var DB_NAME:String = "wbfm.db";
		private var connection:SQLConnection = null;
		
		public function getConnection():SQLConnection {
			
			checkConnection();
			checkTables();
			return this.connection;
			
		}
		
		public function closeConnection(): void {
			
			if (this.connection != null) {
				this.connection.close();
			}
		}
		
		private function checkConnection(): void {
			
			if (this.connection == null) {
				this.connection = new SQLConnection();
			}
			
			
			if (this.connection.connected == false) {
				//File.applicationStorageDirectory
				this.connection.open(File.userDirectory.resolvePath(DB_NAME));
			}
		}
		
		private function checkTables(): void {
			
			var createTableStmt:SQLStatement;
			
			
			createTableStmt = new SQLStatement();
			createTableStmt.sqlConnection = this.connection;
			createTableStmt.text = "CREATE TABLE IF NOT EXISTS CONFIGURATION(configurationId INTEGER PRIMARY KEY AUTOINCREMENT, level TEXT, maxResults INTEGER)";
			createTableStmt.execute();
			
			createTableStmt.text = "CREATE TABLE IF NOT EXISTS WORDFORMATION(wordFormationId INTEGER PRIMARY KEY AUTOINCREMENT, wordRoot TEXT, sentence1 TEXT, sentenceKey1 TEXT, sentence2 TEXT, sentenceKey2 TEXT, sentence3 TEXT, sentenceKey3 TEXT, level TEXT, hits INTEGER, date TEXT)";
			createTableStmt.execute();
			
			createTableStmt.text = "CREATE TABLE IF NOT EXISTS PHRASALVERB(phrasalVerbId INTEGER PRIMARY KEY AUTOINCREMENT, meaning TEXT, example TEXT, key TEXT, level TEXT, hits INTEGER, date TEXT)";
			createTableStmt.execute();
		}
		
		public function execute(sql:String, params:Dictionary):void {
			
			var sqlStmt:SQLStatement = new SQLStatement();
			
			sqlStmt.sqlConnection = this.getConnection();
			sqlStmt.text = sql;
			
			for(var key:String in params) {
				sqlStmt.parameters[key] = params[key];
			}
			
			sqlStmt.execute();
		}
		
		public function executeSql(sql:String, params:Dictionary): Array {
			
			var sqlStmt:SQLStatement = new SQLStatement();
			sqlStmt.sqlConnection = this.getConnection();
			sqlStmt.text = sql;
			
			for(var key:String in params) {
				sqlStmt.parameters[key] = params[key];
			}
			
			sqlStmt.execute();
			return sqlStmt.getResult().data;
		}
	}

}