import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Auto LeetCode
 *
 * @author wgn
 * @date 2022-06-17 16:13
 */
public class Main {
    public static void main(String[] args) {

        Class<Solution> cls = Solution.class;
        Lhelp help = new Lhelp();
        int lineNum = 0;

        Method[] declaredMethods = cls.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (Modifier.isPublic(declaredMethod.getModifiers())) {

                while (lineNum < help.size()) {
                    println("\n类名:" + cls.getName() + " 方法名:" + declaredMethod.getName());
                    Parameter[] parameters = declaredMethod.getParameters();
                    StringBuilder temp = new StringBuilder();
                    Object[] params = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        String typeName = parameters[i].getParameterizedType().getTypeName();
                        temp.append("参数").append(i + 1).append(": ")
                                .append(typeName.substring(typeName.lastIndexOf(".") + 1))
                                .append(" %").append(help.readLineShort(++lineNum)).append("%  ");
                        params[i] = buildParam(help, typeName, lineNum);
                    }
                    println(temp.toString());
                    exists(declaredMethod, params);
                }
                break;
            }
        }
    }

    private static void exists(Method method, Object[] params) {
        Solution solution = new Solution();
        Object result = null;
        long existsTm = System.currentTimeMillis();

        try {
            existsTm = System.currentTimeMillis();
            result = method.invoke(solution, params);
            existsTm = System.currentTimeMillis() - existsTm;
        } catch (IllegalAccessException | InvocationTargetException e) {
            printErr("执行错误", e);
            existsTm = System.currentTimeMillis() - existsTm;
        }

        String typeName = method.getReturnType().getTypeName();
        println("执行结束，耗时: " + existsTm + "ms");
        println("执行结果: " + typeName.substring(typeName.lastIndexOf(".") + 1) + " " + p(result));
    }

    private static Object buildParam(Lhelp help, String typeName, int lineNum) {
        String methodName = Lhelp.mapping.get(typeName);
        Object invoke = null;

        try {
            Method method = Lhelp.class.getDeclaredMethod(methodName, Integer.TYPE);
            invoke = method.invoke(help, lineNum);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            printErr("参数错误", e);
        }
        return invoke;
    }

    private static void println(String str) {
        StringBuilder sb = new StringBuilder("\033[34m");
        boolean start = true;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '%') {
                sb.append(str.charAt(i));
            } else {
                if (start) {
                    sb.append("\033[31m");
                } else {
                    sb.append("\033[34m");
                }
                start = !start;
            }
        }
        sb.append("\033[m");
        System.out.println(sb.toString());
    }

    private static void printErr(String msg, Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String[] errSpl = sw.toString().split("\n");

        StringBuilder sb = new StringBuilder("\033[47;31;1;3;4m===!");
        sb.append(msg);
        sb.append("!===\033[m");
        for (String err : errSpl) {
            sb.append("\n\033[47;31m");
            sb.append(err);
            sb.append("\033[m");
        }
        System.out.println(sb);
    }

    // 特殊格式打印
    private static String p(Object o) {
        String ans;
        if (o == null) {
            ans = "NULL";
        } else if (o instanceof ListNode) {
            List<Integer> list = new ArrayList<>();
            while (o != null) {
                list.add(((ListNode) o).val);
                o = ((ListNode) o).next;
            }
            ans = list.toString();
        } else if (o instanceof TreeNode) {
            List<Integer> list = new ArrayList<>();
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add((TreeNode) o);
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            ans = list.toString();
        } else if (o.getClass().isArray()) {
            switch (o.getClass().getTypeName()) {
                case "int[]":
                    ans = Arrays.toString((int[]) o);
                    break;
                case "int[][]":
                    ans = Arrays.deepToString((int[][]) o);
                    break;
                case "long[]":
                    ans = Arrays.toString((long[]) o);
                    break;
                case "java.lang.String[]":
                    ans = Arrays.toString((String[]) o);
                    break;
                default:
                    ans = o.toString();
                    break;
            }
        } else {
            ans = o.toString();
        }
        return "%" + ans + "%";
    }
}
