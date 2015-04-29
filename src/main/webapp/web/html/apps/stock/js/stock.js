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
	var stockType = {
		1 : '出库单',
		0 : '入库单'
	};
	var URL = {
			SEARCH_STOCK : 'stock/findAll.json',
			SEARCH_STOCK_BY_NAME : 'stock/findByProdName.json',
			STOCK_ORDER_SAVE : 'stock/saveStockOrder.json',
			STOCK_ORDER_SEARCH : 'stock/searchStockOrder.json'
	},Search = {
			$m : $("#search"),
			init : function(){
				var _self = this;
				this._bindEvent();
				var $list = this.$m.find(".list");
				$list.grid({
					single : true,
					height : 400,
					columns : [{title:"编号",field:"id"},
					           {title:"产品编号",field:"prodcutId"},
					           {title:"产品名称",field:"productName"},
					           {title:"数量",field:"number"}]
				});
				$list.find(".ui-tb").css({
					height : ""
				});
				this._initParams();
			},
			_initParams : function(data){
				var that = this;
				$.ajaxJSON({
					url: URL.SEARCH_STOCK,
					data : {},
					success : function(d){
						that.$m.find(".list").grid('loadData', d.datas);
					}
				});	
			},
			save : function(data, fn){
			},
			search : function(data){
				var $m = this.$m;
				$.ajaxJSON({
					url : URL.SEARCH_STOCK_BY_NAME,
					data : data,
					success : function(d){
						$m.find(".list").grid("loadData",{rows: d.datas});
					}});
			},
			editDialog : function(data){
			},
			exportExcel : function(data){
			},
			_bindEvent : function(){
				var $m = this.$m,
					_self = this,
					$grid = $m.find(".list");
				$m.on("click", ".btn", function(){
					var $this = $(this);
					if ($this.is(".search")) {
						_self.search($m.f2j());
					} else if ($this.is(".import")) {
						$("#client-excel").click();
					} else if ($this.is(".export")) {
						_self.exportExcel($m.f2j());
					} else if ($this.is(".add")) {
						_self.editDialog({});
					}
				});
				$grid.on("click", "a", function(){
					var $link = $(this),
						index = $link.parents("tr").attr("findex"),
						row = $grid.grid("getRow", index);
					if ($link.is(".edit-link")) {
						_self.editDialog(row);
					} else if ($link.is(".delete-link")) {
						_self.deleteClient(row, function(){
							$grid.grid("deleteRow", index);
							$grid.grid("reload");
						});
					}
				});
				$("#client-excel").change(function(){
					var file = this.files[0];
					$.ajaxMultiForm({
						url : URL.CLIENT_IMPORT,
						data : {
							file : file
						},
						success : function(d){
							$.msg("导入成功");
						}
					});
					$("#client-excel").val("");
				});
				var $dialog = $("#client_add_dialog");
				$dialog.dialog({
					width : 400,
					height : "auto",
					autoOpen : false,
					title : "",
					modal : true
				}).find(".submit-update").click(function(){
					if ($dialog.checkRequired()) {
						_self.save($dialog.f2j(), function(){
							$dialog.dialog("close");
						});
					}
				}).end().find(".date").datetimepicker();
			}
	},
	Stock = {
			$m : $("#inout"),
			init : function(){
				var _self = this;
				this._bindEvent();
				this.$m.find(".list").grid({
					height : 400,
					single : true,
					columns : [{title : "编号", field : "id", width : 80},
					{title : "登记日期", field : "createDate", formatter : function(ui, data){
						return $.formatDate(new Date(data.cell), "yyyy-MM-dd");
					}},
					{title : "产品编号", field : "productId"},
					{title : "产品名称", field : "productName"},
					{title : "类型", field : "stockType", width : 100,formatter: function(ui, data){
						return stockType[data.cell];
					}},
					{title : "数量", field : "num", width : 100},
					{title : "备注", field : "remark", width : 150}],
					pagination : true,
					pager : {
						select : function(pageNumber, pageSize){
							_self.search($.extend({},{
								pageSize : pageSize,
								pageNumber : pageNumber
							},_self.$m.f2j()));
						}
					}
				});
				this.$m.find(".list").find(".ui-tb").css({
					height : ""
				});
				$("#gift_add").dialog({
					autoOpen : false,
					height : "auto",
					width : 400,
					title : "登记",
					modal : true
				}).find(".btn").click(function(){
					var data = $("#gift_add").f2j();
					data.productName = $("#gift_add").find('select[name=productId] option:selected').text();
					$.ajaxJSON({
						url : URL.STOCK_ORDER_SAVE,
						data : data,
						success : function(d){
							$("#gift_add").dialog("close");
							$.msg("保存成功");
						}
					});
				});
				$("#gift_add_date").datetimepicker();
			},
			exportExcel : function(data){
			},
			_bindEvent : function(){
				var $m = this.$m,
					_self = this;
				$m.on("click", ".btn", function(){
					var $this = $(this);
					if ($this.is(".add")) {
						_self.editGift({});
					} else if ($this.is(".search")) {
						_self.search($m.f2j());
					} else if ($this.is(".import")) {
						$("#gift_excel").click();
					} else if ($this.is(".export")) {
						_self.exportExcel($m.f2j());
					}
				});
				$("#gift_excel").change(function(){
					var file = this.files[0];
					$.ajaxMultiForm({
						url : URL.GIFT_IMPORT,
						data : {
							file : file
						},
						success : function(){
							$.msg("导入成功");
						}
					});
					this.value = "";
				});
				$("#gift_start_date").datetimepicker({
					endDate : new Date(),
					onSelect : function(date){
						$("#gift_end_date").datetimepicker("setStartDate", date);
					}
				});
				$("#gift_end_date").datetimepicker({
					endDate : new Date(),
					onSelect : function(date){
						$("#gift_start_date").datetimepicker("setEndDate", date);
					}
				});
				$m.find(".list").on("click", "a", function() {
					var $link = $(this),
						index = $link.parents("tr[findex]").attr("findex"),
						row = $m.find(".list").grid("getRow", index);
					if ($link.is(".delete-link")) {
						$.msg({
							type : "confirm",
							msg : "确定删除",
							ok : function(){
								_self.deleteGift({
									id : row.id
								}, function(d){
									$m.find(".list").grid("deleteRow", index);
									$m.find(".list").grid("reload");
								});
							}
						});
					} else if ($link.is(".edit-link")) {
						_self.editGift(row);
					}
				});
			},
			editGift : function(data){
				$("#gift_add").dialog("open").find("input").val("");
				var $dialog = $("#gift_add");
				if (data.id) {
					data.date = data.date ? $.formatDate(new Date(data.date), "yyyy-MM-dd") : data.date;
					$dialog.j2f(data);
					$dialog.dialog("option", {
						title : "更新"
					});
				} else {
					$dialog.dialog("option", {
						title : "登记"
					});
					$dialog.find("input").val("").attr("readonly", false);
				}
				$dialog.dialog("open");
			},
			deleteGift : function(data, fn){
				$.ajaxJSON({
					url : URL.GIFT_DELETE,
					data : data,
					success : function(d){
						$.msg("删除成功");
						fn && fn(d);
					}
				});
			},
			search : function(data){
				var $m = this.$m;
				$.ajaxJSON({
					url : URL.STOCK_ORDER_SEARCH,
					data : data,
					success : function(d){
						$m.find(".list").grid("loadData", {
							rows : d.list,
							total : d.count,
							pageSize : d.pageSize,
							currentPage : d.pageNumber
						});
					}
				});
			}
	};
	// init 
	(function(){
		var m = {
				search : Search,
				inout : Stock
		};
		$(".tab").on("click", "li", function(){
			var name = $(this).attr("data-module");
			if (m[name].init) {
				m[name].init();
				m[name].init = false;
			} 
			$(".tab-cons").hide();
			$("#" + name).show();
			$(".tab").find("li").removeClass("tab-active");
			$(this).addClass("tab-active");
		});
		$(".tab").find("li").eq(0).click();
	})();
}());