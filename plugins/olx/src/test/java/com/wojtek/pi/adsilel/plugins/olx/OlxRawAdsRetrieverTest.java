package com.wojtek.pi.adsilel.plugins.olx;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.*;


public class OlxRawAdsRetrieverTest {

    private static String OlxFileContent;
    private static String WikiFileContent;

    @BeforeClass
    public static void setUpClass() throws URISyntaxException, IOException {
        OlxFileContent = getContentOfFile("exampleWebPageWithAds.html");
        WikiFileContent = getContentOfFile("WikiWebPageWithAds.html");
    }

    private static String getContentOfFile(String name) throws URISyntaxException, IOException {
        URL resource = OlxRawAdsRetrieverTest.class.getResource(name);
        Path path = Paths.get(resource.toURI());
        return Files.lines(path).collect(Collectors.joining());
    }

    @Test
    public void fetchAds() throws IOException {
        //equivalent, but from website:
//        Document doc = Jsoup.connect("https://www.olx.pl/nieruchomosci/mieszkania/sprzedaz/krakow/").get();
        Document doc = Jsoup.parse(OlxFileContent);
        System.out.println(doc.title());
        Elements newsHeadlines = doc.select("[href~=.*oferta.*]");
        Set<String> urlsList = new HashSet<>();
        for (Element headline : newsHeadlines) {
            System.out.println(headline.attr("title"));
            String url = headline.attr("href");
            urlsList.add(url);
        }

        //TODO: Investigate - retrieve without duplicates!
        assertThat(urlsList).hasSize(86);
    }

    @Test
    public void fetchInfoFromWiki() throws IOException {
        Document doc = Jsoup.parse(WikiFileContent);
        System.out.println(doc.title());
        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
            System.out.println(headline.attr("title"));
        }
    }
}