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

import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.ByteMatrix

import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.Graphics2D
import java.awt.image.renderable.ParameterBlock
import java.awt.Rectangle
import java.awt.image.AffineTransformOp
import java.awt.geom.AffineTransform

import javax.media.jai.JAI
import javax.media.jai.PlanarImage

/**
 * @author "Shawn Hartsock" <hartsock@acm.org>

 * This class uses the native JDK and Groovy libraries to render the
 * byte matrix returned by the QRCodeWriter in the ZXing libraries
 * as a java.awt.image.RenderedImage which you may then use with
 * the javax.media.jai.JAI libraries to render as another format.
 *
 * The object is defined with sensible defaults that you may choose
 * to override in your constructor.
 *
 * blockColor: color code in hex for the blocks default: black
 * backgroundColor: color code in hex for the background default: white
 *
 */
class QRCodeRenderer {
  QRCodeWriter qrcodeWriter = new QRCodeWriter()
  BarcodeFormat format = BarcodeFormat.QR_CODE

  String blockColor = "#000000"
  String backgroundColor = "#FFFFFF"

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


   /*
    * Draws a simple block (not useful outside this class
    */
  private void drawBlock(Graphics2D graphics, Color color, int x, int y, int w = 1, int h = 1) {
    def block = new Rectangle(w, h)
    block.setLocation(x, y)
    graphics.setColor(color)
    graphics.draw(block)
    graphics.fill(block)
  }

  /**
   * Creates a 1 pixel = 1 block QRCode image containing the data you specify.
   */
  java.awt.image.RenderedImage render(String data) {
    long size = calculateMatrixSize(data)
    render(data, size)
  }

  java.awt.image.RenderedImage render(String data, Long width) {
    render(data, width, width)
  }

  java.awt.image.RenderedImage render(String data, Long width, Long height) {
    render(data, width.intValue(), height.intValue())
  }

  /**
   * Renders a String inside a QRCode in a RenderedImage object of the width and
   * height you specify. The result CANNOT be smaller than one pixel per byte.
   * That means if your data takes 30 bytes and you ask for a 16x16 image the
   * renderer will ignore your scale requests and simply report the smallest image
   * it possibly can.
   *
   * Choose square width and height please.
   */
  java.awt.image.RenderedImage render(String data, int width, int height) {
    // first we render the matrix completely unscaled. One pixel per byte.
    int size = calculateMatrixSize(data)
    ByteMatrix matrix = qrcodeWriter.encode(data, format, size, size)

    // NEVER allow the user to request an image smaller than the minimum pixels
    // needed to render one bit per pixel from our byte matrix.
    double scaleX = width/matrix.width
    if(scaleX < 1.0d) { scaleX = 1.0d }
    double scaleY = height/matrix.height
    if(scaleY < 1.0d) { scaleY = 1.0d }

    def backgroundCode = "0x${backgroundColor.replace('#', '')}"
    Color backgroundColor = Color.decode(backgroundCode)

    def colorCode = "0x${blockColor.replace('#', '')}"
    Color blockColor = Color.decode(colorCode)

    // generate an image stream, clear it then set up for rendering the matrix.
    BufferedImage image = new BufferedImage(matrix.width, matrix.height, BufferedImage.TYPE_INT_RGB)
    Graphics2D graphics = image.graphics
    graphics.setBackground(backgroundColor)
    graphics.clearRect(0, 0, width, height)

    // render the matrix, where there's a 0 draw a dark block 
    for(int y = 0; y < matrix.height; y++) {
      for(int x = 0; x < matrix.width; x++) {
        if(matrix.get(x,y) == 0) {
          drawBlock(graphics, blockColor, x, y)
        }
        else {
          drawBlock(graphics, backgroundColor, x, y)
        }
      }
    }
    // the now rendered matrix is one pixel per block. This is usually far too small
    // to be useful. So we need to scale up the image and then write out
    AffineTransform transform = new AffineTransform();
    transform.scale(scaleX,scaleY)
    graphics.transform(transform)

    AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    image = op.filter(image, null);

    ParameterBlock pb = new ParameterBlock()
    pb.add(image)
    PlanarImage renderedImage = (PlanarImage) JAI.create("awtImage", pb)
    
    return renderedImage
  }

  /**
   * Renders the data supplied in a QRCode of the pixel size (width == height)
   * on the output stream you specify as a PNG.
   */
  void renderPng(String data, int size, OutputStream outputStream) {
    def image = render(data,size,size)
    JAI.create("encode", image, outputStream, "PNG", null)
  }

  /**
   * A simple helper that reports the proper content type to use for a PNG.
   */
  String getPngContentType() { "image/png" }

  void renderPng(response,String data,int size) {
    response.contentType = "image/png"
    renderPng(data, size, response.outputStream)
  }
}