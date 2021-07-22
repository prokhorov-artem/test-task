package ru.noveogroup.demo.service;

import java.util.Date;

public interface DateService {

    Date convert(String stringDate);

    String convert(Date date);

}
