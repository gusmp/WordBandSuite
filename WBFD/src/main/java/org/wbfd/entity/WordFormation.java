package org.wbfd.entity;

import java.util.Date;

import org.wbfd.enums.LEVEL;

public class WordFormation
{
    private Integer wordFormationId;
    private Date date;
    private LEVEL level;
    private String wordRoot;
    private String sentence1;
    private String sentenceKey1;
    private String sentence2;
    private String sentenceKey2;
    private String sentence3;
    private String sentenceKey3;
    private Integer hits;

    public WordFormation()
    {
	this.wordFormationId = -1;
	this.date = new Date();
	this.hits = 0;

    }

    public Integer getWordFormationId()
    {
	return wordFormationId;
    }

    public void setWordFormationId(Integer wordFormationId)
    {
	this.wordFormationId = wordFormationId;
    }

    public Date getDate()
    {
	return date;
    }

    public void setDate(Date date)
    {
	this.date = date;
    }

    public LEVEL getLevel()
    {
	return level;
    }

    public void setLevel(LEVEL level)
    {
	this.level = level;
    }

    public String getWordRoot()
    {
	return wordRoot;
    }

    public void setWordRoot(String wordRoot)
    {
	this.wordRoot = wordRoot;
    }

    public String getSentence1()
    {
	return sentence1;
    }

    public void setSentence1(String sentence1)
    {
	this.sentence1 = sentence1;
    }

    public String getSentenceKey1()
    {
	return sentenceKey1;
    }

    public void setSentenceKey1(String sentenceKey1)
    {
	this.sentenceKey1 = sentenceKey1;
    }

    public String getSentence2()
    {
	return sentence2;
    }

    public void setSentence2(String sentence2)
    {
	this.sentence2 = sentence2;
    }

    public String getSentenceKey2()
    {
	return sentenceKey2;
    }

    public void setSentenceKey2(String sentenceKey2)
    {
	this.sentenceKey2 = sentenceKey2;
    }

    public String getSentence3()
    {
	return sentence3;
    }

    public void setSentence3(String sentence3)
    {
	this.sentence3 = sentence3;
    }

    public String getSentenceKey3()
    {
	return sentenceKey3;
    }

    public void setSentenceKey3(String sentenceKey3)
    {
	this.sentenceKey3 = sentenceKey3;
    }

    public Integer getHits()
    {
	return hits;
    }

    public void setHits(Integer hits)
    {
	this.hits = hits;
    }

}
