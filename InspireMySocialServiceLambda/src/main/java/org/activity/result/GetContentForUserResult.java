package org.activity.result;

import org.model.ContentModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GetContentForUserResult {

    private final List<ContentModel> contentModelList;

    /**
     * Constructor for class.
     * @param contentModelList pass in a List of ContentModels from the GetContentForUserActivity
     */
    public GetContentForUserResult(List<ContentModel> contentModelList) {
        this.contentModelList = contentModelList;
    }

    @Override
    public String toString() {
        return "GetContentForUserResult{" +
                "contentList=" + contentModelList +
                '}';
    }
    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public List<ContentModel> getContentList() {
        return new ArrayList<>(contentModelList);
    }

    //CHECKSTYLE:OFF:Builder
    public static class Builder{
        private List<ContentModel> contentModelList;

        public Builder withContentModelList(List<ContentModel> contentModelList) {
            this.contentModelList = new ArrayList<>(contentModelList);
            return this;
        }
        public GetContentForUserResult build(){
            return new GetContentForUserResult(contentModelList);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GetContentForUserResult that = (GetContentForUserResult) o;
        return Objects.equals(contentModelList, that.contentModelList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentModelList);
    }
}

