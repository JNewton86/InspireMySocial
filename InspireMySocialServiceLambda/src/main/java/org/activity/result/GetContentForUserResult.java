package org.activity.result;

import org.model.ContentModel;

import java.util.ArrayList;
import java.util.List;

public class GetContentForUserResult {

    private final List<ContentModel> contentModelList;

    public GetContentForUserResult(List<ContentModel> contentModelList) {this.contentModelList = contentModelList;}

    @Override
    public String toString() {
        return "GetContentForUserResult{" +
                "contentList=" + contentModelList +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public List<ContentModel> getContentList() {
        return new ArrayList<>(contentModelList);
    }

    public static class Builder{
        private List<ContentModel>  contentModelList;

        public Builder withContentModelList(List<ContentModel> contentModelList){
            this.contentModelList = new ArrayList<>(contentModelList);
            return this;
        }
        public GetContentForUserResult build(){
            return new GetContentForUserResult(contentModelList);
        }
    }
}

