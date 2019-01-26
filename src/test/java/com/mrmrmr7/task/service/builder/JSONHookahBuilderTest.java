package com.mrmrmr7.task.service.builder;

import com.mrmrmr7.task.entity.Hookah;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JSONHookahBuilderTest {

    @Test
    void buildTest() {
        JSONHookahBuilder hookahBuilder = new JSONHookahBuilder();
        List<Hookah> actualHookahs = null;
        try {
            actualHookahs = hookahBuilder.build("src/main/resources/hookah");
        } catch (InvalidHookahException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Hookah> expectedHookahs = new ArrayList<>();
        expectedHookahs.add(new Hookah("MeatHookah1", 1));
        expectedHookahs.add(new Hookah());
        assertEquals(expectedHookahs.get(0).toString(), actualHookahs.get(0).toString());
    }
}