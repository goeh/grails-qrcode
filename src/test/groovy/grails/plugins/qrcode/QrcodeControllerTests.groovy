package grails.plugins.qrcode

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@Mock(QrCodeService)
@TestFor(QrcodeController)
class QrcodeControllerTests extends Specification {

    void "test simple render"() {
        given:
        controller.params.text = 'test'
        controller.params.size = '30'

        when:
        controller.index()
        byte[] out = controller.response.contentAsByteArray

        then:
        'image/png' == controller.response.contentType
        200 == controller.response.status
        png == out

        where:
        png = this.class.getResource('/qrcode.png').bytes
        // a 30 by 30 png image of the word "test" in a QRCode
    }
}
