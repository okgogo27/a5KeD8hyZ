package demo.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import platform.demo.entity.IdEntity;

@Entity
@Table(name = "permission")
public class Permission extends IdEntity {

	private static final long serialVersionUID = -1771109097792521796L;

	/**
	 * 权限名称
	 */
	private String name;

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
