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
@Table(name = "COLLOCATION")
@Getter
@Setter
public class Collocation implements ICvs {
	
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Integer collocationId;
    
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Enumerated(EnumType.STRING)
    private Level level;
	
    private Integer hits;
    
    @Column(name="\"key\"",length=50)
    private String key;
    
    // CPE style
    @Column(length=300)
    private String sentence1;
    
    @Column(length=300)
    private String sentence2;
    
    @Column(length=300)
    private String sentence3;
    
    // CAE style
    @Column(length=300)
    private String phrase;
    
    @Column(length=300)
    private String example;
    
    public String toString() {
    	
    	if (this.level == Level.CAE) {
    		return "date: " + DateUtils.parseDate(this.date) + 
    				" level: " + this.level.name() +
    				" phrase: " + this.phrase + 
    				" example: " + this.example;
    	} else {
    		return "date: " + DateUtils.parseDate(this.date) +
    				" level: " + this.level.name() + 
    				" sentence1: " + this.sentence1 +
    				" sentence2: " + this.sentence2 +
    				" sentence3: " + this.sentence3;
    		
    	}
    }
    
    public String toCsv() {
    	
    	String csv = this.level.name() + ";" +
    			DateUtils.parseDate(this.date) + ";" +
    			this.key + ";";
    	
    	if (this.level == Level.CPE) {
    		csv += this.sentence1 + ";" +
    				this.sentence2 + ";" +
    				this.sentence3;
    	} else {
    		csv += this.phrase + ";" +
    				this.example;
    	}
    	
    	return csv;
    }
}
