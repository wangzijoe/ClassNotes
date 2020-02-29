// 这边路径写全了的原因是models下面暂时没有写package.json
var file = require("../models/file.js");
var msg = require("../models/message.js");
var url = require("url");

// 获取当前路径下的所有文件夹和文件的名称
exports.getFileAndDirNames = function(req, res) {
	var path = url.parse(req.url).pathname;
	file.getAllFiles(path, function(err, pathInfo) {
		if (err) {
			res.send(msg.error(500, err));
			return;
		}
		res.send(msg.ok(pathInfo));
	});
}

/**
 * 创建文件夹，接收请求体 { basePath: '/basePath', newFolderName: 'newFolderName' }
 */
exports.createDir = function(req, res) {
	var basePath = req.body.basePath;
	var newFolderName = req.body.newFolderName;
	file.mkdir(basePath, newFolderName, function(err, info){
		if(err){
			res.send(msg.error(500, err));
			return;
		}
		res.send(msg.ok(info));
	});
}
