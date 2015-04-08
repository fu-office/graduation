(function(){
	$('#login_btn').bind('click', function(){
		var $btn = $(this);
		$('input').trigger('blur');
		var data = {}, error = $('#login_form input.error').length > 0;
		if (!error) {
			$('#login_form input').each(function(){
				var $ipt = $(this);
				data[this.name] = $ipt.val();
			});
			$btn.prop('disabled', true);
			Ajax.json({
				url : 'sign_in.json',
				data : data,
				success : function(data){
					$.tipBox({message : '登录成功！', type : 'success'});
					location.href = './client/main.html?token=' + data.token;
				},
				fail : function(data){
					$.tipBox({message : '账号密码不匹配', type : 'error'});
					$btn.prop('disabled', false);
				}
			});
		}
	});
}());