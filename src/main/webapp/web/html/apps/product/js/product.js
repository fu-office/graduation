(function(){
	$.fn.j2f = function(obj){
		obj = obj || {};
		var $this = $(this);
		$this.find("input[name]").each(function(){
			this.value = obj[this.name] || "";
		});
		$this.find("select[name]").each(function(){
			$(this).select("value", obj[this.name]);
		});
	};
	var URL = {
			findAll : 'product/findAll.json',
			save    : 'product/save.json'
	},Client = {
			$m : $("#productContent"),
			init : function(){
				var _self = this;
				this._bindEvent();
				var $list = this.$m.find(".list");
				$list.grid({
					single : true,
					height : 400,
					columns : [{title:"编号",field:"id",width:100},
					{title : "产品名称", field : "productName"},
					{title : "价格", field : "price",width:120, type: 'text',
						onChange : function(data){
							_self.save(data.row);
						}}],
					editRow : true
				});
				$('#add_dialog').dialog({
					width : 320,
					height: 160,
					title : '添加新产品',
					autoOpen: false,
					close : function(){
						$('#add_dialog input').val('');
					}
				});
			},
			save : function(data, fn){
				$.ajaxJSON({
					url : URL.save,
					data : data,
					success : function(d){
						$.msg("保存成功");
						fn && fn(d);
					}
				});
			},
			search : function(){
				var $m = this.$m;
				$.ajaxJSON({
					url : URL.findAll,
					data : {},
					success : function(d){
						$m.find(".list").grid("loadData", {rows : d.datas});
					}
				});
			},
			addDialog : function(){
				$('#add_dialog').dialog('open');
			},
			_bindEvent : function(){
				var $m = this.$m,
					_self = this,
					$grid = $m.find(".list");
				$m.on("click", ".btn", function(){
					var $this = $(this);
					if ($this.is(".search")) {
						_self.search($m.f2j());
					} else if ($this.is(".add")) {
						_self.addDialog({});
					}
				});
				$('#add_dialog .btn').click(function(){
					var data = $('#add_dialog').f2j();
					if (data.price && data.productName) {
						_self.save(data);
						$('#add_dialog').dialog('close');
					}
				});
			}
	};
	Client.init();
}());