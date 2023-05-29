package org.activity.result;

import org.model.ContentModel;

import java.util.Objects;

public class CreateContentResult {

    private final ContentModel contentModel;

    private CreateContentResult(ContentModel contentModel) {
        this.contentModel = contentModel;
    }

    public ContentModel getContentModel() {
        return contentModel;
    }

    @Override
    public String toString() {
        return "CreateContentResult{ " +
                "contentModel=" + contentModel +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ContentModel contentModel;

        public Builder withContentModel(ContentModel contentModel) {
            this.contentModel = contentModel;
            return this;
        }

        public CreateContentResult build() {
            return new CreateContentResult(contentModel);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateContentResult that = (CreateContentResult) o;
        return Objects.equals(contentModel, that.contentModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentModel);
    }
}
