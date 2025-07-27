package com.step.ap.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.step.ap.base.BaseService;
import com.step.ap.entity.AppVersion;
import com.step.ap.entity.DownloadTime;
import com.step.ap.vo.DownloadVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class DownloadTimeService extends BaseService<DownloadTime> {
    private final AppVersionService appVersionService;


    public synchronized void downloadCount(Integer versionId){
        //记录下载日期
        AppVersion appVersion= appVersionService.getById(versionId);
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        QueryWrapper<DownloadTime> wrapper=new QueryWrapper<>();
        wrapper.eq("app_id",appVersion.getAppId()).eq("date",date);
        System.out.println(date);
        DownloadTime downloadTime=super.getOne(wrapper);
        if(downloadTime!=null){
            downloadTime.setDownloadCount(downloadTime.getDownloadCount()+1);
            super.updateById(downloadTime);
        }else{
            DownloadTime downloadTimeNew=new DownloadTime();
            downloadTimeNew.setDownloadCount(1);
            downloadTimeNew.setAppId(appVersion.getAppId());
            downloadTimeNew.setDate(date);
            baseMapper.insert(downloadTimeNew);
        }
    }
    public List<DownloadVo> getlist() {
        //分别获取不同日期的总下载量
        List<DownloadVo> list=new ArrayList<>();
        List<DownloadTime> listAll=super.list(new LambdaQueryWrapper<DownloadTime>()
                .orderByAsc(DownloadTime::getDate));
        int j=0;
        for (DownloadTime downloadTime : listAll) {
            if (CollectionUtils.isEmpty(list)) {
                list.add(new DownloadVo(downloadTime.getDate(), downloadTime.getDownloadCount()));
            } else {
                if (downloadTime.getDate().equals(list.get(j).getDate())) {
                    list.get(j).setDownloadCount(list.get(j).getDownloadCount() + downloadTime.getDownloadCount());
                } else {
                    //添加下载量为0的日期
                    int days = (int) ((downloadTime.getDate().getTime() - list.get(j).getDate().getTime()) / (1000*3600*24));
                    while(days>1){
                        Date date=new Date(list.get(j).getDate().getTime()+1000*3600*24);
                        list.add(new DownloadVo(date, 0));
                        j++;
                        days--;
                    }
                    list.add(new DownloadVo(downloadTime.getDate(), downloadTime.getDownloadCount()));
                    j++;
                }
            }
        }
        return list;
    }
    public DownloadTime getById(Integer appId) {
        //查询当天的某app下载量
        QueryWrapper<DownloadTime> wrapper = new QueryWrapper<>();
        long time = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(time);
        wrapper.eq("app_id", appId).eq("date", date);
        List<DownloadTime> downloadTimes = super.list(wrapper);
        if(CollectionUtils.isEmpty(downloadTimes)) {
            return null;
        }
        return downloadTimes.get(0);
    }

    public DownloadTime getById(Integer appId, java.sql.Date targetDate) {
        QueryWrapper<DownloadTime> wrapper = new QueryWrapper<>();
        wrapper.eq("app_id", appId).eq("date", targetDate);
        List<DownloadTime> downloadTimes = super.list(wrapper);
        if(CollectionUtils.isEmpty(downloadTimes)) {
            return null;
        }
        return downloadTimes.get(0);
    }
}
