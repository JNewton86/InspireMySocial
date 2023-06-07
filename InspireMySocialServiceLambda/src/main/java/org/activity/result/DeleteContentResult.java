package org.activity.result;

import org.model.ContentModel;

import java.util.Objects;

public class DeleteContentResult {

    private final ContentModel contentModel;

    private DeleteContentResult(ContentModel contentModel) {
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
    //CHECKSTYLE:OFF:Builder
    public static DeleteContentResult.Builder builder() {
        return new DeleteContentResult.Builder();
    }

    public static class Builder {
        private ContentModel contentModel;

        public DeleteContentResult.Builder withContentModel(ContentModel contentModel) {
            this.contentModel = contentModel;
            return this;
        }

        public DeleteContentResult build() {
            return new DeleteContentResult(contentModel);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteContentResult that = (DeleteContentResult) o;
        return Objects.equals(contentModel, that.contentModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentModel);
    }
}

