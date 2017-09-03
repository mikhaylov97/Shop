package com.tsystems.shop.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tsystems.shop.model.dto.ProductDto;
import com.tsystems.shop.model.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PdfBuilderUtil extends PdfAbstractView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
                                    PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        List<ProductDto> productDtoList = (List<ProductDto>) model.get("listProducts");
        List<UserDto> userDtoList = (List<UserDto>) model.get("listUsers");
        long incomePerWeek = (long) model.get("incomePerWeek");
        long incomePerMonth = (long) model.get("incomePerMonth");

        // define font for table header row
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(BaseColor.BLACK);
        font.setSize(16);

        doc.addTitle("Shop Statistics");
        doc.addHeader("Header", "Shop Statistics");
        doc.addAuthor("Black Lion (c)");
        doc.addCreationDate();
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
                new Phrase("Shop Statistics", font),
                (doc.right() - doc.left()) / 2 + doc.leftMargin(),
                doc.top() + 10, 0);

        doc.add(new Paragraph("Income for the last week: $" + incomePerWeek + "."));
        doc.add(new Paragraph("Income for the last month: $" + incomePerMonth + "."));

        doc.add(new Paragraph("Top 10 User for the last time:"));

        PdfPTable tableTopUsers = new PdfPTable(5);
        tableTopUsers.setWidthPercentage(100.0f);
        tableTopUsers.setWidths(new float[] {3.0f, 2.0f, 2.0f, 2.0f, 1.0f});
        tableTopUsers.setSpacingBefore(10);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);

        // write table header
        cell.setPhrase(new Phrase("Email", font));
        tableTopUsers.addCell(cell);

        cell.setPhrase(new Phrase("Name", font));
        tableTopUsers.addCell(cell);

        cell.setPhrase(new Phrase("Surname", font));
        tableTopUsers.addCell(cell);

        cell.setPhrase(new Phrase("Phone", font));
        tableTopUsers.addCell(cell);

        cell.setPhrase(new Phrase("Total Cash", font));
        tableTopUsers.addCell(cell);

        // write table row data
        for (UserDto user : userDtoList) {
            tableTopUsers.addCell(user.getEmail());
            tableTopUsers.addCell(user.getName());
            tableTopUsers.addCell(user.getSurname());
            tableTopUsers.addCell(user.getPhone().equals("") ? "Not specified" : user.getPhone());
            tableTopUsers.addCell("$" + user.getTotalCash());
        }
        doc.add(tableTopUsers);

        doc.add(new Paragraph("Top 10 Products for the last time:"));

        PdfPTable tableTopProducts = new PdfPTable(4);
        tableTopProducts.setWidthPercentage(100.0f);
        tableTopProducts.setWidths(new float[] {2.5f, 2.5f, 2.5f, 2.5f});
        tableTopProducts.setSpacingBefore(10);

        // define table header cell
        PdfPCell topProductsCell = new PdfPCell();
        topProductsCell.setPadding(5);

        // write table header
        topProductsCell.setPhrase(new Phrase("Name", font));
        tableTopProducts.addCell(topProductsCell);

        topProductsCell.setPhrase(new Phrase("Image", font));
        tableTopProducts.addCell(topProductsCell);

        topProductsCell.setPhrase(new Phrase("Price", font));
        tableTopProducts.addCell(topProductsCell);

        topProductsCell.setPhrase(new Phrase("Number of Sales", font));
        tableTopProducts.addCell(topProductsCell);

        Map<Long, Byte[]> images = (HashMap<Long, Byte[]>) model.get("imagesMap");
        // write table row data
        for (ProductDto product : productDtoList) {
            tableTopProducts.addCell(product.getName());
            tableTopProducts.addCell(Image.getInstance(ByteArrayConverterUtil.convertBytes(images.get(product.getId()))));
            tableTopProducts.addCell(product.getPrice());
            tableTopProducts.addCell(String.valueOf(product.getNumberOfSales()));
        }
        doc.add(tableTopProducts);
    }
}
