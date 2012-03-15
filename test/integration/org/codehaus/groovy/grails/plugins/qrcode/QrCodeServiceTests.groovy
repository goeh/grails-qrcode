package org.codehaus.groovy.grails.plugins.qrcode

import grails.test.GrailsUnitTestCase
import org.springframework.core.io.ClassPathResource
import org.apache.commons.io.IOUtils

class QrCodeServiceTests extends GrailsUnitTestCase {
    QrCodeService qrCodeService
    
    void testSimpleRender() {
        // a 30 by 30 png image of the word "test" in a QRCode
        InputStream pngIn =
            new ClassPathResource("org/codehaus/groovy/grails/plugins/qrcode/qrcode.png").getInputStream()
        byte[] png = IOUtils.toByteArray(pngIn)

        assert pngIn != null
        assert qrCodeService != null

        OutputStream outputStream = new ByteArrayOutputStream()
        qrCodeService.renderPng("test",30,outputStream)

        byte[] out = outputStream.toByteArray()
        assert Arrays.equals(png,out)
    }
    
    void testGenerate() {
        // a 30 by 30 png image of the word "test" in a QRCode
        File file =
            new File("/Users/hartsock/Desktop/qrcode.png")
        OutputStream outputStream = file.newOutputStream()
        qrCodeService.renderPng("test",30,outputStream)
	outputStream.flush()
	outputStream.close()
    }
}
