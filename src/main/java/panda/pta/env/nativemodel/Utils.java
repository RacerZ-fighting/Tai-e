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

package panda.pta.env.nativemodel;

import panda.callgraph.CallKind;
import panda.pta.core.ProgramManager;
import panda.pta.element.CallSite;
import panda.pta.element.Method;
import panda.pta.element.Obj;
import panda.pta.element.Type;
import panda.pta.element.Variable;
import panda.pta.env.EnvObj;
import panda.pta.statement.Allocation;
import panda.pta.statement.Call;

import java.util.Collections;

/**
 * Convenient methods for creating native models.
 */
class Utils {

    /**
     * Create allocation site and the corresponding constructor call site.
     * This method only supports non-argument constructor.
     * @param pm the program manager
     * @param container method containing the allocation site
     * @param type type of the allocated object
     * @param name name of the allocated object
     * @param recv variable holds the allocated object and
     *            acts as the receiver variable for the constructor call
     * @param ctorSig signature of the constructor
     * @param callId ID of the mock constructor call site
     */
    static void modelAllocation(
            ProgramManager pm, Method container,
            Type type, String name, Variable recv,
            String ctorSig, String callId) {
        Obj obj = new EnvObj(name, type, container);
        container.addStatement(new Allocation(recv, obj));
        Method ctor = pm.getUniqueMethodBySignature(ctorSig);
        MockCallSite initCallSite = new MockCallSite(
                CallKind.SPECIAL, ctor,
                recv, Collections.emptyList(),
                container, callId);
        Call initCall = new Call(initCallSite, null);
        container.addStatement(initCall);
    }

    /**
     * Model the side effects of a static native call r = T.foo(o, ...)
     * by mocking a virtual call r = o.m().
     */
    static void modelStaticToVirtualCall(
            ProgramManager pm, Method container, Call call,
            String calleeSig, String callId) {
        CallSite origin = call.getCallSite();
        origin.getArg(0).ifPresent(arg0 -> {
            Method callee = pm.getUniqueMethodBySignature(calleeSig);
            MockCallSite callSite = new MockCallSite(CallKind.VIRTUAL, callee,
                    arg0, Collections.emptyList(),
                    container, callId);
            Call mockCall = new Call(callSite, call.getLHS().orElse(null));
            container.addStatement(mockCall);
        });
    }
}
