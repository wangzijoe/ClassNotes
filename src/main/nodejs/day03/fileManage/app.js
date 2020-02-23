/**
 * http://usejsdoc.org/
 */
var express = require("express");
var router = require("./controller");



var app = express();

app.set("view engine", "ejs");

// 路由中间件--提供静态服务的
app.use(express.static("./public"));
// 这边的showIndex是router模块里的，看起来不需要传req和res，
// 其实，是express的get在回调showIndex自动传了这两个参数
app.get("/", router.showIndex);
app.get("/:albumName",router.showAlbum);

app.listen(80);