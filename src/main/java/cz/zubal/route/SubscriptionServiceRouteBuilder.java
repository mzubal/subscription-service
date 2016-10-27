package cz.zubal.route;

import cz.zubal.model.api.MessageResponse;
import cz.zubal.model.api.SubscriptionResponse;
import cz.zubal.model.api.ReadResponse;
import cz.zubal.route.adapters.CamelPostMessageAdapter;
import cz.zubal.route.adapters.CamelSubscriptionCreateAdapter;
import cz.zubal.route.adapters.CamelSubscriptionReadAdapter;
import cz.zubal.route.adapters.CamelSubscriptionUpdateAdapter;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by milos.zubal on 27.10.2016.
 * Camel Routes implementing the REST endpoints and high-level routing
 */
@Component
public class SubscriptionServiceRouteBuilder extends RouteBuilder {

    private static final String DIRECT_CREATE = "direct:createSubscription";
    public static final String DIRECT_READ = "direct:readMessages";
    public static final String DIRECT_UPDATE = "direct:updateSubscription";
    public static final String DIRECT_POST_MESSAGE = "direct:postMessage";

    @Autowired
    private CamelSubscriptionCreateAdapter camelSubscriptionCreateAdapter;

    @Autowired
    private CamelSubscriptionReadAdapter camelSubscriptionReadAdapter;

    @Autowired
    private CamelSubscriptionUpdateAdapter camelSubscriptionUpdateAdapter;

    @Autowired
    private CamelPostMessageAdapter camelPostMessageAdapter;

    @Override
    public void configure() throws Exception {
        // Camel tracing on for easier debugging
        getContext().setTracing(true);

        // Data formats for marshalling to JSON
        JacksonDataFormat subscriptionResponse = new JacksonDataFormat(SubscriptionResponse.class);
        subscriptionResponse.setPrettyPrint(true);

        JacksonDataFormat readResponseFormat = new JacksonDataFormat(ReadResponse.class);
        readResponseFormat.setPrettyPrint(true);

        JacksonDataFormat messageResponseFormat = new JacksonDataFormat(MessageResponse.class);
        messageResponseFormat.setPrettyPrint(true);

        // formatting off to preserve the formatting of Camel Routes
        // @formatter:off
        // setup of Rest Endpoint along with swagger configuration
        restConfiguration().component("undertow").bindingMode(RestBindingMode.off)
            // and output using pretty print
            .dataFormatProperty("prettyPrint", "true")
            // setup context path and port number that netty will use
            .contextPath("/").port(8090)
            // add swagger api-doc out of the box
            .apiContextPath("/subscription-service/api-doc")
            .apiProperty("api.title", "Subscription Service API")
            .apiProperty("api.version", "0.1")
            .host("localhost")
            // and enable CORS
            .enableCORS(true);

        // Default handling for all exception - just return HTTP 500 and the exception message
        onException(Exception.class)
            .handled(true)
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
            .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
            .transform(exceptionMessage());


        // Main routing of REST requests to camel routes
        rest("/subscription-service")
            .put("/create/{" + CustomHeaders.HEADER_MESSAGE_TYPES + "}")
                .description("The service creates new Subscription, taking comma separated list of message types (which are subscribed) as argument.")
                .to(DIRECT_CREATE)
            .get("/read/{" + CustomHeaders.HEADER_SUBSCRIPTION_GUID + "}")
                .description("For Subscription guid, the service returns all relevant messages along with cardinality of all returned types.")
                .to(DIRECT_READ)
            .post("/update/{" + CustomHeaders.HEADER_SUBSCRIPTION_GUID + "}/{" + CustomHeaders.HEADER_MESSAGE_TYPES + "}")
                .description("For Subscription guid, the service updates the subscribed message types.")
                .to(DIRECT_UPDATE)
            .put("/post-message/{" + CustomHeaders.HEADER_MESSAGE_TYPE + "}/{" + CustomHeaders.HEADER_MESSAGE_CONTENT + "}")
                .description("The service posts a new message, taking message type and content as argument.")
                .to(DIRECT_POST_MESSAGE);

        // Routes
        from(DIRECT_CREATE)
            .routeId("createRoute")
            .bean(camelSubscriptionCreateAdapter)
            .marshal(subscriptionResponse);

        from(DIRECT_READ)
            .routeId("readRoute")
            .bean(camelSubscriptionReadAdapter)
            .marshal(readResponseFormat);

        from(DIRECT_UPDATE)
            .routeId("updateRoute")
            .bean(camelSubscriptionUpdateAdapter)
            .marshal(readResponseFormat);

        from(DIRECT_POST_MESSAGE)
            .routeId("postMessageRoute")
            .bean(camelPostMessageAdapter)
            .marshal(messageResponseFormat);
        // @formatter:on
    }
}
