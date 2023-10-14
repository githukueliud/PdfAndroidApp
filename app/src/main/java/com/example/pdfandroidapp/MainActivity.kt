package com.example.pdfandroidapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.pdfandroidapp.ui.theme.PdfAndroidAppTheme
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PdfAndroidAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(onClick = { createPdf(this@MainActivity) }) {
                            Text(text = "Save")
                        }
                    }
                }
            }
        }
    }
}



private fun createPdf(context: Context) {
    val outputFile = File(context.filesDir, "test.pdf")
    val pdfWriter = PdfWriter(outputFile)
    val pdfDocument = PdfDocument(pdfWriter)
    val document = Document(pdfDocument, PageSize.A3)
    pdfDocument.addNewPage()

    document.add(
        Paragraph("Hello world")
    )

    document.close()

}

