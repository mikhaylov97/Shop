package com.tsystems.shop.util;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Custom class which allows us to use pdfView within our application
 * with using modern library for working with pdf - iText
 */
public abstract class PdfAbstractView extends AbstractView {

    /**
     * Content of the file
     */
    public PdfAbstractView() {
        setContentType("application/pdf");
    }

    /**
     * @return the state which allow to generate download content
     */
    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    /**
     *
     * @param model where out data is storing
     * @param request handled by out controller
     * @param response which we should to return
     * @throws Exception
     */
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        // IE workaround: write into byte array first.
        ByteArrayOutputStream baos = createTemporaryOutputStream();

        // Apply preferences and build metadata.
        Document document = newDocument();
        PdfWriter writer = newWriter(document, baos);
        prepareWriter(model, writer, request);
        buildPdfMetadata(model, document, request);

        // Build PDF document.
        document.open();
        buildPdfDocument(model, document, writer, request, response);
        document.close();

        // Flush to HTTP response.
        writeToResponse(response, baos);
    }

    /**
     * Method creates new pdf document
     * @return this document that iText library provide us
     */
    protected Document newDocument() {
        return new Document(PageSize.A4);
    }

    /**
     * Allow us to create some pdfWriter
     * @param document must be created by the method above
     * @param os some output stream
     * @return created pdf writer
     * @throws DocumentException in cases when we have some troubles with document
     */
    protected PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
        return PdfWriter.getInstance(document, os);
    }

    /**
     * Prepare out writer
     * @param model where our data is storing
     * @param writer which we have created
     * @param request handled by our controller
     * @throws DocumentException in cases when we have some troubles during the method processing
     */
    protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
            throws DocumentException {

        writer.setViewerPreferences(getViewerPreferences());
    }

    /**
     * Custom preferences for our pdf writer
     * @return this preferences
     */
    protected int getViewerPreferences() {
        return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
    }

    /**
     * Method build some necessary metadata
     * @param model where out data is storing
     * @param document
     * @param request
     */
    protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
    }

    /**
     * Method the developer will have to implement for using iText library.
     * There should be some instructions what the following document will be represented by itself
     * @param model
     * @param document
     * @param writer
     * @param request
     * @param response
     * @throws Exception
     */
    protected abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception;
}
