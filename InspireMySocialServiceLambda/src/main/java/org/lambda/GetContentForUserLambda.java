package org.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.activity.request.GetContentForUserRequest;
import org.activity.result.GetContentForUserResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetContentForUserLambda extends LambdaActivityRunner<GetContentForUserRequest, GetContentForUserResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetContentForUserRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetContentForUserRequest> input, Context context) {
        log.info("GetContentForUserLambda reached");
        return super.runActivity(

            () -> input.fromPath(path ->
                        GetContentForUserRequest.builder()
                                .withUserEmail(path.get("userEmail"))
                                .build()),
            (request, serviceComponent) -> serviceComponent.provideGetContentForUserActivity().handleRequest(request)
        );
    }
}

