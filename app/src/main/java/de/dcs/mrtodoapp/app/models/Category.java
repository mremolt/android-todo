package de.dcs.mrtodoapp.app.models;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


import org.javalid.annotations.core.ValidateDefinition;
import org.javalid.annotations.validation.MinLength;
import org.javalid.annotations.validation.NotEmpty;

import java.util.List;

@ValidateDefinition
@Table(name = "Categories", id = BaseColumns._ID)
public class Category extends Base {

    @Column(name = "name")
    private String name;

    public Category() {
        super();
    }

    public Category(String name) {
        super();
        this.name = name;
    }

    @NotEmpty
    @MinLength(length = 4)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> items() {
        return getMany(Item.class, "category");
    }
}
