package org.example.strategies.byset;

import java.util.Arrays;
import java.util.Collection;

public class HighLifeBySetStrategy extends BySetStrategy {

    @Override
    protected Collection<Integer> whenAliveKeepAlive() {
        return Arrays.asList(2, 3);
    }

    @Override
    protected Collection<Integer> whenDeadResurrect() {
        return Arrays.asList(3, 6);
    }
}