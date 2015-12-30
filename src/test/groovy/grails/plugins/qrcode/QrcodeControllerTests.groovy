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

    void "test exceed default max image size"() {
        given: "default max size is 1024"
        controller.params.text = 'test'
        controller.params.size = '2000'

        when:
        controller.index()
        byte[] out = controller.response.contentAsByteArray

        then:
        RuntimeException ex = thrown()
        ex.message == "invalid size: 2000"
    }

    void "test custom max image size"() {
        given: "custom max size is 2048"
        grailsApplication.config.qrcode.size.max = 2048
        controller.params.text = 'test'
        controller.params.size = '2000'

        when:
        controller.index()
        byte[] out = controller.response.contentAsByteArray

        then:
        'image/png' == controller.response.contentType
        200 == controller.response.status
    }
}
