package org.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.activity.request.GetCreditsByUserRequest;
import org.activity.result.GetCreditsByUserResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetCreditsByUserLambda extends LambdaActivityRunner<GetCreditsByUserRequest, GetCreditsByUserResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetCreditsByUserRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetCreditsByUserRequest> input, Context context) {
        log.info("GetCreditByUserLambda reached");
        System.out.println("input is: " + input);
        return super.runActivity(
                () -> input.fromUserClaims(claims ->
                        {
                        System.out.println("User email from claims is: "  + claims.get("email"));
                        log.error(claims.get("email"));
                        return GetCreditsByUserRequest.builder()
                                .withUserEmail(claims.get("email"))
                                .withName(claims.get("name"))
                                .build(); }),

                (request, serviceComponent) -> serviceComponent.provideGetCreditsByUserActivity().handleRequest(request)
        );
    }
}
