package com.mysting.tomato.user.service;

import java.util.Map;
import java.util.Set;

import com.mysting.tomato.common.exception.service.ServiceException;
import com.mysting.tomato.common.model.SysPermission;
import com.mysting.tomato.common.model.SysRole;
import com.mysting.tomato.common.web.PageResult;
import com.mysting.tomato.common.web.ResponseEntity;

/**
* @author 作者 owen 
* @version 创建时间：2017年11月12日 上午22:57:51
 */
public interface SysRoleService {

	/**
	 * 保存角色
	 * @param sysRole
	 */
	void save(SysRole sysRole)  throws ServiceException;

	/**
	 * 修改角色
	 * @param sysRole
	 */
	void update(SysRole sysRole)  throws ServiceException;

	/**
	 * 删除角色
	 * @param id
	 */
	void deleteRole(Long id)  throws ServiceException;

	

	/**
	 * ID获取角色
	 * @param id
	 * @return
	 */
	SysRole findById(Long id)  throws ServiceException;

	/**
	 * 角色列表
	 * @param params
	 * @return
	 */
	PageResult<SysRole> findRoles(Map<String, Object> params)  throws ServiceException;

	/**
	 * 角色权限列表
	 * @param roleId
	 * @return
	 */
	Set<SysPermission> findPermissionsByRoleId(Long roleId)  throws ServiceException;

	/**
	 * 更新角色
	 * @param sysRole
	 */
	ResponseEntity saveOrUpdate(SysRole sysRole)  throws ServiceException;

}
