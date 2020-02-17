package ru.valerykorzh.springdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import ru.valerykorzh.springdemo.domain.Tag;
import ru.valerykorzh.springdemo.service.TagService;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.forLanguageTag("ru"));
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return  lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        WebMvcConfigurer.super.addFormatters(registry);
        registry.addFormatter(tagSetFormatter());

    }

    @Bean
    public TagSetFormatter tagSetFormatter() {
        return new TagSetFormatter();
    }

    private static class TagSetFormatter implements Formatter<Set<Tag>> {

        @Autowired
        private TagService tagService;

        @Override
        public Set<Tag> parse(String s, Locale locale) throws ParseException {
            Set<Tag> tagSet = new HashSet<>();
            Arrays.stream(s.split("\\s+"))
                    .forEach(tag -> tagService.findByName(tag)
                            .ifPresentOrElse(tagSet::add, () -> tagSet.add(new Tag(tag))));
            return tagSet;
        }

        @Override
        public String print(Set<Tag> tags, Locale locale) {
            return tags.stream().map(Tag::toString).collect(Collectors.joining(" "));
        }
    }
}
