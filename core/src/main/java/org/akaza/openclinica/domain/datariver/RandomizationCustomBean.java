package org.akaza.openclinica.domain.datariver;

import java.util.Date;

/**
 * @author DataRiver (EC) 28/11/2017
 *
 */
public class RandomizationCustomBean {
	
//	id integer NOT NULL,
//	  strato character varying(7),
//	  randomizzazione integer,
//	  id_paziente integer,
//	  data_randomizzazione timestamp(6) without time zone,
//	  id_utente integer
	
	
	private Integer id;
	private String  strato;
	private Integer  randomizzazione;
	private Integer  idPaziente;
	private Date  dataRandomizzazione;
	private Integer  idUtente;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStrato() {
		return strato;
	}
	public void setStrato(String strato) {
		this.strato = strato;
	}
	public Integer getRandomizzazione() {
		return randomizzazione;
	}
	public void setRandomizzazione(Integer randomizzazione) {
		this.randomizzazione = randomizzazione;
	}
	public Integer getIdPaziente() {
		return idPaziente;
	}
	public void setIdPaziente(Integer idPaziente) {
		this.idPaziente = idPaziente;
	}
	public Date getDataRandomizzazione() {
		return dataRandomizzazione;
	}
	public void setDataRandomizzazione(Date dataRandomizzazione) {
		this.dataRandomizzazione = dataRandomizzazione;
	}
	public Integer getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Integer idUtente) {
		this.idUtente = idUtente;
	}

}
