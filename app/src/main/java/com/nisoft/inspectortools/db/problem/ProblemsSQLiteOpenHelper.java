package com.nisoft.inspectortools.db.problem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nisoft.inspectortools.db.problem.RecodeDbSchema.RecodeTable;


/**
 * Created by NewIdeaSoft on 2017/5/5.
 */

public class ProblemsSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "problem.db";
    private static final String CREATE_PROBLEM = "create table " + RecodeTable.PROBLEM_NAME +
            "(id integer primary key autoincrement,"
            + RecodeTable.Cols.PROBLEM_ID + " unique,"
            + RecodeTable.Cols.TITLE + ","
            + RecodeTable.Cols.ADDRESS + ","
            + RecodeTable.Cols.AUTHOR + ","
            + RecodeTable.Cols.DATE + ","
            + RecodeTable.Cols.SUSPECTS + ","
            + RecodeTable.Cols.TYPE + ","
            + RecodeTable.Cols.IMAGES_NAME + ","
            + RecodeTable.Cols.UPDATE_TIME + ","
            + RecodeTable.Cols.DESCRIPTION_TEXT + ")";
    private static final String CREATE_ANALYSIS = "create table " + RecodeTable.ANALYSIS_NAME +
            "(id integer primary key autoincrement,"
            + RecodeTable.Cols.PROBLEM_ID + " unique,"
            + RecodeTable.Cols.TYPE + ","
            + RecodeTable.Cols.AUTHOR + ","
            + RecodeTable.Cols.DATE + ","
            + RecodeTable.Cols.SUSPECTS + ","
            + RecodeTable.Cols.UPDATE_TIME + ","
            + RecodeTable.Cols.DESCRIPTION_TEXT + ")";
    private static final String CREATE_PROGRAM = "create table " + RecodeTable.PROGRAM_NAME +
            "(id integer primary key autoincrement,"
            + RecodeTable.Cols.PROBLEM_ID + " unique,"
            + RecodeTable.Cols.TYPE + ","
            + RecodeTable.Cols.AUTHOR + ","
            + RecodeTable.Cols.DATE + ","
            + RecodeTable.Cols.UPDATE_TIME + ","
            + RecodeTable.Cols.DESCRIPTION_TEXT + ")";
    private static final String CREATE_RESULT = "create table " + RecodeTable.RESULT_NAME +
            "(id integer primary key autoincrement,"
            + RecodeTable.Cols.PROBLEM_ID + " unique,"
            + RecodeTable.Cols.TYPE + ","
            + RecodeTable.Cols.AUTHOR + ","
            + RecodeTable.Cols.DATE + ","
            + RecodeTable.Cols.UPDATE_TIME + ","
            + RecodeTable.Cols.IMAGES_NAME + ","
            + RecodeTable.Cols.DESCRIPTION_TEXT + ")";
    private Context mContext;

    public ProblemsSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROBLEM);
        db.execSQL(CREATE_ANALYSIS);
        db.execSQL(CREATE_PROGRAM);
        db.execSQL(CREATE_RESULT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
