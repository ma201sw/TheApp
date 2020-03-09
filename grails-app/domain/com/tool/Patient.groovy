package com.tool

class Patient {
    String pharmacistNameFillingRequest
    String pharmacyClinic

    static constraints = {
        pharmacistNameFillingRequest nullable: false, blank: false
        pharmacyClinic nullable: false, blank: false
    }

    String toString() {
        pharmacistNameFillingRequest
    }

}
