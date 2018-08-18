package org.wbfd.service.impl;

import org.wbfd.service.LocaleService;

public class LocaleServiceImpl implements LocaleService
{
    public enum LOCALE
    {
	ENGLISH, SPANISH, CATALAN
    }

    public enum TEXT
    {
	APP_TITLE,
	// Main window
	MAIN_CONFIRM_CLOSE,
		MAIN_CPE_MENU,
		MAIN_CPE_CAE_PV,
		MAIN_CPE_CAE_WF,
		MAIN_CPE_CAE_CO,
		MAIN_CPE_HELL_MODE,
		MAIN_CAE_MENU,
	MAIN_IMPORT_MENU,
		MAIN_IMPORT_MENU_PV,
		MAIN_IMPORT_MENU_WF,
		MAIN_IMPORT_MENU_CO,
		MAIN_IMPORT_MERGE_DB,
		MAIN_IMPORT_RAW_FORMAT,
	MAIN_HIT_MENU,
		MAIN_HIT_CLEAN_PV,
		MAIN_HIT_CLEAN_WF,
		MAIN_HIT_CLEAN_CO,
		MAIN_HIT_CONFIRMATION,
	MAIN_ABOUT_MENU,

	// Phrasal Verbs
	PV_SCREEN_TITLE,
	PV_SCREEN_MEANING,
	PV_SCREEN_EXAMPLE,

	// Word Formation
	WF_SCREEN_TITLE,
	WF_SCREEN_WORD_ROOT,

	// Collocation
	CO_SCREEN_TITLE,
	CO_SCREEN_PHRASE,
	CO_SCREEN_EXAMPLE,

	// Import
	IMPORT_SCREEN_TITLE,
	IMPORT_OK,
	MERGE_SCREEN_TITLE,

	// About
	ABOUT_SCREEN_TITLE,
	ABOUT_SCREEN_DB_UPDATE,

	// Common text
	MSG_OK,
	MSG_KO,
	ERROR,
	MSG_NUMBER_CLUES,
	MSG_NUMBER_CLUES_WARNING,
	MSG_NUMBER_CLUES_NO_HIT,
	MSG_ANSWER_IS,

	// Common buttons
	BT_VERIFY,
	BT_NEXT,
	BT_GIVE_ANSWER,
	BT_GIVE_CLUE,

	// Others
	APP_VERSION
    };

    private LOCALE currentLocale = LOCALE.ENGLISH;

    private String[][] MSG = {
	    // Main window
	    { "Word Bank for desktop", "Word Bank for desktop", "Word Bank for desktop" }, { "Close WBFD?", "", "" }, { "CPE", "CPE", "CPE" }, { "Phrasal Verbs", "", "" }, { "Word Formation", "", "" }, { "Collocations", "", "" }, { "Hell mode", "", "" }, { "CAE", "CAE", "CAE" }, { "Import", "", "" }, { "Import phrasal verbs", "", "" }, { "Import word formation", "", "" }, { "Import collocations", "", "" }, { "Merge outer DB", "", "" }, { "Import from RAW format", "", "" }, { "Hits", "", "" }, { "Clean phrasal verbs hits", "", "" }, { "Clean word formation hits", "", "" }, { "Clean collocation hits", "", "" }, { "Do you really want to clear the hits?", "", "" }, { "About", "", "" },

	    // Phrasal Verbs
	    { "Phrasal Verb", "", "" }, { "Meaning: ", "", "" }, { "Example: ", "", "" },

	    // Word Formation
	    { "Word formation", "", "" }, { "Word root:", "", "" },

	    // Collocation
	    { "Collocation", "", "" }, { "Phrase:", "", "" }, { "Example: ", "", "" },

	    // Import
	    { "Import", "", "" }, { "Import ended successfully", "", "" }, { "Merge", "", "" },

	    // About
	    { "About", "", "" }, { "Database last update: 14/10/2012", "", "" },

	    // Common text
	    { "CORRECT!", "", "" }, { "WRONG!", "", "" }, { "Error", "", "" }, { "Number of clues:", "", "" }, { "(If you need more clues, the exercise will be marked as failed!)", "", "" }, { "(No hit will be given for this exercise)", "", "" }, { "The answer is: ", "", "" },

	    // Common buttons
	    { "Verify", "", "" }, { "Next", "", "" }, { "Give me the answer!", "", "" }, { "Give me a clue", "", "" },

	    // Others
	    { "0.0.3", "", "" } };

    public String getText(TEXT text)
    {
	return (MSG[text.ordinal()][currentLocale.ordinal()]);
    }

}
