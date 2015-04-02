package org.codehaus.groovy.grails.plugins.qrcode

import org.apache.commons.io.IOUtils
import org.springframework.core.io.ClassPathResource

class QrCodeServiceTests extends GroovyTestCase {

    QrCodeService qrCodeService

    void testSimpleRender() {

        assert qrCodeService

        // a 30 by 30 png image of the word "test" in a QRCode
        InputStream pngIn = new ClassPathResource("org/codehaus/groovy/grails/plugins/qrcode/qrcode.png").inputStream
        assert pngIn

        byte[] png = IOUtils.toByteArray(pngIn)

        OutputStream outputStream = new ByteArrayOutputStream()
        qrCodeService.renderPng("test",30,outputStream)

        byte[] out = outputStream.toByteArray()
        assert Arrays.equals(png,out)
    }
}
