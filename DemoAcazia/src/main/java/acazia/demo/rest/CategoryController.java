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
import acazia.demo.modl.CategoryModel;
import acazia.demo.service.CategorySerivce;
import acazia.demo.service.ProductService;
import io.micrometer.core.instrument.util.StringUtils;


@RestController
@RequestMapping(value = "/category/")
public class CategoryController {

	@Autowired
	private CategorySerivce categorySerivce;
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("save")
	private ResponseEntity<CategoryModel> save(@RequestBody(required = false) CategoryModel model){
		
		try {
			String msg = validate(model);
			
			if(StringUtils.isNotEmpty(msg)) {
				model.setReturnMsg(msg);
				return new ResponseEntity<CategoryModel>(model, HttpStatus.BAD_REQUEST);
			}
			
			Category cate = model.toEntity();
			if(cate.getId() == null || cate.getId().compareTo(0l) == 0) {
				Category c = categorySerivce.insert(cate);
				model.setId(c.getId());
			}else {
				categorySerivce.update(cate);
			}
			model.setReturnMsg("Lưu bản ghi thành công");
			return new ResponseEntity<CategoryModel>(model, HttpStatus.OK);
		} catch (Exception e) {
			model.setReturnMsg("Có lỗi xảy ra khi lưu dữ liệu");
		}
		return new ResponseEntity<CategoryModel>(model, HttpStatus.BAD_REQUEST);
		
	}

	private String validate(CategoryModel model) {
		String msg = "";
		if(model == null || (model.getId() != null && model.getId().compareTo(0l) != 0 && !categorySerivce.existById(model.getId()))) {
			msg = "Bản ghi không tồn tại hoặc đã bị xóa";
		}else {
			if(StringUtils.isEmpty(model.getName())) {
				msg += "Tên danh mục không được để trống <br/>";
			}
			
			if(StringUtils.isEmpty(model.getTag())){
				msg += "Tag danh mục không được để trống <br/>";
			}else {
				Category cate = categorySerivce.findByTag(model.getTag());
				if(cate != null && (cate.getId() != model.getId())) {
					msg = "Đã tồn tại bản ghi có Tag là " + model.getTag();
				}
			}
		}
		
		return msg;
	}
	
	@PostMapping("doSearch")
	private ResponseEntity<Map<String, Object>> doSearch(@RequestBody(required = false) CategoryModel model){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			
			 Integer size = model.getPageSize() == null ? 20 : model.getPageSize();
	         Integer page = model.getPageNumber() == null ? 0 : model.getPageNumber();
	         
	         Sort sort = Sort.by(Sort.Direction.DESC, "tag");
	         Pageable pageable = PageRequest.of(page, size, sort);
	         
	         Page<Category> data = categorySerivce.pageCategory(model.getName(), model.getTag(), model.getId() , pageable);

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
	private ResponseEntity<CategoryModel> delete(@RequestParam("id") Long id){
		CategoryModel model = new CategoryModel();
		try {
			
			if(id != null && categorySerivce.existById(id)) {
				if(productService.countByCategoryId(id) > 0) {
					model.setReturnMsg("Bản ghi đang được sử dụng trong sản phẩm");
				}else {
					categorySerivce.delete(id);
					model.setReturnMsg("Xóa bản ghi thành công");
				}
			}else {
				model.setReturnMsg("Bản ghi không tồn tại hoặc đã bị xóa");
			}
			
            return new ResponseEntity<CategoryModel>(model, HttpStatus.OK);
		} catch (Exception e) {
			model.setReturnMsg("Có lỗi xảy ra khi xóa dữ liệu");
		}
		return new ResponseEntity<CategoryModel>(model, HttpStatus.BAD_REQUEST);
		
	}
}
