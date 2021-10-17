(window.NREUM||(NREUM={})).init={ajax:{deny_list:["bam-cell.nr-data.net"]}};(window.NREUM||(NREUM={})).loader_config={licenseKey:"bbc653a5d4",applicationID:"369600669"};window.NREUM||(NREUM={}),__nr_require=function(t,e,n){function r(n){if(!e[n]){var i=e[n]={exports:{}};t[n][0].call(i.exports,function(e){var i=t[n][1][e];return r(i||e)},i,i.exports)}return e[n].exports}if("function"==typeof __nr_require)return __nr_require;for(var i=0;i<n.length;i++)r(n[i]);return r}({1:[function(t,e,n){function r(){}function i(t,e,n){return function(){return o(t,[u.now()].concat(f(arguments)),e?null:this,n),e?void 0:this}}var o=t("handle"),a=t(8),f=t(9),c=t("ee").get("tracer"),u=t("loader"),s=NREUM;"undefined"==typeof window.newrelic&&(newrelic=s);var d=["setPageViewName","setCustomAttribute","setErrorHandler","finished","addToTrace","inlineHit","addRelease"],p="api-",l=p+"ixn-";a(d,function(t,e){s[e]=i(p+e,!0,"api")}),s.addPageAction=i(p+"addPageAction",!0),s.setCurrentRouteName=i(p+"routeName",!0),e.exports=newrelic,s.interaction=function(){return(new r).get()};var m=r.prototype={createTracer:function(t,e){var n={},r=this,i="function"==typeof e;return o(l+"tracer",[u.now(),t,n],r),function(){if(c.emit((i?"":"no-")+"fn-start",[u.now(),r,i],n),i)try{return e.apply(this,arguments)}catch(t){throw c.emit("fn-err",[arguments,this,t],n),t}finally{c.emit("fn-end",[u.now()],n)}}}};a("actionText,setName,setAttribute,save,ignore,onEnd,getContext,end,get".split(","),function(t,e){m[e]=i(l+e)}),newrelic.noticeError=function(t,e){"string"==typeof t&&(t=new Error(t)),o("err",[t,u.now(),!1,e])}},{}],2:[function(t,e,n){function r(t){if(NREUM.init){for(var e=NREUM.init,n=t.split("."),r=0;r<n.length-1;r++)if(e=e[n[r]],"object"!=typeof e)return;return e=e[n[n.length-1]]}}e.exports={getConfiguration:r}},{}],3:[function(t,e,n){function r(){return f.exists&&performance.now?Math.round(performance.now()):(o=Math.max((new Date).getTime(),o))-a}function i(){return o}var o=(new Date).getTime(),a=o,f=t(10);e.exports=r,e.exports.offset=a,e.exports.getLastTimestamp=i},{}],4:[function(t,e,n){function r(t){return!(!t||!t.protocol||"file:"===t.protocol)}e.exports=r},{}],5:[function(t,e,n){function r(t,e){var n=t.getEntries();n.forEach(function(t){"first-paint"===t.name?d("timing",["fp",Math.floor(t.startTime)]):"first-contentful-paint"===t.name&&d("timing",["fcp",Math.floor(t.startTime)])})}function i(t,e){var n=t.getEntries();n.length>0&&d("lcp",[n[n.length-1]])}function o(t){t.getEntries().forEach(function(t){t.hadRecentInput||d("cls",[t])})}function a(t){if(t instanceof m&&!g){var e=Math.round(t.timeStamp),n={type:t.type};e<=p.now()?n.fid=p.now()-e:e>p.offset&&e<=Date.now()?(e-=p.offset,n.fid=p.now()-e):e=p.now(),g=!0,d("timing",["fi",e,n])}}function f(t){"hidden"===t&&d("pageHide",[p.now()])}if(!("init"in NREUM&&"page_view_timing"in NREUM.init&&"enabled"in NREUM.init.page_view_timing&&NREUM.init.page_view_timing.enabled===!1)){var c,u,s,d=t("handle"),p=t("loader"),l=t(7),m=NREUM.o.EV;if("PerformanceObserver"in window&&"function"==typeof window.PerformanceObserver){c=new PerformanceObserver(r);try{c.observe({entryTypes:["paint"]})}catch(v){}u=new PerformanceObserver(i);try{u.observe({entryTypes:["largest-contentful-paint"]})}catch(v){}s=new PerformanceObserver(o);try{s.observe({type:"layout-shift",buffered:!0})}catch(v){}}if("addEventListener"in document){var g=!1,h=["click","keydown","mousedown","pointerdown","touchstart"];h.forEach(function(t){document.addEventListener(t,a,!1)})}l(f)}},{}],6:[function(t,e,n){function r(t,e){if(!i)return!1;if(t!==i)return!1;if(!e)return!0;if(!o)return!1;for(var n=o.split("."),r=e.split("."),a=0;a<r.length;a++)if(r[a]!==n[a])return!1;return!0}var i=null,o=null,a=/Version\/(\S+)\s+Safari/;if(navigator.userAgent){var f=navigator.userAgent,c=f.match(a);c&&f.indexOf("Chrome")===-1&&f.indexOf("Chromium")===-1&&(i="Safari",o=c[1])}e.exports={agent:i,version:o,match:r}},{}],7:[function(t,e,n){function r(t){function e(){t(a&&document[a]?document[a]:document[i]?"hidden":"visible")}"addEventListener"in document&&o&&document.addEventListener(o,e,!1)}e.exports=r;var i,o,a;"undefined"!=typeof document.hidden?(i="hidden",o="visibilitychange",a="visibilityState"):"undefined"!=typeof document.msHidden?(i="msHidden",o="msvisibilitychange"):"undefined"!=typeof document.webkitHidden&&(i="webkitHidden",o="webkitvisibilitychange",a="webkitVisibilityState")},{}],8:[function(t,e,n){function r(t,e){var n=[],r="",o=0;for(r in t)i.call(t,r)&&(n[o]=e(r,t[r]),o+=1);return n}var i=Object.prototype.hasOwnProperty;e.exports=r},{}],9:[function(t,e,n){function r(t,e,n){e||(e=0),"undefined"==typeof n&&(n=t?t.length:0);for(var r=-1,i=n-e||0,o=Array(i<0?0:i);++r<i;)o[r]=t[e+r];return o}e.exports=r},{}],10:[function(t,e,n){e.exports={exists:"undefined"!=typeof window.performance&&window.performance.timing&&"undefined"!=typeof window.performance.timing.navigationStart}},{}],ee:[function(t,e,n){function r(){}function i(t){function e(t){return t&&t instanceof r?t:t?u(t,c,a):a()}function n(n,r,i,o,a){if(a!==!1&&(a=!0),!l.aborted||o){t&&a&&t(n,r,i);for(var f=e(i),c=v(n),u=c.length,s=0;s<u;s++)c[s].apply(f,r);var p=d[w[n]];return p&&p.push([b,n,r,f]),f}}function o(t,e){y[t]=v(t).concat(e)}function m(t,e){var n=y[t];if(n)for(var r=0;r<n.length;r++)n[r]===e&&n.splice(r,1)}function v(t){return y[t]||[]}function g(t){return p[t]=p[t]||i(n)}function h(t,e){l.aborted||s(t,function(t,n){e=e||"feature",w[n]=e,e in d||(d[e]=[])})}var y={},w={},b={on:o,addEventListener:o,removeEventListener:m,emit:n,get:g,listeners:v,context:e,buffer:h,abort:f,aborted:!1};return b}function o(t){return u(t,c,a)}function a(){return new r}function f(){(d.api||d.feature)&&(l.aborted=!0,d=l.backlog={})}var c="nr@context",u=t("gos"),s=t(8),d={},p={},l=e.exports=i();e.exports.getOrSetContext=o,l.backlog=d},{}],gos:[function(t,e,n){function r(t,e,n){if(i.call(t,e))return t[e];var r=n();if(Object.defineProperty&&Object.keys)try{return Object.defineProperty(t,e,{value:r,writable:!0,enumerable:!1}),r}catch(o){}return t[e]=r,r}var i=Object.prototype.hasOwnProperty;e.exports=r},{}],handle:[function(t,e,n){function r(t,e,n,r){i.buffer([t],r),i.emit(t,e,n)}var i=t("ee").get("handle");e.exports=r,r.ee=i},{}],id:[function(t,e,n){function r(t){var e=typeof t;return!t||"object"!==e&&"function"!==e?-1:t===window?0:a(t,o,function(){return i++})}var i=1,o="nr@id",a=t("gos");e.exports=r},{}],loader:[function(t,e,n){function r(){if(!R++){var t=M.info=NREUM.info,e=v.getElementsByTagName("script")[0];if(setTimeout(u.abort,3e4),!(t&&t.licenseKey&&t.applicationID&&e))return u.abort();c(E,function(e,n){t[e]||(t[e]=n)});var n=a();f("mark",["onload",n+M.offset],null,"api"),f("timing",["load",n]);var r=v.createElement("script");0===t.agent.indexOf("http://")||0===t.agent.indexOf("https://")?r.src=t.agent:r.src=l+"://"+t.agent,e.parentNode.insertBefore(r,e)}}function i(){"complete"===v.readyState&&o()}function o(){f("mark",["domContent",a()+M.offset],null,"api")}var a=t(3),f=t("handle"),c=t(8),u=t("ee"),s=t(6),d=t(4),p=t(2),l=p.getConfiguration("ssl")===!1?"http":"https",m=window,v=m.document,g="addEventListener",h="attachEvent",y=m.XMLHttpRequest,w=y&&y.prototype,b=!d(m.location);NREUM.o={ST:setTimeout,SI:m.setImmediate,CT:clearTimeout,XHR:y,REQ:m.Request,EV:m.Event,PR:m.Promise,MO:m.MutationObserver};var x=""+location,E={beacon:"bam.nr-data.net",errorBeacon:"bam.nr-data.net",agent:"js-agent.newrelic.com/nr-1211.min.js"},O=y&&w&&w[g]&&!/CriOS/.test(navigator.userAgent),M=e.exports={offset:a.getLastTimestamp(),now:a,origin:x,features:{},xhrWrappable:O,userAgent:s,disabled:b};if(!b){t(1),t(5),v[g]?(v[g]("DOMContentLoaded",o,!1),m[g]("load",r,!1)):(v[h]("onreadystatechange",i),m[h]("onload",r)),f("mark",["firstbyte",a.getLastTimestamp()],null,"api");var R=0}},{}],"wrap-function":[function(t,e,n){function r(t,e){function n(e,n,r,c,u){function nrWrapper(){var o,a,s,p;try{a=this,o=d(arguments),s="function"==typeof r?r(o,a):r||{}}catch(l){i([l,"",[o,a,c],s],t)}f(n+"start",[o,a,c],s,u);try{return p=e.apply(a,o)}catch(m){throw f(n+"err",[o,a,m],s,u),m}finally{f(n+"end",[o,a,p],s,u)}}return a(e)?e:(n||(n=""),nrWrapper[p]=e,o(e,nrWrapper,t),nrWrapper)}function r(t,e,r,i,o){r||(r="");var f,c,u,s="-"===r.charAt(0);for(u=0;u<e.length;u++)c=e[u],f=t[c],a(f)||(t[c]=n(f,s?c+r:r,i,c,o))}function f(n,r,o,a){if(!m||e){var f=m;m=!0;try{t.emit(n,r,o,e,a)}catch(c){i([c,n,r,o],t)}m=f}}return t||(t=s),n.inPlace=r,n.flag=p,n}function i(t,e){e||(e=s);try{e.emit("internal-error",t)}catch(n){}}function o(t,e,n){if(Object.defineProperty&&Object.keys)try{var r=Object.keys(t);return r.forEach(function(n){Object.defineProperty(e,n,{get:function(){return t[n]},set:function(e){return t[n]=e,e}})}),e}catch(o){i([o],n)}for(var a in t)l.call(t,a)&&(e[a]=t[a]);return e}function a(t){return!(t&&t instanceof Function&&t.apply&&!t[p])}function f(t,e){var n=e(t);return n[p]=t,o(t,n,s),n}function c(t,e,n){var r=t[e];t[e]=f(r,n)}function u(){for(var t=arguments.length,e=new Array(t),n=0;n<t;++n)e[n]=arguments[n];return e}var s=t("ee"),d=t(9),p="nr@original",l=Object.prototype.hasOwnProperty,m=!1;e.exports=r,e.exports.wrapFunction=f,e.exports.wrapInPlace=c,e.exports.argsToArray=u},{}]},{},["loader"]);

