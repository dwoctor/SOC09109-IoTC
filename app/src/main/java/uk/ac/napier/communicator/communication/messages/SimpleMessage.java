package uk.ac.napier.communicator.communication.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import uk.ac.napier.communicator.communication.protocols.SimpleMessageProtocol;

/**
 * Created by David on 21/01/2015.
 */
public class SimpleMessage extends uk.ac.napier.communicator.communication.messages.Message {

    @Expose
    @SerializedName("message")
    private String message = "Hello, World!";

    private String url = "http://192.168.0.244/";

    public String jsonize() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }

    public static SimpleMessage dejsonize(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, SimpleMessage.class);
    }

    public SimpleMessage() throws Exception {
        SimpleMessageProtocol protocol = new SimpleMessageProtocol();
        protocol.setMessage(this.message);
        this.setMessageProtocol(protocol);
    }

}
