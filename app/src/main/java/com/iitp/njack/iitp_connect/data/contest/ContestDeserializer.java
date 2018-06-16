package com.iitp.njack.iitp_connect.data.contest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContestDeserializer extends StdDeserializer<ContestListWrapper> {
    public ContestDeserializer() {
        super(ContestListWrapper.class);
    }

    @Override
    public ContestListWrapper deserialize(JsonParser jsonParser, DeserializationContext ctxt)
            throws IOException {
        List<Contest> contestList = new ArrayList<>();

        ContestListWrapper wrapper = new ContestListWrapper();

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode models = node.get("models");

        if (models.isArray()) {
            for (final JsonNode objNode : models) {
                Contest contest = new Contest();
                contest.setTitle(objNode.get("title").asText());
                contest.setDescription(objNode.get("description").asText());
                contest.setStartDate(objNode.get("start").asText());
                contest.setEndDate(objNode.get("end").asText());
                contest.setUrl(objNode.get("url").asText());

                contestList.add(contest);
            }
        } else {
            Contest contest = new Contest();
            contest.setTitle(models.get("title").asText());
            contest.setDescription(models.get("description").asText());
            contest.setStartDate(models.get("start").asText());
            contest.setEndDate(models.get("end").asText());
            contest.setUrl(models.get("url").asText());

            contestList.add(contest);
        }

        wrapper.setContests(contestList);
        return wrapper;
    }
}
