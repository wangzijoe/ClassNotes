var fs = require("fs");

exports.getAllDirectorys = function(callback) {
	fs.readdir("./uploads", function(err, files) {
		var allDirs = [];
		if (err) {
			callback("服務異常！！！", allDirs);
		}
		// 迭代器
		(function iterator(i) {
			if (i == files.length) {
				callback(null, allDirs);
				return;
			}
			fs.stat("./uploads/" + files[i], function(err, stats) {
				if (err) {
					callback("找不到 " + files[i], null);
				}
				if (stats.isDirectory()) {
					allDirs.push(files[i]);
				}
				iterator(i + 1);
			});
		})(0);
	});
}