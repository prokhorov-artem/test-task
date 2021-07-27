package ru.noveogroup.demo.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.noveogroup.demo.service.DateService;

@Service
@Slf4j
public class DateServiceImpl implements DateService {

    public static final String DATE_PATTERN = "dd-MM-yyyy";

    @Override
    public LocalDate convert(String stringDate) {
        try {
            return LocalDate.from(DateTimeFormatter.ofPattern(DATE_PATTERN).parse(stringDate));
        } catch (Exception e) {
            log.error("Can't parse date: {}", stringDate);
        }
        return null;
    }

    @Override
    public String convert(LocalDate date) {
        return date == null ? null : DateTimeFormatter.ofPattern(DATE_PATTERN).format(date);
    }
}
