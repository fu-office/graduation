(function(){
	function submit(){}
	
	function toggleNewAddrSection(flag){
		flag ? $('.new-addr').removeClass('hide') : $('.new-addr').addClass('hide');
	}
	
		
	function updateAddr(data){
		return Ajax.json({
			url: 'clientAddr/save',
			data: data,
			success: function(data){
				$.tipBox({
					message: '更新成功',
					type: 'tip'
				});
			}
		});
	}
	// events 
	$('#new-addr-btn').bind('click', function(){
		toggleNewAddrSection(true);
	});
	$('#addr_submit').bind('click', function(){
		var $div = $('.new-addr');
		$div.find('select').blur();
		if (0 == $div.find('.error').length) {
			var data = {};
			$div.find('select').each(function(){
				data[this.name] = this.value;
			});
			updateAddr(data);
		}
	});
}());