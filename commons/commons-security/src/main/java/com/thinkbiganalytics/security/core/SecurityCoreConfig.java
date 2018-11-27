/**
 * 
 */
package com.thinkbiganalytics.security.core;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.thinkbiganalytics.security.core.encrypt.EncryptedStringDeserializer;

/*-
 * #%L
 * kylo-commons-security
 * %%
 * Copyright (C) 2017 ThinkBig Analytics
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import com.thinkbiganalytics.security.core.encrypt.EncryptionService;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.bootstrap.encrypt.EncryptionBootstrapConfiguration;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.cloud.bootstrap.encrypt.RsaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 */
@Configuration
@EnableConfigurationProperties({ KeyProperties.class, RsaProperties.class })
@Import({ EncryptionBootstrapConfiguration.class })
public class SecurityCoreConfig {

    @Bean
    @ConditionalOnMissingBean
    public EncryptionService encryptionService() {
        return new EncryptionService();
    }
    
    @Bean
    public EncryptedStringDeserializer encryptedStringDeserializer() {
        return new EncryptedStringDeserializer();
    }
    
    @Bean
    public Module decryptionModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, encryptedStringDeserializer());
        return module;
    }
    
    /*@Bean
    @ConditionalOnMissingBean
    public TextEncryptorLocator textEncryptorLocator(TextEncryptor textEncryptor) {
        return new SingleTextEncryptorLocator(textEncryptor);
    }*/
}
