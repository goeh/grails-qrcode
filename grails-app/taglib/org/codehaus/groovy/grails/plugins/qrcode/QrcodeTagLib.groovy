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
    def mkp = new groovy.xml.MarkupBuilder(out)
    mkp {
      img(alt:url,src:src)
    }
  }

  /**
   * Example:
   *  <qrcode:image text="Some arbitrary text."/>
   */
  def image = { attrs ->
    def size = attrs.height?:attrs.width
    String text = attrs.text
    String src = createLink(controller:'qrcode',action:'text',params:[t:text,s:size])
    def mkp = new groovy.xml.MarkupBuilder(out)
    mkp {
      img(alt:url,src:src)
    }
  }
  /**
   * Example:
   *  <qrcode:link />
   */
  def link = { attrs ->
    def label = attrs.label?:"qrlink"
    def size = attrs.height?:attrs.width
    String target = request.getRequestURL()
    String src = createLink(controller:'qrcode',action:'url',params:[u:target,s:size])
    out << '''<style>
#qrcodebox span {
	display: none;
	position: absolute;
}

#qrcodebox a:hover span {
	display: block;
}
</style>
'''
    def mkp = new groovy.xml.MarkupBuilder(out)
    mkp {
      div('id':'qrcodebox') {
        a('class':'qrcodeLink',href:"javascript:null",label) {
          span('id':'qrcode') {
            img(alt:url,src:src)
          }
        }
      }
    }
  }
}
