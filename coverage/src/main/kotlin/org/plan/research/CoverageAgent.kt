package org.plan.research.org.plan.research

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import java.lang.instrument.ClassFileTransformer
import java.lang.instrument.Instrumentation
import java.security.ProtectionDomain


class CoverageAgent {

    fun premain(agentArgs: String, inst: Instrumentation) {
        inst.addTransformer(object : ClassFileTransformer {
            override fun transform(
                loader: ClassLoader, className: String, classBeingRedefined: Class<*>,
                protectionDomain: ProtectionDomain, classfileBuffer: ByteArray
            ): ByteArray? = when {
                className.startsWith("org/plan/research") && !className.contains("instrumentation") -> {
                    transformClass(className, classfileBuffer)
                }

                else -> {
                    null
                }
            }
        })
    }

    private fun transformClass(
        @Suppress("UNUSED_PARAMETER") className: String,
        classFileBuffer: ByteArray
    ): ByteArray {
        val cr = ClassReader(classFileBuffer)
        val cw = ClassWriter(cr, ClassWriter.COMPUTE_FRAMES or ClassWriter.COMPUTE_MAXS)
        val cv: ClassVisitor = object : ClassVisitor(Opcodes.ASM9, cw) {
            override fun visitMethod(
                access: Int,
                name: String,
                descriptor: String,
                signature: String,
                exceptions: Array<String?>
            ): MethodVisitor {
                val mv: MethodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
                return object : MethodVisitor(Opcodes.ASM9, mv) {
                    override fun visitLineNumber(line: Int, start: Label) {
                        super.visitLineNumber(line, start)
                        CoverageTracker.logFullCoverage(name, line.toString())
                        mv.visitLdcInsn(name)
                        mv.visitLdcInsn(line.toString())
                        mv.visitMethodInsn(
                            Opcodes.INVOKESTATIC,
                            "org/plan/research/CoverageTracker",
                            "logCoverage",
                            "(Ljava/lang/String;Ljava/lang/String;)V",
                            false
                        )
                    }
                }
            }
        }
        cr.accept(cv, 0)
        return cw.toByteArray()
    }
}
