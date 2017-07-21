package com.example.yjlove.objectmanager.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.yjlove.objectmanager.bean.entity.ObjectType;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "OBJECT_TYPE".
*/
public class ObjectTypeDao extends AbstractDao<ObjectType, Long> {

    public static final String TABLENAME = "OBJECT_TYPE";

    /**
     * Properties of entity ObjectType.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ObjectType = new Property(1, String.class, "objectType", false, "OBJECT_TYPE");
    };


    public ObjectTypeDao(DaoConfig config) {
        super(config);
    }
    
    public ObjectTypeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"OBJECT_TYPE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"OBJECT_TYPE\" TEXT NOT NULL );"); // 1: objectType
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"OBJECT_TYPE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ObjectType entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getObjectType());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ObjectType entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getObjectType());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ObjectType readEntity(Cursor cursor, int offset) {
        ObjectType entity = new ObjectType( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1) // objectType
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ObjectType entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setObjectType(cursor.getString(offset + 1));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ObjectType entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ObjectType entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
