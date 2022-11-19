package com.example.gamewebsearch.controller;


import com.example.common.enumException.AliErrorCodeEnum;
import com.example.common.enumException.DirErrorCodeEnum;
import com.example.common.utils.R;
import com.example.common.vo.PrModel;
import com.example.gamewebsearch.service.ElsearchProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
public class ElsearchProductController {


    @Resource
    ElsearchProductService elsearchProductService;


    @PostMapping("/save/prmodel")
    public R savePrModel(@RequestBody PrModel prModel) {

        try {
            elsearchProductService.up(prModel);
        } catch (IOException e) {
            return R.error(DirErrorCodeEnum.SearchError.getCode(), DirErrorCodeEnum.SearchError.getDescription());
        }

        return R.ok();
    }
}
