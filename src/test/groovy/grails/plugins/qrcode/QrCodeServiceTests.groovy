package grails.plugins.qrcode

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(QrCodeService)
class QrCodeServiceTests extends Specification {

    void "test simple render"() {
        when:
        OutputStream outputStream = new ByteArrayOutputStream()
        service.renderPng('test', 30, outputStream)
        byte[] out = outputStream.toByteArray()

        then:
        png == out

        where:
        png = this.class.getResource('/qrcode.png').bytes
        // a 30 by 30 png image of the word "test" in a QRCode
    }

}
