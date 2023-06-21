package org.model;

import java.util.List;
import java.util.Objects;

public class ImageModel {


    /**
     * List of image results as s3 url to access the image.
     */
    List<String> data;

    /**
     * Constructor.
     * @param data passed in from the method building the image object, is a set of String that are URLs for the images
     * from the s3 bucket
     */
    public ImageModel(List<String> data) {
        this.data = data;
    }



    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImageModel that = (ImageModel) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<String> data;



        public Builder withData(List<String> data) {
            this.data = data;
            return this;
        }

        public ImageModel build() {
            return new ImageModel(data); }
    }
}
