package io.elastic.helloworld.triggers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.elastic.api.Component;
import io.elastic.api.EventEmitter;
import io.elastic.api.ExecutionParameters;
import io.elastic.api.Message;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GetHello extends Component {

    /**
     * Creates a component instance with the given {@link io.elastic.api.EventEmitter}.
     *
     * @param eventEmitter emitter to emit events
     */
    public GetHello(EventEmitter eventEmitter) {
        super(eventEmitter);
    }

    @Override
    public void execute(ExecutionParameters parameters) {
        List<String> lines = Arrays.asList("spring", "node", "igor");

        List<String> result = lines.stream() 			//convert list to stream
                .filter(line -> !"igor". equals (line))	//filters the line, equals to "mkyong"
                .collect(Collectors.toList());			//collect the output and convert streams to a List

        result.forEach(System.out::println);			//output : spring node

        final JsonElement name = parameters
                .getConfiguration().get("name");

        final JsonObject body = new JsonObject();
        body.addProperty("greeting", "Hello " + name.getAsString() + "!");

        final Message response
                = new Message.Builder().body(body).build();

        getEventEmitter().emitData(response);

    }
}
