package org.codehaus.groovy.grails.plugins.qrcode

import grails.test.GrailsUnitTestCase
import org.codehaus.groovy.grails.plugins.testing.GrailsMockHttpServletResponse

class QrcodeTagLibTests extends GrailsUnitTestCase {

    StringWriter out
    def qrCodeService
    def controller
    def action
    def attributes

    protected void setUp() {
        super.setUp()
        out = new StringWriter()
        QrcodeTagLib.metaClass.out = out
        QrcodeTagLib.metaClass.createLink = { attrs ->
            controller = attrs.controller
            action = attrs.action
            attributes = attrs.params
        }
        QrcodeController.metaClass.params = [:]
        QrcodeController.metaClass.response = new GrailsMockHttpServletResponse()
    }

    protected void tearDown() {
        super.tearDown()
        def remove = GroovySystem.metaClassRegistry.&removeMetaClass
        remove QrcodeTagLib
        remove QrcodeController
    }

    void testImage() {
        QrcodeTagLib qr = new QrcodeTagLib()

        qr.image(height: "100", width: "100", text: "TEST TEXT")

        assertEquals "qrcode", controller
        assertEquals "text", action
        assertEquals "100", attributes.s
        assertEquals "TEST TEXT", attributes.text

        def ctl = new QrcodeController(params: attributes)
        ctl.qrCodeService = qrCodeService
        ctl.text()
        assertEquals "image/png", ctl.response.contentType
        assertEquals 200, ctl.response.status

        // reset "out" buffer
        out.getBuffer().setLength(0)
    }
}
