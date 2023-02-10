package com.thiefspin.messenger.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiefspin.messenger.common.message.Instruction;
import com.thiefspin.messenger.common.message.InstructionType;
import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.util.Optional;

public class MessageHandler {

    private final Logger logger = Logger.getLogger(this.getClass());

    private final PrintWriter out;
    private final String msg;

    public MessageHandler(PrintWriter out, String msg) {
        this.out = out;
        this.msg = msg;
    }

    public void handle() {
        logger.info("Handling message on " + Thread.currentThread().getName());

        parse(msg).ifPresentOrElse(this::handleValidInstruction, this::handleInvalidInstruction);
    }

    private void handleValidInstruction(Instruction instruction) {
        logger.info("Received instruction: " + instruction.toString());
        if (instruction.type() == InstructionType.PRODUCER) {
            logger.info(instruction.message());
        }
    }

    private void handleInvalidInstruction() {
        out.println("Invalid instruction received");
    }

    private Optional<Instruction> parse(String msg) {
        try {
            return Optional.of(new ObjectMapper().readValue(msg, Instruction.class));
        } catch (JsonProcessingException e) {
            logger.error("Unrecognised message format received: " + msg);
            return Optional.empty();
        }
    }
}
