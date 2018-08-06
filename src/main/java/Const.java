/**
 * @Description TODO
 * @Author zhangpeng
 * @Date 2018/8/6 18:28
 **/
public class Const {

    public final static String GITHUB_BLOB_URL = "https://blog.github.com";

    public final static String REGEX = "https://blog.github.com/[0-9]{4}-[0-9]{2}-[0-9]{2}-[^/]+/";

    public static void systemOutInfos(String url, String title, String content){
        System.out.println("URL:\n" + url);
        System.out.println("title:\n" + title);
        System.out.println("content:\n" + content);
    }
}
