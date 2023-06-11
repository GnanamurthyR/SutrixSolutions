package com.sutrix.demo.core.models;

import com.sutrix.demo.core.services.CardService;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;


@Model(
        adaptables = {Resource.class},
        adapters = {CardModel.class},
        resourceType = {CardModel.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class CardModel{
    protected static final String RESOURCE_TYPE = "mydemo/components/content/card";
    private static final Logger LOGGER = LoggerFactory.getLogger(CardModel.class);
    private static final String TAG = CardModel.class.getSimpleName();

    @ChildResource
    String cardTitle;

    @ChildResource
    String buttonText;

    @OSGiService
    CardService cardService;

    private String organizationName;

    private String homepageURL;

    @PostConstruct
    protected void init() {
        organizationName = cardService.getOrganizationName();
        homepageURL = cardService.getHomepageURL();
        LOGGER.info("{}: organization name: {}", TAG, organizationName);
        LOGGER.info("{}: homepage url: {}", TAG, homepageURL);
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public String getButtonText() {
        return buttonText;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getHomepageURL() {
        return homepageURL;
    }
}
