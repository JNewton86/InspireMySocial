package org.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.activity.request.DeleteContentRequest;
import org.activity.result.DeleteContentResult;

public class DeleteContentLambda extends LambdaActivityRunner<DeleteContentRequest, DeleteContentResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteContentRequest>, LambdaResponse> {

@Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteContentRequest> input, Context context) {
        return super.runActivity(
                        () -> {
                        DeleteContentRequest unauthenticatedRequest = input.fromBody(DeleteContentRequest.class);
                        return input.fromUserClaims(claims -> DeleteContentRequest.builder()
                            .withUserId(claims.get("email"))
                            .withContentId(unauthenticatedRequest.getContentId())
                            .build());
                        },
                    (request, serviceComponent) -> serviceComponent.provideDeleteContentActivity().handleRequest(request)
        );
    }
}
