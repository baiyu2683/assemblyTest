package com.zh.jsengine;

import com.alibaba.fastjson.JSONObject;
import com.eclipsesource.v8.V8;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by zh on 2017-08-18.
 */
public class TestV8 {
    public static final String reg_script = "(<html><head></head><body></body></html>|)(\\s|)<script[\\w\\W]*?>([\\w\\W]*?)</script>";
    public static void main(String[] args) throws IOException {
        JSONObject location = new JSONObject();
//			location.put("href", "http://quzhou.19lou.com/forum-900-thread-165601343792152480-1-1.html");
        location.put("href", "http://quzhou.19lou.com/safeRedirect.htm?http://quzhou.19lou.com/forum-900-thread-165601343792152480-1-1.html");
        JSONObject document = new JSONObject();
        document.put("location", location);
        document.put("cookie", "");
        System.out.println(document.toJSONString());

        V8 v8 = V8.createV8Runtime();
        v8.executeScript("document = " + document.toJSONString());

        FileReader fr = new FileReader("f:/quzhou.html");
        String script = IOUtils.toString(fr);

        v8.executeScript(script);
//        v8.executeScript("eval(function(p,a,c,k,e,r){e=function(c){return(c<62?'':e(parseInt(c/62)))+((c=c%62)>35?String.fromCharCode(c+29):c.toString(36))};if('0'.replace(0,e)==0){while(c--)r[e(c)]=k[c];k=[function(e){return r[e]||e}];e=function(){return'[6-8j-rt-yB-M]'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\\\b'+e(c)+'\\\\b','g'),k[c]);return p}('6 l=7.m.n;6 s=l.indexOf(\"?\");6 o=\"\";6 k=r(l);6 t=\"\";try{t=y.width+\"-\"+y.availWidth+\"-\"+7.B.clientWidth+\"-\"+7.B.clientHeight}catch(e){}p(s>0){o=unescape(l.substr(s+1));C=r(o);p(D(C)){q(\"_Z3nY0d4C_\",\"37XgPK9h-=\"+t,365,\"/\",k);7.m.n=o}E{7.m.n=\"u://F\"+k}}E{7.m.n=\"u://F\"+k}v D(c){6 a=[\".G-inc.j\",\".G.j\",\".cqmmgo.j\",\".0595bbs.cn\",\".qvbuy.j\",\".ihome99.j\",\".shanghaining.j\"];for(6 b=0;b<a.H;b++){p(c==a[b]){w I}}w J}v r(c){6 b=/^u:\\\\/\\\\/([0-9.\\\\-A-x-z]+)(\\\\.[0-9\\\\-A-x-z]{1,}\\\\.[0-9\\\\-A-x-z]{1,})(:[0-9]+){0,1}.*$/;6 a=b.exec(c);w a[2]}v q(b,h){6 c=new Date();6 g=q.K;6 e=q.K.H;6 d=(e>2)?g[2]:8;6 i=(e>3)?g[3]:8;6 f=(e>4)?g[4]:8;6 a=(e>5)?g[5]:J;p(d!=8&&d>=0){c.setTime(c.getTime()+(d*24*L*L*1000))}7.cookie=b+\"=\"+escape(h)+((d==8||d<0)?((d==-1)?\"; M=-1\":\"\"):(\"; M=\"+c.toGMTString()))+((i==8)?\"\":(\"; path=\"+i))+((f==8)?\"\":(\"; k=\"+f))+((a==I)?\"; secure\":\"\")};',[],49,'||||||var|document|null|||||||||||com|domain|url|location|href|redirectUrl|if|SetCookie|getDomainByUrl||pp|http|function|return|Za|screen|||body|redirectDomain|isValid|else|www|19lou|length|true|false|arguments|60|expires'.split('|'),0,{}))");
//        v8.executeScript("var url=document.location.href;var s=url.indexOf(\"?\");var redirectUrl=\"\";var domain=getDomainByUrl(url);var pp=\"\";try{pp=screen.width+\"-\"+screen.availWidth+\"-\"+document.body.clientWidth+\"-\"+document.body.clientHeight}catch(e){}if(s>0){redirectUrl=unescape(url.substr(s+1));redirectDomain=getDomainByUrl(redirectUrl);if(isValid(redirectDomain)){SetCookie(\"_Z3nY0d4C_\",\"37XgPK9h-=\"+pp,365,\"/\",domain);document.location.href=redirectUrl}else{document.location.href=\"http://www\"+domain}}else{document.location.href=\"http://www\"+domain}function isValid(c){var a=[\".19lou-inc.com\",\".19lou.com\",\".cqmmgo.com\",\".0595bbs.cn\",\".qvbuy.com\",\".ihome99.com\",\".shanghaining.com\"];for(var b=0;b<a.length;b++){if(c==a[b]){return true}}return false}function getDomainByUrl(c){var b=/^http:\\/\\/([0-9.\\-A-Za-z]+)(\\.[0-9\\-A-Za-z]{1,}\\.[0-9\\-A-Za-z]{1,})(:[0-9]+){0,1}.*$/;var a=b.exec(c);return a[2]}function SetCookie(b,h){var c=new Date();var g=SetCookie.arguments;var e=4;var d=(e>2)?g[2]:null;var i=(e>3)?g[3]:null;var f=(e>4)?g[4]:null;var a=(e>5)?g[5]:false;if(d!=null&&d>=0){c.setTime(c.getTime()+(d*24*60*60*1000))}document.cookie=b+\"=\"+escape(h)+((d==null||d<0)?((d==-1)?\"; expires=-1\":\"\"):(\"; expires=\"+c.toGMTString()))+((i==null)?\"\":(\"; path=\"+i))+((f==null)?\"\":(\"; domain=\"+f))+((a==true)?\"; secure\":\"\")};");
        String o1 = v8.executeStringScript("document.location.href");
        String o2 = v8.executeStringScript("document.cookie");
        System.out.println(o1 + "-" + o2);
    }
}
