var fs = require("fs");

exports.getAllDirectorys = function(callback) {
	fs.readdir("./uploads", function(err, files) {
		if (err) {
			throw err;
		}
		var allDirs = [];
		// 迭代器
		(function iterator(i) {
			if (i == files.length) {
				callback(allDirs);
				return;
			}
			fs.stat("./uploads", function(err, stats) {
				if (err) {
					throw err;
				}
				if (stats.isDirectory()) {
					allDirs.push(files[i]);
				}
				iterator(i + 1);
			});
		})(0);
	});
}