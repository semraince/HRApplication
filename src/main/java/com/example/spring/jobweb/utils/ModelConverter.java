package com.example.spring.jobweb.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Scope(value="request",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ModelConverter {
    /** @param sourceList List of Database models
    * @param type     DTO class reference.
    * @param <TTarget>  Target type
    * @param <TSource>  Source type
    * @return list of converted DTOs
     */
    //private static final Logger logger = LogManager.getLogger();
    public <TTarget extends IModel<TSource>,TSource> List<TTarget> getModelListFromDataList(List<TSource> sourceList, Class<TTarget> type) {
        List<TTarget> converted = new ArrayList<>();

        for (TSource source : sourceList) {
            converted.add(getModelFromData(source, type));
        }

        return converted;
    }
    public <TTarget extends IModel<TSource>, TSource> TTarget getModelFromData(TSource source, Class<TTarget> type) {
        try {

            TTarget target=type.getConstructor().newInstance();

            target.getDataFromDatabaseModel(source);

            return target;
        } catch (Exception e) {
            //logger.error("FATAL: Failed to create DTO of Entity.");
            //logger.error(e.getMessage(), e);
            throw new RuntimeException("Failed to create DTO from Entity.");
        }
    }
}
