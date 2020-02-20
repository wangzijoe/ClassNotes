//这个案例简单讲解http模块
//引用模块
var http = require("http");
var port = 80;

//创建一个服务器，回调函数表示接收到请求之后做的事情
var server = http.createServer(function(req,res){

	//设置头部
	res.writeHead(200,{"Content-Type":"application/json;charset=UTF8"});
	res.write("{\"status\":200}");
	res.end();
});

//监听端口
server.listen(port, function(){
  console.log("Server is running");
});