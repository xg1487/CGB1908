package com.cy.pj.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.config.PageProperties;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.sys.common.vo.PageObject;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;
import com.cy.pj.sys.vo.SysUserDeptVo;

@Order(3)
@Service
@Transactional(timeout = 30, // 事务特性如果定再方法上的话，那就是事务共性，有限高于事务特性
		readOnly = false, rollbackFor = Throwable.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private PageProperties pageProperties;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;

	@Transactional(readOnly = true)
	@RequiredLog("select")
	@Override
	public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
		// 1.合法校验
		if (pageCurrent == null || pageCurrent <= 0)
			throw new IllegalArgumentException("当前页码不正确");
		// 2.显示总记录数
		int rowCount = sysUserDao.getRowCount(username);
		if (rowCount == 0)
			throw new ServiceException("当前记录可能不存在");
		// 3.显示当前页数
		Integer pageSize = pageProperties.getPageSize();
		System.out.println("pageSize=" + pageSize);
		int startIndex = (pageCurrent - 1) * pageSize;
		List<SysUserDeptVo> records = sysUserDao.findPageObjects(username, startIndex, pageSize);
		// 4.封装并返回
		PageObject<SysUserDeptVo> pageObject = new PageObject<>();
		pageObject.setPageCount((rowCount - 1) / pageSize + 1);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(records);
		return pageObject;
	}

	@RequiresPermissions("sys:user:valid")
	@RequiredLog("valid")
	@Override
	public int validById(Integer id, Integer valid, String modifiedUser) {
		// 1.合法校验
		if (id == null || id < 1)
			throw new IllegalArgumentException("参数不合法，id=" + id);
		if (valid != 1 && valid != 0)
			throw new IllegalArgumentException("参数不合法，valid=" + valid);
		if (StringUtils.isEmpty(modifiedUser))
			throw new ServiceException("修改用户不能为空");
		// 2.执行禁用或启用操作
		int rows = 0;
		rows = sysUserDao.validById(id, valid, modifiedUser);
		if (rows == 0)
			throw new ServiceException("记录可能不存在");
		// 3.返回结果
		return rows;
	}

	@RequiredLog("save")
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
		// 1.验证校验
		if (entity == null)
			throw new IllegalArgumentException("保存对象不能为空");
		if (StringUtils.isEmpty(entity.getUsername()))
			throw new ServiceException("用户名不能为空");
		if (StringUtils.isEmpty(entity.getPassword()))
			throw new ServiceException("用户名不能为空");
		if (roleIds == null || roleIds.length == 0)
			throw new ServiceException("至少要为用户分配角色");
		// 2.保存数据
		String salt = UUID.randomUUID().toString();
		entity.setSalt(salt);
		// 3.加密---->盐值
		SimpleHash sHash = new SimpleHash("MD5", entity.getPassword(), salt, 1);
		entity.setPassword(sHash.toHex());
		int rows = sysUserDao.insertObject(entity);
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		// 4.返回结果
		return rows;
	}

	@Cacheable(value = "userCache")
	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findObjectById(Integer userId) {
		System.out.println("UserServiceImpl.findByIdcache==");
		// 1.校验
		if (userId == null || userId < 1)
			throw new IllegalArgumentException("参数不合法，userTd=" + userId);
		// 2.查询用户
		SysUserDeptVo user = sysUserDao.findObjectById(userId);
		if (user == null)
			throw new ServiceException("此用户不存在");
		// 3.查询角色
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(userId);
		// 4.封装数据并返回
		HashMap<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}

	@CacheEvict(value = "userCache", key = "#entity.id")
	@RequiredLog("update")
	@Override
	public int updateObject(SysUser entity, Integer[] roleIds) {
		System.out.println("UserServiceImpl.updatecache==");
		// 1.参数有效性验证
		if (entity == null)
			throw new IllegalArgumentException("保存对象不能为空");
		if (StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		if (roleIds == null || roleIds.length == 0)
			throw new IllegalArgumentException("必须为其指定角色");
		// 其它验证自己实现，例如用户名已经存在，密码长度，...
		// 2.更新用户自身信息
		int rows = sysUserDao.updateObject(entity);
		// 3.保存用户与角色关系数据
		sysUserRoleDao.deleteObjectsByUserId(entity.getId());
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		// 4.返回结果
		return rows;
	}

	/*
	 * @Override public boolean isExists(String username) { int
	 * rows=sysUserDao.isExists(username); return rows>0; }
	 */
	@Override
	public boolean isExists(String columnName, String columnValue) {
		int rows = sysUserDao.siExists(columnName, columnValue);

		return rows > 0;
	}

	@Override
	public int updatePassword(String password, String newPassword, String cfgPassword) {
		// 1.判定新密码与密码确认是否相同
		if (StringUtils.isEmpty(newPassword))
			throw new IllegalAccessError("新密码不能为空");
		if (StringUtils.isEmpty(cfgPassword))
			throw new IllegalAccessError("确认密码不能为空");
		if (!newPassword.equals(cfgPassword))
			throw new IllegalAccessError("两次密码不相等");
		// 2.判定原密码是否正确
		if (StringUtils.isEmpty(password))
			throw new IllegalAccessError("原密码不能为空");
		// 3.获取登录用户
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		SimpleHash sh = new SimpleHash("MD5", password, user.getSalt(), 1);
		if (!user.getPassword().equals(sh.toHex()))
			throw new IllegalAccessError("原密码不正确");
		// 4.对新密码进行盐值加密
		String salt = UUID.randomUUID().toString();
		sh = new SimpleHash("MD5", newPassword, salt, 1);
		// 5.将新密码加密以后的结果更新到数据库
		int rows = sysUserDao.updatePassword(sh.toHex(), salt, user.getId());
		if (rows == 0)
			throw new ServiceException("修改密码失败");
		return rows;
	}

}
