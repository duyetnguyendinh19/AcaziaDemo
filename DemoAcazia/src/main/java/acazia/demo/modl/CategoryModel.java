package acazia.demo.modl;

import acazia.demo.entity.Category;

public class CategoryModel {

	private Long id;
	private String name;
	private String tag;
	private String returnMsg;
	private Integer pageSize;
    private Integer pageNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Category toEntity() {
		Category c = new Category();
		c.setId(this.id);
		c.setName(this.name);
		c.setTag(this.tag);
		return c;
	}
	
}
