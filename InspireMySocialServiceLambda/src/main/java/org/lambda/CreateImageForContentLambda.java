package org.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.activity.request.CreateImageForContentRequest;
import org.activity.result.CreateImageForContentResult;

public class CreateImageForContentLambda extends LambdaActivityRunner<CreateImageForContentRequest,
        CreateImageForContentResult> implements RequestHandler<AuthenticatedLambdaRequest
        <CreateImageForContentRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateImageForContentRequest> input,
        Context context) {
        return super.runActivity(
            () -> {
                CreateImageForContentRequest unauthenticatedRequest = input.fromBody(
                        CreateImageForContentRequest.class);
                return input.fromUserClaims(claims -> CreateImageForContentRequest.builder()
                        .withUserId(claims.get("email"))
                        .withContentId(unauthenticatedRequest.getContentId())
                        .withPrompt(unauthenticatedRequest.getPrompt())
                        .withNumberOfImages(1)
                        .withImageSize(unauthenticatedRequest.getImageSize())
                        .withResponse_format("b64_json")
                        .build());
            },
            (request, serviceComponent) -> serviceComponent.provideCreateImageForContentActivity()
                    .handleRequest(request)
        );
    }
}
