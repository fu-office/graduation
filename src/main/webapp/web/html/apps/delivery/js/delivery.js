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
			search : 'delivery/search.json',
			save   : 'delivery/add.json'
	},Client = {
			$m : $("#productContent"),
			init : function(){
				var _self = this;
				this._bindEvent();
				var $list = this.$m.find(".list");
				$list.grid({
					single : true,
					height : 400,
					columns : [{title:"编号",field:"id"},
					           {title:"姓名",field:"name"},
					           {title:"性别",field:"sex"}]
				});
				$('#add_dialog').dialog({
					title : '添加新水工',
					width: 320,
					height: 160,
					autoOpen: false
				});
			},
			save : function(data, fn){
				$.ajaxJSON({
					url : URL.save,
					data : data,
					success : function(d){
						$.msg("保存成功");
						$('#add_dialog').dialog('close');
						fn && fn(d);
					}
				});
			},
			search : function(data){
				var $m = this.$m;
				$.ajaxJSON({
					url : URL.search,
					data : data,
					success : function(d){
						$m.find(".list").grid("loadData", {rows : d.datas});
					}
				});
			},
			addDialog : function(data){
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
					if (data.name && data.sex) {
						_self.save(data);
					}
				});
			}
	};
	Client.init();
}());