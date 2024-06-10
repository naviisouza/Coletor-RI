package edu.com.app;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UOLNewsCrawler {

    public static List<Application.NewsData> extractNewsData(String url, int maxNews) {
        List<Application.NewsData> newsList = new ArrayList<>();
        try {
            // Baixa o conteúdo HTML da URL
            Document doc = Jsoup.connect(url).get();
            // Extrai os links das notícias principais
            Elements newsLinks = extractNewsLinks(doc);
            // Itera sobre os links das notícias
            int count = 0;
            for (Element link : newsLinks) {
                String newsUrl = link.attr("href");
                String title = link.text();
                if (!title.isEmpty()) {
                    newsList.add(new Application.NewsData("UOL", title, newsUrl));
                    count++;
                }
                if (count >= maxNews) {
                    break;
                }
            }
            System.out.println("Notícias do UOL extraídas com sucesso!");
        } catch (IOException e) {
            // Trata erros de conexão ou leitura de página
            System.err.println("Erro ao tentar acessar a página do UOL: " + e.getMessage());
        }
        return newsList;
    }

    // Extrai os links das notícias principais da página inicial do UOL
    private static Elements extractNewsLinks(Document document) {
        return document.select("a[href*='/noticias/']");
    }
}
