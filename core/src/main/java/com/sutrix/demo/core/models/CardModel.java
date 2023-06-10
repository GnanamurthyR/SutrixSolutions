package com.sutrix.demo.core.models;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Model(
        adaptables = {Resource.class},
        adapters = {CardModel.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class CardModel{
    private static final Logger LOGGER = LoggerFactory.getLogger(CardModel.class);

    @ChildResource
    String cardTitle;

    @ChildResource
    String buttonText;

    public String getCardTitle() {
        return cardTitle;
    }

    public String getButtonText() {
        return buttonText;
    }

}
