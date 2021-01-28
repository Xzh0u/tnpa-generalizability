import com.github.javaparser.ast.CompilationUnit;
// import org.apache.commons.io.FileUtils;

// import java.io.File;
// import java.util.ArrayList;
import java.util.concurrent.Callable;

public class ASTExplorer implements Callable<Void> {

    ASTExplorer() {
    }

    @Override
    public Void call() {
        inspectDataset();
        return null;
    }

    private void inspectDataset() {
        // String inputDir = Common.ROOT_INPUT_DIR;
        String inputCode = Common.ROOT_INPUT_CODE;
        // ArrayList<File> javaFiles = new ArrayList<>(
        // FileUtils.listFiles(
        // new File(inputDir),
        // new String[]{"java"},
        // true)
        // );
        // System.out.println(inputDir + " : " + javaFiles.size());

        // javaFiles.forEach((javaFile) -> {
        try {
            CompilationUnit cu = Common.getParseUnit(inputCode);
            if (cu != null) {
                // Common.CURRENT_JAVA_FILE = javaFile;
                invokeTransformations(cu);
            }
        } catch (Exception ignore) {
        }
        // });
    }

    private void invokeTransformations(CompilationUnit cu) {
        try {
            new VariableRenaming().inspectSourceCode(cu.clone());
            new PermuteStatement().inspectSourceCode(cu.clone());
            new UnusedStatement().inspectSourceCode(cu.clone());
            new LoopExchange().inspectSourceCode(cu.clone());
            new SwitchToIf().inspectSourceCode(cu.clone());
            new BooleanExchange().inspectSourceCode(cu.clone());
        } catch (Exception ignore) {
        }
    }
}
