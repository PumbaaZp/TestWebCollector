import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;

/**
 * @Description TODO
 * @Author zhangpeng
 * @Date 2018/8/2 17:28
 **/
public class DemoAutoNewsCrawler extends BreadthCrawler {

    public DemoAutoNewsCrawler(String crawlPath, boolean autoParse){
        super(crawlPath, autoParse);
        /*start pages*/
        this.addSeed(Const.GITHUB_BLOB_URL);
        for(int pageIndex = 2; pageIndex <=5; pageIndex++){
            String seedUrl = String.format(Const.GITHUB_BLOB_URL + "/page/%d/", pageIndex);
            this.addSeed(seedUrl);
        }
        this.addRegex(Const.REGEX);
        setThreads(50);
        getConf().setTopN(100);
    }
    public void visit(Page page, CrawlDatums next) {
        String url = page.url();
        if(page.matchUrl(Const.REGEX)){
            String title = page.select("h1[class=lh-condensed]").first().text();
            String content = page.selectText("div.content.markdown-body");

            Const.systemOutInfos(page.url(), title, content);
        }
    }

    public static void main(String[] args) throws Exception {
        DemoAutoNewsCrawler crawler = new DemoAutoNewsCrawler("crawl", true);
        crawler.start(4);
    }
}
