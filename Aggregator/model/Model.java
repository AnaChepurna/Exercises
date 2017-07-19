package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.view.View;
import com.javarush.task.task28.task2810.vo.Vacancy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ana on 18.07.2017.
 */
public class Model {
    private Provider [] providers;
    private View view;

    public Model(View view, Provider... providers)
    {
        if (view == null || providers == null) {
            throw new IllegalArgumentException();
        }if (providers.length == 0) {
        throw new IllegalArgumentException();
    }
        this.view = view;
        this.providers = providers;
    }

    public void selectCity(String city) throws IOException {
        List<Vacancy> vacancyList = new ArrayList<>();
        for (int i=0;i<providers.length;i++){
            vacancyList.addAll(providers[i].getJavaVacancies(city));
        }
        view.update(vacancyList);
    }
}
