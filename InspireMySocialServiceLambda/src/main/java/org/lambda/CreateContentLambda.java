package org.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.activity.CreateContentActivity;
import org.activity.request.CreateContentRequest;
import org.activity.result.CreateContentResult;

import java.util.Map;
import java.util.SortedMap;
import java.util.logging.Logger;

public class CreateContentLambda extends LambdaActivityRunner<CreateContentRequest, CreateContentResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateContentRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateContentRequest> input, Context context) {
           return super.runActivity(
                        () -> {
                CreateContentRequest unauthenticatedRequest = input.fromBody(CreateContentRequest.class);
                return input.fromUserClaims(claims -> CreateContentRequest.builder()
                        .withUserId(claims.get("email"))
                        .withContentType(unauthenticatedRequest.getContentType())
                        .withTone(unauthenticatedRequest.getTone())
                        .withAudience(unauthenticatedRequest.getAudience())
                        .withTopic(unauthenticatedRequest.getTopic())
                        .withWordCount(unauthenticatedRequest.getWordCount())
                        .build());
            },
            (request, serviceComponent) -> serviceComponent.provideCreateContentActivity().handleRequest(request)
    );
    }
}

