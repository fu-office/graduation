(function(){
	var URL = {
		adminLogin : 'admin/login.json'	
	};
	$('#loginBtn').bind('click', function(){
		var data = {};
		$('.login-box').find('input').each(function(){
			data[this.name] = $.trim(this.value);
		});
		if (data.name == 'admin' && data.password == '123456') {
			location.href = './desktop.html';
		} else {
			$('#errMsg').show();
		}
	});
}());