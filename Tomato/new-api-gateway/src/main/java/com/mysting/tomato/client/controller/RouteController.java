package com.mysting.tomato.client.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysting.tomato.client.dto.GatewayRouteDefinition;
import com.mysting.tomato.client.service.DynamicRouteService;
import com.mysting.tomato.common.web.PageResult;
import com.mysting.tomato.common.web.ResponseEntity;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/route")
@SuppressWarnings("all")
public class RouteController {

    @Autowired
    private DynamicRouteService dynamicRouteService;

    //增加路由
    @PostMapping("/add")
    public Mono<ResponseEntity> add(@RequestBody GatewayRouteDefinition gatewayRouteDefinition) {
        return Mono.just(ResponseEntity.succeed(dynamicRouteService.add(gatewayRouteDefinition)));
    }

    //更新路由
    @PostMapping("/update")
    public Mono<ResponseEntity> update(@RequestBody GatewayRouteDefinition gatewayRouteDefinition) {
        return Mono.just(ResponseEntity.succeed(dynamicRouteService.update(gatewayRouteDefinition)));
    }

    //删除路由
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity> delete(@PathVariable String id) {
        return Mono.just(ResponseEntity.succeed(dynamicRouteService.delete(id)));
    }

    //获取全部数据
    @GetMapping("/findAll")
    public Mono<PageResult> findAll(@RequestParam Map<String, Object> params) {
        return Mono.just(dynamicRouteService.findAll(params));
    }

    //同步redis数据 从mysql中同步过去
    @GetMapping("/synchronization")
    public Mono<ResponseEntity> synchronization() {
        return Mono.just(ResponseEntity.succeed(dynamicRouteService.synchronization()));
    }


    //修改路由状态
    @GetMapping("/updateFlag")
    public Mono<ResponseEntity> updateFlag(@RequestParam Map<String, Object> params) {
        return Mono.just(ResponseEntity.succeed(dynamicRouteService.updateFlag(params)));
    }


}
