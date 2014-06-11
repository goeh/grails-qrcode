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
class QrcodeGrailsPlugin {
    def version = "0.5"
    def grailsVersion = "2.0.0 > *"
    def dependsOn = [:]
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]
    def author = "Goran Ehrsson"
    def authorEmail = "goran@technipelago.se"
    def developers = [
            [name: "Shawn Hartsock", email: "hartsock@acm.org"]
    ]
    def title = "QR Code plugin"
    def description = '''\\
This plugin allows you to create QR codes as part of your Grails
application without the need for an external service.'''
    def documentation = "http://grails.org/plugin/qrcode"
    def license = "APACHE"
    def issueManagement = [system: "github", url: "https://github.com/goeh/grails-qrcode/issues"]
    def scm = [url: "https://github.com/goeh/grails-qrcode"]
}
