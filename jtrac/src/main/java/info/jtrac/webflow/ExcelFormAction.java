/*
 * Copyright 2002-2005 the original author or authors.
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

package info.jtrac.webflow;

import info.jtrac.domain.ExcelFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.webflow.Event;
import org.springframework.webflow.RequestContext;
import org.springframework.webflow.ScopeType;
import org.springframework.webflow.context.servlet.ServletExternalContext;

/**
 * Multiaction that participates in the Excel Import flow 
 */
public class ExcelFormAction extends AbstractFormAction {
    
    public ExcelFormAction() {
        setFormObjectClass(ExcelFile.class);
        setFormObjectName("excelFile");
        setFormObjectScope(ScopeType.FLOW);        
    }
    
    public Event uploadHandler(RequestContext context) throws Exception {
        ServletExternalContext servletContext = (ServletExternalContext) context.getLastEvent().getSource();
        MultipartHttpServletRequest request = (MultipartHttpServletRequest) servletContext.getRequest();
        MultipartFile multipartFile = request.getFile("file");
        if(multipartFile.isEmpty()) {
            return error();
        }
        ExcelFile excelFile = new ExcelFile(multipartFile.getInputStream());
        context.getFlowScope().put("excelFile", excelFile);
        return success();
    }        
    
}
