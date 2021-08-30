package com.leyou.item.service;

import com.Willem.leyou.item.pojo.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        // 初始化 example 对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        // 根据 name 模糊查询，或者根据首字母查询
        if(StringUtils.isNotBlank(key)) {
            criteria.andLike("name","%"+key+"%").orEqualTo("letter", key);
        }

        // 添加分页条件
        PageHelper.startPage(page,rows);

        // 添加排序条件
        if(StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc?"desc":"asc"));
        }


        List<Brand> brands = this.brandMapper.selectByExample(example);

        // 包装成 pageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        // 包装成分页结果集返回
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     */
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        // 先新增 brand
        this.brandMapper.insertSelective(brand);

        // 再新增中间表
        cids.forEach(cid -> {
            this.brandMapper.insertCategoryAndBrand(cid, brand.getId());
        });
    }

    /**
     * 更新品牌
     * @param cids
     * @param brand
     */
    @Transactional
    public void updateBrand(List<Long> cids, Brand brand) {
        // 先更新 brand
        brandMapper.updateByPrimaryKey(brand);
        // 通过品牌 id 删除中间表
        brandMapper.deleteCategoryAndBrandByBid(brand.getId());
        // 再新增中间表
        for(Long cid : cids) {
            brandMapper.insertCategoryAndBrand(cid,brand.getId());
        }
    }

    /**
     * 删除品牌
     * @param bid
     */
    public void deleteBrand(Long bid) {
        // 通过品牌 id 删除中间表
        brandMapper.deleteCategoryAndBrandByBid(bid);
        // 删除品牌
        brandMapper.deleteByPrimaryKey(bid);
    }

    /**
     * 根据 分类 id 查询品牌列表
     * @param cid
     * @return
     */
    public List<Brand> queryBrandByCid(Long cid) {
        return this.brandMapper.selectBrandByCid(cid);
    }

    public Brand queryBrandById(Long id) {
        return this.brandMapper.selectByPrimaryKey(id);
    }
}
