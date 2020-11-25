package acazia.demo.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import acazia.demo.entity.Category;
import acazia.demo.entity.Product;
import acazia.demo.modl.ProductModel;
import acazia.demo.service.CategorySerivce;
import acazia.demo.service.ProductService;
import io.micrometer.core.instrument.util.StringUtils;

@RestController
@RequestMapping(value = "/product/")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@Autowired
	CategorySerivce categorySerivce;
	
	@PostMapping("save")
	private ResponseEntity<ProductModel> save(@RequestBody(required = false) ProductModel model){
		
		try {
			String msg = validate(model);
			
			if(StringUtils.isNotEmpty(msg)) {
				model.setReturnMsg(msg);
				return new ResponseEntity<ProductModel>(model, HttpStatus.BAD_REQUEST);
			}
			
			Product product = model.toEntity();
			if(product.getId() == null || product.getId().compareTo(0l) == 0) {
				Product c = productService.insert(product);
				model.setId(c.getId());
			}else {
				productService.update(product);
			}
			model.setReturnMsg("Lưu bản ghi thành công");
			return new ResponseEntity<ProductModel>(model, HttpStatus.OK);
		} catch (Exception e) {
			model.setReturnMsg("Có lỗi xảy ra khi lưu dữ liệu");
		}
		return new ResponseEntity<ProductModel>(model, HttpStatus.BAD_REQUEST);
		
	}

	private String validate(ProductModel model) {
		String msg = "";
		if(model == null || (model.getId() != null && model.getId().compareTo(0l) != 0 && !productService.existById(model.getId()))) {
			msg = "Bản ghi không tồn tại hoặc đã bị xóa";
		}else {
			if(StringUtils.isEmpty(model.getName())) {
				msg += "Tên sản phẩm không được để trống <br/>";
			}
			
			if(model.getPrice() == null || model.getPrice().compareTo(0d) == 0) {
				msg += "Đơn giá phải lớn hơn 0 <br/>";
			}
			
			if(StringUtils.isEmpty(model.getCategoryTag())) {
				msg += "Danh mục sản phẩm không được để trống <br/>";
			}else {
				Category category = categorySerivce.findByTag(model.getCategoryTag());
				if(category == null) {
					msg += "Danh mục sản phẩm không tồn tại <br/>";
				}else {
					model.setCategory(category);
				}
			}
			
		}
		
		return msg;
	}
	
	@PostMapping("doSearch")
	private ResponseEntity<Map<String, Object>> doSearch(@RequestBody(required = false) ProductModel model){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			
			 Integer size = model.getPageSize() == null ? 20 : model.getPageSize();
	         Integer page = model.getPageNumber() == null ? 0 : model.getPageNumber();
	         
	         Pageable pageable = PageRequest.of(page, size);
	         
	         Page<ProductModel> data = productService.pageProduct(model.getName(), model.getInputSearch(), model.getId() , pageable);

             responseMap.put("pageNumber", page);
             responseMap.put("pageSize", size);
             responseMap.put("totalElement", data.getTotalElements());
             responseMap.put("dataContent", data);
			
             return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("delete")
	private ResponseEntity<ProductModel> delete(@RequestParam("id") Long id){
		ProductModel model = new ProductModel();
		try {
			
			if(id != null && productService.existById(id)) {
				productService.delete(id);
				model.setReturnMsg("Xóa bản ghi thành công");
			}else {
				model.setReturnMsg("Bản ghi không tồn tại hoặc đã bị xóa");
			}
			
            return new ResponseEntity<ProductModel>(model, HttpStatus.OK);
		} catch (Exception e) {
			model.setReturnMsg("Có lỗi xảy ra khi xóa dữ liệu");
		}
		return new ResponseEntity<ProductModel>(model, HttpStatus.BAD_REQUEST);
		
	}
	
}
