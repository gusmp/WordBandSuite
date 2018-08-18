package org.wbfd.entity;

import java.util.Date;

import org.wbfd.enums.LEVEL;

public class PhrasalVerb
{

    private Integer phrasalVerbId;
    private Date date;
    private LEVEL level;
    private String meaning;
    private String example;
    private String key;
    private Integer hits;

    public PhrasalVerb()
    {
	this.phrasalVerbId = -1;
	this.date = new Date();
	this.hits = 0;
    }

    /* Getter & Setters */
    public Integer getPhrasalVerbId()
    {
	return phrasalVerbId;
    }

    public void setPhrasalVerbId(Integer phrasalVerbId)
    {
	this.phrasalVerbId = phrasalVerbId;
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

    public String getMeaning()
    {
	return meaning;
    }

    public void setMeaning(String meaning)
    {
	this.meaning = meaning;
    }

    public String getExample()
    {
	return example;
    }

    public void setExample(String example)
    {
	this.example = example;
    }

    public String getKey()
    {
	return key;
    }

    public void setKey(String key)
    {
	this.key = key;
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
