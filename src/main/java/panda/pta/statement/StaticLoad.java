/*
 * Panda - A Program Analysis Framework for Java
 *
 * Copyright (C) 2020 Tian Tan <tiantan@nju.edu.cn>
 * Copyright (C) 2020 Yue Li <yueli@nju.edu.cn>
 * All rights reserved.
 *
 * This software is designed for the "Static Program Analysis" course at
 * Nanjing University, and it supports a subset of Java features.
 * Panda is only for educational and academic purposes, and any form of
 * commercial use is disallowed.
 */

package panda.pta.statement;

import panda.pta.element.Field;
import panda.pta.element.Variable;

/**
 * Represents a static load: to = T.field.
 */
public class StaticLoad implements Statement {

    private final Variable to;

    private final Field field;

    public StaticLoad(Variable to, Field field) {
        this.to = to;
        this.field = field;
    }

    public Variable getTo() {
        return to;
    }

    public Field getField() {
        return field;
    }

    @Override
    public void accept(StatementVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Kind getKind() {
        return Kind.STATIC_LOAD;
    }

    @Override
    public String toString() {
        return to + " = " + field;
    }
}
