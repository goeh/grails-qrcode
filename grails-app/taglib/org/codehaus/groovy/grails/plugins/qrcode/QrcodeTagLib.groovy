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
class QrcodeTagLib {
  static namespace = "qrcode"

  /**
   * Example:
   * <qrcode:url width="96"/>
   */
  def url = { attrs ->
    def size = attrs.height?:attrs.width
    String target = request.getRequestURL()
    String src = createLink(controller:'qrcode',action:'url',params:[u:target,s:size])
    out << """<img class="qrcode" alt="${url}" src="${src}"/>"""
  }

  /**
   * Example:
   *  <qrcode:image text="Some arbitrary text."/>
   */
  def image = { attrs ->
    def size = attrs.height?:attrs.width
    String text = attrs.text
    String src = createLink(controller:'qrcode',action:'text',params:[text:text,s:size])
    out << """<img class="qrcode ${attrs['class'] ? attrs['class'] : ''}" alt="${attrs.alt ?: text}" src="${src}"/>"""
  }
    /**
     * Example:
     *  <qrcode:link label="my text" />
     */
    def link = { attrs ->
        def label = attrs.label?:"qrlink"
        def size = attrs.height?:attrs.width
        String target = request.getRequestURL()
        String src = createLink(controller:'qrcode',action:'url',params:[u:target,s:size])
        out << '''<style>
div.qrcodebox span.qrcodespan {
	display: none;
	position: absolute;
}

div.qrcodebox a.qrcodeLink:hover span.qrcodespan {
	display: block;
}
</style>
'''
        out << """
    <div class="qrcodebox">
        <a class="qrcodeLink" href="javascript:null">${label}
            <span class="qrcodespan">
                <img class="qrcode" alt="${target}" src="${src}"/>
            </span>
        </a>
    </div>
"""
    }

}
