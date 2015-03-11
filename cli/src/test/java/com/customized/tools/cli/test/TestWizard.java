package com.customized.tools.cli.test;

import java.util.List;

import com.customized.tools.cli.wizard.Wizard;

public class TestWizard extends Wizard {
	
	private Entity entity;

	public TestWizard(Entity entity) {
		super("Test Wizard");
		this.entity = entity;
		doInit();
	}
	
	String id = "Id";
	String name = "Name";
	String address = "Address";

	public void doInit() {
		
		List<String> list = getOrderList();
		
		String key = id;
		list.add(key);
		update(key, entity.getId());
		
		key = name;
		list.add(key);
		update(key, entity.getName());
		
		key = address;
		list.add(key);
		update(key, entity.getAddress());
		
		updateKeyLength();
	}
	
	public Entity getEntity() {
		Entity en = new Entity();
		en.setId(getContent().get(id));
		en.setName(getContent().get(name));
		en.setAddress(getContent().get(address));
		return en;
	}

}
