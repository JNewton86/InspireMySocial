package org.activity.result;

import org.model.ImageModel;

import java.util.Objects;

public class CreateImageForContentResult {

    private final ImageModel imageModel;

    /**
     * Constructor.
     * @param imageModel object created by and returned from the CreateImageForContentActivity
     */
    public CreateImageForContentResult(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    public ImageModel getImageModel() {
        return imageModel;
    }

    @Override
    public String toString() {
        return "CreateImageForContentResult{" +
                "imageModel=" + imageModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ImageModel imageModel;

        public Builder withImageModel(ImageModel imageModel){
            this.imageModel = imageModel;
            return this;
        }

        public CreateImageForContentResult build() {return new CreateImageForContentResult(imageModel);}
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateImageForContentResult that = (CreateImageForContentResult) o;
        return Objects.equals(imageModel, that.imageModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageModel);
    }
}
