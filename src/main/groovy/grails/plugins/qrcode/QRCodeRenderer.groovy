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

package grails.plugins.qrcode

import ar.com.hjg.pngj.ImageLineHelper
import ar.com.hjg.pngj.ImageLineInt
import ar.com.hjg.pngj.chunks.PngChunkTextVar
import ar.com.hjg.pngj.chunks.PngMetadata


import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix

import ar.com.hjg.pngj.ImageInfo
import ar.com.hjg.pngj.PngWriter
import com.google.zxing.EncodeHintType

/**
 * @author "Shawn Hartsock" <hartsock@acm.org>
 * @author "Goran Ehrsson" <goran@technipelago.se>
 *
 * This class uses the native JDK and Groovy libraries to render the
 * byte matrix returned by the QRCodeWriter in the ZXing libraries
 * using the PngWriter libraries provided by pngj. These libraries
 * use only white listed Google Approved classes to create PNG images.
 *
 * The object is defined with sensible defaults that you may choose
 * to override in your constructor.
 *
 */
class QRCodeRenderer {
  QRCodeWriter qrcodeWriter = new QRCodeWriter()
  BarcodeFormat format = BarcodeFormat.QR_CODE

  /**
   * Determines the size of the data matrix we will need
   * by reading the size of the string and calculating what
   * the square root of that size is. This is the minimum
   * width in pixels we can create a QRCode in.
   *
   * NOTE: this does not mean the <b>image</b> is that size.
   * You will have to scale the image up or down to fit.
   */
  int calculateMatrixSize(String data) {
    def length = data.length()
    def sqr = Math.sqrt(length)
    int size = Math.round(sqr + 0.5)
    return size
  }

  /**
   * Renders the data supplied in a QRCode of the pixel size (width == height)
   * on the output stream you specify as a PNG.
   */
  void renderPng(String data, int requestedSize, OutputStream outputStream) {
    Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>(2);
    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

    int size = calculateMatrixSize(data)
    BitMatrix matrix = qrcodeWriter.encode(data, format, size, size, hints)
    renderMatrix(matrix, requestedSize, outputStream)
  }

  /**
   * Renders a String inside a QRCode in a RenderedImage object of the width and
   * height you specify. The result CANNOT be smaller than one pixel per byte.
   * That means if your data takes 30 bytes and you ask for a 16x16 image the
   * renderer will ignore your scale requests and simply report the smallest image
   * it possibly can.
   */
  void renderMatrix(BitMatrix matrix, int size, OutputStream outputStream) {
    assert size > 0
    int cols = matrix.width
    double scale = size / cols;

    // setup the image preamble
    def imageInfo = new ImageInfo(size, size, 16, true, false, false)
    PngWriter png = new PngWriter((OutputStream) outputStream, imageInfo)
    PngMetadata md = png.getMetadata()
    md.setText(PngChunkTextVar.KEY_Title, "QRCode")
    md.setText(PngChunkTextVar.KEY_Author, "Shawn Hartsock")
    md.setText(PngChunkTextVar.KEY_Software, "Grails QRCode Plugin")
    md.setText(PngChunkTextVar.KEY_Source, this.getClass().getCanonicalName())
    md.setText(PngChunkTextVar.KEY_Comment, "Uses ZXing (https://github.com/zxing/zxing) and pngj (https://github.com/leonbloy/pngj)")

    // loop over byte array to render but project
    // down from the image onto the smaller byte
    // matrix. Loop "y" on the outside and "x" on
    // the inside since the byte stream must "draw"
    // the image one line at a time.
    ImageLineInt line = new ImageLineInt(png.imgInfo)
    int channels = png.imgInfo.channels;
    assert channels == 4
    for (int ii = 0; ii < size; ii++) {
      int y = ii / scale // truncate
      for (int jj = 0; jj < size; jj++) {
        int x = jj / scale // truncate
        double color = 1.0
        if (x < cols && y < cols && matrix.get(x, y)) {
          color = 0.0
        }
        ImageLineHelper.setValD(line, jj * channels, color)
        ImageLineHelper.setValD(line, jj * channels + 1, color)
        ImageLineHelper.setValD(line, jj * channels + 2, color)
      }
      addAlpha(line)
      png.writeRow(line)
    }
    png.end()
  }

  private static void addAlpha(ImageLineInt line) {
    for (int i = 0; i < line.imgInfo.cols; i++) {
        ImageLineHelper.setValD(line, i * 4 + 3, 1.0);
    }
  }

  /**
   * A simple helper that reports the proper content type to use for a PNG.
   */
  String getPngContentType() { "image/png" }

  void renderPng(response, String data, int size) {
    response.contentType = getPngContentType()
    renderPng(data, size, response.outputStream)
  }
}