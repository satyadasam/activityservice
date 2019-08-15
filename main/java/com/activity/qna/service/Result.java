package com.  .activity.qna.service;

/**
 * @author SSangapalli
 *
 * @param <T>
 */
public final class Result<T> {
	
	
	private T t;
	
	
	private Result(){		
		
	}
	private Result(T t){		
		this.t=t;
	}
	
	public void add(T t){
		
		this.t=t;
	}
	
	public boolean isPresent(){
		
		return (t!=null);
	}
	
	public T get(){
		
		return t;
	}
	
	 public  static <T> Result<T> of(T value){
		 
		 return new Result<>(value);
	 }

}
