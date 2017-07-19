package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ana on 17.07.2017.
 */
public class HHStrategy implements Strategy {
  //  private final String URL_FORMAT = "https://hh.ua/search/vacancy?text=java&&area=115&page=%d";
      private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) throws IOException {
        List<Vacancy> list = new ArrayList<>();
        int page = 0;
        while (true) {
            Document doc = getDocument(searchString, page++);
            Elements elements = doc.getElementsByAttributeValue("data-qa","vacancy-serp__vacancy");
            if (elements.size() <= 1)
                break;
            else {
                for (Element el : elements) {
                    Vacancy vacancy = new Vacancy();

                    String title = el.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").text();
                    vacancy.setTitle(title);

                    String salary = el.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").text();
                    if (salary != null)
                        vacancy.setSalary(salary);
                    else vacancy.setSalary("");

                    String city = el.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text();
                    vacancy.setCity(city);

                    String companyName = el.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text();
                    vacancy.setCompanyName(companyName);

                    String url = el.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").attr("href");
                    vacancy.setUrl(url);

                    String siteName = "http://hh.ua";
                    vacancy.setSiteName(siteName);

                    list.add(vacancy);
                }
            }
        }
        return list;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        String url = String.format(URL_FORMAT, searchString, page);
        //String url = "http://javarush.ru/testdata/big28data.html";
        Document doc = Jsoup.connect(url).userAgent("Chrome/59.0.3071.115").referrer("http://hh.ua").get();
        return doc;
    }

}
