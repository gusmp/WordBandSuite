package org.gusmp.wbfmserver.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gusmp.wbfmserver.enumeration.Level;
import org.gusmp.wbfmserver.utils.DateUtils;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "WORDFORMATION")
@Getter
@Setter
public class WordFormation implements ICvs {
	
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Integer wordFormationId;
    
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Enumerated(EnumType.STRING)
    private Level level;
	
    private Integer hits;
	
	@Column(length=50)
    private String wordRoot;
	
	@Column(length=300)
    private String sentence1;
    
	@Column(length=50)
	private String sentenceKey1;
    
	@Column(length=300)
	private String sentence2;
    
	@Column(length=50)
	private String sentenceKey2;
	
	@Column(length=300)
    private String sentence3;
    
    @Column(length=50)
    private String sentenceKey3;
    
    public String toString() {
    	
    	return "date: " + DateUtils.parseDate(this.date) +
    			" level: " + this.level.name() +
    			" wordRoot: " + this.wordRoot + 
    			" sentence1: " + this.sentence1 +
    			" sentence2: " + this.sentence2 +
    			" sentence3: " + this.sentence3;
    }
    
    public String toCsv() {
    	
    	return this.level.name() + ";" +
    			DateUtils.parseDate(this.date) + ";" +
    			this.wordRoot + ";" +
    			this.sentence1 + ";" +
    			this.sentence2 + ";" +
    			this.sentence3 + ";" +
    			this.sentenceKey1 + ";" +
    			this.sentenceKey2 + ";" +
    			this.sentenceKey3;
    }
    

}