var monetateT = new Date().getTime();

(function(otid, env) {
			const d = window.document;
			const now = Math.floor(Date.now() / 1000);
			const newYear2020 = 1577836800;
			d.oneTrustIsEnabled = false;
			if (now >= newYear2020 || d.location.hash === '#ccpa') {
				const otscript = d.createElement('script');
				const firstscr = d.getElementsByTagName('script')[0];
				otscript.src = 'https://cdn.cookielaw.org/scripttemplates/otSDKStub.js';
				otscript.type = 'text/javascript';
				otscript.charset = 'UTF-8';
				otscript.async = true;
				otscript.setAttribute('data-domain-script', otid + (env != 'prod' ? '-test' : ''));
				firstscr.parentNode.insertBefore(otscript, firstscr);
				d.oneTrustIsEnabled = true;
			}
		}('3d8ef039-13a5-4851-a635-f8c92e08f023', 'prod'));
		
function OptanonWrapper() {}

(function(w, d, s, l, i) {
			w[l] = w[l] || [];
			w[l].push({
				'gtm.start': new Date().getTime(),
				event: 'gtm.js'
			});
			var f = d.getElementsByTagName(s)[0],
				j = d.createElement(s),
				dl = l != 'dataLayer' ? '&l=' + l : '';
			j.async = true;
			j.src =
				'https://www.googletagmanager.com/gtm.js?id=' + i + dl;
			f.parentNode.insertBefore(j, f);
		})(window, document, 'script', 'dataLayer', 'GTM-5M62NCB');
		
