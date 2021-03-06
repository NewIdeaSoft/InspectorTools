package com.nisoft.inspectortools.bean.problem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nisoft.inspectortools.db.problem.ProblemsSQLiteOpenHelper;
import com.nisoft.inspectortools.db.problem.RecodeCursorWrapper;
import com.nisoft.inspectortools.db.problem.RecodeDbSchema.RecodeTable;
import com.nisoft.inspectortools.utils.StringFormatUtil;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by NewIdeaSoft on 2017/7/1.
 */

public class ProblemDataLab {
    private static ProblemDataLab sProblemDataLab;
    private Context mContext;
    private ProblemDataPackage mProblemDataPackage;
    private SQLiteDatabase mDatabase;

    private ProblemDataLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ProblemsSQLiteOpenHelper(mContext).getWritableDatabase();
    }

    public static ProblemDataLab getProblemDataLab(Context context) {
        if (sProblemDataLab == null) {
            sProblemDataLab = new ProblemDataLab(context);
        }
        return sProblemDataLab;
    }

    //    private static ContentValues getImageValues(ImageRecode recode){
//        ContentValues values = getRecodeValues(recode);
//        String folderPath = recode.getImagesFolderPath();
//        if(folderPath!=null) {
//            values.put(RecodeTable.Cols.FOLDER_PATH,folderPath);
//        }
//        return values;
//    }
    private static ContentValues getRecodeValues(Recode recode, String table) {
        ContentValues values = new ContentValues();
        String problemId = recode.getRecodeId();
        String type = recode.getType();
        String description = recode.getDescription();
        String authorId = recode.getAuthor();
        Date date = recode.getDate();
        if (date == null) {
            date = new Date();
        }
        long dateTime = date.getTime();
        long updateTime = recode.getUpdateTime();
        if (problemId != null) {
            problemId = StringFormatUtil.correctString(problemId);
            values.put(RecodeTable.Cols.PROBLEM_ID, problemId);
        }
        if (type != null) {
            type = StringFormatUtil.correctString(type);
            values.put(RecodeTable.Cols.TYPE, type);
        }
        if (description != null) {
            description = StringFormatUtil.correctString(description);
            values.put(RecodeTable.Cols.DESCRIPTION_TEXT, description);
        }
        if (authorId != null) {
            authorId = StringFormatUtil.correctString(authorId);
            values.put(RecodeTable.Cols.AUTHOR, authorId);
        }
        values.put(RecodeTable.Cols.DATE, dateTime);
        values.put(RecodeTable.Cols.UPDATE_TIME, updateTime);
        if (table.equals(RecodeTable.RESULT_NAME) || table.equals(RecodeTable.PROBLEM_NAME)) {
            ArrayList<String> folderPath = ((ImageRecode) recode).getImagesNameOnServer();
            if (folderPath != null) {
                values.put(RecodeTable.Cols.IMAGES_NAME, folderPath.toString());
            }
            if (table.equals(RecodeTable.PROBLEM_NAME)) {
                if (((ProblemRecode) recode).getSuspects() != null) {
                    String suspects = ((ProblemRecode) recode).getSuspects().toString();
                    values.put(RecodeTable.Cols.SUSPECTS, suspects);
                }

                String address = ((ProblemRecode) recode).getAddress();
                String title = ((ProblemRecode) recode).getTitle();
                if (title != null) {
                    title = StringFormatUtil.correctString(title);
                    values.put(RecodeTable.Cols.TITLE, title);
                }
                if (address != null) {
                    address = StringFormatUtil.correctString(address);
                    values.put(RecodeTable.Cols.ADDRESS, address);
                }
            }
        }
        return values;
    }

    public ProblemDataPackage getProblemDataPackage() {
        return mProblemDataPackage;
    }

    public void setProblemDataPackage(ProblemDataPackage problemDataPackage) {
        mProblemDataPackage = problemDataPackage;
    }
    public RecodeCursorWrapper queryRecode(String table, String selection, String[] args) {
        Cursor cursor = mDatabase.query(table,
                null,
                selection,
                args,
                null,
                null,
                null);
        return new RecodeCursorWrapper(cursor);
    }

    public Recode queryRecode(String table, String problemId) {
        Cursor cursor = mDatabase.query(table,
                null,
                RecodeTable.Cols.PROBLEM_ID + "=?",
                new String[]{problemId},
                null,
                null,
                null);
        cursor.moveToFirst();
        RecodeCursorWrapper cursorWrapper = new RecodeCursorWrapper(cursor);
        Recode recode = null;
        if (cursorWrapper.getCount() > 0) {
            switch (table) {
                case RecodeTable.PROBLEM_NAME:
                    recode = cursorWrapper.getProblemRecode();
                    break;
                case RecodeTable.PROGRAM_NAME:
                    recode = cursorWrapper.getRecode();
                    break;
                case RecodeTable.RESULT_NAME:
                    recode = cursorWrapper.getImageRecode();
                    break;
                case RecodeTable.ANALYSIS_NAME:
                    recode = cursorWrapper.getRecode();
                    break;
            }
        }

        cursorWrapper.close();
        return recode;
    }

    public ArrayList<ProblemRecode> getAllProblem() {
        RecodeCursorWrapper wrapper = queryRecode(RecodeTable.PROBLEM_NAME, null, null);
        wrapper.moveToFirst();
        ArrayList<ProblemRecode> problems = new ArrayList<>();
        while (!wrapper.isAfterLast()) {
            problems.add(wrapper.getProblemRecode());
            wrapper.moveToNext();
        }
        wrapper.close();
        return problems;
    }

    public ProblemDataPackage getProblemById(String problemId) {
        ProblemDataPackage recode = new ProblemDataPackage(problemId);
        ProblemRecode problemRecode = (ProblemRecode) queryRecode(RecodeTable.PROBLEM_NAME, problemId);
        Recode analysis = queryRecode(RecodeTable.ANALYSIS_NAME, problemId);
        Recode program = queryRecode(RecodeTable.PROGRAM_NAME, problemId);
        ImageRecode result = (ImageRecode) queryRecode(RecodeTable.RESULT_NAME, problemId);
        if (problemRecode != null) {
            recode.setProblem(problemRecode);
        }
        if (analysis != null) {
            recode.setAnalysis(analysis);
        }
        if (program != null) {
            recode.setProgram(program);
        }
        if (result != null) {
            recode.setResultRecode(result);
        }
        return recode;
    }

    public void delete(ProblemRecode problem) {
        String whereClause = RecodeTable.Cols.PROBLEM_ID + "=?";
        String[] args = new String[]{problem.getRecodeId()};
        mDatabase.delete(RecodeTable.PROBLEM_NAME, whereClause, args);
        mDatabase.delete(RecodeTable.ANALYSIS_NAME, whereClause, args);
        mDatabase.delete(RecodeTable.PROGRAM_NAME, whereClause, args);
        mDatabase.delete(RecodeTable.RESULT_NAME, whereClause, args);
    }

    public void updateRecode(String table, Recode recode) {
        ContentValues contentValues = getRecodeValues(recode, table);
        if (contentValues.size() > 0) {
            if (recode.getRecodeId() == null) {
                return;
            }
            RecodeCursorWrapper cursorWrapper = queryRecode(table,
                    RecodeTable.Cols.PROBLEM_ID + "=?",
                    new String[]{recode.getRecodeId()});
            if (cursorWrapper.getCount() > 0) {
                mDatabase.update(table, contentValues,
                        RecodeTable.Cols.PROBLEM_ID + "=?",
                        new String[]{recode.getRecodeId()});
                Log.e("TAG", "update " + table + ":" + recode.getRecodeId());
            } else {
                mDatabase.insert(table, null, contentValues);
                Log.e("TAG", "insert:" + recode.getRecodeId());
            }
            cursorWrapper.close();
        }
    }

    public void updateProblem(ProblemDataPackage problem) {
        if (problem.getProblem() != null) {
            updateRecode(RecodeTable.PROBLEM_NAME, problem.getProblem());
        }
        if (problem.getAnalysis() != null) {
            updateRecode(RecodeTable.ANALYSIS_NAME, problem.getAnalysis());
        }
        if (problem.getProgram() != null) {
            updateRecode(RecodeTable.PROGRAM_NAME, problem.getProgram());
        }
        if (problem.getResultRecode() != null) {
            updateRecode(RecodeTable.RESULT_NAME, problem.getResultRecode());
        }

    }

    public ArrayList<ProblemRecode> getProblems(RecodeCursorWrapper cursor) {
        cursor.moveToFirst();
        ArrayList<ProblemRecode> problems = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            problems.add(cursor.getProblemRecode());
            cursor.moveToNext();
        }
        cursor.close();
        return problems;
    }
}
