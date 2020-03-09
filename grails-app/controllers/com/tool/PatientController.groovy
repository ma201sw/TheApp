package com.tool

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import org.grails.plugins.wkhtmltopdf.WkhtmltopdfGrailsPlugin
import org.grails.plugins.wkhtmltopdf.WkhtmltoxService

import static org.springframework.http.HttpStatus.*

class PatientController {

    PatientService patientService

    PrescriptionService prescriptionService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", generate: "POST"]

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond patientService.list(params), model:[patientCount: patientService.count()]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def show(Long id) {
        respond patientService.get(id)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def create() {
        respond new Patient(params)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def save(Patient patient) {
        if (patient == null) {
            notFound()
            return
        }

        try {
            patientService.save(patient)
        } catch (ValidationException e) {
            respond patient.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'patient.label', default: 'Patient'), patient.id])
                redirect patient
            }
            '*' { respond patient, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def edit(Long id) {
        respond patientService.get(id)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def preview(Long id) {
        respond patientService.get(id)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def generate(Long id) {

        response.setHeader("Content-disposition", "attachment; filename=test.pdf")

        //prescriptionService.gen(id)
        def p = Patient.get(id)
        response.setContentType("application/pdf")
//        println(response.format)
//        File f = new File("c:/git/my.pdf")
//        f.createNewFile()
//        render(file: new File("c:/git/my.pdf"),
//                view:"/patient/preview",
//                model:[patient:p],
//                fileName: "my.pdf",
//                )

                render( filename:"File C:/git/test.pdf",
                view:"/patient/preview",
                model:[patient:p],
                marginLeft:20,
                marginTop:35,
                marginBottom:20,
                marginRight:20,
                headerSpacing:10)

        //redirect action: 'index', controller: 'patient'
        //respond patientService.get(id)

    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def update(Patient patient) {
        if (patient == null) {
            notFound()
            return
        }

        try {
            patientService.save(patient)
        } catch (ValidationException e) {
            respond patient.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'patient.label', default: 'Patient'), patient.id])
                redirect patient
            }
            '*'{ respond patient, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        patientService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'patient.label', default: 'Patient'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'patient.label', default: 'Patient'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
