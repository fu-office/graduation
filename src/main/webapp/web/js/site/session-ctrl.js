(function(){
	var query = app.getURLParams();
	if (!query.token || query.token.length != 64) {
		$.tipBox({message: '登录超时，请重新登录', type: 'warn'});
		setTimeout(function(){
			app.gotoLogin();
		}, 1500);
		throw {
			message: 'no session'
		};
	}
}());