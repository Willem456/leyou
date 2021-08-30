package com.Willem.leyou.item.api;


import com.Willem.leyou.item.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("brand")
public interface BrandApi {


    @GetMapping("{id}")
    public Brand queryBrandById(@PathVariable("id")Long id);


}
