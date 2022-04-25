package com.ctgu.enums;

/**
 * ClassName: AccountEnum
 * Description:
 * date: 2019/12/19 16:04
 *
 * @author crwen
 * @create 2019-12-19-16:04
 * @since JDK 1.8
 */
public enum  AccountEnum {

	ADMIN(3, "Admin"),
	TEACHER(2, "Teacher"),
	ASSISTANT(1, "Assistant"),
	STUDENT(0, "Student"),

			;

	Integer level;
	String name;

	AccountEnum(Integer level, String name) {
		this.level = level;
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}
	public static AccountEnum getAccountEnum(Integer level){
		for(AccountEnum accountEnum:AccountEnum.values()){
			if(accountEnum.level.equals(level)){
				return accountEnum;
			}
		}
		return null;
	}
}
