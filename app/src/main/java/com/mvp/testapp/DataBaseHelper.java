package com.mvp.testapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.HashMap;
import java.util.Map;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
    public static final Class<?>[] DataBaseClasses = new Class[] {
            Contact.class
    };
    private static final String DATABASE_NAME = "Mvp.sqlite";
    private static final int DATABASE_VERSION = 1;
    private Map<String, Dao<?, ?>> daoMap = new HashMap<>();

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            for (Class obj : DataBaseClasses) {
                TableUtils.createTable(connectionSource, obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            try {
                for (Class obj : DataBaseClasses) {
                    TableUtils.dropTable(connectionSource, obj, false);
                }
                onCreate(database, connectionSource);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearDatabase() {
        for (Class clazz : DataBaseClasses) {
            try {
                TableUtils.clearTable(getConnectionSource(), clazz);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T, ID> Dao<T, ID> getCachedDao(Class<T> clazz) {
        Dao<?, ?> result = daoMap.get(clazz.getName());
        if (result == null) {
            try {
                result = getDao(clazz);
                daoMap.put(clazz.getName(), result);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return (Dao<T, ID>) result;
    }
}


