package com.step.ap.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class DownloadVo{
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;
    private Integer downloadCount;
    public DownloadVo(Date date,Integer downloadCount){
        this.setDate(date);
        this.setDownloadCount(downloadCount);
    }
}
