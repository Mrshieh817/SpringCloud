package com.igxe.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
public class city {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 6180869216498363919L;

    @Column(name="city_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Integer id;
    
    private String city;
    
    private Integer countryId;
    
    private Date lastUpdate;

}
