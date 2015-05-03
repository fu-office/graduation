(function(){
	// getOrder
	var orderList = [];
	var method = {
			0 : '货到付款',
			1 : '在线支付',
			2 : '赊账'
	};
	var status = {
			0 : '未接单',
			1 : '已接单',
			2 : '配送中',
			3 : '完成',
			4 : '取消',
			5 : '关闭',
			6 : '删除'
	};
	var payStatus = {
			0 : '未支付',
			1 : '已支付'
	};
	var url = {
			GET_ORDER : 'order/getOrderByClient.json',
			CANCEL_ORDER : 'order/cancel.json'
	};
	Ajax.json({
		url : url.GET_ORDER,
		data : {},
		success : function(data){
			orderList = data.list;
			var $ul = $('#orderList');
			var html = '',
				item;
			for (var i = 0, len = orderList.length; i < len; i++) {
				item = orderList[i];
				html += '<li data-index="' + i + '" class="order-item ' + (i % 2 == 0 ? 'odd' : '') + '"><div class="order-date"><span>' + Date.format(new Date(item.date), 'yyyy-MM-dd') 
				+ '</span></div><div class="order-content"><div class="content-row"><label>编号</label><span>'+
				item.id + '</span></div><div class="content-row"><label>地址</label><span>' 
				+ item.address + '</span></div><div class="content-row"><label>联系电话</label><span>' 
				+ item.phone + '</span></div><div class="content-row"><label>配送时间</label><span>' 
				+ Date.format(new Date(item.deliveryDate)) + '&nbsp;'+ item.deliveryTime + '</span></div><div class="content-row"><label>支付方式</label><span>'
				+ method[item.payMethod] + '</span></div><div class="content-row"><label>订单状态</label><span>'
				+ payStatus[item.payStatus] + '&nbsp;' + status[item.status] + '</span></div><div class="content-row"><label>金额</label><span>￥' 
				+ item.total + '</span></div><div class="ctrl-row">' + (0 == item.payStatus && item.status == 0 ? '<a href="javascript:/*fzl*/" class="pay-link">支付</a> <a href="javascript:/*fzl*/" class="cancel-link">取消</a>' : '') + '</div></div></li>';
			}
			$ul.html(html);
		},
		fail: function(){
			
		}
	});
	$('#orderList').on('click', '.pay-link', function(){
		var index = $(this).closest('li').attr('data-index');
		location.href = './order-pay.html?token=' + app.getURLParams().token + '&orderId=' + orderList[index].id;
	});
	$('#orderList').on('click', '.cancel-link', function(){
		var index = $(this).closest('li').attr('data-index'),
			order = orderList[index];
		order.status = 4;
		Ajax.json({
			url : url.CANCEL_ORDER,
			data : order,
			success : function(){
				location.reload();
			}
		});
		return false;
	});
	$('#new-orders').click(function(){
		location.href = "./main.html?token=" + app.getURLParams().token;
	})
}());