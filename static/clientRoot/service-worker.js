"use strict";var precacheConfig=[["/index.html","3b3f15cb7730e849963aa2c3ea2b4c28"],["/static/css/main.ad8a3d6e.css","c21eba12ae613c2ed6c578406fe98d2b"],["/static/js/main.34c8b4c3.js","d8572d2b9950280782e9631ad897649c"],["/static/media/1.606d89a7.jpg","606d89a73931906f70c63ac402e5666b"],["/static/media/2.e0a15766.jpg","e0a1576604230239889664ab95bc434c"],["/static/media/3.655ec6b0.jpg","655ec6b071cee7a3631cc4ed94a2f295"],["/static/media/4.1042bbde.jpg","1042bbde6277be7a23c2c000ce55f18b"],["/static/media/aero.78e751b4.jpg","78e751b44e9a59d49491777ac675e993"],["/static/media/beach.c3cc8074.jpg","c3cc80741858f12563066cd79468eb10"],["/static/media/blinkr.0a148740.svg","0a14874051d291a7121884d5d1ae14d3"],["/static/media/dondra.b9b5226a.jpg","b9b5226a14e73ddbeb24449e10e70ace"],["/static/media/fashion.f3fde4e3.jpg","f3fde4e3f076593193be488aad2a8287"],["/static/media/final.0c04c711.jpg","0c04c711df14e00b1a39084ac5b28daf"],["/static/media/getintouch.6f78b880.jpg","6f78b8805e8a53abafac5db1beef1fb6"],["/static/media/lightroom.cdacf498.jpg","cdacf4984c152022805521354425b69f"],["/static/media/logo.eb08d154.svg","eb08d154db56ffefe51d1e24119adcc7"],["/static/media/nature2.ecf3ecf5.jpg","ecf3ecf58582a121a93c3ce75e0ea76b"],["/static/media/original.f773a52d.jpg","f773a52db7c65a52cbf984c78449d42e"],["/static/media/paddy.df67b3a7.jpg","df67b3a710a30ceaf9844edd3444bdb0"],["/static/media/profile.13ff6e75.jpg","13ff6e758f02243c9fd39ddc7a3afe71"],["/static/media/profile.5f6d31a0.jpg","5f6d31a055a2151bf370b9a886b3e66f"]],cacheName="sw-precache-v3-sw-precache-webpack-plugin-"+(self.registration?self.registration.scope:""),ignoreUrlParametersMatching=[/^utm_/],addDirectoryIndex=function(e,t){var a=new URL(e);return"/"===a.pathname.slice(-1)&&(a.pathname+=t),a.toString()},cleanResponse=function(t){return t.redirected?("body"in t?Promise.resolve(t.body):t.blob()).then(function(e){return new Response(e,{headers:t.headers,status:t.status,statusText:t.statusText})}):Promise.resolve(t)},createCacheKey=function(e,t,a,n){var c=new URL(e);return n&&c.pathname.match(n)||(c.search+=(c.search?"&":"")+encodeURIComponent(t)+"="+encodeURIComponent(a)),c.toString()},isPathWhitelisted=function(e,t){if(0===e.length)return!0;var a=new URL(t).pathname;return e.some(function(e){return a.match(e)})},stripIgnoredUrlParameters=function(e,a){var t=new URL(e);return t.hash="",t.search=t.search.slice(1).split("&").map(function(e){return e.split("=")}).filter(function(t){return a.every(function(e){return!e.test(t[0])})}).map(function(e){return e.join("=")}).join("&"),t.toString()},hashParamName="_sw-precache",urlsToCacheKeys=new Map(precacheConfig.map(function(e){var t=e[0],a=e[1],n=new URL(t,self.location),c=createCacheKey(n,hashParamName,a,/\.\w{8}\./);return[n.toString(),c]}));function setOfCachedUrls(e){return e.keys().then(function(e){return e.map(function(e){return e.url})}).then(function(e){return new Set(e)})}self.addEventListener("install",function(e){e.waitUntil(caches.open(cacheName).then(function(n){return setOfCachedUrls(n).then(function(a){return Promise.all(Array.from(urlsToCacheKeys.values()).map(function(t){if(!a.has(t)){var e=new Request(t,{credentials:"same-origin"});return fetch(e).then(function(e){if(!e.ok)throw new Error("Request for "+t+" returned a response with status "+e.status);return cleanResponse(e).then(function(e){return n.put(t,e)})})}}))})}).then(function(){return self.skipWaiting()}))}),self.addEventListener("activate",function(e){var a=new Set(urlsToCacheKeys.values());e.waitUntil(caches.open(cacheName).then(function(t){return t.keys().then(function(e){return Promise.all(e.map(function(e){if(!a.has(e.url))return t.delete(e)}))})}).then(function(){return self.clients.claim()}))}),self.addEventListener("fetch",function(t){if("GET"===t.request.method){var e,a=stripIgnoredUrlParameters(t.request.url,ignoreUrlParametersMatching),n="index.html";(e=urlsToCacheKeys.has(a))||(a=addDirectoryIndex(a,n),e=urlsToCacheKeys.has(a));var c="/index.html";!e&&"navigate"===t.request.mode&&isPathWhitelisted(["^(?!\\/__).*"],t.request.url)&&(a=new URL(c,self.location).toString(),e=urlsToCacheKeys.has(a)),e&&t.respondWith(caches.open(cacheName).then(function(e){return e.match(urlsToCacheKeys.get(a)).then(function(e){if(e)return e;throw Error("The cached response that was expected is missing.")})}).catch(function(e){return console.warn('Couldn\'t serve response for "%s" from cache: %O',t.request.url,e),fetch(t.request)}))}});