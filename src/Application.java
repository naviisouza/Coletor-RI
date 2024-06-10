
package edu.com.app;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        // Define os URLs das fontes de notícias
        String g1Url = "https://g1.globo.com/";
        String terraUrl = "https://www.terra.com.br/";
        String uolUrl = "https://www.uol.com.br/";

        // Define o número máximo de notícias a serem extraídas de cada fonte
        int maxNewsPerSource = 5;

        // Extrai as notícias de cada fonte
        List<NewsData> g1News = G1NewsCrawler.extractNewsData(g1Url, maxNewsPerSource);
        List<NewsData> terraNews = TerraNewsCrawler.extractNewsData(terraUrl, maxNewsPerSource);
        List<NewsData> uolNews = UOLNewsCrawler.extractNewsData(uolUrl, maxNewsPerSource);

        // Une todas as notícias em uma lista única
        List<NewsData> allNews = new ArrayList<>();
        allNews.addAll(g1News);
        allNews.addAll(terraNews);
        allNews.addAll(uolNews);

        // Gera o relatório
        generateReport(allNews);
    }

    // Classe interna para representar uma notícia
    static class NewsData {
        String source;
        String title;
        String link;

        NewsData(String source, String title, String link) {
            this.source = source;
            this.title = title;
            this.link = link;
        }
    }

    // Função para gerar o relatório com as notícias
    private static void generateReport(List<NewsData> newsList) {
        String reportFileName = "relatorio_noticias.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFileName))) {
            writer.write("Relatório das notícias mais relevantes:\n\n");
            for (NewsData news : newsList) {
                writer.write("Fonte: " + news.source + "\n");
                writer.write("Título: " + news.title + "\n");
                writer.write("Link: " + news.link + "\n\n");
            }
            System.out.println("Relatório gerado com sucesso: " + reportFileName);
        } catch (IOException e) {
            System.err.println("Erro ao gerar o relatório: " + e.getMessage());
        }
    }
}

