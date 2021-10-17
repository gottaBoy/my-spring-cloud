package com.mysting.tomato.common.test;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.mysting.tomato.common.model.SysMenu;

public class Test {

	public static void main(String[] args) throws JsonProcessingException {
		SysMenu root = new SysMenu();
		root.setId(-1L);
		root.setParentId(0L);

		SysMenu child1 = new SysMenu();
		child1.setId(1L);
		child1.setParentId(-1L);

		SysMenu child11 = new SysMenu();
		child11.setId(11L);
		child11.setParentId(1L);

		SysMenu child111 = new SysMenu();
		child111.setId(111L);
		child111.setParentId(11L);

		SysMenu child12 = new SysMenu();
		child12.setId(12L);
		child12.setParentId(1L);

		SysMenu child2 = new SysMenu();
		child2.setId(2L);
		child2.setParentId(-1L);

		SysMenu child21 = new SysMenu();
		child21.setId(21L);
		child21.setParentId(2L);

		SysMenu child22 = new SysMenu();
		child22.setId(22L);
		child22.setParentId(2L);

		//完整树
		List<SysMenu> list = Lists.newArrayList();
		list.add(root);
		list.add(child1);
		list.add(child11);
		list.add(child12);
		list.add(child111);

		list.add(child2);
		list.add(child21);
		list.add(child22);

		//递归树
		List<SysMenu> menuTree = list.stream().filter(t -> t.getParentId() == -1L).map((menu) -> {
			menu.setSubMenus(treeChildren(menu, list));
			return menu;
		}).collect(Collectors.toList());

		System.out.println(new ObjectMapper().writeValueAsString(menuTree));

		//扁平子节点列表
		List<SysMenu> platChildList = Lists.newArrayList();
		
		flatChildren(child1, list,platChildList) ;
		
		System.out.println(new ObjectMapper().writeValueAsString(platChildList));
		 
		
		
		//扁平父节点列表
		List<SysMenu> platParentList = Lists.newArrayList();
				
		flatParent(child111, list,platParentList) ;
				
		System.out.println(new ObjectMapper().writeValueAsString(platParentList));
		
	}
	
	/**
	 * 递归子节点树
	 * @param root
	 * @param all
	 * @return
	 */
	private static List<SysMenu> treeChildren(SysMenu root, List<SysMenu> all) {
		return all.stream().filter(t -> root.getId().equals(t.getParentId())).map((g) -> {
			g.setSubMenus(treeChildren(g, all));
			return g;
		}).collect(Collectors.toList());

	}

	/**
	 * 扁平子节点列表
	 * @param current
	 * @param list
	 * @param flatTree
	 */
	private static void flatChildren(SysMenu current, List<SysMenu> list , List<SysMenu> flatTree  ) {
		if (current != null) {
			List<SysMenu> tempList = list.stream()
					.filter(t -> t.getParentId().equals(current.getId()))
					.collect(Collectors.toList());
			flatTree.addAll(tempList);

			if (CollectionUtils.isNotEmpty(tempList)) {
				tempList.stream().forEach(item -> flatChildren(item, list  , flatTree));
			}

		} else {
			return;
		}


	}
	
	
	/**
	 * 扁平父节点列表
	 * @param current
	 * @param list
	 * @param flatTree
	 */
	private static void flatParent(SysMenu current, List<SysMenu> list , List<SysMenu> flatTree  ) {
		if (current != null) {
			List<SysMenu> tempList = list.stream()
					.filter(t -> t.getId().equals(current.getParentId()))
					.collect(Collectors.toList());
			flatTree.addAll(tempList);

			if (CollectionUtils.isNotEmpty(tempList)) {
				tempList.stream().forEach(item -> flatParent(item, list  , flatTree));
			}

		} else {
			return;
		}


	}
	 

}
