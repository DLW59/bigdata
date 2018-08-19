package com.dlw.bigdata.netty.sendobject.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String sex;
	private int age;
}
