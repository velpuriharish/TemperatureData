package com.interview.program2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeatingTest {

    @Test
    void seatThem() {

        assertEquals("M P C \nC M P ",new Seating().seatThem(6));
        assertEquals("M P C M P C \nC M P C M P ",new Seating().seatThem(12));
    }

    @Test

    void seatThemError(){
        assertThrows(RuntimeException.class, ()-> {
            new Seating().seatThem(5);
        });
    }
}