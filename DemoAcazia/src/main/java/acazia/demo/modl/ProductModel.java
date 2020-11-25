package acazia.demo.modl;

import acazia.demo.entity.Category;
import acazia.demo.entity.Product;

public class ProductModel {

	private Long id;
	private String name;
	private Double price;
	private String categoryTag;
	private Category category;
	private String returnMsg;
	private Integer pageSize;
    private Integer pageNumber;
    private String categoryName;
    private String inputSearch;
    
    public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategoryTag() {
		return categoryTag;
	}

	public void setCategoryTag(String categoryTag) {
		this.categoryTag = categoryTag;
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
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public String getInputSearch() {
		return inputSearch;
	}

	public void setInputSearch(String inputSearch) {
		this.inputSearch = inputSearch;
	}

	

	public ProductModel(Long id, String name, Double price, String categoryTag, String categoryName) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.categoryTag = categoryTag;
		this.categoryName = categoryName;
	}

	public ProductModel() {
		super();
	}
	
	public Product toEntity() {
		Product p = new Product();
		p.setId(this.id);
		p.setName(this.name);
		p.setCategory(this.category);
		p.setPrice(this.price);
		return p;
	}

   
}