window._wpemojiSettings = {"baseUrl":"https:\/\/s.w.org\/images\/core\/emoji\/13.0.1\/72x72\/","ext":".png","svgUrl":"https:\/\/s.w.org\/images\/core\/emoji\/13.0.1\/svg\/","svgExt":".svg","source":{"concatemoji":"https:\/\/www.life.com\/wp-includes\/js\/wp-emoji-release.min.js?ver=5.7.2"}};
			!function(e,a,t){var n,r,o,i=a.createElement("canvas"),p=i.getContext&&i.getContext("2d");function s(e,t){var a=String.fromCharCode;p.clearRect(0,0,i.width,i.height),p.fillText(a.apply(this,e),0,0);e=i.toDataURL();return p.clearRect(0,0,i.width,i.height),p.fillText(a.apply(this,t),0,0),e===i.toDataURL()}function c(e){var t=a.createElement("script");t.src=e,t.defer=t.type="text/javascript",a.getElementsByTagName("head")[0].appendChild(t)}for(o=Array("flag","emoji"),t.supports={everything:!0,everythingExceptFlag:!0},r=0;r<o.length;r++)t.supports[o[r]]=function(e){if(!p||!p.fillText)return!1;switch(p.textBaseline="top",p.font="600 32px Arial",e){case"flag":return s([127987,65039,8205,9895,65039],[127987,65039,8203,9895,65039])?!1:!s([55356,56826,55356,56819],[55356,56826,8203,55356,56819])&&!s([55356,57332,56128,56423,56128,56418,56128,56421,56128,56430,56128,56423,56128,56447],[55356,57332,8203,56128,56423,8203,56128,56418,8203,56128,56421,8203,56128,56430,8203,56128,56423,8203,56128,56447]);case"emoji":return!s([55357,56424,8205,55356,57212],[55357,56424,8203,55356,57212])}return!1}(o[r]),t.supports.everything=t.supports.everything&&t.supports[o[r]],"flag"!==o[r]&&(t.supports.everythingExceptFlag=t.supports.everythingExceptFlag&&t.supports[o[r]]);t.supports.everythingExceptFlag=t.supports.everythingExceptFlag&&!t.supports.flag,t.DOMReady=!1,t.readyCallback=function(){t.DOMReady=!0},t.supports.everything||(n=function(){t.readyCallback()},a.addEventListener?(a.addEventListener("DOMContentLoaded",n,!1),e.addEventListener("load",n,!1)):(e.attachEvent("onload",n),a.attachEvent("onreadystatechange",function(){"complete"===a.readyState&&t.readyCallback()})),(n=t.source||{}).concatemoji?c(n.concatemoji):n.wpemoji&&n.twemoji&&(c(n.twemoji),c(n.wpemoji)))}(window,document,window._wpemojiSettings);

    (function () {
                            window.siqConfig = {
                                engineKey: "00623ce7d78b2deea1f63f6663acf24a"
                            };
                            window.siqConfig.baseUrl = "//pub.searchiq.co/";
                            var script = document.createElement("SCRIPT");
                            script.src = window.siqConfig.baseUrl + 'js/container/siq-container-2.js?cb=' + (Math.floor(Math.random()*999999)) + '&engineKey=' + siqConfig.engineKey;
                            script.id = "siq-container";
                            document.getElementsByTagName("HEAD")[0].appendChild(script);
                        })();
                        
