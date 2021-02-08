/*
 * Tai-e: A Program Analysis Framework for Java
 *
 * Copyright (C) 2020 Tian Tan <tiantan@nju.edu.cn>
 * Copyright (C) 2020 Yue Li <yueli@nju.edu.cn>
 * All rights reserved.
 *
 * This software is designed for the "Static Program Analysis" course at
 * Nanjing University, and it supports a subset of Java features.
 * Tai-e is only for educational and academic purposes, and any form of
 * commercial use is disallowed.
 */

package pascal.taie.icfg;

import pascal.taie.dataflow.analysis.EdgeTransfer;

public class LocalEdge<Node> extends Edge<Node> {

    public LocalEdge(Node source, Node target) {
        super(Kind.LOCAL, source, target);
    }

    @Override
    public <Domain> void accept(EdgeTransfer<Node, Domain> transfer,
                                Domain sourceInFlow, Domain sourceOutFlow,
                                Domain edgeFlow) {
        transfer.transferLocalEdge(this, sourceOutFlow, edgeFlow);
    }
}