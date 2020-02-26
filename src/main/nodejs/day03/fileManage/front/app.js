/**
 * http://usejsdoc.org/
 */
var express = require("express");

var app = express();

// 路由中间件--提供静态服务的
app.use(express.static(__dirname + "/views"));
app.use(express.static(__dirname + "/static"))

app.listen(80);