!function(s){var o,a,n,r;void 0!==s&&(o=location.href.replace(/^https?:\/\/([^\/]+).*$/i,"$1").toLowerCase(),a=location.href,n=function(e,i,n){var r,t,s;i&&((r=new Image).onerror=r.onload=n,t=null!=i.kw?i.kw.replace(/\"/g,""):i.kw,s='{"sid":"'+i.sid+'","said":"'+i.said.replace("www.","")+'","kw":"'+t+'","source_url":"'+a+'","engineKey":"'+i.engineKey+'"'+(i.refURL?',"external_referrer":"'+i.refURL+'"':"")+"}",r.src="//track.searchiq.co/api/tr?event="+encodeURIComponent("search")+"&eventInfo="+encodeURIComponent(s))},r=function(){var e=s(".holdResults");if(0!==e.length){for(var i=e.attr("class").split(" "),n=-1,r=0;r<i.length;r++){var t=i[r].trim();/^siq_searchIndexResult-\d+$/.test(t)&&(n=t.replace(/^siq_searchIndexResult-(\d+)$/,"$1"))}if(!(n<0))return{kw:s(".siq_searchIndex-"+n).val(),sid:SiqConfig.siqSid,said:o,engineKey:siqConfig.engineKey}}},function(){if(s(document).on("click",".holdResults .searchWrapperLabel, .holdResults ul li .resultsMore",function(){n(0,r())}),s(document).on("click",".siq-autocomplete a",function(){s(this).hasClass("siq-recommended-link")||0!==s(this).parents(".siq-recommended-link").length||n(0,r())}),s(document).on("submit","form.siq_searchForm",function(){n(0,r())}),s(document).on("keydown",function(e){switch((e=e||window.event).which||e.keyCode){case 13:(0<s(".holdResults").length&&0<s(".holdResults .siq-autocomplete.highlighted").length||s(e.target).is("#siq_ajax_search")||s(e.target).parents("form").is("#siq_search_results div.siq_search_ajax.siq_search_box form"))&&n(0,r())}}),s(document).on("click touch",".siq_aclinewrap",function(){n(0,{kw:s(this).parents("#siq_mobile").find(".siq_searchwrap input.siq_searchfield").val(),sid:SiqConfig.siqSid,said:o,engineKey:siqConfig.engineKey})}),document.referrer&&document.referrer.replace(/^https?:\/\/([^\/]+).*$/i,"$1").toLowerCase()!==o){if(SiqConfig.resultPageUrl.replace(/^https?:/,"").replace(/\?.*$/,"")!==location.href.replace(/^https?:/,"").replace(/\?.*$/,""))return;function e(e,i){if(!(e.indexOf("?")<0)){var n=e.substr(e.indexOf("?")+1);if(0!==n.length)for(var r=n.split("&"),t=0;t<r.length;t++){var s=r[t];if(s.indexOf("=")<0&&decodeURIComponent(s)===i)return!0;if(i===decodeURIComponent(s.substr(0,s.indexOf("="))))return decodeURIComponent(s.substr(s.indexOf("=")+1))}}}var i=e(location.href,SiqConfig.queryParameter)||e(location.href,SiqConfig.searchBoxName);eventData={kw:i,sid:SiqConfig.siqSid,said:o,engineKey:siqConfig.engineKey,refURL:document.referrer},n(0,eventData)}}())}(window.siq_S);

 var img=new Image();
 img.src="//api.searchiq.co/live?engineKey="+window.siqConfig.engineKey;
}();

(function() {
		const d = window.document;
		const otLinkClass = '.ot-sdk-show-settings';
		if (d.oneTrustIsEnabled === false) {
			const otLink = d.querySelector(otLinkClass);
			otLink.style.display = 'none';
		}
	}());
	
var offSide = {"side":"left"};

if(typeof jQuery != "undefined") {
					jQuery('.siq-expsearch-icon').each(function(){
					var searchBox = (jQuery(this).parents('.siq-icon-searchbox-wrap').size() > 0) ? jQuery(this).parents('.siq-icon-searchbox-wrap') : jQuery('.siq-menu-searchbox-wrap') ;
					var inputBox = searchBox.find('.siq-expsearch-input');
					
					jQuery(this).on('click', function () {
						if (!searchBox.hasClass('siq-search-open')) {
							searchBox.addClass('siq-search-open');
							inputBox.focus();
						} else {
							searchBox.removeClass('siq-search-open');
							inputBox.focusout();
						}
					});
					jQuery('body').click(function (evt) {
						if (evt.target.id == "siq-menu-searchbox-wrap")
							return;
						if (jQuery(evt.target).closest('#siq-menu-searchbox-wrap').length || jQuery(evt.target).closest('.siq-icon-searchbox-wrap').length)
							return;

						if (searchBox.hasClass('siq-search-open')) {
							searchBox.removeClass('siq-search-open');
						}
					});
				});
			}
			
			
window.lazyLoadOptions = {
                elements_selector: "img[data-lazy-src],.rocket-lazyload",
                data_src: "lazy-src",
                data_srcset: "lazy-srcset",
                data_sizes: "lazy-sizes",
                class_loading: "lazyloading",
                class_loaded: "lazyloaded",
                threshold: 300,
                callback_loaded: function(element) {
                    if ( element.tagName === "IFRAME" && element.dataset.rocketLazyload == "fitvidscompatible" ) {
                        if (element.classList.contains("lazyloaded") ) {
                            if (typeof window.jQuery != "undefined") {
                                if (jQuery.fn.fitVids) {
                                    jQuery(element).parent().fitVids();
                                }
                            }
                        }
                    }
                }};
        window.addEventListener('LazyLoad::Initialized', function (e) {
            var lazyLoadInstance = e.detail.instance;
        
            if (window.MutationObserver) {
                var observer = new MutationObserver(function(mutations) {
                    var image_count = 0;
                    var iframe_count = 0;
                    var rocketlazy_count = 0;

                    mutations.forEach(function(mutation) {
                        for (i = 0; i < mutation.addedNodes.length; i++) {
                            if (typeof mutation.addedNodes[i].getElementsByTagName !== 'function') {
                                return;
                            }

                           if (typeof mutation.addedNodes[i].getElementsByClassName !== 'function') {
                                return;
                            }

                            images = mutation.addedNodes[i].getElementsByTagName('img');
                            is_image = mutation.addedNodes[i].tagName == "IMG";
                            iframes = mutation.addedNodes[i].getElementsByTagName('iframe');
                            is_iframe = mutation.addedNodes[i].tagName == "IFRAME";
                            rocket_lazy = mutation.addedNodes[i].getElementsByClassName('rocket-lazyload');

                            image_count += images.length;
			                iframe_count += iframes.length;
			                rocketlazy_count += rocket_lazy.length;
                            
                            if(is_image){
                                image_count += 1;
                            }

                            if(is_iframe){
                                iframe_count += 1;
                            }
                        }
                    } );

                    if(image_count > 0 || iframe_count > 0 || rocketlazy_count > 0){
                        lazyLoadInstance.update();
                    }
                } );
                
                var b      = document.getElementsByTagName("body")[0];
                var config = { childList: true, subtree: true };
                
                observer.observe(b, config);
            }
        }, false);
        
>window.NREUM||(NREUM={});NREUM.info={"beacon":"bam-cell.nr-data.net","licenseKey":"bbc653a5d4","applicationID":"369600669","transactionName":"Z1JaN0UEDEpTAUUMX14YeQBDDA1XHRZUCEBcVkwGGg0NVFc=","queueTime":0,"applicationTime":280,"atts":"SxVZQQ0eH0Q=","errorBeacon":"bam-cell.nr-data.net","agent":""}

function trackMonetateLinkRQ(){
window.monetateQ = window.monetateQ || [];
window.monetateQ.push(["trackEvent",["MonetateLink"]]);
}


function trackMonetateLinkRA(){
window.monetateQ = window.monetateQ || [];
window.monetateQ.push(["trackEvent",["MonetateLink"]]);
}






