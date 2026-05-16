package ru.itis.controlworkstub.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Locale;

@ControllerAdvice
public class LocaleModelAdvice {

    @ModelAttribute("lang")
    public String lang(Locale locale) {
        return locale.getLanguage();
    }
}
