"use strict";var precacheConfig=[["/index.html","d5a81e32e965ff3d1b818fa741d9f90d"],["/static/css/main.66d6aca3.css","be8bd2562140a67439b636aaceb49646"],["/static/js/main.2a85365f.js","81715d469e55d17a15544deee1f68834"],["/static/media/1.606d89a7.jpg","606d89a73931906f70c63ac402e5666b"],["/static/media/2.e0a15766.jpg","e0a1576604230239889664ab95bc434c"],["/static/media/3.655ec6b0.jpg","655ec6b071cee7a3631cc4ed94a2f295"],["/static/media/4.1042bbde.jpg","1042bbde6277be7a23c2c000ce55f18b"],["/static/media/aero.78e751b4.jpg","78e751b44e9a59d49491777ac675e993"],["/static/media/beach.c3cc8074.jpg","c3cc80741858f12563066cd79468eb10"],["/static/media/blinkr.8aba356a.svg","8aba356a983257293cb8fa91d28f61a0"],["/static/media/dondra.b9b5226a.jpg","b9b5226a14e73ddbeb24449e10e70ace"],["/static/media/fashion.f3fde4e3.jpg","f3fde4e3f076593193be488aad2a8287"],["/static/media/final.0c04c711.jpg","0c04c711df14e00b1a39084ac5b28daf"],["/static/media/getintouch.6f78b880.jpg","6f78b8805e8a53abafac5db1beef1fb6"],["/static/media/lightroom.cdacf498.jpg","cdacf4984c152022805521354425b69f"],["/static/media/logo.b791f25d.svg","b791f25dbd53929273500669194f14bb"],["/static/media/nature2.ecf3ecf5.jpg","ecf3ecf58582a121a93c3ce75e0ea76b"],["/static/media/original.f773a52d.jpg","f773a52db7c65a52cbf984c78449d42e"],["/static/media/paddy.df67b3a7.jpg","df67b3a710a30ceaf9844edd3444bdb0"],["/static/media/profile.13ff6e75.jpg","13ff6e758f02243c9fd39ddc7a3afe71"],["/static/media/profile.5f6d31a0.jpg","5f6d31a055a2151bf370b9a886b3e66f"]],cacheName="sw-precache-v3-sw-precache-webpack-plugin-"+(self.registration?self.registration.scope:""),ignoreUrlParametersMatching=[/^utm_/],addDirectoryIndex=function(e,a){var t=new URL(e);return"/"===t.pathname.slice(-1)&&(t.pathname+=a),t.toString()},cleanResponse=function(a){return a.redirected?("body"in a?Promise.resolve(a.body):a.blob()).then(function(e){return new Response(e,{headers:a.headers,status:a.status,statusText:a.statusText})}):Promise.resolve(a)},createCacheKey=function(e,a,t,n){var r=new URL(e);return n&&r.pathname.match(n)||(r.search+=(r.search?"&":"")+encodeURIComponent(a)+"="+encodeURIComponent(t)),r.toString()},isPathWhitelisted=function(e,a){if(0===e.length)return!0;var t=new URL(a).pathname;return e.some(function(e){return t.match(e)})},stripIgnoredUrlParameters=function(e,t){var a=new URL(e);return a.hash="",a.search=a.search.slice(1).split("&").map(function(e){return e.split("=")}).filter(function(a){return t.every(function(e){return!e.test(a[0])})}).map(function(e){return e.join("=")}).join("&"),a.toString()},hashParamName="_sw-precache",urlsToCacheKeys=new Map(precacheConfig.map(function(e){var a=e[0],t=e[1],n=new URL(a,self.location),r=createCacheKey(n,hashParamName,t,/\.\w{8}\./);return[n.toString(),r]}));function setOfCachedUrls(e){return e.keys().then(function(e){return e.map(function(e){return e.url})}).then(function(e){return new Set(e)})}self.addEventListener("install",function(e){e.waitUntil(caches.open(cacheName).then(function(n){return setOfCachedUrls(n).then(function(t){return Promise.all(Array.from(urlsToCacheKeys.values()).map(function(a){if(!t.has(a)){var e=new Request(a,{credentials:"same-origin"});return fetch(e).then(function(e){if(!e.ok)throw new Error("Request for "+a+" returned a response with status "+e.status);return cleanResponse(e).then(function(e){return n.put(a,e)})})}}))})}).then(function(){return self.skipWaiting()}))}),self.addEventListener("activate",function(e){var t=new Set(urlsToCacheKeys.values());e.waitUntil(caches.open(cacheName).then(function(a){return a.keys().then(function(e){return Promise.all(e.map(function(e){if(!t.has(e.url))return a.delete(e)}))})}).then(function(){return self.clients.claim()}))}),self.addEventListener("fetch",function(a){if("GET"===a.request.method){var e,t=stripIgnoredUrlParameters(a.request.url,ignoreUrlParametersMatching),n="index.html";(e=urlsToCacheKeys.has(t))||(t=addDirectoryIndex(t,n),e=urlsToCacheKeys.has(t));var r="/index.html";!e&&"navigate"===a.request.mode&&isPathWhitelisted(["^(?!\\/__).*"],a.request.url)&&(t=new URL(r,self.location).toString(),e=urlsToCacheKeys.has(t)),e&&a.respondWith(caches.open(cacheName).then(function(e){return e.match(urlsToCacheKeys.get(t)).then(function(e){if(e)return e;throw Error("The cached response that was expected is missing.")})}).catch(function(e){return console.warn('Couldn\'t serve response for "%s" from cache: %O',a.request.url,e),fetch(a.request)}))}});