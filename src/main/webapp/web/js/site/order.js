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
				toggleNewAddrSection(false);
				$('#new_addr_id').val(data.id);
			},
			fail: function(data){
				$.tipBox({
					message: data.errors[0].message,
					type: 'error'
				});
			}
		});
	}
	function getClientAddress(){
		Ajax.json({
			url : 'address/client.json',
			success : function(data){
				if (data.id){
					$('input, select').each(function(){
						if (this.name) {
							$(this).val(data[this.name]);
						}
					});
					$('.last-addr-con span').each(function(){
						$(this).text(data[this.className]);
					});
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
		$div.find('select, input').blur();
		if (0 == $div.find('.error').length) {
			var data = {};
			$div.find('select, input').each(function(){
				data[this.name] = this.value;
			});
			updateAddr(data);
		}
	});
	$('#cancel_submit').bind('click', function(){
		toggleNewAddrSection(false);
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