CREATE TABLE AUTHENTICATION (name text, baseUrl text);
CREATE TABLE BOOKMARKS (name text, description text, baseUrl text, tags text);
CREATE TABLE CACHE (baseUrl text, source blob, contenLenght num, etag text, lastModified date, type text);
CREATE TABLE CHAR (name text, value integer);
CREATE TABLE COLOR (name text, value text);
CREATE TABLE COOKIE (cookieName text, cookieValue text, domain text, path text, expires date, maxAge text, secure integer, httponly integer);
CREATE TABLE CONNECTION (proxyType text, userName text, password text, authenticated integer, host text, port integer, disableProxyForLocalAddresses integer);
CREATE TABLE FONT (name text);
CREATE TABLE FONT_SIZE (name text);
CREATE TABLE HOST (baseUrl text);
CREATE TABLE INPUT (name text, value text);
CREATE TABLE LOOK_AND_FEEL (acryl integer, aero integer, aluminium integer, bernstein integer, fast integer, graphite integer, hiFi integer,luna integer, mcWin integer, mint integer, noire integer, smart integer, texture integer,bold integer, italic integer, underline integer, strikethrough integer, subscript integer, superscript integer, fontSize text, font text, color text);
CREATE TABLE NETWORK (js integer, css integer, cookie integer, cache integer, navigation integer);
CREATE TABLE SEARCH (name text, description text, baseUrl text, queryParameter text, selected integer, type text);
CREATE TABLE STARTUP (baseUrl text);
CREATE TABLE SIZE (width integer, height integer);
CREATE TABLE USER_AGENT (description text);