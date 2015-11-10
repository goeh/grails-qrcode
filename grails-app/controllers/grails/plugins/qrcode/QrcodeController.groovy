package grails.plugins.qrcode
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

  static final String backupText = "This qrcode was rendered by http://grails.org/plugin/qrcode"

  QrCodeService qrCodeService

  /**
   * Renders a QRCode that contains the Referer URL that you just came from.
   */
  def index(String url, String text) {
    renderPng(url ?: text ?: request.getHeader("REFERER") ?: backupText)
  }

  /**
   * Renders a QRCode with the URL specified.
   */
  def url(String u, String id) {
    renderPng(u ?: id ?: request.getHeader("REFERER"))
  }

  /**
   * Renders a QRCode containing arbitrary text. This can
   * include iCal or vCard data or whatever you can come up with.
   */
  def text(String text, String id) {
    renderPng(text ?: id)
  }

  private int getSize(Map params) {
    String size = params.s ?: params.size ?: params.w ?: params.width
    if (!size || size.matches(/\D/)) { size = "300"}
    return size as int
  }

  protected void renderPng(String data) {
    qrCodeService.renderPng(response, data, getSize(params))
  }
}
