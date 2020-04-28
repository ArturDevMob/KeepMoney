package com.arturdevmob.keepmoney.data.database.models;

import com.arturdevmob.keepmoney.data.database.DbScheme;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = DbScheme.Category.TABLE_NAME)
public class CategoryModels {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = DbScheme.Category.Cool.PARENT_ID)
    private long parentId;

    private String title;

    private String imagePath;

    private boolean isExpense;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isExpense() {
        return isExpense;
    }

    public void setExpense(boolean expense) {
        isExpense = expense;
    }
}
