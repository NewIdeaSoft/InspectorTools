package com.nisoft.inspectortools.db.inspect;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.nisoft.inspectortools.bean.inspect.InspectRecodePics;
import com.nisoft.inspectortools.db.inspect.PicsDbSchema.PicTable;
import com.nisoft.inspectortools.utils.StringFormatUtil;

import java.util.Date;

/**
 * Created by Administrator on 2017/5/22.
 */

public class PicsCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public PicsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public InspectRecodePics getPics(){
        InspectRecodePics pics = new InspectRecodePics();
        pics.setJobNum(getString(getColumnIndex(PicTable.Cols.PIC_JOB_NUM)));
        pics.setDate(new Date(getLong(getColumnIndex(PicTable.Cols.PIC_JOB_DATE))));
        pics.setImagesFolderPath(getString(getColumnIndex(PicTable.Cols.FOLDER_PATH)));
        pics.setPicPath(StringFormatUtil.getStrings(getString(getColumnIndex(PicTable.Cols.PICS))));
        pics.setType(getString(getColumnIndex(PicTable.Cols.TYPE)));
        pics.setDescription(getString(getColumnIndex(PicTable.Cols.DESCRIPTION)));
        return pics;
    }
}
