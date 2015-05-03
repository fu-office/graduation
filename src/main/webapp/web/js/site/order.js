(function(){
	function submit(){}
	
	function toHistory(){
		location.href = './order-list.html?token=' + app.getURLParams().token;
	}
	
	function toggleNewAddrSection(flag){
		flag ? $('.new-addr').removeClass('hide') : $('.new-addr').addClass('hide');
	}
	
	function submitOrder(data) {
		data.address = data.area + '区' + data.department + '幢' + data.floor + '楼' + data.room;
		delete data.id;
		Ajax.json({
			url: 'order/saveOrUpdate.json',
			data : data,
			success: function(result){
				$.tipBox({
					type: 'success',
					message: '提交成功',
					close : function(){
						toHistory();
					}
				});
			},
			fail: function(result){
				var error = result.errors[0] || {};
				$.tipBox({
					type: 'error',
					message : error.message
				});
			}
		});
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
				$('.last-addr').j2f(data);
				$('.last-addr-con span').each(function(){
					$(this).text(data[this.className]);
				});
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
	$('#order_submit').bind('click', function(){
		$('.required').trigger('blur');
		if ($('.error').length == 0) {
			var items = [];
			var total = +$('#total').text();
			if (0 == total) {
				$.tipBox({
					type: 'error',
					message: '请选择商品'
				});
				return false;
			}
			$('#prodList').find('.order-item').each(function(){
				var $li = $(this);
				items.push({
					price : $li.find('.item-price').attr('data-value'),
					productId : $li.find('.item-id').attr('data-value'),
					num : $li.find('.item-num').val(),
					productName : $li.find('.item-name').attr('data-value')
				});
			});
			submitOrder($.extend({items: items, total: total}, $('.deliver-box').f2j(), $('.last-addr').f2j()));
		}
	});
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
	$('#hist-orders').bind('click', function(){
		toHistory();
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
		html = '';
		for (i = 0; i < 3; i++) {
			var day = new Date();
			day.setDate(day.getDate() + i);
			html += '<option value="' + Date.format(day) + '">' + Date.format(day) + '</option>';
		}
		$('#day_list').html(html);
		var mo = '09:00-12:00';
		var noon = '13:00-17:00';
		var now = new Date();
		var deliverySet = {};
		if (now.getHours() >= 17) {
			deliverySet[Date.format(now)] = [];
		} else if (now.getHours() > 10) {
			deliverySet[Date.format(now)] = [noon];  
		} else {
			deliverySet[Date.format(now)] = [mo, noon];
		}
		for (var i = 0; i < 2; i++){
			now.setDate(now.getDate() + 1);
			deliverySet[Date.format(now)] = [mo, noon];
		}
		$('#day_list').bind('change', function(){
			var list = deliverySet[$(this).val()],
				html = '';
			for (var i = 0, len = list.length; i < len; i++) {
				html += '<option value="' + list[i] + '">' + list[i] + '</option>';
			}
			$('#time_list').html(html);
		}).change();
		$('#prodList').on('change', '.item-num', function(){
			var total = 0;
			$('#prodList .order-item').each(function(){
				total += +$(this).find('.item-num').val() * $(this).find('.item-price').attr('data-value');
			});
			$('#total').text(total.toFixed(2));
		});
	}());
	
}());