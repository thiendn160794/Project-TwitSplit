package com.assignment.thiendn.project_twitsplit;

import android.util.Log;

import java.util.InputMismatchException;

import static com.assignment.thiendn.project_twitsplit.Common.LIMIT_MESSAGE_LENGTH;

/**
 * Created by thiendn on 3/17/18.
 */

public class MessageUtils {

    private static String WHITE_SPACE = " ";

    public static String[] splitMessage(String inputMsg) throws InputMismatchException {
        int assumeNumberOfChildMessage = (int) Math.ceil(inputMsg.length() / (double)LIMIT_MESSAGE_LENGTH);

        String[] results;
        if (inputMsg.length() < LIMIT_MESSAGE_LENGTH) {
            results = new String[]{inputMsg};
            return results;
        }
        if (!inputMsg.contains(WHITE_SPACE)) {
            results = new String[0];
            return results;
        }
        String tempMsg;
        //remove leading, trailing space and duplicate white space.
        inputMsg = inputMsg.trim().replaceAll("\\s+", " ");
        do {
            tempMsg = inputMsg;
            results = new String[assumeNumberOfChildMessage];
            String prefix;
            String msg;
            for (int i = 1; i <= assumeNumberOfChildMessage; i++) {
                prefix = i + "/" + assumeNumberOfChildMessage;
                tempMsg = prefix + " " + tempMsg;
                msg = getFirstMostPossibleChildString(tempMsg);
                if (msg.equals(prefix)) {
                    results = new String[0];
                    tempMsg = "";
                    break;
                } else {
                    results[i - 1] = msg;
                    tempMsg = tempMsg.substring(msg.length(), tempMsg.length());

                    if (tempMsg.length() > 0) {
                        //delete first WHITE_SPACE character
                        tempMsg = tempMsg.substring(1, tempMsg.length());
                    }
                }
            }
            assumeNumberOfChildMessage += 1;
        } while (tempMsg.length() > 0);

        return results;
    }

    private static String getFirstMostPossibleChildString(String inputString) {
        if (inputString.length() <= LIMIT_MESSAGE_LENGTH) {
            return inputString;
        } else {
            return inputString.substring(0, getMostPossibleWhiteSpacePosition(inputString));
        }
    }

    private static int getMostPossibleWhiteSpacePosition(String inputString) {
        int currentWhiteSpacePosition;
        boolean positionIsPossible = false;

        do {
            currentWhiteSpacePosition = inputString.lastIndexOf(WHITE_SPACE);
            if (currentWhiteSpacePosition < LIMIT_MESSAGE_LENGTH) {
                positionIsPossible = true;
            } else {
                inputString = inputString.substring(0, currentWhiteSpacePosition);
            }
        } while (!positionIsPossible);
        return currentWhiteSpacePosition;
    }
}
