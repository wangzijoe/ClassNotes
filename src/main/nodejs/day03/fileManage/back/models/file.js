var fs = require("fs");

// 获取当前路径下的所有文件夹和文件的名称
exports.getAllFiles = function(path, callback) {
	path = "./uploads" + path + "/";
	fs.exists(path, function(exists) {
		// 如果文件存在
		if (exists) {
			fs.readdir(path, function(err, files) {
				// 所有文件夹名称
				var allDirNames = [];
				// 所有文件名称
				var allFileNames = [];
				if (err) {
					callback(err, createPathInfo(allDirNames, allFileNames));
				}
				// 迭代器(虽然有同步的API，但是，我就是想秀一下，用回调函数的写法)
				(function iterator(i) {
					if (i == files.length) {
						callback(null,
								createPathInfo(allDirNames, allFileNames));
						return;
					}
					fs.stat(path + files[i], function(err, stats) {
						console.log(path + files[i]);
						if (err) {
							callback(err, null);
						}
						if (stats.isDirectory()) {
							allDirNames.push(files[i]);
						} else {
							allFileNames.push(files[i]);
						}
						iterator(i + 1);
					});
				})(0);
			});
		} else {
			callback("该路径不存在，请检查！！", null);
		}
	});
}

var createPathInfo = function(allDirNames, allFileNames) {
	var pathInfo = {
		"allDirNames" : allDirNames,
		"allFileNames" : allFileNames
	};
	return pathInfo;
}