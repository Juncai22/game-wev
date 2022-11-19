package com.example.gamewebpms.feign;

import com.example.common.utils.R;
import com.example.common.vo.PrModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("game-search")
public interface ElSearchFeign {


    @PostMapping("/save/prmodel")
    public R savePrModel(@RequestBody PrModel prModel);
}
