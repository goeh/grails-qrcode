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
class QrcodeTagLib {
  static namespace = "qrcode"

  /**
   * Example:
   * <qrcode:url width="96"/>
   */
  def url = { attrs ->
      def params = takeAttributes(attrs, ['height', 'width', 'class', 'alt', 'absolute'])
      def linkParams = [controller: 'qrcode', action: 'url']
      def size =  params.height ?: params.width
      def target = request.getRequestURL()
      linkParams.params = [u: target, s: size]
      linkParams.absolute = Boolean.valueOf(params.absolute)
      String src = createLink(linkParams)
      if(! params['class']) {
          params['class'] = 'qrcode'
      }
      out << """<img src="${src}" class="${params['class']}" alt="${params.alt ?: target}"${renderAttributes(attrs)}/>"""
  }

  /**
   * Example:
   *  <qrcode:image text="Some arbitrary text."/>
   */
  def image = { attrs ->
      def params = takeAttributes(attrs, ['height', 'width', 'class', 'alt', 'absolute', 'text'])
      def linkParams = [controller: 'qrcode', action: 'text']
      def size =  params.height ?: params.width
      linkParams.params = [text: params.text, s: size]
      linkParams.absolute = Boolean.valueOf(params.absolute)
      String src = createLink(linkParams)
      if(! params['class']) {
          params['class'] = 'qrcode'
      }
      out << """<img src="${src}" class="${params['class']}" alt="${params.alt ?: params.text}"${renderAttributes(attrs)}/>"""
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

    private Map takeAttributes(Map takeFrom, List attributeNames) {
        def result = [:]
        for (a in attributeNames) {
            def v = takeFrom.remove(a)
            if (v != null) {
                result[a] = v
            }
        }
        return result
    }

    private String renderAttributes(Map attrs) {
        def s = new StringBuilder()
        attrs.each { key, value ->
            if (value != null) {
                s << " ${key.encodeAsURL()}=\"${value.encodeAsHTML()}\""
            }
        }
        s.toString()
    }
}
