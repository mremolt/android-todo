package de.dcs.mrtodoapp.app.models;

import android.provider.BaseColumns;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.javalid.annotations.core.ValidateDefinition;
import org.javalid.annotations.validation.MinLength;
import org.javalid.annotations.validation.NotEmpty;
import org.javalid.annotations.validation.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

@ValidateDefinition
@Table(name = "Items", id = BaseColumns._ID)
public class Item extends Base {

    @Column(name = "category")
    private Category category;

    @Column(name = "content")
    private String content;

    @Column(name = "lat")
    private Double lat = 0.0;

    @Column(name = "lng")
    private Double lng = 0.0;

    @Column(name = "imageName")
    private String imageName;

    @Column(name = "deadline")
    private Date deadline;

    @Column(name = "completed")
    private Boolean completed = false;

    public Item() {
        super();
    }

    public Item(Category category, String content, Double lat, Double lng, String imageName, Date deadline) {
        super();
        this.category = category;
        this.content = content;
        this.lat = lat;
        this.lng = lng;
        this.imageName = imageName;
        this.deadline = deadline;
    }

    @NotNull
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @NotEmpty
    @MinLength(length = 8)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getLat() {
        if (lat != null) {
            return lat;

        } else {
            return 0.0;
        }
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        if (lng != null) {
            return lng;

        } else {
            return 0.0;
        }
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getCategoryName() {
        String result = "No Category";

        if (getCategory() != null) {
            result = getCategory().getName();
        }

        return result;
    }

    public long getCategoryId() {
        long result = 0;

        if (getCategory() != null) {
            result = getCategory().getId();
        }

        return result;
    }

    public String getDeadlineText() {
        String result = "";

        if (getDeadline() != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            result = format.format(getDeadline());
        }

        return result;
    }

}
