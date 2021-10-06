package com.company;

import javax.smartcardio.*;
import java.nio.ByteBuffer;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            // Display the list of terminals
            TerminalFactory factory = TerminalFactory.getDefault();
            List<CardTerminal> terminals = factory.terminals().list();
            System.out.println("Terminals: " + terminals);

            // Use the first terminal
            CardTerminal terminal = terminals.get(0);

            // Connect wit hthe card
            Card card = terminal.connect("*");
            System.out.println("card: " + card);
            CardChannel channel = card.getBasicChannel();

            System.out.println("channel " + channel);

//             Send Select Applet command
//            Thread thread = new Thread(() -> {
//                try {
//                    card.beginExclusive();
//                } catch (CardException e) {
//                    System.out.println("CardEception" + e.getMessage());
//                }
//            });
//            thread.start();

            byte[] chooseApplet = ExtensionsKt.fromHexStringToByteArray("00A404000E66697363616C6472697665533031");
            ByteBuffer chooseAppl = ByteBuffer.wrap(chooseApplet);
//            byte[] aid = {(byte) 0xA0, 0x00, 0x00, 0x00, 0x62, 0x03, 0x01, 0x0C, 0x06, 0x01};
//            ResponseAPDU answer = channel.transmit(new CommandAPDU(0x00, 0xA4, 0x04, 0x00, aid));
            ResponseAPDU answer = channel.transmit(new CommandAPDU(chooseAppl));

            System.out.println("answer: " + answer.toString());

            // Send test command
            answer = channel.transmit(new CommandAPDU(0x00, 0x04, 0x00, 0x00));
            System.out.println("answer: data " + answer.getData().length);
            System.out.println("answer: " + answer.getBytes().length);
            System.out.println("to hex " + ExtensionsKt.fromByteArrayToHexString(answer.getBytes()));

            byte r[] = answer.getData();
            for (int i = 0; i < r.length; i++)
                System.out.print((char) r[i]);
            System.out.println();

            // Disconnect the card
            card.disconnect(false);
        } catch (Exception e) {
            System.out.println("Ouch: " + e.toString());
        }
    }
}




