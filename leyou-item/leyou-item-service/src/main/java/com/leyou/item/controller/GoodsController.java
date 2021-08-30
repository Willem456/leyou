package com.leyou.item.controller;

import com.Willem.leyou.item.Bo.SpuBo;
import com.Willem.leyou.item.pojo.Sku;
import com.Willem.leyou.item.pojo.Spu;
import com.Willem.leyou.item.pojo.SpuDetail;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 根据分页条件查询 Spu
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuBoByPage(
        @RequestParam(value = "key", required = false)String key,
        @RequestParam(value = "saleable", required = false)Boolean saleable,
        @RequestParam(value = "page", defaultValue = "1")Integer page,
        @RequestParam(value = "rows", defaultValue = "5")Integer rows
    ){
        PageResult<SpuBo> pageResult = goodsService.querySpuBoByPage(key, saleable, page, rows);
        if(CollectionUtils.isEmpty(pageResult.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 新增商品
     * @param spuBo
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo) {
        this.goodsService.saveGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据 spuId 查询 SpuDetail
     * @param spuId
     * @return
     */
    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId")Long spuId) {
        SpuDetail spuDetail = this.goodsService.querySpuDetailBySpuId(spuId);
        if(spuDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spuDetail);
    }

    /**
     * 根据 spuId 查询 Sku集合
     * @param spuId
     * @return
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkusBySpuId(@RequestParam("id")Long spuId) {
        List<Sku> skus = this.goodsService.querySkusBySpuId(spuId);
        if(CollectionUtils.isEmpty(skus)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skus);
    }

    /**
     * 更新商品信息
     * @param spuBo
     * @return
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo) {
        this.goodsService.updateGoods(spuBo);
        return ResponseEntity.noContent().build();
    }

    /**
     * 根据 id 删除商品
     * @param id
     * @return
     */
    @DeleteMapping("goods/spu/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable("id")Long id) {
        this.goodsService.deleteGoods(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 商品上下架
     * @param id
     * @return
     */
    @PutMapping("goods/spu/out/{id}")
    public ResponseEntity<Void> goodsSoldOut(@PathVariable("id") Long id){
        this.goodsService.goodsSoldOut(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 根据 spuId 查询 spu
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Spu> querySpuById(@PathVariable("id")Long id) {
        Spu spu = this.goodsService.querySpuById(id);
        if (spu == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spu);
    }

    @GetMapping("sku/{skuId}")
    public ResponseEntity<Sku> querySkuBySkuId(@PathVariable("skuId")Long skuId) {
        Sku sku = this.goodsService.querySkuBySkuId(skuId);
        if (sku == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sku);
    }
}
