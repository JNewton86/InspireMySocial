package org.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.activity.request.UpdateUserCreditBalanceRequest;

import org.activity.result.UpdateUserCreditBalanceResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateUserCreditBalanceLambda extends LambdaActivityRunner<UpdateUserCreditBalanceRequest,
        UpdateUserCreditBalanceResult> implements RequestHandler<AuthenticatedLambdaRequest
        <UpdateUserCreditBalanceRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateUserCreditBalanceRequest> input,
                                        Context context) {
        log.info("UpdateUserCreditBalanceLambda reached");
        return super.runActivity(
            () -> {
                UpdateUserCreditBalanceRequest unauthenticatedRequest = input.fromBody(
                    UpdateUserCreditBalanceRequest.class);
                return input.fromUserClaims(claims -> UpdateUserCreditBalanceRequest.builder()
                        .withUserEmail(claims.get("email"))
                        .withCreditUsage(unauthenticatedRequest.getCreditUsage())
                        .build());
            },
            (request, serviceComponent) -> serviceComponent.provideUpdateUserCreditBalanceActivity()
                        .handleRequest(request)
        );
    }
}
