package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by Ana on 18.07.2017.
 */
public class HtmlView implements View {
    private Controller controller;
  //  private final String filePath = "./src/" + getClass().getPackage().getName().replaceAll("\\.", "/") + "/vacancies.html";
    private final String filePath = "C:\\Users\\Ana\\Documents\\JavaRushTasks\\4.JavaCollections\\src\\com\\javarush\\task\\task28\\task2810\\view\\vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies) {
        System.out.println(vacancies.size());
        try {
            String fileUpdated = getUpdatedFileContent(vacancies);
            updateFile(fileUpdated);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private String getUpdatedFileContent(List<Vacancy> vacancies) throws IOException {
        Document doc;
        try {
            doc = getDocument();
            Element element = doc.getElementsByClass("template").first();
            Element cloneElement = element.clone();

            cloneElement.removeClass("template");
            cloneElement.removeAttr("style");

            Elements elements = doc.getElementsByAttributeValue("class", "vacancy");
            for (int i = 0; i < elements.size(); i++) {
                elements.get(i).remove();
            }

            for (Vacancy d : vacancies) {
                Element el = cloneElement.clone();
                el.getElementsByClass("city").first().text(d.getCity());
                el.getElementsByClass("companyName").first().text(d.getCompanyName());
                el.getElementsByClass("salary").first().text(d.getSalary());
                Element linkEl = el.getElementsByTag("a").first();
                linkEl.text(d.getTitle());
                linkEl.attr("href", d.getUrl());
                element.before(el.outerHtml());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Some exception occurred";
        }
        return doc.toString();
    }

    private void updateFile(String str) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            fileOutputStream.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() throws IOException {
        controller.onCitySelect("kiev+junior");
    }

    protected Document getDocument() throws IOException {
        return Jsoup.parse(new File (filePath), "UTF-8");
    }
}
