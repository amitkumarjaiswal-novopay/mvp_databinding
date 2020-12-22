package com.mvp.testapp;

import android.content.Context;
import android.database.SQLException;
import android.os.AsyncTask;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import java.util.Collections;
import java.util.List;

public class ContactManagerUtil {
    public static void saveContact(final Context context, final Contact contact) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    DataBaseHelper dataBaseHelper =
                            OpenHelperManager.getHelper(context, DataBaseHelper.class);

                    Dao<Contact, Integer> dao = dataBaseHelper.getCachedDao(Contact.class);
                    dao.createOrUpdate(contact);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                } finally {
                    OpenHelperManager.releaseHelper();
                }
            }
        });
        Toast.makeText(context, "SAVED", Toast.LENGTH_SHORT).show();
    }

    public static void saveListContacts(Context context, List<Contact> list) {
        for (Contact contact : list)
            saveContact(context, contact);
    }

    public static List<Contact> getListContacts(Context context) {
        DataBaseHelper dataBaseHelper = OpenHelperManager.getHelper(context, DataBaseHelper.class);

        Dao<Contact, Integer> dao = dataBaseHelper.getCachedDao(Contact.class);
        QueryBuilder<Contact, Integer> query = dao.queryBuilder();
        try {
            //query.where().eq("mId", taskId);
            PreparedQuery<Contact> prepare = query.prepare();
            List<Contact> result = dao.query(prepare);
            Collections.reverse(result);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            OpenHelperManager.releaseHelper();
        }

        return null;
    }

    public static int deleteContact(Context context, int id) {
        DataBaseHelper dataBaseHelper = OpenHelperManager.getHelper(context, DataBaseHelper.class);

        Dao<Contact, Integer> dao = dataBaseHelper.getCachedDao(Contact.class);
        DeleteBuilder<Contact, Integer> query = dao.deleteBuilder();
        Where where = query.where();
        try {
            where.eq("mId", id);
            return dao.delete(query.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            OpenHelperManager.releaseHelper();
        }

        return 0;
    }

    public static void updateContact(Context context, Contact contact) {
        DataBaseHelper dataBaseHelper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
        Dao<Contact, Integer> catDao = dataBaseHelper.getCachedDao(Contact.class);
        UpdateBuilder<Contact, Integer> updateBuilder = catDao.updateBuilder();
        try {
            updateBuilder.where().eq("mId", contact.getId());
            // update the value of your field(s)
            updateBuilder.updateColumnValue("mName", contact.getName());
            updateBuilder.updateColumnValue("mMobile", contact.getMobile());
            updateBuilder.updateColumnValue("mEmail", contact.getEmail());
            updateBuilder.updateColumnValue("mImageUrl", contact.getImageUrl());
            updateBuilder.updateColumnValue("mPassword", contact.getPassword());
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            OpenHelperManager.releaseHelper();
        }
    }
}
