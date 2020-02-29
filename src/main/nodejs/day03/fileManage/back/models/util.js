/**
 * "/a//b//c" ==> "a/b/c"
 */
exports.pathBeauty = function(path) {
	var temp = [];
	var arr = path.split("/");
	for (var i = 0; i < arr.length; i++) {
		if (arr[i])
			temp.push(arr[i]);
	}
	return temp.join("/");
}