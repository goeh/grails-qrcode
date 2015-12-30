package grails.plugins.qrcode

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(QrcodeTagLib)
class QrcodeTagLibTests extends Specification {

    void "test url"() {
        expect:
        applyTemplate(template) == '<img src="/qrcode/url?u=http%3A%2F%2Flocalhost&s=100" class="qrcode" alt="http://localhost"/>'

        where:
        template = '<qrcode:url height="100" width="100"/>'
    }

    void "test url absolute"() {
        expect:
        applyTemplate(template) == '<img src="http://localhost:8080/qrcode/url?u=http%3A%2F%2Flocalhost&s=100" class="qrcode" alt="http://localhost"/>'

        where:
        template = '<qrcode:url height="100" width="100" absolute="true"/>'
    }

    void "test image"() {
        expect:
        applyTemplate(template) == '<img src="/qrcode/text?text=TEST+TEXT&s=100" class="qrcode" alt="TEST TEXT"/>'

        where:
        template = '<qrcode:image height="100" width="100" text="TEST TEXT"/>'
    }

    void "test image absolute"() {
        expect:
        applyTemplate(template) == '<img src="http://localhost:8080/qrcode/text?text=TEST+TEXT&s=100" class="qrcode" alt="TEST TEXT"/>'

        where:
        template = '<qrcode:image height="100" width="100" text="TEST TEXT" absolute="true"/>'
    }

    void "test image style"() {
        expect:
        applyTemplate(template) == '<img src="/qrcode/text?text=TEST+TEXT&s=100" class="qrcode" alt="TEST TEXT" style="border:1px solid black"/>'

        where:
        template = '<qrcode:image height="100" width="100" text="TEST TEXT" style="border:1px solid black"/>'
    }
}
