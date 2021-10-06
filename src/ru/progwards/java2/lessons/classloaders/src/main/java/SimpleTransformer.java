import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;


public class SimpleTransformer implements ClassFileTransformer { // Это класс, который и будет заниматься изменением классов на низком уровне

    @Override
    public byte[] transform(final ClassLoader loader,
                            final String className,
                            final Class<?> classBeingRedefined,
                            final ProtectionDomain protectionDomain,
                            final byte[] classfileBuffer) {

        byte[] byteCode = classfileBuffer;
        ClassPool pool = ClassPool.getDefault(); // готовим пул классов
        return byteCode;
    }
}