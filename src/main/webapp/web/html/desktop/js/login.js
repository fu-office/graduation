(function(){
	var URL = {
		adminLogin : 'admin/login.json'	
	};
	$('#loginBtn').bind('click', function(){
		var data = {};
		$('.login-box').find('input').each(function(){
			data[this.name] = $.trim(this.value);
		});
		if (data.name && data.password) {
			Ajax.json({
				
			});
		}
	});
}());