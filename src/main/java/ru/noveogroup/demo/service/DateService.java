package ru.noveogroup.demo.service;

import java.time.LocalDate;

public interface DateService {

    LocalDate convert(String stringDate);

    String convert(LocalDate date);

}
