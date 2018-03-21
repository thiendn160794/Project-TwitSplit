package com.assignment.thiendn.project_twitsplit;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by thiendn on 3/17/18.
 */
public class MessageUtilsTest {

    @Test
    public void splitMessage() throws Exception {

        // Please change LIMIT_MESSAGE_LENGTH = 20 for easily testing

        String input1 = "I can't image Tweeter now supports chunking my messages.";
        String[] output1;
        String[] expected1 = new String[]{"1/5 I can't image",
                                        "2/5 Tweeter now",
                                        "3/5 supports",
                                        "4/5 chunking my",
                                        "5/5 messages."};
        output1 = MessageUtils.splitMessage(input1);
        assertEquals(expected1, output1);

        String input2 = "Ixcan'txbelievexT weeter";
        String[] output2;
        String[] expected2 = new String[]{};
        output2 = MessageUtils.splitMessage(input2);
        assertEquals(expected2, output2);

        String input3 = "I can't believeTweeterxnow";
        String[] output3;
        String[] expected3 = new String[]{};
        output3 = MessageUtils.splitMessage(input3);
        assertEquals(expected3, output3);

        String input4 = "     I can't image Tweeter               now supports chunking my messages.                   ";
        String[] output4;
        String[] expected4 = new String[]{"1/5 I can't image",
                "2/5 Tweeter now",
                "3/5 supports",
                "4/5 chunking my",
                "5/5 messages."};
        output4 = MessageUtils.splitMessage(input4);
        assertEquals(expected4, output4);

        String input5 = "   Hello World";
        String[] output5;
        String[] expected5 = new String[]{"   Hello World"};
        output5 = MessageUtils.splitMessage(input5);
        assertEquals(expected5, output5);
    }
}