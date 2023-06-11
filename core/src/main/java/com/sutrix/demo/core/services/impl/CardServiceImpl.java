package com.sutrix.demo.core.services.impl;

import com.sutrix.demo.core.services.CardService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.caconfig.annotation.Property;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(
        service = CardService.class,
        immediate = true,
        property = {
                Constants.SERVICE_ID + "=Card Service",
                Constants.SERVICE_DESCRIPTION + "=This service reads values from Card Configuration"
        })
@Designate(ocd = CardServiceImpl.Configuration.class)
public class CardServiceImpl implements CardService {

    private String HomepageURL;

    private String OrganizationName;

    @Activate
    @Modified
    public void activate(Configuration config) {
        this.HomepageURL = config.HomepageURL();
        this.OrganizationName = config.OrganizationName();
    }
    @Override
    public String getOrganizationName() {
        return OrganizationName;
    }

    @Override
    public String getHomepageURL() {
        return HomepageURL;
    }

    @ObjectClassDefinition(name = "Card Configuration")
    public @interface Configuration {

        @AttributeDefinition(
                name = "Homepage URL", type = AttributeType.STRING,
                description = "URL of the website's homepage"
        )
        public String HomepageURL() default StringUtils.EMPTY;

        @AttributeDefinition(
                name = "Organization Name", type = AttributeType.STRING,
                description = "Name of the organization you wish to display on the card"
        )
        public String OrganizationName() default StringUtils.EMPTY;

    }
}
