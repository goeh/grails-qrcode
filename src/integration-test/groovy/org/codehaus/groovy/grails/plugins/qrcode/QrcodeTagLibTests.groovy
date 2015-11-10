package org.codehaus.groovy.grails.plugins.qrcode

import grails.test.GroovyPagesTestCase

class QrcodeTagLibTests extends GroovyPagesTestCase {

    void testUrl() {
        def template = '<qrcode:url height="100" width="100"/>'
        assert applyTemplate(template) == '<img src="/qrcode/url?u=http%3A%2F%2Flocalhost&s=100" class="qrcode" alt="http://localhost"/>'
    }

    void testUrlAbsolute() {
        def template = '<qrcode:url height="100" width="100" absolute="true"/>'
        assert applyTemplate(template) == '<img src="http://localhost/qrcode/url?u=http%3A%2F%2Flocalhost&s=100" class="qrcode" alt="http://localhost"/>'
    }

    void testImage() {
        def template = '<qrcode:image height="100" width="100" text="TEST TEXT"/>'
        assert applyTemplate(template) == '<img src="/qrcode/text?text=TEST+TEXT&s=100" class="qrcode" alt="TEST TEXT"/>'
    }

    void testImageAbsolute() {
        def template = '<qrcode:image height="100" width="100" text="TEST TEXT" absolute="true"/>'
        assert applyTemplate(template) == '<img src="http://localhost/qrcode/text?text=TEST+TEXT&s=100" class="qrcode" alt="TEST TEXT"/>'
    }

    void testImageStyle() {
        def template = '<qrcode:image height="100" width="100" text="TEST TEXT" style="border:1px solid black"/>'
        assert applyTemplate(template) == '<img src="/qrcode/text?text=TEST+TEXT&s=100" class="qrcode" alt="TEST TEXT" style="border:1px solid black"/>'
    }
}
