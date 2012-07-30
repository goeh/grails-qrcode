package org.codehaus.groovy.grails.plugins.qrcode

import grails.test.GroovyPagesTestCase

class QrcodeTagLibTests extends GroovyPagesTestCase {

    void testImage() {

        def template = '<qrcode:image height="100" width="100" text="TEST TEXT"/>'
        assert applyTemplate(template) == '<img class="qrcode " alt="TEST TEXT" src="/qrcode/text?text=TEST+TEXT&s=100"/>'
    }
}
