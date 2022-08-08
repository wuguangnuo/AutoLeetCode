import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author wgn
 * @date 2022-06-04 18:53
 */
public class Lhelp {

    private List<String> lineList = null;
    public static Map<String, String> mapping;

    public Lhelp() {
        readAllLine();
    }

    public int size() {
        return lineList.size();
    }

    public int readAsInt(int lineNum) {
        String str = readLine(lineNum);
        return Integer.parseInt(str);
    }

    public long readAsLong(int lineNum) {
        String str = readLine(lineNum);
        return Long.parseLong(str);
    }

    public double readAsDouble(int lineNum) {
        String str = readLine(lineNum);
        return Double.parseDouble(str);
    }

    public int[] readAsArr(int lineNum) {
        String str = readLine(lineNum);
        return readAsArray(str);
    }

    public int[][] readAsGrid(int lineNum) {
        String str = readLine(lineNum);
        if (str == null || str.equals("[]"))
            return new int[0][0];
        str = str.replace("[[", "").replace("]]", "");
        String[] split = str.split("] *, *\\[");
        int a = split.length, b = str2arr(split[0]).length;
        int[][] grid = new int[a][b];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = readAsArray(split[i]);
        }
        return grid;
    }

    public ListNode readAsListNode(int lineNum) {
        String str = readLine(lineNum);
        int[] arr = readAsArray(str);
        if (arr.length == 0)
            return null;
        ListNode head = new ListNode(arr[0]), node = head;
        for (int i = 1; i < arr.length; i++) {
            node.next = new ListNode(arr[i]);
            node = node.next;
        }
        return head;
    }

    public TreeNode readAsTreeNode(int lineNum) {
        String str = readLine(lineNum);
        str = str.replace("[", "").replace("]", "");
        Integer[] arr = str2arr(str);
        if (arr == null || arr.length == 0)
            return null;
        TreeNode root = new TreeNode(arr[0]), node = root;
        Queue<TreeNode> queue = new LinkedList<>();
        int i = 0;
        while (node != null) {
            if (2 * i + 1 < arr.length) {
                Integer temp = arr[2 * i + 1];
                node.left = temp == null ? null : new TreeNode(temp);
                queue.add(node.left);
            }
            if (2 * i + 2 < arr.length) {
                Integer temp = arr[2 * i + 2];
                node.right = temp == null ? null : new TreeNode(temp);
                queue.add(node.right);
            }
            node = queue.poll();
            i += 1;
        }
        return root;
    }

    public String readAsStr(int lineNum) {
        String str = readLine(lineNum);
        if (str.startsWith("\"")) {
            str = str.substring(1);
        }
        if (str.endsWith("\"")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public String[] readAsStrArr(int lineNum) {
        String str = readLine(lineNum);
        return readAsStrArray(str);
    }

    public List<String> readAsStrList(int lineNum) {
        String str = readLine(lineNum);
        String[] strArr = readAsStrArray(str);
        return Arrays.asList(strArr);
    }

    public String[][] readAsStrGrid(int lineNum) {
        String str = readLine(lineNum);
        if (str == null || str.equals("[]"))
            return new String[0][0];
        str = str.replace("[[", "").replace("]]", "");
        String[] split = str.split("] *, *\\[");
        int a = split.length, b = readAsStrArray(split[0]).length;
        String[][] grid = new String[a][b];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = readAsStrArray(split[i]);
        }
        return grid;
    }

    public char[] readAsCharArr(int lineNum) {
        String str = readLine(lineNum);
        String[] strArr = readAsStrArray(str);
        char[] charArr = new char[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            charArr[i] = strArr[i].charAt(0);
        }
        return charArr;
    }

    public char[][] readAsCharGrid(int lineNum) {
        String[][] strGrid = readAsStrGrid(lineNum);
        int m = strGrid.length, n = strGrid[0].length;
        char[][] charGrid = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                charGrid[i][j] = strGrid[i][j].charAt(0);
            }
        }
        return charGrid;
    }

    // 参数过长截取打印
    public String readLineShort(int lineNum) {
        String str = readLine(lineNum);
        if (str != null && str.length() > 48) {
            str = str.substring(0, 45) + "...";
        }
        return str;
    }

    private int[] readAsArray(String str) {
        str = str.replace("[", "").replace("]", "");
        Integer[] arr = str2arr(str);
        if (arr == null || arr.length == 0)
            return new int[]{};
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    private String[] readAsStrArray(String str) {
        str = str.replace("[", "").replace("]", "");
        String[] split = str.split(",");
        String[] arr = new String[split.length];
        for (int i = 0; i < arr.length; i++) {
            if ("null".equals(split[i].trim())) {
                arr[i] = null;
            } else {
                arr[i] = split[i].trim().replace("\"", "");
            }
        }
        return arr;
    }

    private String readLine(int lineNum) {
        return lineList.get(lineNum - 1);
    }

    private Integer[] str2arr(String str) {
        if (str == null || str.length() == 0)
            return null;
        String[] split = str.split(",");
        Integer[] arr = new Integer[split.length];
        for (int i = 0; i < arr.length; i++) {
            if ("null".equals(split[i].trim())) {
                arr[i] = null;
            } else {
                arr[i] = Integer.parseInt(split[i].trim());
            }
        }
        return arr;
    }

    private void readAllLine() {
        String separator = File.separator;
        String FILE_PATH = System.getProperty("user.dir") + separator + "src" + separator + "Lhelp.txt";
        lineList = new ArrayList<>();
        BufferedReader br = null;
        try {
            String str;
            br = new BufferedReader(new FileReader(FILE_PATH));
            while ((str = br.readLine()) != null) {
                if (str.trim().startsWith("//")) continue;
                lineList.add(str.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static {
        mapping = new HashMap<>();
        mapping.put("int", "readAsInt");
        mapping.put("long", "readAsLong");
        mapping.put("double", "readAsDouble");
        mapping.put("int[]", "readAsArr");
        mapping.put("int[][]", "readAsGrid");
        mapping.put("java.lang.String", "readAsStr");
        mapping.put("java.lang.String[]", "readAsStrArr");
        mapping.put("java.lang.String[][]", "readAsStrGrid");
        mapping.put("java.util.List<java.lang.String>", "readAsStrList");
        mapping.put("char[]", "readAsCharArr");
        mapping.put("char[][]", "readAsCharGrid");
        mapping.put("ListNode", "readAsListNode");
        mapping.put("TreeNode", "readAsTreeNode");
    }
}
