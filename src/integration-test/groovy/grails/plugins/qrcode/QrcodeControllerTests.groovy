package grails.plugins.qrcode

import org.springframework.core.io.ClassPathResource
import org.apache.commons.io.IOUtils

class QrcodeControllerTests extends ControllerUnitTestCase {
    QrCodeService qrCodeService
    QrcodeController controller = new QrcodeController()

    void testSimpleRender() {
        controller.qrCodeService = qrCodeService
        controller.params.text = 'test'
        controller.params.size = '30'
        controller.index()

        assertEquals "image/png", controller.response.contentType
        assertEquals 200, controller.response.status

        byte[] out = controller.response.contentAsByteArray
        // a 30 by 30 png image of the word "test" in a QRCode
        InputStream pngIn =
            new ClassPathResource("org/codehaus/groovy/grails/plugins/qrcode/qrcode.png").getInputStream()
        byte[] png = IOUtils.toByteArray(pngIn)

        assert png != null

        assert Arrays.equals(png,out)
    }
}
