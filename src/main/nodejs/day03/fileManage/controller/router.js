// 這邊的路徑寫全了的原因是models下面暫時沒有寫package.json
var file = require("../models/file.js");
// 展示首頁
exports.showIndex = function(req, res) {
	// res.render("index", {
	// "albums":file.getAllAlbumns()
	// });

	// nodejs的所有東西都是異步的，内層函數不是return回來就行，
	// 而是調用外層函數提供的回調函數，把數據當作回調函數的參數來使用
	file.getAllDirectorys(function(err, dirs) {
		if (err) {
			res.send(err);
			return;
		}
		res.render("index", {
			"dirs" : dirs
		});
	});

}

// 顯示相冊
exports.showAlbum = function(req, res) {
	res.send("相冊：" + req.params["albumName"]);
}