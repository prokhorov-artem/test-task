package ru.noveogroup.demo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.noveogroup.demo.service.DateService;

@Service
@Slf4j
public class DateServiceImpl implements DateService {

    public static final String DATE_PATTERN = "dd-MM-yyyy";

    @Override
    public Date convert(String stringDate) {
        try {
            return new SimpleDateFormat(DATE_PATTERN).parse(stringDate);
        } catch (Exception e) {
            log.error("Can't parse date: {}", stringDate);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String convert(Date date) {
        return date == null ? null : new SimpleDateFormat(DATE_PATTERN).format(date);
    }
}
