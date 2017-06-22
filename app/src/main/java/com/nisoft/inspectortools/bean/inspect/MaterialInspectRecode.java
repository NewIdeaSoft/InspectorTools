package com.nisoft.inspectortools.bean.inspect;

import com.nisoft.inspectortools.utils.StringFormatUtil;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/22.
 */

public class MaterialInspectRecode {
    private String mJobNum;
    private String mPicsRootPath;
    private Date mDate;
    private String mType;
    private String mDescription;

    public String getJobNum() {
        return mJobNum;
    }

    public void setJobNum(String jobNum) {
        mJobNum = jobNum;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
    public String getPicsRootPath() {
        return mPicsRootPath;
    }

    public void setPicsRootPath(String picsRootPath) {
        mPicsRootPath = picsRootPath;
    }

    @Override
    public String toString() {
        String separator = System.getProperty("line.separator");
        String data = "";
        data = "检验编号：" + mJobNum + separator;
        data = data + "检验时间：" + StringFormatUtil.dateFormat(mDate) + separator;
        data = data + "工作描述：" + mDescription + separator;
        return data;
    }
}
