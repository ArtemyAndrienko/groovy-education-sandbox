package im.educ.groovy.model

import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString
class Hardware {
    String screen
    String camera
    String keypad
    String cardReader
    String printer
    String validator
    String computeModule
    String ups
    String nfcReader


    Hardware(){
        this.camera = 'cameraModel'
        this.cardReader='card_reader_model'
        this.screen = 'screen_model_resolution800'
        this.keypad = 'keypad_model_simple'
        this.printer = 'printer_model'
        this.validator = 'validator_model'
        this.computeModule = 'x86_linux'
        this.ups = 'ups_model_600w'
        this.nfcReader = 'nfc_model'
    }
}
