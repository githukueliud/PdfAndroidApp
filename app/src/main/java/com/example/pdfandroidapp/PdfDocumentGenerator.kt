package com.example.pdfandroidapp

import android.content.Context
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.action.PdfAction
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine
import com.itextpdf.layout.Document
import com.itextpdf.layout.Style
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.LineSeparator
import com.itextpdf.layout.element.Link
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.Leading
import com.itextpdf.layout.property.Property
import com.itextpdf.layout.property.TextAlignment
import java.io.File

class PdfDocumentGenerator(
    private val context: Context
) {


    fun generateInvoicePdf(invoice: Invoice) {
        val outputFile = File(context.filesDir, "invoice.pdf")
        val pdfWriter = PdfWriter(outputFile)
        val pdfDocument = PdfDocument(pdfWriter)
        val document = Document(pdfDocument, PageSize(650f, 700f)).apply {
            setMargins(50f, 13f, 13f, 13f)
            setProperty(
                Property.LEADING,
                Leading(Leading.MULTIPLIED, 1f)
            )
        }.setWordSpacing(0f)


        val page = pdfDocument.addNewPage()

        //invoice number
        val invoiceText = Paragraph("Invoice #${invoice.number}")
            .setBold()
            .setFontSize(32f)

        //Date
        val dateText = createTextParagraph(invoice.date)


        //pay button
        val payLink = Link("Pay ${invoice.price}", PdfAction.createURI(invoice.link))
            .setTextAlignment(TextAlignment.RIGHT)
            .setFontColor(DeviceRgb.WHITE)
        val payParagraph = Paragraph()
            .add(payLink)
            .setBackgroundColor(DeviceRgb(0, 92, 230))
            .setPadding(12f)
            .setWidth(100f)
            .setHorizontalAlignment(HorizontalAlignment.RIGHT)
            .setTextAlignment(TextAlignment.CENTER)

        //Top Section Table
        val topSectionTable = Table(2)
            .setMarginLeft(15f)
            .setMarginRight(15f)
            .setWidth(page.pageSize.width - 30 - 26f) //this will subtract the already set widths
        topSectionTable.addCell(createNoBorderCell(invoiceText))
        topSectionTable.addCell(createNoBorderCell(payParagraph))
        topSectionTable.addCell(createNoBorderCell(dateText))

        //Top Section Separator
        val line = LineSeparator(
            SolidLine().apply {
                color = DeviceRgb(204, 204, 204)
            }
        ).setMarginTop(20f)

        document.add(topSectionTable)
        document.add(line)
    }

    private fun createTextParagraph(text: String): Paragraph {
        val lightTextStyle = Style().apply {
            setFontSize(12f)
            setFontColor(DeviceRgb(166, 166, 166))
        }
        return Paragraph(text).addStyle(lightTextStyle)
    }

    private fun createNoBorderCell(paragraph: Paragraph): Cell {
        return Cell().add(paragraph).setBorder(null)
    }
}