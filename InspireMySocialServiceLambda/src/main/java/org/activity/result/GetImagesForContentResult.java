package org.activity.result;

import org.model.ImageModel;

import java.util.Objects;

public class GetImagesForContentResult {


    private final ImageModel imageModel;

    /**
     * Constructor.
     * @param imageModel object created by and returned from the GetImagesForContentActivity
     */
    public GetImagesForContentResult(ImageModel imageModel) {
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

        public GetImagesForContentResult build() {return new GetImagesForContentResult(imageModel);}
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetImagesForContentResult that = (GetImagesForContentResult) o;
        return Objects.equals(imageModel, that.imageModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageModel);
    }
}
