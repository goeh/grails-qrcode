package org.codehaus.groovy.grails.plugins.qrcode

import grails.test.GrailsUnitTestCase
import org.springframework.core.io.ClassPathResource
import org.apache.commons.io.IOUtils

class QrCodeServiceTests extends GrailsUnitTestCase {
    QrCodeService qrCodeRendererService
    
    void testSimpleRender() {
        // a 30 by 30 png image of the word "test" in a QRCode
        InputStream pngIn =
            new ClassPathResource("org/codehaus/groovy/grails/plugins/qrcode/qrcode_test.png").getInputStream()
        byte[] png = IOUtils.toByteArray(pngIn)

        assert pngIn != null
        assert qrCodeRendererService != null

        OutputStream outputStream = new ByteArrayOutputStream()
        qrCodeRendererService.renderPng("test",30,outputStream)

        byte[] out = outputStream.toByteArray()
        assert Arrays.equals(png,out)
    }
}
