/**
 * http://usejsdoc.org/
 */
var express = require("express");
var controller = require("./controller");

var app = express();

app.use(controller.getFileAndDirNames);

app.listen(88);