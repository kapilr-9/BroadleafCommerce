
/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.common.i18n.domain;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Table;
import org.hibernate.annotations.Type;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@javax.persistence.Table(name = "BLC_TRANSLATION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blStandardElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "TranslationImpl_friendlyName")
//multi-column indexes don't appear to get exported correctly when declared at the field level, so declaring here as a workaround
@Table(appliesTo = "BLC_TRANSLATION", indexes = {
        @Index(name = "TRANSLATION_INDEX", columnNames = {"ENTITY_TYPE","ENTITY_ID","FIELD_NAME","LOCALE_CODE"})
})
public class TranslationImpl implements Serializable, Translation {

    private static final long serialVersionUID = -122818474469147685L;

    @Id
    @GeneratedValue(generator = "TranslationId")
    @GenericGenerator(
        name = "TranslationId",
        strategy = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
                @Parameter(name = "segment_value", value = "TranslationImpl"),
                @Parameter(name = "entity_name", value = "org.broadleafcommerce.common.i18n.domain.TranslationImpl")
        }
    )
    @Column(name = "TRANSLATION_ID")
    protected Long id;

    @Column(name = "ENTITY_TYPE")
    //@AdminPresentation(friendlyName = "TranslationImpl_Entity_Type", order = 1, gridOrder = 1, prominent = true)
    protected String entityType;

    @Column(name = "ENTITY_ID")
    //@AdminPresentation(friendlyName = "TranslationImpl_Entity_ID", order = 2, gridOrder = 2, prominent = true)
    protected String entityId;

    @Column(name = "FIELD_NAME")
    //@AdminPresentation(friendlyName = "TranslationImpl_Field_Name", order = 3, gridOrder = 3, prominent = true)
    protected String fieldName;

    @Column(name = "LOCALE_CODE")
    //@AdminPresentation(friendlyName = "TranslationImpl_Locale_Code", order = 4, gridOrder = 4, prominent = true)
    protected String localeCode;

    @Column(name = "TRANSLATED_VALUE", length = Integer.MAX_VALUE - 1)
    @Lob
    @Type(type = "org.hibernate.type.StringClobType")
    //@AdminPresentation(friendlyName = "TranslationImpl_Translated_Value", order = 5, gridOrder = 5, prominent = true)
    protected String translatedValue;

    /* ************************ */
    /* CUSTOM GETTERS / SETTERS */
    /* ************************ */

    @Override
    public TranslatedEntity getEntityType() {
        return TranslatedEntity.getInstanceFromFriendlyType(entityType);
    }

    @Override
    public void setEntityType(TranslatedEntity entityType) {
        this.entityType = entityType.getFriendlyType();
    }

    /* ************************** */
    /* STANDARD GETTERS / SETTERS */
    /* ************************** */
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public String getEntityId() {
        return entityId;
    }

    @Override
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getLocaleCode() {
        return localeCode;
    }

    @Override
    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    @Override
    public String getTranslatedValue() {
        return translatedValue;
    }

    @Override
    public void setTranslatedValue(String translatedValue) {
        this.translatedValue = translatedValue;
    }

}
