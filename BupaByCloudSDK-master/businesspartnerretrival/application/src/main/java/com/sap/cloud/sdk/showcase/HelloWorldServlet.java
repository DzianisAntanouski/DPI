package com.sap.cloud.sdk.showcase;

import org.slf4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;

import io.vavr.collection.List;

@WebServlet("/businesspartners")
public class HelloWorldServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(HelloWorldServlet.class);

    @Override
    protected void doGet( final HttpServletRequest request, final HttpServletResponse response )
        throws IOException
    {
        final PrintWriter writer = response.getWriter();
        try {          
            final java.util.List<BusinessPartner> businessPartnerList = new DefaultBusinessPartnerService().getAllBusinessPartner().select(
                    BusinessPartner.BUSINESS_PARTNER_FULL_NAME,
                    BusinessPartner.BUSINESS_PARTNER_IS_BLOCKED,
                    BusinessPartner.BUSINESS_PARTNER)
            .execute();
            writer.write("Total Number of Business Partners fetched from System = " + businessPartnerList.size());
            writer.println("");
            writer.println("=============================================");
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            String message = gson.toJson(businessPartnerList);
            writer.write(message);

            
            response.setContentType("application/json");
        } catch (Exception e) {
            writer.write("Error: " + e.getMessage());
            e.printStackTrace(writer);
        }
    }
}
