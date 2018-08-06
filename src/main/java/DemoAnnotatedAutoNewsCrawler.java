import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;

/**
 * @Description TODO
 * @Author zhangpeng
 * @Date 2018/8/6 18:56
 **/
public class DemoAnnotatedAutoNewsCrawler extends BreadthCrawler{

    public DemoAnnotatedAutoNewsCrawler(String crawlPath, boolean autoParse){
        super(crawlPath, autoParse);
        this.addSeed(Const.GITHUB_BLOB_URL);
        for (int pageIndex = 2; pageIndex <=5; pageIndex++){
            this.addSeed(String.format(Const.GITHUB_BLOB_URL + "/page/%d", pageIndex));
        }
        this.addRegex(Const.REGEX);
        setThreads(50);
        getConf().setTopN(100);
    }

    @MatchUrl(urlRegex = Const.REGEX)
    public void visitNew(Page page, CrawlDatums next){
        String title = page.select("h1[class=lh-condensed]").first().text();
        String content = page.selectText("div.content.markdown-body");

        Const.systemOutInfos(page.url(), title, content);
    }

    public void visit(Page page, CrawlDatums next) {
        System.out.println("visit pages that don't match any annotation rules: " + page.url());
    }

    public static void main(String[] args) throws Exception {
        DemoAnnotatedAutoNewsCrawler crawler = new DemoAnnotatedAutoNewsCrawler("crawl", true);

        crawler.start(4);
    }
}
