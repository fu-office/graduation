(function(){
	function submit(){}
	
	function toggleNewAddrSection(flag){
		flag ? $('.new-addr').removeClass('hide') : $('.new-addr').addClass('hide');
	}
	
		
	function updateAddr(data){
		return Ajax.json({
			url: 'address/save.json',
			data: data,
			success: function(data){
				$.tipBox({
					message: '更新成功',
					type: 'tip'
				});
			}
		});
	}
	function getClientAddress(){
		Ajax.json({
			url : 'address/client.json',
			success : function(data){
				if (data.id){
					
				} else {
					// need add new address
					toggleNewAddrSection(true);
				}
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
	// init
	getClientAddress();
	(function(){
		var html = '';
		for (var i = 1; i <= 54; i++) {
			html += '<option value="' + i + '">' + i + '</option>';
		}
		$('#department_list').html(html);
		html = '';
		for (i = 1; i < 10; i++) {
			html += '<option value="' + i + '">' + i + '</option>';
		}
		$('#floor_list').html(html);
	}());
}());