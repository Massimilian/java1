import java.lang.instrument.Instrumentation;

public class Interceptor {
    public static void premain(String agentArgument, Instrumentation instrumentation) {
        instrumentation.addTransformer(new SystemProfiler(agentArgument));
    }
}
