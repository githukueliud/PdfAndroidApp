package com.example.pdfandroidapp

import android.content.Context
import com.itextpdf.kernel.colors.Color
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
import com.itextpdf.layout.property.VerticalAlignment
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
        val dateText = createLightTextParagraph(invoice.date)


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

        //seller/buyer information
        val from = createLightTextParagraph("From")
        val to = createLightTextParagraph("To").setTextAlignment(TextAlignment.RIGHT)

        val fromName = createBoldTextParagraph(invoice.from.name).setTextAlignment(TextAlignment.LEFT)
        val toName = createBoldTextParagraph(invoice.to.name).setTextAlignment(TextAlignment.RIGHT)

        val fromAddress = createLightTextParagraph(invoice.from.address).setTextAlignment(TextAlignment.LEFT)
        val toAddress = createLightTextParagraph(invoice.to.address).setTextAlignment(TextAlignment.RIGHT)

        val peopleTable = Table(2)
        peopleTable.addCell(from)
        peopleTable.addCell(to)
        peopleTable.addCell(fromName)
        peopleTable.addCell(toName)
        peopleTable.addCell(fromAddress)
        peopleTable.addCell(toAddress)

        document.add(peopleTable)


        document.close()
    }

    private fun createLightTextParagraph(text: String): Paragraph {
        val lightTextStyle = Style().apply {
            setFontSize(12f)
            setFontColor(DeviceRgb(166, 166, 166))
        }
        return Paragraph(text).addStyle(lightTextStyle)
    }

    private fun createBoldTextParagraph(text: String, color: Color = DeviceRgb.BLACK): Paragraph {
        val boldTextStyle = Style().apply {
            setFontSize(16f)
            setFontColor(color)
            setVerticalAlignment(VerticalAlignment.MIDDLE)
        }
        return Paragraph(text).addStyle(boldTextStyle)
    }

    private fun createNoBorderCell(paragraph: Paragraph): Cell {
        return Cell().add(paragraph).setBorder(null)
    }
}