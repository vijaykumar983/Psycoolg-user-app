package com.psycoolg.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BlogCategoryData {

    @SerializedName("data")
    private List<DataItem> data;

    @SerializedName("massage")
    private String massage;

    @SerializedName("status")
    private boolean status;

    public void setData(List<DataItem> data) {
        this.data = data;
    }

    public List<DataItem> getData() {
        return data;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getMassage() {
        return massage;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }


    public static class DataItem {

        @SerializedName("parent")
        private int parent;

        @SerializedName("cat_name")
        private String catName;

        @SerializedName("term_taxonomy_id")
        private int termTaxonomyId;

        @SerializedName("count")
        private int count;

        @SerializedName("description")
        private String description;

        @SerializedName("term_id")
        private int termId;

        @SerializedName("taxonomy")
        private String taxonomy;

        @SerializedName("filter")
        private String filter;

        @SerializedName("category_description")
        private String categoryDescription;

        @SerializedName("category_count")
        private int categoryCount;

        @SerializedName("name")
        private String name;

        @SerializedName("cat_ID")
        private int catID;

        @SerializedName("category_parent")
        private int categoryParent;

        @SerializedName("term_group")
        private int termGroup;

        @SerializedName("slug")
        private String slug;

        @SerializedName("category_nicename")
        private String categoryNicename;

        public void setParent(int parent) {
            this.parent = parent;
        }

        public int getParent() {
            return parent;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }

        public String getCatName() {
            return catName;
        }

        public void setTermTaxonomyId(int termTaxonomyId) {
            this.termTaxonomyId = termTaxonomyId;
        }

        public int getTermTaxonomyId() {
            return termTaxonomyId;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setTermId(int termId) {
            this.termId = termId;
        }

        public int getTermId() {
            return termId;
        }

        public void setTaxonomy(String taxonomy) {
            this.taxonomy = taxonomy;
        }

        public String getTaxonomy() {
            return taxonomy;
        }

        public void setFilter(String filter) {
            this.filter = filter;
        }

        public String getFilter() {
            return filter;
        }

        public void setCategoryDescription(String categoryDescription) {
            this.categoryDescription = categoryDescription;
        }

        public String getCategoryDescription() {
            return categoryDescription;
        }

        public void setCategoryCount(int categoryCount) {
            this.categoryCount = categoryCount;
        }

        public int getCategoryCount() {
            return categoryCount;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setCatID(int catID) {
            this.catID = catID;
        }

        public int getCatID() {
            return catID;
        }

        public void setCategoryParent(int categoryParent) {
            this.categoryParent = categoryParent;
        }

        public int getCategoryParent() {
            return categoryParent;
        }

        public void setTermGroup(int termGroup) {
            this.termGroup = termGroup;
        }

        public int getTermGroup() {
            return termGroup;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getSlug() {
            return slug;
        }

        public void setCategoryNicename(String categoryNicename) {
            this.categoryNicename = categoryNicename;
        }

        public String getCategoryNicename() {
            return categoryNicename;
        }
    }

}