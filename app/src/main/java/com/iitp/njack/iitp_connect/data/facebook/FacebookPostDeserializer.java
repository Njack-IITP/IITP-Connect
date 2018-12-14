package com.iitp.njack.iitp_connect.data.facebook;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FacebookPostDeserializer extends StdDeserializer<FacebookPostListWrapper> {
    public FacebookPostDeserializer() {
        super(FacebookPostListWrapper.class);
    }

    @Override
    public FacebookPostListWrapper deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {

        List<FacebookPost> facebookPostList = new ArrayList<>();

        FacebookPostListWrapper wrapper = new FacebookPostListWrapper();

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode data = node.get("data");

        if (data.isArray()) {
            for (final JsonNode objNode : data) {
                FacebookPost facebookPost = new FacebookPost();
                JsonNode from = objNode.get("from");
                JsonNode picture = from.get("picture");
                JsonNode datum = picture.get("data");
                JsonNode likes = objNode.get("likes");
                JsonNode likesSummary = likes.get("summary");
                JsonNode comments = objNode.get("comments");
                JsonNode commentsSummary = comments.get("summary");

                if (objNode.get("shares") != null) {
                    JsonNode shares = objNode.get("shares");
                    facebookPost.setShares(shares.get("count").asText());
                } else {
                    facebookPost.setShares("0");
                }
                facebookPost.setTime(objNode.get("created_time").asText());
                facebookPost.setName(from.get("name").asText());
                facebookPost.setUrl(objNode.get("permalink_url").asText());
                if (objNode.get("full_picture") != null)
                    facebookPost.setPostpic(objNode.get("full_picture").asText());
                facebookPost.setPropic(datum.get("url").asText());
                if (objNode.get("message") != null)
                    facebookPost.setMessage(objNode.get("message").asText());
                facebookPost.setLikes(likesSummary.get("total_count").asText());
                facebookPost.setComments(commentsSummary.get("total_count").asText());

                facebookPostList.add(facebookPost);
            }
        } else {
            FacebookPost facebookPost = new FacebookPost();
            JsonNode from = data.get("from");
            JsonNode picture = from.get("picture");
            JsonNode datum = picture.get("data");
            JsonNode likes = data.get("likes");
            JsonNode likesSummary = likes.get("summary");
            JsonNode comments = data.get("comments");
            JsonNode commentsSummary = comments.get("summary");

            if (data.get("shares") != null) {
                JsonNode shares = data.get("shares");
                facebookPost.setShares(shares.get("count").asText());
            } else {
                facebookPost.setShares("0");
            }
            facebookPost.setTime(data.get("created_time").asText());
            facebookPost.setName(from.get("name").asText());
            facebookPost.setUrl(data.get("permalink_url").asText());
            if (data.get("full_picture") != null)
                facebookPost.setPostpic(data.get("full_picture").asText());
            facebookPost.setPropic(datum.get("url").asText());
            if (data.get("message") != null)
                facebookPost.setMessage(data.get("message").asText());
            facebookPost.setLikes(likesSummary.get("total_count").asText());
            facebookPost.setComments(commentsSummary.get("total_count").asText());

            facebookPostList.add(facebookPost);
        }

        wrapper.setFacebookPosts(facebookPostList);
        return wrapper;
    }
}
