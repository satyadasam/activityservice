package com.  .activity.beanutils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.HashSet;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;


public class BeanUtils {
	
	
private	BeanWrapper beanWrapper= null;

private HashMap<String,PropertyDescriptor> mappedFields= null;	
private HashSet mappedProperties=null;	 
	public BeanUtils(Class<?> classz) {
		
		beanWrapper = new BeanWrapperImpl(classz);
		intialize();
	}

	
	public BeanUtils(Object object) {
		
		beanWrapper = new BeanWrapperImpl(object);
		intialize();
		
	}
	
	public void intialize(){
		

		this.mappedFields = new HashMap<String, PropertyDescriptor>();
		this.mappedProperties = new HashSet<String>();
		PropertyDescriptor[] pds =beanWrapper.getPropertyDescriptors();
		for (PropertyDescriptor pd : pds) {
			if (pd.getWriteMethod() != null) {
				this.mappedFields.put(pd.getName(), pd);
				this.mappedProperties.add(pd.getName());
			}
		}
	
		
	}
	
	public Object getValue(String propertyName){
		
		return beanWrapper.getPropertyValue(propertyName);
	}
	


}
