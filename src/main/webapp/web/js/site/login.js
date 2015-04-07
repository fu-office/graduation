(function(){
	$('#regist_btn').bind('click', function(){
		var data = {};
		$('#login_form input').each(function(){
			var $ipt = $(this);
			data[this.name] = $ipt.val();
			if ($ipt.hasClass('required') && !$ipt.val()) {
				$ipt.addClass('error');
			}
		});
	});
}());