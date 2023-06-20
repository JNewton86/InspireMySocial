package org.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.activity.request.DeleteContentRequest;
import org.activity.result.DeleteContentResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteContentLambda extends LambdaActivityRunner<DeleteContentRequest, DeleteContentResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteContentRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteContentRequest> input, Context context) {
        log.info("DeleteContentLambda called");
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
