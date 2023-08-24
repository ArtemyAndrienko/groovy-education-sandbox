package im.educ.groovy

import static im.educ.groovy.Utils.transformToHTML
import static im.educ.groovy.Utils.transformToXML


static void main(String[] args) {

    def resources = "https://raw.githubusercontent.com/CycloneDX/bom-examples/master/SBOM/cern-lhc-vdm-editor-e564943/bom.json"
    def xmlFileName = "C:\\temp\\hw4-output.xml"
    def htmlFileName = "C:\\temp\\hw4-output.html"

    transformToXML(resources, xmlFileName)
    transformToHTML(resources,htmlFileName)
}

