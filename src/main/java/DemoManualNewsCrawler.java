import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;

/**
 * @Description TODO
 * @Author zhangpeng
 * @Date 2018/8/6 18:25
 **/
public class DemoManualNewsCrawler extends BreadthCrawler{

    private String TYPE_LIST = "list";
    private String TYPE_CONTENT = "content";

    public DemoManualNewsCrawler(String crawlPath, boolean autoParse){
        super(crawlPath, autoParse);
        this.addSeedAndReturn(Const.GITHUB_BLOB_URL).type(TYPE_LIST);
        for(int pageIndex = 2; pageIndex <=5; pageIndex++){
            String seedUrl = String.format(Const.GITHUB_BLOB_URL + "/page/%d/", pageIndex);
            this.addSeed(seedUrl, TYPE_LIST);
        }

        setThreads(50);
        getConf().setTopN(100);
    }

    public void visit(Page page, CrawlDatums next) {
        String url = page.url();
        if (page.matchType(TYPE_LIST)){
            next.add(page.links("h1.lh-condensed>a")).type(TYPE_CONTENT);
        }else if (page.matchType(TYPE_CONTENT)){
            String title = page.select("h1[class=lh-condensed]").first().text();
            String content = page.selectText("div.content.markdown-body");
            title = getConf().getString("title_prefix") + title;
            content = content.substring(0, getConf().getInteger("content_length_limit"));

            Const.systemOutInfos(page.url(), title, content);
        }
    }

    public static void main(String[] args) throws Exception {
        DemoManualNewsCrawler crawler = new DemoManualNewsCrawler("crawl", false);
        crawler.getConf().setExecuteInterval(5000);
        crawler.getConf().set("title_prefix", "PREFIX_");
        crawler.getConf().set("content_limit_length", 20);

        crawler.start(4);
    }
}
