package com.pulse.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

/**
 * Service for resolving localized message codes from the application message bundle.
 */
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

    public String getMessage(String code, Object... args) {

        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());

    }

}
