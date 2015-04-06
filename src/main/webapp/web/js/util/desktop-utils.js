(function(window, $) {
	window.ROOT = window.location.protocol + "//" + window.location.host + "/client/";
	window.token = "";
	/*
	 * 将Form数据转化为JSON对象
	 */
	var F2J = {
		FORM_PATTERN : /.+\[\d*\]+$/,
		generate : function(obj, name, value) {
			if (obj[name] === undefined) {
				obj[name] = value;
			} else {
				if (!obj[name].push) {
					obj[name] = [ obj[name] ];
				}
				obj[name].push(value);
			}
			return obj;
		},
		translate : function(arrs) {
			var obj = {};
			$.each(arrs, function() {
				if (this.name.indexOf('.') != -1) {
					var params = this.name.split('.'), //
					innerobj = obj;
					for ( var i = 0; i < params.length - 1; i++) {
						var index = -1, //
						name = params[i], //
						tobj;
						if (F2J.FORM_PATTERN.test(name)) {
							index = name.substring(name.indexOf('[') + 1, name.indexOf(']'));
							name = name.substring(0, name.indexOf('['));
						}
						tobj = innerobj[name];
						// 未定义，则初始化
						if (tobj === undefined) {
							if (-1 === index) {
								innerobj[name] = {};
							} else {
								innerobj[name] = [];
							}
						}
						if (-1 === index) {
							innerobj = innerobj[name];
						} else {
							if (!innerobj[name][index]) {
								innerobj[name][index] = {};
							}
							innerobj = innerobj[name][index];
						}
					}
					F2J.generate(innerobj, params[params.length - 1], this.value);
				} else {
					F2J.generate(obj, this.name, this.value);
				}
			});
			return obj;
		}
	};
	$.fn.f2j = function(opts) {
		var $this = $(this), //
		$els = $this.find('input[name]:enabled, select[name]:enabled, textarea[name]:enabled'), //
		arrs = [];
		opts = {
			trim : true
		};

		while ($els.length > 0) {
			var $el = $els.eq(0);
			if ($el.is('input[type=text]') || $el.is('input[type=hidden]') || $el.is('textarea')) {
				arrs.push({
					name : $el.attr('name'),
					value : $.trim($el.val())
				});
				$els = $els.not($el);
			} else if ($el.is('input[type=checkbox]')) {
				var name = $el.attr('name'), //
				$checkbox = $els.filter('[name="' + name + '"]'), //
				$checked = $checkbox.filter(':checked');
				vals = [];
				for ( var i = 0; i < $checked.length; i++) {
					vals.push($checked.eq(i).val());
				}
				arrs.push({
					name : name,
					value : vals
				});
				$els = $els.not($checkbox);
			} else if ($el.is('input[type=radio]')) {
				var name = $el.attr('name'), //
				$radio = $els.filter('[name="' + name + '"]');
				arrs.push({
					name : name,
					value : $radio.filter(':checked').val()
				});
				$els = $els.not($radio);
			} else if ($el.is('select')) {
				arrs.push({
					name : $el.attr('name'),
					value : $el.val()
				});
				$els = $els.not($el);
			} else {
				console.log('解析错误', $els);
				$els = $els.not($el);
			}
		}
		return F2J.translate(arrs);
	};

	$.fn.j2f = function() {

	};

	{
		$.fn.mask = function(options) {
			options = options == undefined ? true : options;
			var $this = $(this);
			if (options === true) {
				$this.addClass('ui-loading');
			} else {
				$this.removeClass('ui-loading');
			}
			return $this;
		};
	}
	
	/**
	 * 将Form数据转化为JSON字符串
	 */
	$.fn.f2s = function() {
		return JSON.stringify($(this).f2j());
	};
	var CN_ZH_PATTERN = /[\u4E00-\u9FBF]/;
	/**
	 * @param date(Date)
	 *            	(Date对象) 需要格式化的时间 
	 *            
	 * @param ptn(string)           
	 * 				格式化标准，如yyyy-MM-dd hh:mm:ss
	 *            	支持
	 *            	1. yyyy 	年份
	 *            	2. MM 		月份
	 *            	3. dd		日期
	 *            	4. hh		小时
	 *            	5. mm		分
	 *            	6. ss		秒
	 *            	7. SSS		毫秒
	 *            	8. q		季度
	 * 
	 * @return(string)
	 * 				返回格式化字符串
	 */
	$.formatDate = function(date, ptn) {
		if (typeof date === 'string') {
			ptn = date;
			date = new Date();
		}
		ptn = ptn || "yyyy年MM月dd日 hh:mm";
		date = date || new Date();
		var dt = {
			// 年份
			"yyyy" : date.getFullYear(),
			// 月份
			"MM" : date.getMonth() + 1,
			// 日
			"dd" : date.getDate(),
			// 小时
			"hh" : date.getHours(),
			// 分
			"mm" : date.getMinutes(),
			// 秒
			"ss" : date.getSeconds(),
			// 季度
			"q" : Math.floor((date.getMonth() + 3) / 3),
			// 毫秒
			"SSS" : date.getMilliseconds()
		};
		dt.mm = padLeft(dt.mm, 2, '0');
		if (!CN_ZH_PATTERN.test(ptn)) {
			dt.MM = padLeft(dt.MM, 2, '0');
			dt.dd = padLeft(dt.dd, 2, '0');
		}
		for ( var key in dt) {
			ptn = ptn.replace(key, dt[key]);
		}
		return ptn;
	};
	function padLeft(s, l, c) {
		s = '' + s;
		if (l < s.length)
			return s;
		else
			return Array(l - s.length + 1).join(c || ' ') + s;
	}
	
	/**
	 * @param qstr(string)
	 *            	需要获取参数的URL
	 *            
	 * @return(JSON)
	 * 				返回参数值JSON对象
	 */
	$.getQueryParams = function(qstr) {
		qstr = qstr || location.search;
		var index = qstr.indexOf('?'), //
		params = {};
		if (index != -1) {
			qstr = qstr.substring(index + 1);
		}
		if (qstr) {
			qstr = qstr.split('&');
			for ( var i in qstr) {
				var kv = qstr[i].split('=');
				params[kv[0]] = kv[1] || '';
			}
		}
		return params;
	};

	/**
	 * @param opts(string/JSON)
	 *            	String	:	提示信息内容（默认为提示）
	 *				JSON	:	生成提示框参数
	 *            		包括：
	 *						1.	msg		:	信息内容
	 *						2.	type	:	提示框类型，可选：alert/confirm/info/error
	 *						3.	title 	:	提示框标题，默认：警告/确认/提示/错误
	 * @return(jQuery Object)
	 *				返回对话框的jQuery对象
	 */
	$.msg = function(opts) {
		if (!$.isPlainObject(opts)) {
			opts = {
				msg : opts
			};
		}
		opts = $.extend({
			buttons : opts.type === 'confirm' ? {
				"确定" : function() {
					opts.ok && opts.ok.apply(this);
					$(this).dialog("destroy");
				},
				"取消" : function() {
					if($.isFunction(opts.cancel)){
						opts.cancel.apply(this);
					}
					$(this).dialog("destroy");
				}
			} : {
				"确定" : function() {
					opts.ok && opts.ok.apply(this);
					$(this).dialog("destroy");
				}
			}
		}, opts);
		if (opts.type == 'alert') {
			opts = $.extend({
				dialogClass : 'nf-dialog nf-alert',
				title : '警告'
			}, opts);
		} else if (opts.type == 'confirm') {
			opts = $.extend({
				dialogClass : 'nf-dialog nf-confirm',
				title : '确认',
				modal: true
			}, opts);
		} else if (opts.type == 'error') {
			opts = $.extend({
				dialogClass : 'nf-dialog nf-error',
				title : '错误'
			}, opts);
		} else {
			opts = $.extend({
				dialogClass : 'nf-dialog nf-info',
				title : '提示'
			}, opts);
		}
		var nopts = $.extend({}, {
			title : '警告',
			resizable : false,
			minHeight : 14,
			width: opts.width,
			modal : opts.modal,
			buttons : {
				"确定" : function() {
					opts.ok && opts.ok.apply(this);
					$(this).dialog("destroy");
				}
			}
		}, opts);
		return $('<div>').html(opts.msg).dialog(nopts);
	};

	var Error = {
		generateList : function(list) {
			var $ul = $('<ul class="error-list">');
			for ( var i = 0; i < list.length; i++) {
				var $li = $('<li>'), error = list[i];
				$li.text(error.message === '' ? '未知错误' : error.message);
				$ul.append($li);
			}
			$ul.hide().appendTo('body');
			return $ul.dialog({
				dialogClass : 'nf-dialog nf-error',
				title : '错误',
				minHeight : 14,
				minWidth : $ul.outerWidth() * 1.4,
				close : function() {
					$(this).dialog("destroy").remove();
				},
				buttons : {
					"确定" : function() {
						$(this).dialog("destroy").remove();
					}
				}
			});
		}
	};
	
	var AJAX = {
		removeMask : function(opts) {
			var $el = $(opts.el);
			if ($el.is('body')) {
				$el.removeClass('ui-loading-' + opts.identify);
				AJAX.handler && clearTimeout(AJAX.handler);
				AJAX.handler = setTimeout(function() {
					if (!$el.is('[class*="ui-loading-"]')) {
						$('body').mask(false);
					}
				}, 200);
			}else{
				$el.mask(false);
			}
		},
		addMask : function(opts) {
			var $el = $(opts.el).mask();
			if ($el.is('body')) {
				$el.addClass('ui-loading-' + opts.identify);
			}
		}
	};
	
	/**
	 * @param options(JSON)
	 *            JSON : AJAX参数(基于jQuery.ajax) 扩展属性包括： 
	 *				1. data			:	发送数据（可以是JSON字符串或JSON对象,会自动转换为字符串）
	 *				2. name			: 	定义此次请求的名称（错误提示）
	 *				3. fail			：	返回data.success为false时执行
	 *				4. success		：	返回data.success为true时执行
	 *            	5. url			：	不以http或者https开头时，自动填充服务器地址（配置在desktop-utils.js中），否则直接访问url
	 *            	6. type			：	默认为POST
	 *            	7. contentType	： 	默认为JSON
	 *              8. hideError	:	隐藏错误信息
	 *              9. hideShadow   :   隐藏阴影背景
	 * @return(jQuery Ajax Object) 返回jQuery Ajax对象
	 */
	$.ajaxJSON = function(options) {
		var opts = {
			el : 'body',
			identify : $.now()
		};
		opts = $.extend(opts, options, {
			error : function(jqXHR, tStatus, errorThrown) {
				AJAX.removeMask(opts);
				options.error && options.error.apply(this, arguments);
				//如果是401，表明token过期需要重新登录
				if(jqXHR.status == '401') {
					return;
				}
				// TODO abort类型重新处理
				if (!opts.hideError) {
					$.msg({
						type : 'error',
						msg : '调用服务[' + (options.name || '') + ']失败，'
								+ ('timeout' === tStatus ? 
									'请求超时' : ('abort' === tStatus 
										? '求情已取消' : ('parsererror' === tStatus 
											? '解析错误' : ('parsererror' === tStatus 
												? '服务器错误' : '服务器错误[' + jqXHR.status + ']'))))
					});
				}
			},
			success : function(data) {
				AJAX.removeMask(opts);
				if (data.success) {
					options.success && options.success.apply(this, arguments);
				} else {
					options.fail && options.fail.apply(this, arguments);
					if (!opts.hideError) {
						Error.generateList(data.errors);
					}
				}
			},
			dataType : 'json',
			type : options.type ? options.type : 'POST',
			contentType : "application/json; charset=UTF-8",
			url : (options.url.indexOf('http://') && options.url.indexOf('https://')) ? window.ROOT + options.url : options.url,
			data : (options.data && $.isPlainObject(options.data)) ? JSON.stringify(options.data) : options.data
		});
		!opts.hideShadow && AJAX.addMask(opts);
		return $.ajax(opts);
	};
	var antiEscapeAND = /&amp;/ig,
		antiEscapeGT = /&gt;/ig,
		antiEscapeLT = /&lt;/ig,
		scriptTag = /<\/?script/ig,
		AntiEscape = {
			escapeScript : function(content){
				 return content.replace(scriptTag, function(str){
						return "&lt;" + str.substring(1);
					});
			},
			antiEscapeTag : function(content){
				content = content.replace(antiEscapeAND, "&");
				content = content.replace(antiEscapeGT, ">");
				content = content.replace(antiEscapeLT, "<");
				return AntiEscape.escapeScript(content);
			}
	};
	$.antiEscapeTag = AntiEscape.antiEscapeTag;
})(window, jQuery);
