package com.adventofcode.flashk.day08;

import module java.base;
import com.adventofcode.flashk.common.Vector3;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class JunctionBox {

    @EqualsAndHashCode.Include
    private final Vector3 position;

    @Setter
    private long circuitId;

    public JunctionBox(String input) {
        position = new Vector3(input);
    }

}
