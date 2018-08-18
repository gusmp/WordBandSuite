package org.wbfd.entity;

import java.util.Date;

import org.wbfd.enums.LEVEL;

public class Collocation
{
    private Integer collocationId;
    private Date date;
    private LEVEL level;
    private Integer hits;
    private String key;

    // CPE
    private String sentence1;
    private String sentence2;
    private String sentence3;

    // CAE
    private String phrase;
    private String example;

    public Collocation()
    {
	this.collocationId = -1;
	this.date = new Date();
	this.hits = 0;
    }

    public Integer getCollocationId()
    {
	return collocationId;
    }

    public void setCollocationId(Integer collocationId)
    {
	this.collocationId = collocationId;
    }

    public LEVEL getLevel()
    {
	return level;
    }

    public void setLevel(LEVEL level)
    {
	this.level = level;
    }

    public Date getDate()
    {
	return date;
    }

    public void setDate(Date date)
    {
	this.date = date;
    }

    public Integer getHits()
    {
	return hits;
    }

    public void setHits(Integer hits)
    {
	this.hits = hits;
    }

    public String getKey()
    {
	return key;
    }

    public void setKey(String key)
    {
	this.key = key;
    }

    // CPE
    public String getSentence1()
    {
	return sentence1;
    }

    public void setSentence1(String sentence1)
    {
	this.sentence1 = sentence1;
    }

    public String getSentence2()
    {
	return sentence2;
    }

    public void setSentence2(String sentence2)
    {
	this.sentence2 = sentence2;
    }

    public String getSentence3()
    {
	return sentence3;
    }

    public void setSentence3(String sentence3)
    {
	this.sentence3 = sentence3;
    }

    // CAE
    public String getPhrase()
    {
	return phrase;
    }

    public void setPhrase(String phrase)
    {
	this.phrase = phrase;
    }

    public String getExample()
    {
	return example;
    }

    public void setExample(String example)
    {
	this.example = example;
    }

}
