/*
 * Copyright 2014 Alessandro Falappa <alex.falappa@gmail.com>.
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
package main.data;

/**
 * A catalogue definition.
 * <p>
 * Includes a name, the service endpoint URL and the SOAP version to use.
 *
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class CatalogueDefinition {

    private String name;
    private String endpoint;
    private boolean soapV12;
    private int timeoutMillis;
    private String[] collections;

    public CatalogueDefinition(String name, String endpoint, boolean soapV12, int timeout) {
        this.name = name;
        this.endpoint = endpoint;
        this.soapV12 = soapV12;
        this.collections = new String[0];
        this.timeoutMillis = timeout;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public boolean isSoapV12() {
        return soapV12;
    }

    public void setSoapV12(boolean soapV12) {
        this.soapV12 = soapV12;
    }

    public String[] getCollections() {
        return collections;
    }

    public void setCollections(String[] collections) {
        this.collections = collections;
    }

    public int getTimeoutMillis() {
        return timeoutMillis;
    }

    public void setTimeoutMillis(int timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    @Override
    public String toString() {
        return name;
    }

}
