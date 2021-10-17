package com.mysting.tomato.uaa.service;

import java.util.List;
import java.util.Map;

import com.mysting.tomato.common.model.SysClient;
import com.mysting.tomato.common.web.PageResult;
import com.mysting.tomato.common.web.ResponseEntity;

@SuppressWarnings("all")
public interface SysClientService {

	
	ResponseEntity saveOrUpdate(SysClient clientDto);
	
	void delete(Long id);
	
	ResponseEntity updateEnabled(Map<String, Object> params);
	
	SysClient getById(Long id) ;

  
    
    public PageResult<SysClient> list(Map<String, Object> params);
    
    List<SysClient> findList(Map<String, Object> params) ;
    

	
    
}
