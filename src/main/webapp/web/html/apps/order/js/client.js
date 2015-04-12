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
		 CLIENT_IMPORT : "customer/import.json",
		 CLIENT_SEARCH : "customer/search.json",
		 CLIENT_DELETE : "customer/deleteById.json",
		 CLIENT_SAVE : "customer/save.json",
		 CLIENT_EXPORT : "customer/export.json",
		 AREA_IMPORT : "area/import.json",
		 AREA_EXPORT : "area/export.json",
		 AREA_SEARCH : "area/search.json",
		 AREA_DELETE : "area/delete.json",
		 AREA_SAVE_OR_UPDATE : "area/saveOrUpdate.json",
		 AREA_PARAMS : "area/findAll.json",
		 GIFT_SAVE : "gift/save.json",
		 GIFT_IMPORT : "gift/import.json",
		 GIFT_EXPORT : "gift/export.json",
		 GIFT_SEARCH : "gift/search.json",
		 GIFT_DELETE : "gift/delete.json"
	},Client = {
			$m : $("#client"),
			init : function(){
				var _self = this;
				this._bindEvent();
				this._initParams();
				var $list = this.$m.find(".list");
				$list.grid({
					single : true,
					height : 400,
					columns : [{title:"编号",field:"id",width:80},
					{title : "登记日期", field : "registerDate",width:120, formatter : function(ui, data){
						return data.cell ? $.formatDate(new Date(data.cell), "yyyy-MM-dd") : "";
					}},
					{title : "卡号", field : "cardNum",width:150},
					{title : "姓名", field : "name",width:120},
					{title : "手机号", field : "phoneNumber",width:120},
					{title : "电话", field : "telNumber",width:120},
					{title : "联系地址", field : "address",width:350},
					{title : "邮编", field : "postCode",width:100},
					{title : "生日", field : "birthday",width:120, formatter : function(ui, data){
						return data.cell ? $.formatDate(new Date(data.cell), "yyyy-MM-dd") : "";
					}},
					{title : "区域", field : "prov-city",width:120,formatter : function(ui, data){
						var row = data.row;
						return row.province ? row.province + "-" + row.city : row.city; 
					}},
					{title : "专柜名", field : "shopName",width:120},
					{title : "操作", field : "opera",width:120, formatter : function(ui, data){
						return "<a href='javascript:void(0);' class='edit-link'> 修改 </a> " +
							"<a href='javascript:void(0);' class='delete-link'> 删除 </a>";
					}}],
					pagination : true,
					pager : {
						select : function(pageNum, pageSize){
							_self.search($.extend({}, {
								pageSize : pageSize,
								pageNumber : pageNum
							}, _self.$m.f2j()));
						}
					}
				});
				$list.find(".ui-tb").css({
					height : ""
				});
			},
			_initParams : function(){
				initProvCity();
			},
			deleteClient : function(data, fn){
				$.msg({
					type : "confirm",
					msg : "确定删除",
					ok : function(){
						$.ajaxJSON({
							url : URL.CLIENT_DELETE,
							data : data,
							success : function(d){
								$.msg("删除成功");
								fn && fn(d);
							}
						});
					}
				});
			},
			save : function(data, fn){
				$.ajaxJSON({
					url : URL.CLIENT_SAVE,
					data : data,
					success : function(d){
						$.msg("保存成功");
						fn && fn(d);
					}
				});
			},
			search : function(data){
				var $m = this.$m;
				$.ajaxJSON({
					url : URL.CLIENT_SEARCH,
					data : data,
					success : function(d){
						$m.find(".list").grid("loadData", {rows : d.list, total : d.count,
							currentPage : data.pageNumber || 1, pageSize : d.pageSize || 10});
					}
				});
			},
			editDialog : function(data){
				var $dialog = $("#client_add_dialog");
				$dialog.find(".ui-select-button").removeClass("error");
				$dialog.find("input").val("").removeClass("error");
				if (data.id) {
					data.birthday = data.birthday ? $.formatDate(new Date(data.birthday), "yyyy-MM-dd") : data.birthday;
					$dialog.dialog("option", {
						title : "更新"
					}).j2f(data);	
					$("#client_add_prov").select("trigger");
					$("#client_add_city").select("value", data.city);
				} else {
					$dialog.dialog("option", {
						title : "新增"
					});	
				}
				$dialog.dialog("open");
			},
			exportExcel : function(data){
				$.dlFile({
					url : URL.CLIENT_EXPORT,
					data : data,
					fileName : (data.province ? data.province : "") + (data.city ? data.city : "") + "客户名单.xls"
				});
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
	Gift = {
			$m : $("#gift"),
			init : function(){
				var _self = this;
				this._bindEvent();
				this.$m.find(".list").grid({
					height : 400,
					single : true,
					columns : [{title : "编号", field : "id", width : 80},
					{title : "姓名", field : "name"},
					{title : "联系电话", field : "phone"},
					{title : "登记日期", field : "date", formatter : function(ui, data){
						return $.formatDate(new Date(data.cell), "yyyy-MM-dd");
					}},
					{title : "操作", field : "opera", width : 100, formatter : function(ui, data){
						return "<a href='javascript:void(0);' class='edit-link'>修改</a>&nbsp;&nbsp;" + 
							"<a href='javascript:void(0);' class='delete-link'>删除</a>";
					}}],
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
					$.ajaxJSON({
						url : URL.GIFT_SAVE,
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
				var prefix = "";
				if (data.startDate && data.endDate) {
					prefix = data.startDate + "-" + data.endDate; 
				} else if (data.startDate) {
					prefix = data.startDate + "-至今";
				} else if (data.endDate){
					prefix = "截止" + data.endDate;
				}
				$.dlFile({
					url : URL.GIFT_EXPORT,
					data : data,
					fileName : prefix + "赠品.xls"
				});
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
					url : URL.GIFT_SEARCH,
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
	},
	Area = {
			$m : $("#area"),
			init : function(){
				var $m = this.$m,
					_self = this;
				this._bindEvent();
				$m.find(".list").grid({
					single : true,
					height : 400, 
					columns : [
					       {title : "编号", field : "id", width : 80},
					       {title : "省", field : "prov"},
				           {title : "市", field : "city"},
				           {title : "专柜状态", field : "shopState"},
				           {title : "操作", field : "opera", formatter : function(ui, data){
				        	   return "<a href='javascript:void(0);' data-index='" + data.index + 
				        	   	"' class='edit-link'>修改</a> &nbsp;&nbsp;<a href='javascript:void(0);' " +
				        	   	"class='delete-link' data-index='" + data.index + "'>删除</a>";
				           }}] 
				});
				$m.find(".list").find(".ui-tb").css({
					height : ""
				});
				$("#area_update_dialog").dialog({
					height : "auto",
					autoOpen : false,
					modal : true,
					width : 400,
					title : "更新"
				}).find(".submit-update").click(function(){
					_self.saveOrUpdate($("#area_update_dialog").f2j(), function(d){
						$("#area_update_dialog").dialog("close");
					});
				});
			},
			saveOrUpdate : function(area, fn){
				if (area.prov && area.city) {
					$.ajaxJSON({
						url : URL.AREA_SAVE_OR_UPDATE,
						data : area,
						success : function(d){
							$.msg("保存成功");
							fn && fn(d);
							initProvCity();
						}
					});
				} else {
					$.msg("请输入省，市");
				} 
			},
			search : function(data){
				var _self = this;
				$.ajaxJSON({
					url : URL.AREA_SEARCH,
					data : data,
					success : function(d){
						_self.$m.find(".list").grid("loadData", {
							rows : d.list
						});
					}
				});
			},
			exportExcel : function(data){
				$.dlFile({
					url : URL.AREA_EXPORT,
					data : data,
					fileName : "区域名单.xls"
				});
			},
			_bindEvent : function(){
				var $m = this.$m,
					_self = this;
				$m.on("click", ".btn", function(){
					var $this = $(this);
					if ($this.is(".search")) {
						_self.search($m.find(".fm-row").f2j());
					} else if ($this.is(".import")) {
						$("#area-excel").click();
					} else if ($this.is(".export")) {
						_self.exportExcel($m.find(".fm-row").f2j());
					} else if ($this.is(".add")) {
						_self.editArea({});
					}
				});
				$m.find(".list").on("click", "a", function(){
					var $link = $(this),
						index = $link.attr("data-index"),
						row = $m.find(".list").grid("getRows")[index]; 
					if ($link.is(".edit-link")) {
						_self.editArea(row);
					} else if ($link.is(".delete-link")) {
						$.msg({
							type : "confirm",
							msg : "确定删除？",
							ok : function(){
								_self.deleteArea(row.id, function(){
									$m.find(".list").grid("deleteRow", index);
									$m.find(".list").grid("reload");
								});
							}
						});
					}
				});
				$("#area-excel").change(function(){
					var file = this.files[0];
					$.ajaxMultiForm({
						url : URL.AREA_IMPORT,
						data : {
							file : file
						},
						success : function(d){
							$.msg("导入成功");
						}
					});
					$("#area-excel").val("");
				});
			},
			editArea : function(area){
				if (area.id) {
					$("#area_update_dialog").dialog("option", {
						title : "更新"
					}).find("input").prop("readonly", true);
				} else {
					$("#area_update_dialog").dialog("option", {
						title : "新建"
					}).find("input").prop("readonly", false);
				}
				$("#area_update_dialog").dialog("open").j2f(area);
			},
			deleteArea : function(id, fn){
				$.ajaxJSON({
					url : URL.AREA_DELETE,
					data : {
						id : id
					},
					success : function(d){
						$.msg("删除成功");
						fn && fn(d);
						initProvCity();
					}
				});
			}
	};
	// init 
	(function(){
		var m = {
				gift : Gift,
				client : Client,
				area : Area
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
	// comm function
	function initProvCity(){
		$.ajaxJSON({
			url : URL.AREA_PARAMS,
			data : {},
			success : function(d){
				var list = d.list, areaMap = {}, provMap = {"" : ""};
				for (var i = 0, len = list.length; i < len; i++) {
					var citys = areaMap[list[i].prov] || [{text : "", value : ""}];
					provMap[list[i].prov] = list[i].prov;
					citys.push({
						text : list[i].city,
						value : list[i].city
					});
					areaMap[list[i].prov] = citys;
				}
				$("#province").select("destroy").select({
					data : provMap,
					after : function(opt){
						$("#city").select("data", areaMap[opt.val] || {});
					}
				});
				$("#province").select("trigger");
				$("#client_add_prov").select("destroy").select({
					data : provMap,
					after : function(opt){
						$("#client_add_city").select("data", (areaMap[opt.val] && areaMap[opt.val].slice(1)) || {});
					}
				});
				$("#client_add_prov").select("trigger");
				$("#area_prov").select("destroy").select({
					data : provMap,
					after : function(opt){
						$("#area_city").select("data", areaMap[opt.val] || {});
					}
				});
				$("#area_prov").select("trigger");
			}
		});
	}
}());