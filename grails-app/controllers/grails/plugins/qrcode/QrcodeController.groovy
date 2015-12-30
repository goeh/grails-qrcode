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

  public static final String BACKUP_TEXT = "This qrcode was rendered by http://grails.org/plugin/qrcode"

  QrCodeService qrCodeService

  /**
   * Renders a QRCode that contains the Referer URL that you just came from.
   */
  def index(String url, String text) {
    renderPng(url ?: text ?: request.getHeader("REFERER") ?: BACKUP_TEXT)
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
    int wantedSize = size as int
    int maxSize = grailsApplication.config.qrcode.size.max ?: 1024
    // make sure the specified size is within reasonable limits, to avoid DoS attacks.
    if (wantedSize > maxSize) {
      throw new RuntimeException("invalid size: " + wantedSize)
    }
    return wantedSize
  }

  protected void renderPng(String data) {
    qrCodeService.renderPng(response, data, getSize(params))
  }
}
