package uy.gub.dgr.sur.service;

import net.sf.jasperreports.engine.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import uy.gub.dgr.sur.controller.LocaleController;
import uy.gub.dgr.sur.controller.PreventivoReportController;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: rafa
 * Date: 06/04/14
 * Time: 11:22 PM
 */
@WebServlet("/app/report")
public class ReportService extends HttpServlet {

    private static final String DD_MM_YYYY = "dd/MM/yyyy";
    private static String reportDefinition = "uy/gub/dgr/sur/report/Preventivos.jasper";
    @Inject
    transient Logger log;
    @Inject
    LocaleController localeController;
    @Inject
    private DataSource dataSource;
    @Inject
    private PreventivoReportController preventivoReportController;

    private Map<String, Object> getParameters(HttpServletRequest request) {
        Map<String, Object> parameters = new HashMap<>();
        //parameters.put(JRJpaQueryExecuterFactory.PARAMETER_JPA_ENTITY_MANAGER, em);
        parameters.put(JRParameter.REPORT_LOCALE, localeController.getLocale());
        parameters.put("ReportTitle", "Reporte Mantenimientos Preventivo");

        Date dateFrom = null;
        Date dateTo = null;

        if (preventivoReportController != null) {
            dateFrom = preventivoReportController.getFechaDesde();
            dateTo = preventivoReportController.getFechaHasta();
        }
        if (dateFrom == null) {
            String dateFromParameter = request.getParameter("dateFrom");
            if (!StringUtils.isEmpty(dateFromParameter)) {
                try {
                    dateFrom = DateUtils.parseDate(dateFromParameter, DD_MM_YYYY);
                } catch (ParseException e) {
                    dateFrom = DateUtils.addMonths(new Date(), -1);
                }
            } else {
                dateFrom = DateUtils.addMonths(new Date(), -1);
            }
        }

        if (dateTo == null) {
            String dateToParameter = request.getParameter("dateTo");
            if (!StringUtils.isEmpty(dateToParameter)) {
                try {
                    dateTo = DateUtils.parseDate(dateToParameter, DD_MM_YYYY);
                } catch (ParseException e) {
                    dateTo = new Date();
                }
            } else {
                dateTo = new Date();
            }
        }

        parameters.put("dateFrom", new java.sql.Date(dateFrom.getTime()));
        parameters.put("dateTo", new java.sql.Date(dateTo.getTime()));

        return parameters;
    }

    public void service(HttpServletRequest request,
                        HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = this.getServletConfig().getServletContext();

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; filename=reporte_preventivos.pdf");
        //response.setHeader("Content-disposition", "attachment; filename=reporte_preventivos.pdf");

        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream(reportDefinition);
            if (stream == null) {
                throw new JRRuntimeException("Reporte " + reportDefinition + " no encontrado. El reporte debe estar compilado primero.");
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            Map<String, Object> parameters = getParameters(request);

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            stream,
                            parameters,
                            dataSource.getConnection()
                    );

            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
/*
            HtmlExporter exporter = new HtmlExporter();
            request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(out);
            output.setImageHandler(new WebHtmlResourceHandler("image?image={0}"));
            exporter.setExporterOutput(output);

            exporter.exportReport();
            */

        } catch (JRException e) {
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>SUR - Sistema Único Registral</title>");
            out.println("</head>");

            out.println("<body>");

            out.println("<span>Error al generar reporte:</span>");
            out.println("<pre>");

            e.printStackTrace(out);

            out.println("</pre>");

            out.println("</body>");
            out.println("</html>");
            log.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}
