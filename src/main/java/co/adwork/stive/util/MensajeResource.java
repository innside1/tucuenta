package co.adwork.stive.util;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

/**
 * Componente para lectura de properties
 * @author juan.hoyos
 *
 */
@Component
public class MensajeResource {

    @Autowired
    private MessageSource messageSource;
    private MessageSourceAccessor accessor;
   
    @PostConstruct
    private void init() {
        Locale locale = new Locale.Builder().setLanguage("es").setRegion("AR").build();
        accessor = new MessageSourceAccessor(messageSource, locale);
    }

    public String get(String code) {
        return accessor.getMessage(code);        
    }

}
