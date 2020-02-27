exports.error = function(status, msg) {
	var errMsg = {
		"status" : status,
		"msg" : msg,
	}
	return errMsg;
}

exports.ok = function(data) {
	return {
		"status" : 200,
		"msg" : data,
	}
}