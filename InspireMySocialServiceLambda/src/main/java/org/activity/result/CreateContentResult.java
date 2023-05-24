package org.activity.result;

import org.model.ContentModel;

public class CreateContentResult {

    private final ContentModel contentModel;

    private CreateContentResult(ContentModel contentModel) {
        this.contentModel = contentModel;
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
}
