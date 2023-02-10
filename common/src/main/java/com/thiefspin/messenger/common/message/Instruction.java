package com.thiefspin.messenger.common.message;

public record Instruction(
        InstructionType type,
        String topic,
        String message
) {
}
