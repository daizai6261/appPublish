package com.step.ap.controller;

import com.step.ap.config.annotation.NoAuth;
import com.step.ap.service.DownloadTimeService;
import com.step.ap.vo.DownloadVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("下载统计")
@AllArgsConstructor
@RestController
@RequestMapping("api/downloadTimes")
public class DownloadTimeController {
    private final DownloadTimeService downloadTimeService;

    @NoAuth
    @ApiOperation("获取今日下载量")
    @GetMapping
    public List<DownloadVo> getList() {
        return downloadTimeService.getlist();
    }

}
