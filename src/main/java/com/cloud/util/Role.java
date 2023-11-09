package com.cloud.util;

public enum Role {

	SUPER_ADMIN(4, "SUPER_ADMIN"),
	ADMIN(3, "ADMIN"),
	USER(2, "USER"),
	VISITOR(1, "VISITOR");

	Role(int id, String name) {
	}

}
