/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package domainapp.fixture.dom.simple;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import com.cosylab.dom.report.Report;
import com.cosylab.dom.report.Reports;
import com.cosylab.dom.simple.CosyObjects;

public class ReportsCreate extends FixtureScript {

    //region > name (input)
    private String name;
    /**
     * Name of the object (required)
     */
    public String getName() {
        return name;
    }

    public ReportsCreate setName(final String name) {
        this.name = name;
        return this;
    }
    //endregion


    //region > simpleObject (output)
    private Report report;

    /**
     * The created simple object (output).
     * @return
     */
    public Report getSimpleObject() {
        return report;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        String name = checkParam("name", ec, String.class);
        
        

        this.report = wrap(reports).createReport(name);
        // also make available to UI
        ec.addResult(this, report);
    }

    @javax.inject.Inject
    private CosyObjects simpleObjects;

    @javax.inject.Inject
    private Reports reports;
    
}
