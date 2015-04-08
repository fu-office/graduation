(function(){
	$('#regist_btn').bind('click', function(){
		$('input').trigger('blur');
		var data = {}, error = $('#login_form input.error').length > 0;
		if (!error) {
			$('#login_form input').each(function(){
				var $ipt = $(this);
				data[this.name] = $ipt.val();
			});
			if (data.password.length > 12 || data.password.length < 6) {
				$.tipBox({message : '密码长度为6-12位', type : 'warn'});
				$('input[name="password"]').addClass('error');
				return false;
			} else if (data.password != data.confirm) {
				$.tipBox({message : '确认密码不一致', type : 'error'});
				$('input[name="confirm"]').addClass('error');
				return false;
			}
			delete data.confirm;
			Ajax.json({
				url : 'sign_up.json',
				data : data,
				success : function(data){
					$.tipBox({message : '注册成功！', type : 'success'});
					location.href = './client/main.html?token=' + data.token;
				},
				fail : function(data){
					$.tipBox({message : '该学号已注册！请直接登录', type : 'error'});
				}
			});
		}
	});
	// mark error
	$('input').on('blur', function(){
		var $ipt = $(this); 
		if ($ipt.hasClass('required') && !$ipt.val()) {
			$ipt.addClass('error');
		} else {
			$ipt.removeClass('error');
		}
	});
}());