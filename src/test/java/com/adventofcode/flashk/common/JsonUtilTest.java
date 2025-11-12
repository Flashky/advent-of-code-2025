package com.adventofcode.flashk.common;

import com.adventofcode.flashk.common.test.constants.TestFilename;
import com.adventofcode.flashk.common.test.constants.TestFolder;
import com.adventofcode.flashk.common.test.utils.Input;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonElement;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class JsonUtilTest {

    @Test
    void buildTreeFromArrayJsonTest() {
        String content = Input.readStringLines(TestFolder.COMMON, TestFilename.ARRAY_JSON).get(0);
        JsonNode jsonNode = JsonUtil.buildTree(content);

        assertNotNull(jsonNode);
        assertTrue(jsonNode.isArray());

        assertEquals(2,jsonNode.get(0).asInt());
        assertEquals(3,jsonNode.get(1).asInt());
        assertEquals(4,jsonNode.get(2).asInt());
    }

    @Test
    void buildTreeFromInvalidJsonTest() {
        String content = Input.readStringLines(TestFolder.COMMON, TestFilename.INVALID_JSON).get(0);
        assertThrows(IllegalArgumentException.class, () -> { JsonUtil.buildTree(content); });
    }

    @Test
    void buildGsonTreeTest() {

        String content = Input.readStringLines(TestFolder.COMMON, TestFilename.ARRAY_JSON).get(0);
        JsonElement jsonElement = JsonUtil.buildGsonTree(content);

        assertNotNull(jsonElement);
        assertTrue(jsonElement.isJsonArray());

        assertEquals(2, jsonElement.getAsJsonArray().get(0).getAsInt());
        assertEquals(3, jsonElement.getAsJsonArray().get(1).getAsInt());
        assertEquals(4, jsonElement.getAsJsonArray().get(2).getAsInt());
    }

    @Test
    void isIntTest() {

        String content = Input.readStringLines(TestFolder.COMMON, TestFilename.ARRAY_JSON).get(0);
        JsonElement jsonElement = JsonUtil.buildGsonTree(content).getAsJsonArray().get(0);

        assertTrue(JsonUtil.isInt(jsonElement));
    }
}