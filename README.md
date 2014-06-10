This plugin allows you to create QR codes as part of your Grails
application without the need for an external service.

## QrcodeController

Render text QRCode at default size (300x300px)

    .../qrcode/text/Hello+World

Render text QRCode in 30x30px

    .../qrcode/text?w=30&text=test

![QRCode](test/integration/org/codehaus/groovy/grails/plugins/qrcode/qrcode.png)

    Render url QRCode

    .../qrcode/url?u=http://grails.org/plugin/qrcode

## QrCodeService

    qrCodeService.renderPng("test", 30, outputStream)

## Tag Library

Namespace: *qrcode*

Render text as QRCode

    <qrcode:image height="100" width="100" text="TEST TEXT"/>

Render current request url as QRCode

    <qrcode:url width="64"/>