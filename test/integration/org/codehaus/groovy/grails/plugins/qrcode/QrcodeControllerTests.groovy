package org.codehaus.groovy.grails.plugins.qrcode

import grails.test.ControllerUnitTestCase
import org.apache.commons.io.IOUtils
import org.springframework.core.io.ClassPathResource

class QrcodeControllerTests extends ControllerUnitTestCase {
    QrCodeService qrCodeService
    QrcodeController controller = new QrcodeController()

    void testSimpleRender() {
        controller.qrCodeService = qrCodeService
        controller.params.text = 'test'
        controller.params.size = '30'
        controller.index()

        assert "image/png" == controller.response.contentType
        assert 200 == controller.response.status

        byte[] out = controller.response.contentAsByteArray
        // a 30 by 30 png image of the word "test" in a QRCode
        InputStream pngIn = new ClassPathResource("org/codehaus/groovy/grails/plugins/qrcode/qrcode.png").inputStream
        byte[] png = IOUtils.toByteArray(pngIn)

        assert png

        assert Arrays.equals(png,out)
    }
}
