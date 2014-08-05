package db.entity;

import java.util.ArrayList;
import java.util.List;

import constant.MenuItems;

public class User_N {
	private int id;
	private String name;
	private int menuConf;

	public User_N() {

	}

	public User_N(int id, int conf) {
		this.id = id;
		this.menuConf = conf;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMenuConf() {
		return menuConf;
	}

	public void setMenuConf(int menuConf) {
		this.menuConf = menuConf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
