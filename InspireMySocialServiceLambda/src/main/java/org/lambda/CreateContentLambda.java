package org.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.activity.CreateContentActivity;
import org.activity.request.CreateContentRequest;
import org.activity.result.CreateContentResult;

import java.util.Map;
import java.util.SortedMap;

public class CreateContentLambda
        extends LambdaActivityRunner<CreateContentRequest, CreateContentResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateContentRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateContentRequest> input, Context context) {
        CreateContentRequest unauthenticatedRequest1 = input.fromBody(CreateContentRequest.class);
        System.out.println(unauthenticatedRequest1.toString());
        return super.runActivity(
            () -> {
                System.out.println("*** creating Content Request object in CreateLambda ***");
                CreateContentRequest unauthenticatedRequest = input.fromBody(CreateContentRequest.class);

                return input.fromUserClaims(claims -> {
                    String email = claims.get("email");
                    System.out.println("Email is : " + email);

                    return CreateContentRequest.builder()
                            .withUserId(email)
                            .withContentType(unauthenticatedRequest.getContentType())
                            .withTone(unauthenticatedRequest.getTone())
                            .withAudience(unauthenticatedRequest.getAudience())
                            .withTopic(unauthenticatedRequest.getTopic())
                            .withWordCount(unauthenticatedRequest.getWordCount())
                            .build();
                });
            },
            (request, serviceComponent) -> {
                try {
                    System.out.println("you are hitting the request for the activity");
                    System.out.println("this is the service component " + serviceComponent);
                    CreateContentActivity provideCreateContentActivity = serviceComponent.provideCreateContentActivity();
                    System.out.println("provideCreateContent activity line passed" + provideCreateContentActivity.toString());
                    CreateContentResult result = provideCreateContentActivity.handleRequest(request);
                    System.out.println("handlerequest requested, result: " + request.toString());
                    return result;
                } catch (Exception e){
                    System.out.println(e);
                    return null;
                }
            }
        );
    }
}

