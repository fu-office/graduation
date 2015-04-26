(function(){
	// getOrder
	var url = {
			GET_ORDER_BY_ID : 'order/findById.json',
			UPDATE_PAY_STATUS : 'order/updatePayStatus.json',
	};
	var id = app.getURLParams().orderId;;
	Ajax.json({
		url : url.GET_ORDER_BY_ID,
		data : {
			id : id
		},
		success : function(data){
			$('.id').text(data.id);
			$('.total').text(data.total);
		},
		fail: function(){
			
		}
	});
	$('#payBtn').click(function(){
		Ajax.json({
			url : url.UPDATE_PAY_STATUS,
			data : {
				payStatus: 1,
				id: id
			},
			success : function(data){
				$.tipBox({
					message: '支付成功!',
				});
				setTimeout(function(){
					location.href = "order-list.html?token=" + app.getURLParams().token;
				},300);
			},
			fail: function(){
				
			}
		});
	});
}());