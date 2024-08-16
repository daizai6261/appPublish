package com.step.ap.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@ApiModel("应用下载列表")
@Getter
@Setter
@TableName("download_time")
public class DownloadTime extends Model<DownloadTime> {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private Integer id;

    @TableField("app_id")
    @ApiModelProperty("应用id")
    private Integer appId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty("下载日期")
    private Date date;

    @TableField("download_count")
    @ApiModelProperty("下载次数")
    private Integer downloadCount;
}
