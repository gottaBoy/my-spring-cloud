package com.mysting.tomato.user.controller;

import java.util.Map;

import com.mysting.tomato.user.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mysting.tomato.common.exception.controller.ControllerException;
import com.mysting.tomato.common.exception.service.ServiceException;
import com.mysting.tomato.common.model.SysRole;
import com.mysting.tomato.common.web.PageResult;
import com.mysting.tomato.common.web.ResponseEntity;
import com.mysting.tomato.log.annotation.LogAnnotation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @author 作者 owen 
* @version 创建时间：2017年11月12日 上午22:57:51
* 角色管理
 */

@RestController
@Api(tags = "ROLE API")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 后台管理查询角色
	 * @param params
	 * @return
	 * @throws JsonProcessingException 
	 */
	@GetMapping("/roles")
	@ApiOperation(value = "后台管理查询角色")
	@PreAuthorize("hasAuthority('role:get/roles')")
	@LogAnnotation(module="user-center",recordRequestParam=false)
	public PageResult<SysRole> findRoles(@RequestParam Map<String, Object> params) throws ControllerException {
		try {
//			BizLog.info("角色列表", LogEntry.builder().clazz(this.getClass().getName()).method("findRoles").msg("hello").path("/roles").build());
			return sysRoleService.findRoles(params);
		} catch (ServiceException e) {
			 throw new ControllerException(e);
		}
	}

	/**
	 * 角色新增或者更新
	 * @param sysRole
	 * @return
	 * @throws ControllerException 
	 */
	@PostMapping("/roles/saveOrUpdate")
	@PreAuthorize("hasAnyAuthority('role:post/roles','role:put/roles')")
	@LogAnnotation(module="user-center",recordRequestParam=false)
	public ResponseEntity saveOrUpdate(@RequestBody SysRole sysRole) throws ControllerException {
		try {
			return sysRoleService.saveOrUpdate(sysRole);
		} catch (ServiceException e) {
			 throw new ControllerException(e);
		}
	}

	/**
	 * 后台管理删除角色
	 * delete /role/1
	 * @param id
	 * @throws ControllerException 
	 */
	@DeleteMapping("/roles/{id}")
	@ApiOperation(value = "后台管理删除角色")
	@PreAuthorize("hasAuthority('role:delete/roles/{id}')")
	@LogAnnotation(module="user-center",recordRequestParam=false)
	public ResponseEntity deleteRole(@PathVariable Long id) throws ControllerException {
		try {
			if (id == 1L){
				return ResponseEntity.failed("管理员不可以删除");
			}
			sysRoleService.deleteRole(id);
			return ResponseEntity.succeed("操作成功");
		}catch (Exception e){
			 throw new ControllerException(e);
		}
	}
}
