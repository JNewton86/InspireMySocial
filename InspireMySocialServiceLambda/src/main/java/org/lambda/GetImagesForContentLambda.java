package org.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.activity.request.CreateImageForContentRequest;
import org.activity.request.GetContentForUserRequest;
import org.activity.request.GetImagesForContentRequest;
import org.activity.result.GetImagesForContentResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetImagesForContentLambda extends LambdaActivityRunner<GetImagesForContentRequest, GetImagesForContentResult>
    implements RequestHandler<AuthenticatedLambdaRequest<GetImagesForContentRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetImagesForContentRequest> input, Context context) {
        log.info("GetImagesForContentLmabda reached");
        return super.runActivity(
                () -> {
                    GetImagesForContentRequest unauthenticatedRequest = input.fromBody(GetImagesForContentRequest.class);
                    return input.fromUserClaims(claims -> GetImagesForContentRequest.builder()
                            .withUserEmail(claims.get("email"))
                            .withContentId(unauthenticatedRequest.getContentId())
                            .build());
                },
                (request, serviceComponent) -> serviceComponent.provideGetImagesForContentActivity().handleRequest(request)
        );
    }
}
