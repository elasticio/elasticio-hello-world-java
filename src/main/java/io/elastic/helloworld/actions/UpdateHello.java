package io.elastic.helloworld.actions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.elastic.api.Component;
import io.elastic.api.EventEmitter;
import io.elastic.api.ExecutionParameters;
import io.elastic.api.Message;

public class UpdateHello extends Component {
    /**
     * Creates a component instance with the given {@link io.elastic.api.EventEmitter}.
     *
     * @param eventEmitter emitter to emit events
     */
    public UpdateHello(EventEmitter eventEmitter) {
        super(eventEmitter);
    }

    @Override
    public void execute(ExecutionParameters parameters) {

        final JsonObject configuration = parameters
                .getConfiguration();
        final JsonElement name = configuration.get("name");
        final Message message = parameters.getMessage();

        final JsonObject body = new JsonObject();
        body.addProperty("greeting",
                name.getAsString() + " How are you today?");
        body.addProperty("originalGreeting",
                message.getBody().get("greeting").getAsString());

        final Message response
                = new Message.Builder().body(body).build();

        getEventEmitter().emitData(response);
    }
}
