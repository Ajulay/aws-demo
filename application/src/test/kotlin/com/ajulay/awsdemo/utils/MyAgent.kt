package test.kotlin.com.ajulay.awsdemo.utils

import net.bytebuddy.agent.builder.AgentBuilder
import net.bytebuddy.asm.Advice
import net.bytebuddy.description.type.TypeDescription
import net.bytebuddy.dynamic.DynamicType
import net.bytebuddy.matcher.ElementMatchers.*
import net.bytebuddy.utility.JavaModule

import java.lang.instrument.Instrumentation
import java.security.ProtectionDomain


object MyAgent {
    @JvmStatic
    fun premain(agentArgs: String?, inst: Instrumentation) {
        AgentBuilder.Default()
            .type(any()) // Match any class
            .transform { builder: DynamicType.Builder<*>, typeDescription: TypeDescription, classLoader: ClassLoader?, javaModule: JavaModule?, protectionDomain: ProtectionDomain? ->
                builder.visit(Advice.to(MyAdvice::class.java).on(named("methodName")))
            }
            .installOn(inst)
    }

    class MyAdvice {
        companion object {
            @Advice.OnMethodEnter
            @JvmStatic
            fun enter() {
                // Code to execute before the method
            }

            @Advice.OnMethodExit
            @JvmStatic
            fun exit() {
                // Code to execute after the method
            }
        }
    }
}