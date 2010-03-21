package org.codehaus.groovy.grails.plugins.qrcode
/**
 * Copyright (C) 2010 Shawn Hartsock
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class QrcodeController {
  QRCodeRenderer qrcodeRenderer = new QRCodeRenderer()

  /**
   * Renders a QRCode that contains the Referer URL that you just came from
   */
  def index = {
    qrcodeRenderer.renderPng(response,request.getHeader("REFERER"), 300i)
  }

  /**
   * Renders a QRCode with the URL specified
   */
  def url = {
    String uri = params.u
    String size = getSize(params)
    qrcodeRenderer.renderPng(response, uri, size.toInteger().intValue())
  }

  String getSize(Map params) {
    String size = params.s
    if (!size || size.matches(/\D/)) { size = "128"}
    return size
  }

  /**
   * Renders a QRCode containing arbitrary text. This can
   * include iCal or vCard data or whatever you can come up with.
   */
  def text = {
    String content = params.text
    String size = getSize(params)
    qrcodeRenderer.renderPng(response, content, size.toInteger().intValue())
  }


}
