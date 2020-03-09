package com.tool

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import org.grails.plugins.wkhtmltopdf.WkhtmltopdfGrailsPlugin
import org.grails.plugins.wkhtmltopdf.WkhtmltopdfRenderer
import org.grails.plugins.wkhtmltopdf.WkhtmltoxService

@Transactional
class PrescriptionService {

    def executorService
    //static transactional = false
    WkhtmltoxService wkhtmltoxService
    PatientService patientService

    def gen(long id) {
        def p = patientService.get(id)

            def byte[] pdfData = wkhtmltoxService.makePdf(
                    view: "/patient/preview",
                    model: [patient: p],
//                    header:"/patient/preview/1",
//                    footer:"/patient/preview/1",
                    marginLeft: 20,
                    marginTop: 35,
                    marginBottom: 20,
                    marginRight: 20,
                    headerSpacing: 10)

            FileOutputStream fos = new FileOutputStream("c:/git/foo.pdf");
            fos.write(pdfData);
            fos.close();




    }
}
