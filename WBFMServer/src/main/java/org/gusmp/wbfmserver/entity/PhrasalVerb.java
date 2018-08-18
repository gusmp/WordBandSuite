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
@Table(name = "PHRASALVERB")
@Getter
@Setter
public class PhrasalVerb implements ICvs {
	
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Integer phrasalVerbId;
    
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Enumerated(EnumType.STRING)
    private Level level;
	
    private Integer hits;
    
    @Column(length=300)
    private String meaning;
    
    @Column(length=300)
    private String example;
    
    @Column(name="\"key\"",length=50)
    private String key;
    
    public String toString() {
    	
    	return "date: " + DateUtils.parseDate(this.date) +
    			" level: " + this.level.name() + 
    			" Meaning: " + this.meaning + 
    			" example: " + this.example + 
    			" key: " + this.key;  
    	
    }
    
    public String toCsv() {
    	
    	return this.level.name() + ";" +
    			DateUtils.parseDate(this.date) + ";" +
    			this.meaning + ";" +
    			this.example + ";" +
    			this.key;
    }
	
}
