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

/**
 * @Author Shawn Hartsock
 * This is a prototype scoped service. That means that each new reference
 * to this service is a new copy. That's fine in this case as the service
 * is completely free of any request, session, or thread related concerns.
 * <p/>
 * In Grails, controllers are created new on each request. That means that
 * your controller gets a new copy of this service on each incoming request.
 * The previous version created a new instance of this renderer on each
 * request. That's because this POGO is at least as light weight as a Closure
 * and we got new instances of each closure on every request as well.
 */
class QrCodeService extends QRCodeRenderer {
    static scope = 'prototype'
    static transactional = false
}
