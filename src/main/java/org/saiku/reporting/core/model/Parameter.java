//package org.saiku.reporting.core.model;
//
//import javax.xml.bind.annotation.XmlAccessType;
//import javax.xml.bind.annotation.XmlAccessorType;
//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlRootElement;
//
//import org.codehaus.jackson.annotate.JsonProperty;
//
//@XmlRootElement(name="parameter")
//@XmlAccessorType(XmlAccessType.FIELD)
//public class Parameter {
//	
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	public DataSource getDatasource() {
//		return datasource;
//	}
//
//	public void setDatasource(DataSource datasource) {
//		this.datasource = datasource;
//	}
//
//	@XmlElement(name="id")
//	@JsonProperty("id")
//	private String id;
//
//	@XmlElement(name="name")
//	@JsonProperty("name")
//	private String name;
//	
//	//TODO: change that!
//	@XmlElement(name="type")
//	@JsonProperty("type")
//	private String type;
//	
//	@XmlElement(name="datasource")
//	@JsonProperty("datasource")
//	private DataSource datasource;
//
//}
